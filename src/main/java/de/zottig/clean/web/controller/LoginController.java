package de.zottig.clean.web.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.Member;
import de.zottig.clean.service.IMemberService;
import de.zottig.clean.web.dto.GroupDto;

@RestController
@RequestMapping("api")
public class LoginController {
	@Autowired
	private IMemberService userService;

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody GroupDto userDto) throws AuthenticationException {
		if (StringUtils.isEmpty(userDto.getEmail()) || StringUtils.isEmpty(userDto.getPassword())) {
			throw new AuthenticationException("Email and password must be provided");
		}
		Member user = userService.findUserByEmail(userDto.getEmail());
		if (user == null) {
			throw new AuthenticationException("Invalid username or password");
		}

		// if (passwordEncoder.matches(userDto.getPassword(), user.getEmail()))
		// {
		// System.out.println(userDto.getEmail());
		// System.out.println(passwordEncoder.encode(userDto.getPassword()));
		// throw new AuthenticationException("Invalid username or password");
		// }

		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
}