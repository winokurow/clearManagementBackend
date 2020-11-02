package de.zottig.clean.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.TaskRepository;
import de.zottig.clean.persistence.model.CleaningHistory;
import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.Task;

@Service
@Transactional
public class TasksServiceImpl implements ITasksService {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private ICleaningHistoryService historyService;

	/**
	 * Get tasks list for household
	 * 
	 * @param email
	 *            - user email
	 * @param isOnlyCurrent
	 *            - if only current tasks would be searched
	 */
	@Override
	public List<Task> getTasks(String email, boolean isOnlyCurrent) {
		Member member = memberService.findUserByEmail(email);
		if (isOnlyCurrent) {
			return repository.findCurrentByHouseholdIdAndNextRunBefore(
					member.getHousehold().getId(), LocalDateTime.now().with(LocalTime.of(23, 0)));
		} else {
			return repository.findByHouseholdId(member.getHousehold().getId());
		}
	}

	/**
	 * Get task patterns list
	 * 
	 */
	@Override
	public List<Task> getTaskPatterns() {
		return repository.findAllByInitial(1);
	}

	/**
	 * Submit a task
	 * 
	 * @param task
	 *            - task to submit
	 * @param member
	 *            - member that submit the tasks
	 * @return submitted task
	 */
	@Override
	public Task submitTask(Task task, Member member) {
		Period duration = Period.parse(task.getShedule());
		LocalDateTime now = LocalDateTime.now();
		now = now.plus(duration);
		task.setNextRun(now);
		task.setAssignedTo(null);
		task = repository.save(task);

		CleaningHistory history = new CleaningHistory();
		history.setTaskname(task.getGroupname() + ". " + task.getName());
		history.setTimestamp(LocalDateTime.now());
		history.setAction("SUBMIT");
		history.setComplexity(task.getComplexity());
		history.setMember(member);
		historyService.submitHistory(history);

		return task;

	}

	/**
	 * Assign random tasks to member
	 * 
	 * @param count
	 *            - how many tasks
	 */
	@Override
	public void assignTasks(int memberId, int count) {
		Member member = memberService.findUserById(memberId);
		List<Task> tasks = repository.findCurrentByHouseholdIdAndNextRunBefore(
					member.getHousehold().getId(), LocalDateTime.now().with(LocalTime.of(23, 0)));
		List<Long> weigtedList = new ArrayList<>();
		for (Task task : tasks) {
			for (int i = 0; i < 6-task.getComplexity(); i++) {
				weigtedList.add(task.getId());
			}
		}
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			int position = rand.nextInt(weigtedList.size());
			Task task = this.getTaskById(weigtedList.get(position));
			task.setAssignedTo(member);
			weigtedList.removeIf(n -> Objects.equals(n, weigtedList.get(position)));
		}
	}
	
	/**
	 * Find a task
	 * 
	 * @param id
	 *            - id to search
	 * @return task that was found
	 */
	@Override
	public Task getTaskById(long id) {
		return repository.getOne(id);
	}

	/**
	 * Update a task
	 * 
	 * @param task
	 *            - updated task
	 * @return updated task
	 */
	@Override
	public Task updateTask(Task task) {
		return repository.saveAndFlush(task);
	}

	/**
	 * Insert a task
	 * 
	 * @param task
	 *            - new task
	 * @return inserted task
	 */
	@Override
	public Task insertTask(Task task) {
		return repository.saveAndFlush(task);
	}

	/**
	 * Delete a task
	 * 
	 * @param task
	 *            - task to delete
	 */
	@Override
	public void deleteTask(long id) {
		repository.deleteById(id);
	}

}