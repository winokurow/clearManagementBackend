package de.zottig.clean.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.HouseholdRepository;
import de.zottig.clean.persistence.dao.MemberRepository;
import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.error.UserAlreadyExistException;

@Service(value = "userService")
@Transactional
public class MemberServiceImpl implements IMemberService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemberRepository repository;

	@Autowired
	private HouseholdRepository householdRepository;

	public static String APP_NAME = "Clean Manager";

	// API

	@Override
	public void registerNewUserAccount(final GroupDto accountDto,
			String householdName) {
		if (emailExist(accountDto.getEmail())) {
			throw new UserAlreadyExistException(
					"There is an account with that email adress: "
							+ accountDto.getEmail());
		}
		final Member user = new Member();

		user.setFirstName(accountDto.getFirstname());
		user.setLastName(accountDto.getLastname());
		// user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());

		Household household = householdRepository.findOneByName(householdName);
		user.setHousehold(household);
		repository.save(user);
	}

	@Override
	public void deleteUser(final Member user) {
		repository.delete(user);
	}

	@Override
	public Member findUserByEmail(final String email) {
		return repository.findOneByEmail(email);
	}

	@Override
	public void changeUserPassword(final Member user, final String password) {
		// user.setPassword(passwordEncoder.encode(password));
		repository.save(user);
	}

	@Override
	public boolean emailExist(final String email) {
		return repository.findOneByEmail(email) != null;
	}

	@Override
	public List<Member> getUsers() {
		return repository.findAll();
	}
}