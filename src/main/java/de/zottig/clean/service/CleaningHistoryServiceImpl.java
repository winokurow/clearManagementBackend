package de.zottig.clean.service;

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

	/**
	 * Get history list for member
	 * 
	 * @param email
	 *            - user email
	 * @return history list
	 */
	@Override
	public List<CleaningHistory> getMemberHistory(String email) {
		Member member = memberService.findUserByEmail(email);
		return repository.findByMemberId(member.getId());
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
		List<CleaningHistory> history = new ArrayList<>();
		for (Member member : members) {
			history.addAll(repository.findByMemberId(member.getId()));
		}
		return history;
	}

}