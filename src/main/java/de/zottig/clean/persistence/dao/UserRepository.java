package de.zottig.clean.persistence.dao;

import org.springframework.data.repository.CrudRepository;

import de.zottig.clean.persistence.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);
}
