package utility;


/**
 * This small class contains some useful method. For example we have two small methods
 * to manage dateformat for writing/reading an xs:date used in XSD validation.
 * @author Marco Dondio
 *
 */
public final class GeneralFunctions {

	// This small function adds :, to have a valid ISO8601
	// datetime format, same used in xs:date for XSD validation
	// https://www.ibm.com/developerworks/mydeveloperworks/blogs/HermannSW/entry/java_simpledateformat_vs_xs_datetime26?lang=en
	public static final String RFC822toISO8601(String dateString) {
		return new StringBuilder(dateString).insert(dateString.length() - 2,
				':').toString();
	}

	// This small function removes :, to have valid RFC822
	// format compatible with Java format
	// https://www.ibm.com/developerworks/mydeveloperworks/blogs/HermannSW/entry/java_simpledateformat_vs_xs_datetime26?lang=en
	public static final String ISO8601toRFC822(String dateString) {
		int j = dateString.lastIndexOf(":");
		return new StringBuilder(dateString).replace(j, j + 1, "").toString();
	}

}
