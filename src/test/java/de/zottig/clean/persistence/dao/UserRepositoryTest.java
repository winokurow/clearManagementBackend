package de.zottig.clean.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import de.zottig.clean.persistence.model.Role;
import de.zottig.clean.persistence.model.User;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	User user1 = new User();
	User user2 = new User();
	List<Role> roles1 = new ArrayList<>();
	List<Role> roles2 = new ArrayList<>();
	Role role1 = new Role();
	Role role2 = new Role();
	@Before
	public void init() {
		// given
		role1.setName("tester1");
		roles1.add(role1);

		role2.setName("tester2");
		roles2.add(role2);

		user1.setEmail("test1@test1");
		user1.setPassword("1234");
		user1.setRoles(roles1);

		user2.setEmail("test2@test2");
		user2.setPassword("1235");
		user2.setRoles(roles2);

		entityManager.persistAndFlush(role1);
		entityManager.persistAndFlush(role2);

		entityManager.persistAndFlush(user1);
		entityManager.persistAndFlush(user2);
		entityManager.flush();
	}

	@Test
	public void whenFindByEmail_thenReturnUser() {

		// when
		User found = userRepository.findByEmail(user1.getEmail());

		// then
		assertThat(found.getEmail()).isEqualTo(user1.getEmail());
		assertThat(found.getPassword()).isEqualTo(user1.getPassword());
		assertThat(found.getId()).isEqualTo(user1.getId());
		assertThat(found.getRoles().size()).isEqualTo(1);
		assertThat(found.getRoles().get(0).getName())
				.isEqualTo(user1.getRoles().get(0).getName());
	}

}
