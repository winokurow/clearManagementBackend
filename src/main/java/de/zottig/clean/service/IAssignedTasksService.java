package de.zottig.clean.service;

import java.util.List;

import de.zottig.clean.persistence.model.AssignedTask;

public interface IAssignedTasksService {

	List<AssignedTask> getTasksByHousehold(String email);
}