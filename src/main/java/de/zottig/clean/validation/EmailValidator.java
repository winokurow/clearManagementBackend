package de.zottig.clean.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * The interface ConstraintValidator requires this method to be implemented.
	 */
	@Override
	public void initialize(final ValidEmail constraintAnnotation) {
	}

	@Override
	public boolean isValid(final String username,
			final ConstraintValidatorContext context) {
		return (validateEmail(username));
	}

	private boolean validateEmail(final String email) {
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
}