package de.zottig.clean.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import de.zottig.clean.web.dto.GroupDto;

public class UserValidator implements Validator {

	@Override
	public boolean supports(final Class<?> clazz) {
		return GroupDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(final Object obj, final Errors errors) {
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
		// "message.firstName", "Firstname is required.");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
		// "message.lastName", "LastName is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "message.password", "LastName is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "message.username", "UserName is required.");
	}

}