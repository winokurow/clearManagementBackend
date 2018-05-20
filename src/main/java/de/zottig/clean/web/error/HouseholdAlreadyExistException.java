package de.zottig.clean.web.error;

public final class HouseholdAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 5861310537366287163L;

	public HouseholdAlreadyExistException() {
		super();
	}

	public HouseholdAlreadyExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public HouseholdAlreadyExistException(final String message) {
		super(message);
	}

	public HouseholdAlreadyExistException(final Throwable cause) {
		super(cause);
	}

}