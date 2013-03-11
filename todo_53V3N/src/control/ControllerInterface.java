package control;

import java.awt.Color;
import java.io.FileNotFoundException;
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
import exceptions.InvalidDateException;

import model.Category;
import model.Task;
import model.DataModel;
import model.Task.Priority;

/**
 * This class will implement the controller part of our application. It will
 * receive call from view part through GUI usage. This class is responsible to
 * check values and look for problems, will raise exceptions if needed. After
 * this, it will operate on dataModel by editing/ retrieving data.
 * 
 * @author Marco Dondio
 * 
 */

public final class ControllerInterface {

	public final static int AUTOSAVE_INTERVAL = 300000; // Time interval (5
														// minutes) to perform
														// one autosave

	public static enum SortType {
		DATE, CATEGORY, PRIORITY, NAME, NONE
	};

	public static enum ActionName {
		CHANGELANG, EXIT, NEWTASK, NEWCAT, SORT, TIMER
	};

	public static enum ChangeMessage {
		INIT, CHANGED_THEME, SORTED_TASK, CHANGED_PROPERTY, NEW_TASK, NEW_CATEGORY, DELETED_TASK, DELETED_CATEGORY, EDIT_TASK, CHANGED_FILTER, EDIT_URGENT
	};

	// TODO move other enum here?

	public static enum DateFormat {
		ITALIAN, SWEDISH
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

	public static void init(DataModel db) {
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
	 * This method will be called to add a new Category to data model
	 * 
	 * @param categoryName
	 * @param categoryColor
	 * @throws InvalidCategoryException
	 */
	public static void addCategory(String categoryName, Color categoryColor)
			throws InvalidCategoryException {
		dc.addCategory(categoryName, categoryColor);
	}

	/**
	 * This method sort Task List
	 * 
	 * @param ordering
	 *            type of ordering
	 * @return
	 */
	public static void sortTasks(SortType ordering) {
		dc.sortTasks(ordering);
	}

	/**
	 * This methods retrieves tasks list form data controller
	 * 
	 * @return
	 */
	public final List<Task> getTaskList() {
		return dc.getTaskList();
	}

	/**
	 * This methods retrieves complete/incomplete tasks list form data
	 * controller
	 * 
	 * @return
	 */
	public static List<Task> getFilteredTaskList() {
		return dc.getFilteredTaskList();
	}

	/**
	 * This methods retrieves urgent/non-urgent tasks list form data controller
	 * 
	 * @return
	 */
	public static List<Task> getTaskListUrgent(boolean b) {
		return dc.getTaskListUrgent(b);
	}

	/**
	 * This method will be called to add a new Task to data model
	 * 
	 * @param name
	 * @param date
	 * @param priority
	 * @param completed
	 * @param categoryName
	 * @param description
	 */
	public static void addTask(String name, Date date, Task.Priority priority,
			Boolean completed, String categoryName, String description) {
		dc.addTask(name, date, priority, completed, categoryName, description);
	}

	/**
	 * This method is called with a task and new modified values
	 * 
	 * @param task
	 * @param name
	 * @param date
	 * @param priority
	 * @param completed
	 * @param categoryName
	 * @param description
	 * @throws InvalidCategoryException
	 * @throws InvalidDateException
	 */
	public static void editTask(Task task, String name, Date date, Priority priority, Boolean completed,
			String categoryName, String description)
			throws InvalidCategoryException {
		dc.editTask(task, name, date, priority, completed, categoryName,
				description);
	}

	/**
	 * Remove a task from the data model
	 * 
	 * @param task
	 */
	public static void deleteTask(Task task) {
		dc.deleteTask(task);
	}

	/**
	 * Set a task as urgent
	 * 
	 * @param task
	 */
	public static void setUrgent(Task task, boolean b) {
		dc.setUrgent(task, b);
	}

	/**
	 * Retrieves category map from datamodel
	 * 
	 * @return
	 */
	public static Map<String, Category> getCategories() {
		return dc.getCategories();
	}

	/**
	 * Retrieves an action from the Controller
	 * 
	 * @param actionName
	 * @return
	 */
	public static Action getAction(ActionName actionName) {
		return ac.getAction(actionName);
	}

	/**
	 * * This method retrieves the property from the dataModel
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return pc.getProperty(key);
	}

	/**
	 * * This method sets a property on the dataModel
	 * 
	 * @param key
	 * @param value
	 * @param notifyObservers
	 *            indicates wheter the property change should fire an event
	 * @return
	 */
	public static void setProperty(String key, String value,
			boolean notifyObservers) {
		pc.setProperty(key, value, notifyObservers);
	}

	/**
	 * Retrieves current dateFormat
	 * 
	 * @return
	 */
	public static SimpleDateFormat getDateFormat() {
		return dateFormats[Integer
				.parseInt(getProperty(GlobalValues.DATEFORMATKEY))];
	}

	/**
	 * Set a new dateFormat for displaying date
	 * 
	 * @param df
	 */
	public static void setDateFormat(DateFormat df) {

		setProperty(GlobalValues.DATEFORMATKEY, Integer.toString(df.ordinal()),
				true);
	}

	/**
	 * This method retrieves the current setted languageBundle from dataModel
	 * 
	 * @return
	 */
	public static ResourceBundle getLanguageBundle() {
		return pc.getLanguageBundle();
	}

	/**
	 * This method is called when new language is selected
	 * 
	 * @param index
	 */
	public static void setLanguage(Languages language) {

		// System.out.println(language);
		pc.setLanguage(language);

		// XXX should i call the setlanguage on the actioncontroller as well?
		// Or better: we should have the view explicitly call this method? to
		// discuss
		ac.refreshLanguage();
	}

	/**
	 * This method is called to retrieve current theme
	 */
	public static Properties getThemeBundle() {
		return curTheme;
	}

	/**
	 * This method is called when new theme is selected
	 * 
	 * @param index
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void setTheme(Themes theme) throws FileNotFoundException,
			IOException {
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

	public static void setIsViewingCompletedTasks(boolean b) {
		dc.setIsViewingCompletedTasks(b);
	}

	/**
	 * This method is called to register as an observer on the datamodel
	 * 
	 * @param o
	 */
	public static void registerAsObserver(Observer o) {
		oc.registerAsObserver(o);
	}

	/**
	 * This method is called to delete an observer from the datamodel
	 * 
	 * @param o
	 */
	public final void deleteObserver(Observer o) {
		oc.registerAsObserver(o);
	}

	/**
	 * This function is called to retrieve a resource at runtime
	 * 
	 * @param name
	 * @return
	 */
	public static URL getResource(String name) {
		return cl.getResource(name);
	}

	// TODO fix exceptions
	/**
	 * This method is called to save into files the application state.
	 */
	public static void saveDB() {
		try {
			dataModel.saveDB();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void toggleCompleted(Task t) {
		dc.toggleCompleted(t);

	}
}
