package de.zottig.clean.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.TaskRepository;
import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.Task;

@Service
@Transactional
public class TasksServiceImpl implements ITasksService {

	@Autowired
	private TaskRepository repository;

	@Autowired
	private IMemberService memberService;

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
			return repository
					.findCurrentByHouseholdId(member.getHousehold().getId());
		}
	}

	/**
	 * Submit a task
	 * 
	 * @param task
	 *            - task to submit
	 * @return submitted task
	 */
	@Override
	public Task submitTask(Task task) {
		Duration duration = Duration.parse(task.getShedule());
		LocalDateTime now = LocalDateTime.now();
		now = now.plus(duration);
		task.setNextRun(now);
		task = repository.save(task);
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

}