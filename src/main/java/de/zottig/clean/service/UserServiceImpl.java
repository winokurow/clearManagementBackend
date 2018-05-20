package de.zottig.clean.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.RoleRepository;
import de.zottig.clean.persistence.dao.UserRepository;
import de.zottig.clean.persistence.model.Role;
import de.zottig.clean.persistence.model.User;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.error.UserAlreadyExistException;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static String APP_NAME = "Clean Manager";

	// API

	@Override
	public void registerNewUserAccount(final GroupDto accountDto) {
		if (emailExist(accountDto.getEmail())) {
			throw new UserAlreadyExistException("There is an account with that email adress: " + accountDto.getEmail());
		}
		final User user = new User();

		user.setFirstName(accountDto.getFirstname());
		user.setLastName(accountDto.getLastname());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());

		if (accountDto.getIsAdmin()) {
			Role admin = roleRepository.findByName("member");
			Role member = roleRepository.findByName("administrator");
			List<Role> roles = new ArrayList<>();
			roles.add(admin);
			roles.add(member);
			user.setRoles(roles);
		} else {
			user.setRoles(Arrays.asList(roleRepository.findByName("member")));

		}
		repository.save(user);
	}

	@Override
	public void deleteUser(final User user) {
		repository.delete(user);
	}

	@Override
	public User findUserByEmail(final String email) {
		return repository.findOneByEmail(email);
	}

	@Override
	public User getUserByID(final long id) {
		return repository.findOne(id);
	}

	@Override
	public void changeUserPassword(final User user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		repository.save(user);
	}

	@Override
	public boolean emailExist(final String email) {
		return repository.findOneByEmail(email) != null;
	}

	@Override
	public List<User> getUsers() {
		return repository.findAll();
	}

	@Override
	public User getUser(Long id) {
		return repository.findOne(id);
	}
}