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
public class RoleRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoleRepository roleRepository;

	User user1 = new User();
	User user2 = new User();
	List<User> users = new ArrayList<>();;
	Role role = new Role();

	@Before
	public void init() {
		// given
		user1.setEmail("test@test1");
		user1.setPassword("1234");

		user2.setEmail("test@test2");
		user2.setPassword("1235");

		users.add(user1);
		users.add(user2);

		role.setName("tester");
		role.setUsers(users);

		entityManager.persistAndFlush(role);
		entityManager.flush();
	}

	@Test
	public void whenFindByName_thenReturnRole() {

		// when
		Role found = roleRepository.findByName(role.getName());

		// then
		assertThat(found.getName()).isEqualTo(role.getName());
		assertThat(found.getId()).isEqualTo(role.getId());
		assertThat(found.getUsers().get(0).getEmail())
				.isEqualTo(user1.getEmail());
		assertThat(found.getUsers().get(1).getEmail())
				.isEqualTo(user2.getEmail());
	}

	@Test
	public void whenDelete_thenReturnNull() {

		// when
		roleRepository.delete(role);
		Role found = roleRepository.findByName(role.getName());

		// then
		assertThat(found).isNull();
	}

}
