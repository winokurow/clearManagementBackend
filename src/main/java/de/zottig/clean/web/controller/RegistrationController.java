package de.zottig.clean.web.controller;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.service.IHouseholdService;
import de.zottig.clean.service.IMemberService;
import de.zottig.clean.web.dto.ErrorDto;
import de.zottig.clean.web.dto.HouseholdDto;
import de.zottig.clean.web.dto.GroupDto;
import de.zottig.clean.web.error.HouseholdAlreadyExistException;
import de.zottig.clean.web.error.UserAlreadyExistException;

@RestController
@RequestMapping("api")
public class RegistrationController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMemberService userService;

	@Autowired
	private IHouseholdService householdService;

	public RegistrationController() {
		super();
	}

	/**
	 * 
	 * Post request that registrate new household
	 * 
	 * @param household
	 *            - new household
	 * @param bindingResult
	 *            - Result of Binding
	 * @return response
	 * @throws UserAlreadyExistException
	 *             - User already exists
	 * @throws HouseholdAlreadyExistException
	 *             - Household already exists
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@Validated @RequestBody final HouseholdDto household, BindingResult bindingResult)
			throws UserAlreadyExistException, HouseholdAlreadyExistException {

		if (bindingResult.hasErrors()) {
			LOGGER.info("Error");
			return new ResponseEntity<>(new ErrorDto(bindingResult.getAllErrors().stream()
					.map(x -> x.getDefaultMessage()).collect(Collectors.joining(","))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (householdService.isHouseholdExists(household.getHouseholdname())) {
			return new ResponseEntity<>(new ErrorDto("Household with such name is already exist"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		for (GroupDto user : household.getMembers()) {
			if (userService.emailExist(user.getEmail())) {
				LOGGER.error("test");
				return new ResponseEntity<>(new ErrorDto(
						"User with name " + user.getFirstname() + " " + user.getLastname() + " is already exist"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

		try {
			householdService.registerNewHousehold(household.getHouseholdname());
			// User user = userService.registerNewUserAccount(userdto);
			for (GroupDto user : household.getMembers()) {
				LOGGER.info(user.toString());
				userService.registerNewUserAccount(user);

			}
			return new ResponseEntity<>(household, HttpStatus.CREATED);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(new ErrorDto("Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}