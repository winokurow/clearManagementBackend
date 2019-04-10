package de.zottig.clean.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.persistence.model.Member;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class MemberRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void whenFindOneByEmail_thenReturnMember() {
		// given
		Household household = new Household();
		household.setName("test1");

		Member member = new Member();
		member.setEmail("test@test.de");
		member.setFirstName("testFirstName");
		member.setLastName("testLastName");
		member.setHousehold(household);

		entityManager.persistAndFlush(member);

		// when
		Member found = memberRepository.findOneByEmail(member.getEmail());

		// then
		assertThat(found.getEmail()).isEqualTo(member.getEmail());
		assertThat(found.getFirstName()).isEqualTo(member.getFirstName());
		assertThat(found.getLastName()).isEqualTo(member.getLastName());
		assertThat(found.getHousehold().getName())
				.isEqualTo(member.getHousehold().getName());
	}
	
	@Test
	public void whenFindHouseholdId_thenReturnListOfMember() {
		// given
		Household household = new Household();
		household.setName("test1");
		
		// household.setId(1L);
		entityManager.persistAndFlush(household);
		Member member1 = new Member();
		member1.setEmail("test1@test.email.de");
		member1.setFirstName("Vorname1");
		member1.setLastName("Nachname1");
		member1.setHousehold(household);
		entityManager.persistAndFlush(member1);

		Member member2 = new Member();
		member2.setEmail("test2@test.email.de");
		member2.setFirstName("Vorname2");
		member2.setLastName("Nachname2");
		member2.setHousehold(household);
		entityManager.persistAndFlush(member2);

		// when
		List<Member> found = memberRepository.findByHouseholdId(household.getId());

		// then
		assertThat(found.size()).isEqualTo(2);
		
		assertThat(found.get(0).getEmail()).isEqualTo(member1.getEmail());
		assertThat(found.get(0).getFirstName()).isEqualTo(member1.getFirstName());
		assertThat(found.get(0).getHousehold()).isEqualTo(member1.getHousehold());
		assertThat(found.get(0).getId()).isEqualTo(member1.getId());
		assertThat(found.get(0).getLastName()).isEqualTo(member1.getLastName());
		
		assertThat(found.get(1).getEmail()).isEqualTo(member2.getEmail());
		assertThat(found.get(1).getFirstName()).isEqualTo(member2.getFirstName());
		assertThat(found.get(1).getHousehold()).isEqualTo(member2.getHousehold());
		assertThat(found.get(1).getId()).isEqualTo(member2.getId());
		assertThat(found.get(1).getLastName()).isEqualTo(member2.getLastName());
	}

}
