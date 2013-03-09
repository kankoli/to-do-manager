package utility;

import java.util.Locale;

public class GlobalValues {

	/* General DB preferences */
	public static final String DBFILE = "todoManagerData.xml";
	public static final String DBXSDFILE = "todoManagerData.xsd";

	/* Graphical preferences */
	public static final int MINXSIZE = 1120;
	public static final int MINYSIZE = 600;

	public static final int TASKROW_DESC_ROWS = 4;
	public static final int TASKROW_DESC_COLS = 20;

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

	public static final String[] supportedThemes = { "DefaultTheme.thm",
			"Theme_0.thm", "Theme_1.thm" };

	public static enum Themes {
		DEFAULT, THEME_0, THEME_1
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

	public static final String THEMEKEY = "CurrentTheme";
	public static final String THEMEVAL = "DEFAULT";

}
