package de.zottig.clean.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByHouseholdId(Long long1);

	@Override
	Optional<Task> findById(Long long1);

	List<Task> findCurrentByHouseholdIdAndNextRunBefore(Long long1,
			LocalDateTime localDateTime);
	
	List<Task> findCurrentByHouseholdIdAndNextRunBeforeAndAssignedTo(Long long1,
			LocalDateTime localDateTime, Member assignedTo);

	List<Task> findAllByInitial(int i);
}