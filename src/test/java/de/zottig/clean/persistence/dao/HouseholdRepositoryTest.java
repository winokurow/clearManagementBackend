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
public class HouseholdRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private HouseholdRepository householdRepository;

	@Test
	public void whenFindOneByName_thenReturnHousehold() {
		// given
		Household household = new Household();
		household.setName("test1");
		// household.setId(1L);
		entityManager.persistAndFlush(household);
		entityManager.flush();

		// when
		Household found = householdRepository
				.findOneByName(household.getName());

		// then
		assertThat(found.getName()).isEqualTo(household.getName());
	}

}
