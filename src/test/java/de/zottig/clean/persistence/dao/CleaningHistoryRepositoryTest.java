package de.zottig.clean.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import de.zottig.clean.persistence.model.CleaningHistory;
import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.persistence.model.Member;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CleaningHistoryRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CleaningHistoryRepository cleaningHistoryRepository;

	@Test
	public void whenFindByMemberId_thenReturnMember() {
		// given

		List<CleaningHistory> histories = new ArrayList<>();

		Household household = new Household();
		household.setName("test1");

		Member member = new Member();
		member.setEmail("test@test.de");
		member.setFirstName("testFirstName");
		member.setLastName("testLastName");
		member.setHousehold(household);

		CleaningHistory cleaningHistory1 = new CleaningHistory();
		cleaningHistory1.setAction("SUBMIT");
		cleaningHistory1.setComplexity(1);
		cleaningHistory1.setMember(member);
		cleaningHistory1.setTaskname("clean1");
		cleaningHistory1.setTimestamp(LocalDateTime.now());

		histories.add(cleaningHistory1);

		CleaningHistory cleaningHistory2 = new CleaningHistory();
		cleaningHistory2.setAction("SUBMIT");
		cleaningHistory2.setComplexity(2);
		cleaningHistory2.setMember(member);
		cleaningHistory2.setTaskname("clean2");
		cleaningHistory2.setTimestamp(LocalDateTime.now().minusDays(2));

		entityManager.persistAndFlush(cleaningHistory1);
		entityManager.persistAndFlush(cleaningHistory2);
		entityManager.flush();

		// when
		LocalDateTime dateTo = LocalDateTime.now();
		LocalDateTime dateFrom = LocalDateTime.now()
				.with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay());
		List<CleaningHistory> found = cleaningHistoryRepository
				.findByMemberIdAndTimestampBetween(
						cleaningHistory1.getMember().getId(), dateFrom, dateTo);
		// then
		assertThat(found.size()).isEqualTo(1);

		assertThat(found.get(0).getAction())
				.isEqualTo(cleaningHistory1.getAction());
		assertThat(found.get(0).getComplexity())
				.isEqualTo(cleaningHistory1.getComplexity());
		assertThat(found.get(0).getMember().getFirstName())
				.isEqualTo(cleaningHistory1.getMember().getFirstName());
		assertThat(found.get(0).getTaskname())
				.isEqualTo(cleaningHistory1.getTaskname());
		assertThat(found.get(0).getTimestamp())
				.isEqualTo(cleaningHistory1.getTimestamp());

	}

}
