package de.zottig.clean.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findOneByEmail(String email);

	@Override
	void delete(User user);
}