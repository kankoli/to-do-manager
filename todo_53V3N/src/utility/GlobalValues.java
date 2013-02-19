package utility;

import java.util.Locale;

public class GlobalValues {

	/* General DB preferences */
	public static final String DBFILE = "todoManagerData.xml";
	public static final String DBXSDFILE = "todoManagerData.xsd";

	/* Graphical preferences */
	public static final int NEW_TASK_WINDOW_WIDTH = 400;
	public static final int NEW_TASK_WINDOW_HEIGHT = 500;
	public static final int FIELD_WIDTH = 150;
	public static final int FIELD_HEIGHT = 20;

	public static final int TASKROW_SPACING_Y = 50;
	public static final int TASKROW_ELEMENT_SPACING_X = 10;
	public static final int TASKROW_ELEMENT_SPACING_Y = 30;
	public static final int TASKROW_DESC_ROWS = 3;
	public static final int TASKROW_DESC_COLS = 10;

	// TODO we will need icons? Like mac cool small icons?
	/* Supported language */

	/* Default locale file */
	public static final String LANGUAGEFILE = "lang";

	// Supported locale
	public static final Locale[] supportedLocales = { new Locale("swe"),
			Locale.US, Locale.ITALIAN };

	
	/* Resource bundle key values */
	// TODO
	public static final String NEWTASK = "mainFrame.topPanel.button.newTask.name";
	
	
	/* Default properties value */
	public static final String PROPSFILE = "config.properties"; // TODO or just
																// config??

	public static final String LANGUAGEKEY = "CurrentLanguage";
	public static final String LANGUAGEVAL = "0"; // we put index of supported
													// locale array

	public static final String WINXPOSKEY = "WindowsXPos";
	public static final String WINXPOSVAL = "0";

	public static final String WINYPOSKEY = "WindowsYPos";
	public static final String WINYPOSVAL = "0";

	public static final String WINXSIZEKEY = "WindowsXSize"; // TODO ideal: if
																// not value
																// specified,
																// read system
																// resolution
	public static final String WINXSIZEVAL = "800";

	public static final String WINYSIZEKEY = "WindowsYSize";
	public static final String WINYSIZEVAL = "600";

}
