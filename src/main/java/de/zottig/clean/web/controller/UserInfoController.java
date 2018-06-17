package de.zottig.clean.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.zottig.clean.persistence.model.Role;
import de.zottig.clean.persistence.model.User;
import de.zottig.clean.web.dto.RoleDto;

@RestController
@RequestMapping("api")
public class UserInfoController {

	@RequestMapping(value = "/identity/userinfo", method = RequestMethod.GET)
	public ResponseEntity<?> getRoles(Principal principal,
			HttpServletRequest request) {
		User activeUser = (User) ((Authentication) principal).getPrincipal();
		List<Role> roles = activeUser.getRoles();
		List<RoleDto> roleDtos = new ArrayList<>();
		for (Role role : roles) {
			roleDtos.add(convertToDto(role));
		}
		return ResponseEntity.ok(roleDtos);
	}

	private RoleDto convertToDto(Role role) {
		ModelMapper modelMapper = new ModelMapper();
		RoleDto roleDto = modelMapper.map(role, RoleDto.class);
		return roleDto;
	}

}