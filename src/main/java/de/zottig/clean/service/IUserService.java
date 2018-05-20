package de.zottig.clean.service;

import java.util.List;

import de.zottig.clean.persistence.model.User;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.error.UserAlreadyExistException;

public interface IUserService {

	void registerNewUserAccount(GroupDto accountDto) throws UserAlreadyExistException;

	void deleteUser(User user);

	User findUserByEmail(String email);

	User getUserByID(long id);

	void changeUserPassword(User user, String password);

	List<User> getUsers();

	User getUser(Long id);

	boolean emailExist(String email);
}