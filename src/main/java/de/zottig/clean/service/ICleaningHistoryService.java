package de.zottig.clean.service;

import java.util.List;

import de.zottig.clean.persistence.model.CleaningHistory;

public interface ICleaningHistoryService {

	public CleaningHistory submitHistory(CleaningHistory history);

	public List<CleaningHistory> getMemberHistory(String email);

	public List<CleaningHistory> getHouseholdHistory(String email);
}