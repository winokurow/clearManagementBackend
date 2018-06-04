package de.zottig.clean.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.Task;
import de.zottig.clean.service.IMemberService;
import de.zottig.clean.service.ITasksService;
import de.zottig.clean.web.util.GenericResponse;

@RestController
@RequestMapping("api")
public class TasksController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITasksService TasksService;

	@Autowired
	private IMemberService memberService;

	public TasksController() {
		super();
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "tasks", method = RequestMethod.GET)
	public ResponseEntity<?> get() {
		LOGGER.info("*******************");
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String user = authentication.getName().toString();
		List<Task> tasks = TasksService.getСurrentTasksByHousehold(user);
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "task/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> submitTask(@PathVariable Long id) {
		LOGGER.info("*******************1");
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String user = authentication.getName().toString();
		Member member = memberService.findUserByEmail(user);
		Task task = TasksService.getTaskById(id);
		System.out.println(user);
		System.out.println(task);
		System.out.println(task.getHousehold());
		System.out.println(task.getHousehold().getId());
		System.out.println(member);
		System.out.println(member.getHousehold());
		System.out.println(member.getHousehold().getId());
		if ((task == null) || (task.getHousehold().getId() != member
				.getHousehold().getId())) {
			GenericResponse response = new GenericResponse("Task not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		task = TasksService.submitTask(task);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
}