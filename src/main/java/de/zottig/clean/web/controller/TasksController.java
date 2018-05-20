package de.zottig.clean.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	@RequestMapping(value = "tasks/household/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable Long id) {
		return new ResponseEntity<>(AssignedTasksService.getTasks(id), HttpStatus.OK);
	}
}