package de.zottig.clean.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.zottig.clean.persistence.model.CleaningHistory;

public interface CleaningHistoryRepository
		extends
			JpaRepository<CleaningHistory, Long> {

	List<CleaningHistory> findByMemberIdAndTimestampBetween(Long memberId, LocalDateTime dateFrom, LocalDateTime dateTo);
}