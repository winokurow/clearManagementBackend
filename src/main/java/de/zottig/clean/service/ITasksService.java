package de.zottig.clean.service;

import java.util.List;

import de.zottig.clean.persistence.model.Task;

public interface ITasksService {

	List<Task> getTasks(String email, boolean isOnlyCurrent);

	Task submitTask(Task task);

	Task getTaskById(long id);

	Task updateTask(Task task);
}