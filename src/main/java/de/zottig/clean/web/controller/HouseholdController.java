package de.zottig.clean.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.service.IMemberService;
import de.zottig.clean.web.dto.MemberDto;

@RestController
@RequestMapping("api")
public class HouseholdController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMemberService memberService;

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@GetMapping(value = "member_history")
	public ResponseEntity<?> getMembers() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String email = authentication.getName();
		Member currentMember = memberService.findUserByEmail(email);
		List<Member> members = memberService.getMembersByHouseholdId(currentMember.getHousehold().getId());
		List<MemberDto> memberDtos = new ArrayList<>();
		for (Member member : members) {
			memberDtos.add(convertToDto(member));
		}
		return new ResponseEntity<>(memberDtos, HttpStatus.OK);
	}

	private MemberDto convertToDto(Member member) {
		MemberDto dto = new MemberDto();
		dto.setId(member.getId());
		dto.setName(member.getFirstName()
				+ " " + member.getLastName());
		return dto;
	}

}