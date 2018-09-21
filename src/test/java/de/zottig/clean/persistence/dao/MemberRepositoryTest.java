package de.zottig.clean.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

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
		entityManager.flush();

		// when
		Member found = memberRepository.findOneByEmail(member.getEmail());

		// then
		assertThat(found.getEmail()).isEqualTo(member.getEmail());
		assertThat(found.getFirstName()).isEqualTo(member.getFirstName());
		assertThat(found.getLastName()).isEqualTo(member.getLastName());
		assertThat(found.getHousehold().getName())
				.isEqualTo(member.getHousehold().getName());
	}

}
