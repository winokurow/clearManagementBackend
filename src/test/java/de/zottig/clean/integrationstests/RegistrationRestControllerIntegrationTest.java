package de.zottig.clean.integrationstests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.zottig.clean.CleanApplication;
import de.zottig.clean.persistence.dao.HouseholdRepository;
import de.zottig.clean.persistence.dao.MemberRepository;
import de.zottig.clean.persistence.dao.UserRepository;
import de.zottig.clean.persistence.model.Household;
import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.persistence.model.User;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.dto.HouseholdDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CleanApplication.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:persistence-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("prod")
public class RegistrationRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private HouseholdRepository householdRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void whenPutHousehold_thenCreateHousehold() throws Exception {
//		GroupDto member1 = new GroupDto();
//		member1.setFirstname("firstname1");
//		member1.setLastname("lastname1");
//		member1.setPassword("#Start01");
//		member1.setConfirmpassword("#Start01");
//		member1.setIsAdmin(true);
//		member1.setEmail("email1");
//
//		GroupDto member2 = new GroupDto();
//		member2.setFirstname("firstname2");
//		member2.setLastname("lastname2");
//		member2.setPassword("#Start02");
//		member2.setConfirmpassword("#Start02");
//		member2.setIsAdmin(false);
//		member2.setEmail("email2");
//
//		HouseholdDto householdDto = new HouseholdDto();
//		householdDto.setHouseholdname("testName");
//		List<GroupDto> members = new ArrayList<>();
//		members.add(member1);
//		members.add(member2);
//		householdDto.setMembers(members);
//
//		mvc.perform(put("/api/register").contentType(MediaType.APPLICATION_JSON)
//				.content(JsonUtil.toJson(householdDto)))
//				.andExpect(status().is2xxSuccessful());
//
//		List<Household> foundHousehold = householdRepository.findAll();
//		assertThat(foundHousehold.get(0).getName())
//				.isEqualTo(householdDto.getHouseholdname());
//
//		Member foundMember = memberRepository.findOneByEmail("email1");
//		assertThat(foundMember.getEmail())
//				.isEqualTo(householdDto.getMembers().get(0).getEmail());
//		assertThat(foundMember.getFirstName())
//				.isEqualTo(householdDto.getMembers().get(0).getFirstname());
//		assertThat(foundMember.getLastName())
//				.isEqualTo(householdDto.getMembers().get(0).getLastname());
//		assertThat(foundMember.getHousehold().getName())
//				.isEqualTo(householdDto.getHouseholdname());
//
//		foundMember = memberRepository.findOneByEmail("email2");
//		assertThat(foundMember.getEmail())
//				.isEqualTo(householdDto.getMembers().get(1).getEmail());
//		assertThat(foundMember.getFirstName())
//				.isEqualTo(householdDto.getMembers().get(1).getFirstname());
//		assertThat(foundMember.getLastName())
//				.isEqualTo(householdDto.getMembers().get(1).getLastname());
//		assertThat(foundMember.getHousehold().getName())
//				.isEqualTo(householdDto.getHouseholdname());
//
//		User user = userRepository.findByEmail("email1");
//		assertThat(user.getEmail())
//				.isEqualTo(householdDto.getMembers().get(0).getEmail());
//		assertThat(user.getPassword()).isNotNull();
//		//TODO
//		//assertThat(user.getRoles().size()).isEqualTo(2);
//		//assertThat(user.getRoles().get(0)).isEqualTo("ROLE_USER");
//		//assertThat(user.getRoles().get(1)).isEqualTo("ROLE_ADMIN");
//
//		user = userRepository.findByEmail("email2");
//		assertThat(user.getEmail())
//				.isEqualTo(householdDto.getMembers().get(1).getEmail());
//		assertThat(user.getPassword()).isNotNull();
//		//assertThat(user.getRoles().size()).isEqualTo(1);
//		//assertThat(user.getRoles().get(0).getName()).isEqualTo("ROLE_USER");
	}

	private Household convertToEntity(HouseholdDto householdDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(householdDto, Household.class);
	}
}
