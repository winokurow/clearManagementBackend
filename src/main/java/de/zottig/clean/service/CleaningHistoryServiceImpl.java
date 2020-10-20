package de.zottig.clean.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.zottig.clean.persistence.dao.CleaningHistoryRepository;
import de.zottig.clean.persistence.model.CleaningHistory;
import de.zottig.clean.persistence.model.Member;

@Service
@Transactional
public class CleaningHistoryServiceImpl implements ICleaningHistoryService {

	@Autowired
	private CleaningHistoryRepository repository;

	@Autowired
	private IMemberService memberService;

	/**
	 * Submit history
	 * 
	 * @param task
	 *            - history to submit
	 * @return submitted history
	 */
	@Override
	public CleaningHistory submitHistory(CleaningHistory history) {
		history = repository.save(history);
		return history;
	}

	@Override
	public List<CleaningHistory> getMemberHistory(String email, LocalDateTime dateFrom, LocalDateTime dateTo) {
		Member member = memberService.findUserByEmail(email);
		if (dateTo == null) {
			dateTo = LocalDateTime.now(); 
		}
		if (dateFrom == null) {
			dateFrom = LocalDateTime.now().with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());
		}
		return repository.findByMemberIdAndTimestampBetween(member.getId(), dateFrom, dateTo);
	}

	/**
	 * Get history list for household
	 * 
	 * @param email
	 *            - member email
	 * @return history list
	 */
	@Override
	public List<CleaningHistory> getHouseholdHistory(String email) {
		Long householdId = memberService.findUserByEmail(email).getHousehold()
				.getId();
		List<Member> members = memberService
				.getMembersByHouseholdId(householdId);
		LocalDateTime dateTo = null;
		LocalDateTime dateFrom = null;
		if (dateTo == null) {
			dateTo = LocalDateTime.now(); 
		}
		if (dateFrom == null) {
			dateFrom = LocalDateTime.now().with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());
		}
		
		List<CleaningHistory> history = new ArrayList<>();
		for (Member member : members) {
			history.addAll(repository.findByMemberIdAndTimestampBetween(member.getId(), dateFrom, dateTo));
		}
		return history;
	}

}