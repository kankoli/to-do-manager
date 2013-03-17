package control;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.Timer;

import utility.GlobalValues;
import utility.GlobalValues.Languages;
import utility.GlobalValues.Themes;

import exceptions.InvalidCategoryException;

import model.Category;
import model.Task;
import model.DataModel;
import model.Task.Priority;

/**
 * Implements the controller part of MVC architecture. Responsible to check
 * values and look for problems, will raise exceptions if needed. Operates on
 * the data model.
 * 
 * @author Marco Dondio
 * 
 */

public final class ControllerInterface {

	// Time interval (5 minutes) to perform one auto-save.
	public final static int AUTOSAVE_INTERVAL = 300000;

	public static enum SortType {
		DATE, CATEGORY, PRIORITY, NAME, NONE
	};

	// Shared action names.
	public static enum ActionName {
		CHANGELANG, EXIT, NEWTASK, EDITTASK, NEWCAT, SORT, TIMER
	};

	public static enum DateFormat {
		DDMMYYY, YYYYMMDD
	};

	private static SimpleDateFormat[] dateFormats = {
			new SimpleDateFormat("dd-MM-yyyy HH:mm"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm") };

	// TODO we were discussing if we could use dataModel in a static way
	private static DataModel dataModel;

	// For theme support
	private static Properties curTheme;

	private static ClassLoader cl = ClassLoader.getSystemClassLoader();

	// Subcontrollers
	private static DataController dc;
	private static ActionsController ac;
	private static PropertiesController pc;
	private static ObserverController oc;

	/***
	 * Initialize the static variables of the class. Subcontrollers are
	 * initialized and auto-save timer is started.
	 * 
	 * @param db
	 */
	public static final void init(DataModel db) {
		dataModel = db;

		dc = new DataController(dataModel);
		ac = new ActionsController(dataModel);
		pc = new PropertiesController(dataModel);
		oc = new ObserverController(dataModel);

		// Timer object is used to fire up the TimerAction every
		// AUTOSAVE_INTERVAL miliseconds.
		Timer autosaveTimer = new Timer(AUTOSAVE_INTERVAL,
				getAction(ControllerInterface.ActionName.TIMER));
		autosaveTimer.start();

		// This makes a date like "31-13-2013 17:45"
		// not valid!
		for (SimpleDateFormat sdf : dateFormats)
			sdf.setLenient(false);

		// Load last execution theme
		curTheme = new Properties();

		try {

			Themes theme = GlobalValues.Themes.valueOf(dataModel
					.getProperty(GlobalValues.THEMEKEY));

			curTheme.load(getResource(
					GlobalValues.supportedThemes[theme.ordinal()]).openStream());

			// System.out
			// .println("[ControllerInterface] Tema settato da ultima esecuzione"
			// + theme);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Adds a new Category to data model
	 * 
	 * @param categoryName
	 * @param categoryColor
	 * @throws InvalidCategoryException
	 */
	public static final void addCategory(String categoryName,
			Color categoryColor) throws InvalidCategoryException {
		dc.addCategory(categoryName, categoryColor);
	}

	/**
	 * Sorts the internal task list
	 * 
	 * @param ordering
	 *            type of ordering
	 */
	public static final void sortTasks(SortType ordering) {
		dc.sortTasks(ordering);
	}

	/**
	 * Retrieves the tasks list form data controller
	 * 
	 * @return The whole list of tasks
	 * @deprecated
	 */
	public static final List<Task> getTaskList() {
		return dc.getTaskList();
	}

	/**
	 * Retrieves complete/incomplete tasks list. Wired to pending/completed
	 * selection in the View
	 * 
	 * @return A list of tasks
	 */
	public static final List<Task> getFilteredTaskList() {
		return dc.getFilteredTaskList();
	}

	/**
	 * Retrieves urgent/non-urgent tasks list.
	 * 
	 * @param b
	 *            - true for urgent tasks
	 * @return A list of tasks
	 */
	public static final List<Task> getTaskListUrgent(boolean b) {
		return dc.getTaskListUrgent(b);
	}

	/**
	 * Adds a new Task to data model
	 * 
	 * @param name
	 * @param date
	 * @param priority
	 * @param completed
	 * @param categoryName
	 * @param description
	 */
	public static final void addTask(String name, Date date,
			Task.Priority priority, Boolean completed, String categoryName,
			String description) {
		dc.addTask(name, date, priority, completed, categoryName, description);
	}

	/**
	 * Edits a given task with given values
	 * 
	 * @param task
	 * @param name
	 * @param date
	 * @param priority
	 * @param completed
	 * @param categoryName
	 * @param description
	 * @throws InvalidCategoryException
	 */
	public static final void editTask(Task task, String name, Date date,
			Priority priority, Boolean completed, String categoryName,
			String description) throws InvalidCategoryException {
		dc.editTask(task, name, date, priority, completed, categoryName,
				description);
	}

	/**
	 * Removes a task from the data model.
	 * 
	 * @param task
	 */
	public static final void deleteTask(Task task) {
		dc.deleteTask(task);
	}

	/**
	 * Sets a task as urgent.
	 * 
	 * @param task
	 */
	public static final void setUrgent(Task task, boolean b) {
		dc.setUrgent(task, b);
	}

	/**
	 * Retrieves category map.
	 * 
	 * @return Categories mapped with their names
	 */
	public static final Map<String, Category> getCategories() {
		return dc.getCategories();
	}

	/**
	 * Retrieves the action with given name.
	 * 
	 * @param actionName
	 * @return An action
	 */
	public static final Action getAction(ActionName actionName) {
		return ac.getAction(actionName);
	}

	/**
	 * Retrieves the property with given key.
	 * 
	 * @param key
	 * @return The value of the property
	 */
	public static final String getProperty(String key) {
		return pc.getProperty(key);
	}

	/**
	 * Sets a property of the data model
	 * 
	 * @param key
	 * @param value
	 * @param notifyObservers
	 *            - indicates whether the property change should fire an event
	 */
	public static final void setProperty(String key, String value,
			boolean notifyObservers) {
		pc.setProperty(key, value, notifyObservers);
	}

	/**
	 * Retrieves current date format.
	 * 
	 * @return A SimpleDateFormat object
	 */
	public static final SimpleDateFormat getDateFormat() {
		return dateFormats[Integer
				.parseInt(getProperty(GlobalValues.DATEFORMATKEY))];
	}

	/**
	 * Sets date format.
	 * 
	 * @param df
	 *            - DateFormat object
	 */
	public static final void setDateFormat(DateFormat df) {

		setProperty(GlobalValues.DATEFORMATKEY, Integer.toString(df.ordinal()),
				true);
	}

	/**
	 * Retrieves the current language bundle from data model.
	 * 
	 * @return A ResourceBundle object
	 */
	public static final ResourceBundle getLanguageBundle() {
		return pc.getLanguageBundle();
	}

	/**
	 * Sets language of the application.
	 * 
	 * @param language
	 */
	public static final void setLanguage(Languages language) {

		pc.setLanguage(language);

		// XXX should i call the setlanguage on the actioncontroller as well?
		// Or better: we should have the view explicitly call this method? to
		// discuss
		ac.refreshLanguage();
	}

	/**
	 * Retrieves the current theme.
	 * 
	 * @return A Properties object
	 */
	public static final Properties getThemeBundle() {
		return curTheme;
	}

	/**
	 * Sets the current theme
	 * 
	 * @param theme
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static final void setTheme(Themes theme)
			throws FileNotFoundException, IOException {
		// XXX Marco: im managing theme at this level, you think it's good?
		// i still want to use the classloader
		Themes oldTheme = Themes.valueOf(dataModel
				.getProperty(GlobalValues.THEMEKEY));
		if (oldTheme != theme) {
			curTheme.load(getResource(
					GlobalValues.supportedThemes[theme.ordinal()]).openStream());
			pc.setTheme(theme);
		}
	}

	/***
	 * Sets task viewing flag for
	 * {@link ControllerInterface#getFilteredTaskList()}
	 * 
	 * @param b
	 *            - true for viewing completed tasks, false for viewing pending
	 *            tasks.
	 */
	public static final void setIsViewingCompletedTasks(boolean b) {
		dc.setIsViewingCompletedTasks(b);
	}

	/**
	 * Registers as an observer on the data model
	 * 
	 * @param o
	 */
	public static final void registerAsObserver(Observer o) {
		oc.registerAsObserver(o);
	}

	/**
	 * Retrieves a resource through a ClassLoader object.
	 * 
	 * @param name
	 * @return URL
	 */
	public static final URL getResource(String name) {
		return cl.getResource(name);
	}

	// TODO fix exceptions
	/**
	 * Saves the application state (tasks, properties, etc.) into files.
	 */
	public static final void saveDB() {
		try {
			dataModel.saveDB(new FileOutputStream(GlobalValues.DBFILE),
					GlobalValues.DBXSDFILE, new FileOutputStream(
							GlobalValues.PROPSFILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Toggles the pending/completed flag of the given task
	 * 
	 * @param t
	 */
	public static final void toggleCompleted(Task t) {
		dc.toggleCompleted(t);
	}
}
