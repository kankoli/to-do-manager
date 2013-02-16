package exceptions;

@SuppressWarnings("serial")
public final class InvalidDateException extends Exception {

	public InvalidDateException(final String argMessage) {
		super(argMessage);
	}
}
