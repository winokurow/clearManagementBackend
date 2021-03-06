package de.zottig.clean.web.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.CleaningHistory;
import de.zottig.clean.service.ICleaningHistoryService;
import de.zottig.clean.web.dto.CleaningHistoryDto;

@RestController
@RequestMapping("api")
public class HistoryController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICleaningHistoryService cleaningHistoryService;

	@PreAuthorize("#oauth2.hasScope('tasks') and #oauth2.hasScope('read')")
	@GetMapping(value = "member_history")
	public ResponseEntity<?> getMemberHistory(
			@RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {
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
	@GetMapping(value = "household_history")
	public ResponseEntity<?> getHouseholdHistory(Principal principal,
			@RequestParam(name = "dateFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
			@RequestParam(name = "dateTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String email = authentication.getName();

		List<CleaningHistory> cleaningHistoryList = cleaningHistoryService
				.getHouseholdHistory(email, dateFrom, dateTo);
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