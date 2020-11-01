package de.zottig.clean.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger logger = LoggerFactory.getLogger(getClass());
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
	public List<CleaningHistory> getHouseholdHistory(String email, LocalDateTime dateFrom, LocalDateTime dateTo) {
		Long householdId = memberService.findUserByEmail(email).getHousehold()
				.getId();
		List<Member> members = memberService
				.getMembersByHouseholdId(householdId);

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