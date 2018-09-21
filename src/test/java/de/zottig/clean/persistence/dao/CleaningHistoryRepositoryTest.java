package de.zottig.clean.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
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
		cleaningHistory2.setTimestamp(LocalDateTime.now());

		entityManager.persistAndFlush(cleaningHistory1);
		entityManager.persistAndFlush(cleaningHistory2);
		entityManager.flush();

		// when
		List<CleaningHistory> found = cleaningHistoryRepository
				.findByMemberId(cleaningHistory1.getMember().getId());

		// then
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

		assertThat(found.get(1).getAction())
				.isEqualTo(cleaningHistory2.getAction());
		assertThat(found.get(1).getComplexity())
				.isEqualTo(cleaningHistory2.getComplexity());
		assertThat(found.get(1).getMember().getFirstName())
				.isEqualTo(cleaningHistory2.getMember().getFirstName());
		assertThat(found.get(1).getTaskname())
				.isEqualTo(cleaningHistory2.getTaskname());
		assertThat(found.get(1).getTimestamp())
				.isEqualTo(cleaningHistory2.getTimestamp());

	}

}
