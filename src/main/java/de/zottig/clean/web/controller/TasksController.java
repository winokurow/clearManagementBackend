package de.zottig.clean.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.AssignedTask;
import de.zottig.clean.service.IAssignedTasksService;

@RestController
@RequestMapping("api")
public class TasksController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IAssignedTasksService AssignedTasksService;

	public TasksController() {
		super();
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "tasks/household/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable Long id) {
		LOGGER.info("*******************");
		List<AssignedTask> tasks = AssignedTasksService.getTasks(id);
		LOGGER.info("Complexity =" + tasks.get(0).getTask().getComplexity());
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
}