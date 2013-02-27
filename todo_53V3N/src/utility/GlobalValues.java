package utility;

import java.util.Locale;

public class GlobalValues {

	/* General DB preferences */
	public static final String DBFILE = "todoManagerData.xml";
	public static final String DBXSDFILE = "todoManagerData.xsd";

	/* Graphical preferences */
	public static final int MINXSIZE = 900;
	public static final int MINYSIZE = 600;

	public static final int TASKROW_DESC_ROWS = 3;
	public static final int TASKROW_DESC_COLS = 10;

	// TODO we will need icons? Like mac cool small icons?
	/* Supported language */

	/* Default locale file */
	public static final String LANGUAGEFILE = "lang";

	// TODO better way?
	// Supported locale
	public static final Locale[] supportedLocales = { new Locale("swe"),
			Locale.ENGLISH, Locale.ITALIAN };

	public static enum Languages {
		SWE, EN, IT
	};

	/* Default properties values */
	public static final String PROPSFILE = "config.properties";

	public static final String LANGUAGEKEY = "CurrentLanguage";
	public static final String LANGUAGEVAL = "EN"; // we put index of supported
													// locale array

	public static final String WINXPOSKEY = "WindowsXPos";
	public static final String WINXPOSVAL = "0";

	public static final String WINYPOSKEY = "WindowsYPos";
	public static final String WINYPOSVAL = "0";

	// TODO in future, default resolution may be system screen dependent
	public static final String WINXSIZEKEY = "WindowsXSize";

	public static final String WINXSIZEVAL = "800";

	public static final String WINYSIZEKEY = "WindowsYSize";
	public static final String WINYSIZEVAL = "600";

	public static final String DATEFORMATKEY = "DateFormat";
	public static final String DATEFORMATVAL = "0";

}
