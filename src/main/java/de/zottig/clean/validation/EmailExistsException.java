package de.zottig.clean.validation;

/**
 * Exception that wil be thrown when user with such email already exists
 * 
 * @author Ilja.Winokurow
 *
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Exception {

	public EmailExistsException(final String message) {
		super(message);
	}
}
