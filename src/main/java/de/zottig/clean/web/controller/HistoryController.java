package de.zottig.clean.web.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.CleaningHistory;
import de.zottig.clean.persistence.model.Role;
import de.zottig.clean.persistence.model.User;
import de.zottig.clean.service.ICleaningHistoryService;
import de.zottig.clean.web.dto.CleaningHistoryDto;
import de.zottig.clean.web.util.GenericResponse;

@RestController
@RequestMapping("api")
public class HistoryController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICleaningHistoryService cleaningHistoryService;

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@GetMapping(value = "member_history")
	public ResponseEntity<?> getMemberHistory(
			@RequestParam(name = "dateFrom", required = false) LocalDateTime dateFrom,
			@RequestParam(name = "dateTo", required = false) LocalDateTime dateTo) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String email = authentication.getName();

		List<CleaningHistory> cleaningHistoryList = cleaningHistoryService
				.getMemberHistory(email, dateFrom, dateTo);
		List<CleaningHistoryDto> cleaningHistoryDtos = new ArrayList<>();
		for (CleaningHistory cleaningHistory : cleaningHistoryList) {
			cleaningHistoryDtos.add(convertToDto(cleaningHistory));
		}
		return new ResponseEntity<>(cleaningHistoryDtos, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@RequestMapping(value = "household_history", method = RequestMethod.GET)
	public ResponseEntity<?> getHouseholdHistory(Principal principal) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String email = authentication.getName();

		User activeUser = (User) ((Authentication) principal).getPrincipal();
		List<Role> roles = activeUser.getRoles();
		Role admin = roles.stream()
				.filter(role -> "ROLE_ADMIN".equals(role.getName())).findAny()
				.orElse(null);

		if (admin == null) {
			GenericResponse response = new GenericResponse("Member not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		List<CleaningHistory> cleaningHistoryList = cleaningHistoryService
				.getHouseholdHistory(email);
		List<CleaningHistoryDto> cleaningHistoryDtos = new ArrayList<>();
		for (CleaningHistory cleaningHistory : cleaningHistoryList) {
			cleaningHistoryDtos.add(convertToDto(cleaningHistory));
		}
		return new ResponseEntity<>(cleaningHistoryDtos, HttpStatus.OK);
	}

	private CleaningHistoryDto convertToDto(CleaningHistory cleaningHistory) {
		ModelMapper modelMapper = new ModelMapper();
		CleaningHistoryDto cleaningHistoryDto = modelMapper.map(cleaningHistory,
				CleaningHistoryDto.class);
		cleaningHistoryDto.setMember(cleaningHistory.getMember().getFirstName()
				+ " " + cleaningHistory.getMember().getLastName());
		return cleaningHistoryDto;
	}

}