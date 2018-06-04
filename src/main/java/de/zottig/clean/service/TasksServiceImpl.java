package de.zottig.clean.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
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

	@Override
	public List<Task> getСurrentTasksByHousehold(String email) {
		Member member = memberService.findUserByEmail(email);
		Calendar cal = Calendar.getInstance();
		return repository.findCurrentByHouseholdIdAndNextRunBefore(
				member.getHousehold().getId(), LocalDateTime.now());
	}

	@Override
	public Task submitTask(Task task) {
		Duration duration = Duration.parse(task.getShedule());
		LocalDateTime now = LocalDateTime.now();
		now = now.plus(duration);
		task.setNextRun(now);
		task = repository.save(task);
		return task;

	}

	@Override
	public Task getTaskById(long id) {
		return repository.getOne(id);
	}

}