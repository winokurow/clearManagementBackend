package de.zottig.clean.service;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

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
					member.getHousehold().getId(), LocalDateTime.now());
		} else {
			return repository.findByHouseholdId(member.getHousehold().getId());
		}
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
		System.out.println(task.getShedule());
		Period duration = Period.parse(task.getShedule());
		LocalDateTime now = LocalDateTime.now();
		now = now.plus(duration);
		task.setNextRun(now);
		task = repository.save(task);

		CleaningHistory history = new CleaningHistory();
		history.setTaskname(task.getName());
		history.setTimestamp(LocalDateTime.now());
		history.setAction("SUBMIT");
		history.setComplexity(task.getComplexity());
		history.setMember(member);
		historyService.submitHistory(history);

		return task;

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
		repository.delete(id);
	}

}