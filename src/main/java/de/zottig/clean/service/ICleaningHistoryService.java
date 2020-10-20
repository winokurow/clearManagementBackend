package de.zottig.clean.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import de.zottig.clean.persistence.model.CleaningHistory;

public interface ICleaningHistoryService {

	public CleaningHistory submitHistory(CleaningHistory history);

	public List<CleaningHistory> getMemberHistory(String email, LocalDateTime dateFrom, LocalDateTime dateTo);

	public List<CleaningHistory> getHouseholdHistory(String email);
}