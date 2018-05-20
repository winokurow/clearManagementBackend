package de.zottig.clean.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.AssignedTaskRepository;
import de.zottig.clean.persistence.model.AssignedTask;

@Service
@Transactional
public class AssignedTasksServiceImpl implements IAssignedTasksService {

	@Autowired
	private AssignedTaskRepository repository;

	@Override
	public List<AssignedTask> getTasks(Long householdid) {
		return repository.findAll();
	}

}