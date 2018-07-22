package de.zottig.clean.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findOneByEmail(String email);

	List<Member> findByHouseholdId(Long long1);
}