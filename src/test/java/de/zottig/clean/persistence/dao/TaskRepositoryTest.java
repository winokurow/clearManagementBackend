package de.zottig.clean.persistence.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.persistence.model.Task;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TaskRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TaskRepository taskRepository;

	Task task1 = new Task();
	Task task2 = new Task();
	Task task3 = new Task();
	Task task4 = new Task();
	Task task5 = new Task();
	Household household1 = new Household();
	Household household2 = new Household();

	@Before
	public void init() {
		household1.setName("family1");
		household2.setName("family2");

		task1.setComplexity(1);
		task1.setDescription("descr1");
		task1.setGroupname("group1");
		task1.setHousehold(household1);
		task1.setName("Task1");
		task1.setNextRun(LocalDateTime.now().plus(10, ChronoUnit.DAYS));
		task1.setPriority(1);
		task1.setShedule("shedule1");
		task1.setInitial(0);
		task1.setRoom("room1");

		entityManager.persistAndFlush(task1);

		task2.setComplexity(2);
		task2.setDescription("descr2");
		task2.setGroupname("group2");
		task2.setHousehold(household1);
		task2.setName("Task2");
		task2.setNextRun(LocalDateTime.now().plus(10, ChronoUnit.DAYS));
		task2.setPriority(2);
		task2.setShedule("shedule2");
		task2.setInitial(0);
		task2.setRoom("room2");

		entityManager.persistAndFlush(task2);

		task3.setComplexity(3);
		task3.setDescription("descr3");
		task3.setGroupname("group3");
		task3.setHousehold(household2);
		task3.setName("Task3");
		task3.setNextRun(LocalDateTime.now().minus(10, ChronoUnit.DAYS));
		task3.setPriority(3);
		task3.setShedule("shedule3");
		task3.setInitial(1);
		task3.setRoom("room3");

		entityManager.persistAndFlush(task3);

		task4.setComplexity(4);
		task4.setDescription("descr4");
		task4.setGroupname("group4");
		task4.setHousehold(household2);
		task4.setName("Task4");
		task4.setNextRun(LocalDateTime.now().minus(10, ChronoUnit.DAYS));
		task4.setPriority(4);
		task4.setShedule("shedule4");
		task4.setInitial(1);
		task4.setRoom("room4");

		entityManager.persistAndFlush(task4);

		task5.setComplexity(5);
		task5.setDescription("descr5");
		task5.setGroupname("group5");
		task5.setHousehold(household2);
		task5.setName("Task5");
		task5.setNextRun(LocalDateTime.now().plus(10, ChronoUnit.DAYS));
		task5.setPriority(5);
		task5.setShedule("shedule5");
		task5.setInitial(0);
		task5.setRoom("room5");

		entityManager.persistAndFlush(task5);

	}

	@Test
	public void whenFindByHouseholdId_thenReturnTask() {

		// when
		List<Task> found = taskRepository
				.findByHouseholdId(task1.getHousehold().getId());

		// then
		assertThat(found.size()).isEqualTo(2);
		assertThat(found.get(0).getComplexity())
				.isEqualTo(task1.getComplexity());
		assertThat(found.get(0).getDescription())
				.isEqualTo(task1.getDescription());
		assertThat(found.get(0).getGroupname()).isEqualTo(task1.getGroupname());
		assertThat(found.get(0).getHousehold().getName())
				.isEqualTo(task1.getHousehold().getName());
		assertThat(found.get(0).getName()).isEqualTo(task1.getName());
		assertThat(found.get(0).getId()).isEqualTo(task1.getId());
		assertThat(found.get(0).getName()).isEqualTo(task1.getName());
		assertThat(found.get(0).getNextRun()).isEqualTo(task1.getNextRun());
		assertThat(found.get(0).getPriority()).isEqualTo(task1.getPriority());
		assertThat(found.get(0).getShedule()).isEqualTo(task1.getShedule());
		assertThat(found.get(0).getInitial()).isEqualTo(task1.getInitial());
		assertThat(found.get(0).getRoom()).isEqualTo(task1.getRoom());

		assertThat(found.get(1).getComplexity())
				.isEqualTo(task2.getComplexity());
		assertThat(found.get(1).getDescription())
				.isEqualTo(task2.getDescription());
		assertThat(found.get(1).getGroupname()).isEqualTo(task2.getGroupname());
		assertThat(found.get(1).getHousehold().getName())
				.isEqualTo(task2.getHousehold().getName());
		assertThat(found.get(1).getName()).isEqualTo(task2.getName());
		assertThat(found.get(1).getId()).isEqualTo(task2.getId());
		assertThat(found.get(1).getName()).isEqualTo(task2.getName());
		assertThat(found.get(1).getNextRun()).isEqualTo(task2.getNextRun());
		assertThat(found.get(1).getPriority()).isEqualTo(task2.getPriority());
		assertThat(found.get(1).getShedule()).isEqualTo(task2.getShedule());
		assertThat(found.get(1).getInitial()).isEqualTo(task2.getInitial());
		assertThat(found.get(1).getRoom()).isEqualTo(task2.getRoom());
	}

	@Test
	public void whenFindOne_thenReturnTask() {

		// when
		Task found = taskRepository.findById(task1.getId()).orElse(null);

		// then
		assertThat(found.getComplexity()).isEqualTo(task1.getComplexity());
		assertThat(found.getDescription()).isEqualTo(task1.getDescription());
		assertThat(found.getGroupname()).isEqualTo(task1.getGroupname());
		assertThat(found.getHousehold().getName())
				.isEqualTo(task1.getHousehold().getName());
		assertThat(found.getName()).isEqualTo(task1.getName());
		assertThat(found.getId()).isEqualTo(task1.getId());
		assertThat(found.getName()).isEqualTo(task1.getName());
		assertThat(found.getNextRun()).isEqualTo(task1.getNextRun());
		assertThat(found.getPriority()).isEqualTo(task1.getPriority());
		assertThat(found.getShedule()).isEqualTo(task1.getShedule());
		assertThat(found.getInitial()).isEqualTo(task1.getInitial());
		assertThat(found.getRoom()).isEqualTo(task1.getRoom());
	}

	@Test
	public void whenFindCurrentByHouseholdIdAndNextRunBefore_thenReturnTask() {

		// when
		List<Task> found = taskRepository
				.findCurrentByHouseholdIdAndNextRunBefore(
						task3.getHousehold().getId(), LocalDateTime.now());

		// then
		assertThat(found.size()).isEqualTo(2);
		assertThat(found.get(0).getComplexity())
				.isEqualTo(task3.getComplexity());
		assertThat(found.get(0).getDescription())
				.isEqualTo(task3.getDescription());
		assertThat(found.get(0).getGroupname()).isEqualTo(task3.getGroupname());
		assertThat(found.get(0).getHousehold().getName())
				.isEqualTo(task3.getHousehold().getName());
		assertThat(found.get(0).getName()).isEqualTo(task3.getName());
		assertThat(found.get(0).getId()).isEqualTo(task3.getId());
		assertThat(found.get(0).getName()).isEqualTo(task3.getName());
		assertThat(found.get(0).getNextRun()).isEqualTo(task3.getNextRun());
		assertThat(found.get(0).getPriority()).isEqualTo(task3.getPriority());
		assertThat(found.get(0).getShedule()).isEqualTo(task3.getShedule());
		assertThat(found.get(0).getInitial()).isEqualTo(task3.getInitial());
		assertThat(found.get(0).getRoom()).isEqualTo(task3.getRoom());

		assertThat(found.get(1).getComplexity())
				.isEqualTo(task4.getComplexity());
		assertThat(found.get(1).getDescription())
				.isEqualTo(task4.getDescription());
		assertThat(found.get(1).getGroupname()).isEqualTo(task4.getGroupname());
		assertThat(found.get(1).getHousehold().getName())
				.isEqualTo(task4.getHousehold().getName());
		assertThat(found.get(1).getName()).isEqualTo(task4.getName());
		assertThat(found.get(1).getId()).isEqualTo(task4.getId());
		assertThat(found.get(1).getName()).isEqualTo(task4.getName());
		assertThat(found.get(1).getNextRun()).isEqualTo(task4.getNextRun());
		assertThat(found.get(1).getPriority()).isEqualTo(task4.getPriority());
		assertThat(found.get(1).getShedule()).isEqualTo(task4.getShedule());
		assertThat(found.get(1).getInitial()).isEqualTo(task4.getInitial());
		assertThat(found.get(1).getRoom()).isEqualTo(task4.getRoom());
	}

	@Test
	public void whenFindAllByInitial_thenReturnTask() {

		// when
		List<Task> found = taskRepository.findAllByInitial(1);

		// then
		assertThat(found.size()).isEqualTo(2);
		assertThat(found.get(0).getComplexity())
				.isEqualTo(task3.getComplexity());
		assertThat(found.get(0).getDescription())
				.isEqualTo(task3.getDescription());
		assertThat(found.get(0).getGroupname()).isEqualTo(task3.getGroupname());
		assertThat(found.get(0).getHousehold().getName())
				.isEqualTo(task3.getHousehold().getName());
		assertThat(found.get(0).getName()).isEqualTo(task3.getName());
		assertThat(found.get(0).getId()).isEqualTo(task3.getId());
		assertThat(found.get(0).getName()).isEqualTo(task3.getName());
		assertThat(found.get(0).getNextRun()).isEqualTo(task3.getNextRun());
		assertThat(found.get(0).getPriority()).isEqualTo(task3.getPriority());
		assertThat(found.get(0).getShedule()).isEqualTo(task3.getShedule());
		assertThat(found.get(0).getInitial()).isEqualTo(task3.getInitial());
		assertThat(found.get(0).getRoom()).isEqualTo(task3.getRoom());

		assertThat(found.get(1).getComplexity())
				.isEqualTo(task4.getComplexity());
		assertThat(found.get(1).getDescription())
				.isEqualTo(task4.getDescription());
		assertThat(found.get(1).getGroupname()).isEqualTo(task4.getGroupname());
		assertThat(found.get(1).getHousehold().getName())
				.isEqualTo(task4.getHousehold().getName());
		assertThat(found.get(1).getName()).isEqualTo(task4.getName());
		assertThat(found.get(1).getId()).isEqualTo(task4.getId());
		assertThat(found.get(1).getName()).isEqualTo(task4.getName());
		assertThat(found.get(1).getNextRun()).isEqualTo(task4.getNextRun());
		assertThat(found.get(1).getPriority()).isEqualTo(task4.getPriority());
		assertThat(found.get(1).getShedule()).isEqualTo(task4.getShedule());
		assertThat(found.get(1).getInitial()).isEqualTo(task4.getInitial());
		assertThat(found.get(1).getRoom()).isEqualTo(task4.getRoom());
	}

}
