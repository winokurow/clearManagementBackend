package de.zottig.clean.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByHouseholdId(Long long1);

	@Override
	Task findOne(Long long1);

	List<Task> findCurrentByHouseholdIdAndNextRunBefore(Long long1,
			LocalDateTime localDateTime);
}