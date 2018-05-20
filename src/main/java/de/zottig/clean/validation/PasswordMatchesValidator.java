package de.zottig.clean.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import de.zottig.clean.web.dto.GroupDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, GroupDto> {

	@Override
	public void initialize(final PasswordMatches constraintAnnotation) {
		//
	}

	@Override
	public boolean isValid(final GroupDto user, final ConstraintValidatorContext context) {
		System.out.println("Password1" + user.getPassword());
		System.out.println("Password2" + user.getConfirmpassword());
		System.out.println(user.getPassword().equals(user.getConfirmpassword()));

		boolean isValid = user.getPassword().equals(user.getConfirmpassword());
		if (!isValid) {
			ConstraintViolationBuilder cvb = context
					.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate());
			cvb.addNode("Password").addConstraintViolation();
		}
		System.out.println(isValid);
		return isValid;
	}

}
