package de.zottig.clean.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.Household;

public interface HouseholdRepository extends JpaRepository<Household, Long> {

	Household findOneByName(String name);
}