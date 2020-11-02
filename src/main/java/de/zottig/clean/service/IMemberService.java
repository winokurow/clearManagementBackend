package de.zottig.clean.service;

import java.util.List;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.error.UserAlreadyExistException;

public interface IMemberService {

	void registerNewUserAccount(GroupDto accountDto, String householdName)
			throws UserAlreadyExistException;

	void deleteUser(Member user);

	Member findUserByEmail(String email);

	void changeUserPassword(Member user, String password);

	List<Member> getUsers();

	boolean emailExist(String email);

	public List<Member> getMembersByHouseholdId(Long householdId);

	Member findUserById(int memberId);
}