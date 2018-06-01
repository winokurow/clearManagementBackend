package de.zottig.clean.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.AssignedTaskRepository;
import de.zottig.clean.persistence.model.AssignedTask;
import de.zottig.clean.persistence.model.Member;

@Service
@Transactional
public class AssignedTasksServiceImpl implements IAssignedTasksService {

	@Autowired
	private AssignedTaskRepository repository;

	@Autowired
	private IMemberService memberService;

	@Override
	public List<AssignedTask> getTasksByHousehold(String email) {
		Member member = memberService.findUserByEmail(email);
		return repository.findByHouseholdId(member.getHousehold().getId());
	}

}