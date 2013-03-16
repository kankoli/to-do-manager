package exceptions;

@SuppressWarnings("serial")
/**
 * This exception is fired when we deal with a not valid category
 * @author Marco Dondio
 *
 */
public final class InvalidCategoryException extends Exception {

	public InvalidCategoryException(final String argMessage) {
		super(argMessage);
	}
}
