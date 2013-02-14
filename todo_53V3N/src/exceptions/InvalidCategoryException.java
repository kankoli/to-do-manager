package exceptions;

@SuppressWarnings("serial")
public final class InvalidCategoryException extends Exception {

	public InvalidCategoryException(final String argMessage) {
		super(argMessage);
	}
}
