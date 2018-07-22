package de.zottig.clean.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.Task;
import de.zottig.clean.service.IHouseholdService;
import de.zottig.clean.service.IMemberService;
import de.zottig.clean.service.ITasksService;
import de.zottig.clean.web.dto.ErrorDto;
import de.zottig.clean.web.dto.TaskDto;
import de.zottig.clean.web.util.GenericResponse;

@RestController
@RequestMapping("api")
public class TasksController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITasksService tasksService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IHouseholdService householdService;

	public TasksController() {
		super();
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "tasks", method = RequestMethod.GET)
	public ResponseEntity<?> get(
			@RequestParam("show_only_current") boolean showOnlyCurrent) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String email = authentication.getName().toString();
		List<Task> tasks = tasksService.getTasks(email, showOnlyCurrent);
		List<TaskDto> taskDtos = new ArrayList<>();
		for (Task task : tasks) {
			taskDtos.add(convertToDto(task));
		}
		return new ResponseEntity<>(taskDtos, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "/tasks/task/{id}/submit", method = RequestMethod.POST)
	public ResponseEntity<?> submitTask(@PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String user = authentication.getName();
		Member member = memberService.findUserByEmail(user);
		Task task = tasksService.getTaskById(id);

		if ((task == null) || (task.getHousehold().getId() != member
				.getHousehold().getId())) {
			GenericResponse response = new GenericResponse("Task not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		task = tasksService.submitTask(task, member);
		return new ResponseEntity<>(task, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "/tasks/task/{id}/change", method = RequestMethod.POST)
	public ResponseEntity<?> changeTask(@PathVariable Long id,
			@Validated @RequestBody final TaskDto updatedTaskDto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			logger.info("Error");
			return new ResponseEntity<>(
					new ErrorDto(bindingResult.getAllErrors().stream()
							.map(x -> x.getDefaultMessage())
							.collect(Collectors.joining(","))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String user = authentication.getName();
		Member member = memberService.findUserByEmail(user);

		Task oldTask = tasksService.getTaskById(id);

		if ((oldTask == null) || (oldTask.getHousehold().getId() != member
				.getHousehold().getId())) {
			GenericResponse response = new GenericResponse("Task not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		Task updatedTask = convertToEntity(updatedTaskDto);
		updatedTask.setId(id);
		updatedTask.setHousehold(oldTask.getHousehold());
		Task task = tasksService.updateTask(updatedTask);
		return new ResponseEntity<>(convertToDto(task), HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "/tasks/task/create", method = RequestMethod.PUT)
	public ResponseEntity<?> createTask(
			@Validated @RequestBody final TaskDto newTaskDto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			logger.info("Error");
			return new ResponseEntity<>(
					new ErrorDto(bindingResult.getAllErrors().stream()
							.map(x -> x.getDefaultMessage())
							.collect(Collectors.joining(","))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String user = authentication.getName();
		Member member = memberService.findUserByEmail(user);

		Task newTask = convertToEntity(newTaskDto);
		newTask.setHousehold(member.getHousehold());
		Task task = tasksService.insertTask(newTask);
		return new ResponseEntity<>(convertToDto(task), HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "/tasks/task/{id}/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTask(@PathVariable Long id) {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String user = authentication.getName();
		Member member = memberService.findUserByEmail(user);

		Task oldTask = tasksService.getTaskById(id);

		if ((oldTask == null) || (oldTask.getHousehold().getId() != member
				.getHousehold().getId())) {
			GenericResponse response = new GenericResponse("Task not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		tasksService.deleteTask(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private TaskDto convertToDto(Task task) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(task, TaskDto.class);
	}

	private Task convertToEntity(TaskDto taskDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(taskDto, Task.class);
	}
}