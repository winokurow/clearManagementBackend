package de.zottig.clean.service;

import java.util.List;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.Task;

public interface ITasksService {

	public List<Task> getTasks(String email, boolean isOnlyCurrent, boolean showOnlyAssignedToMe);

	public List<Task> getTaskPatterns();

	public Task submitTask(Task task, Member member);

	public Task getTaskById(long id);

	public void deleteTask(long id);

	public Task updateTask(Task task);

	public Task insertTask(Task task);
	
	public void assignTasks(int member, int minimalTotalComplexity);
}