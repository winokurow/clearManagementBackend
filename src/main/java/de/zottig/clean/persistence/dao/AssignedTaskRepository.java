package de.zottig.clean.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.AssignedTask;

public interface AssignedTaskRepository extends JpaRepository<AssignedTask, Long> {

}