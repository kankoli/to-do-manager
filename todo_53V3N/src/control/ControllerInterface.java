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
import java.util.ResourceBundle;

import javax.swing.Action;

import utility.GlobalValues;
import utility.GlobalValues.Languages;

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

	public static enum SortType {
		DATE, CATEGORY, PRIORITY, NAME, NONE
	};

	public static enum ActionName {
		CHANGELANG, EXIT, NEWTASK, NEWCAT, SORT, TIMER
	};

	public static enum ChangeMessage {
		INIT, SORTED_TASK, CHANGED_PROPERTY, NEW_TASK, NEW_CATEGORY, DELETED_TASK, DELETED_CATEGORY, EDIT_TASK
	};

	// TODO move other enum here?

	public static enum DateFormat {
		ITALIAN, SWEDISH
	};

	private static SimpleDateFormat[] dateFormats = {
			new SimpleDateFormat("dd-MM-yyyy HH:mm"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm") };

	// TODO we were discussing if we could use dataModel in a static way
	private DataModel dataModel;

	private ClassLoader cl;

	// Subcontrollers
	private DataController dc;
	private ActionsController ac;
	private PropertiesController pc;
	private ObserverController oc;

	public ControllerInterface(DataModel dataModel) {

		this.dataModel = dataModel;
		cl = ClassLoader.getSystemClassLoader();

		dc = new DataController(dataModel);
		ac = new ActionsController(dataModel, this); // need reference for
														// creating actions
		pc = new PropertiesController(dataModel);
		oc = new ObserverController(dataModel);

		// This makes a date like "31-13-2013 17:45"
		// not valid!
		for (SimpleDateFormat sdf : dateFormats)
			sdf.setLenient(false);
	}

	/**
	 * This method will be called to add a new Category to data model
	 * 
	 * @param categoryName
	 * @param categoryColor
	 * @throws InvalidCategoryException
	 */
	public final void addCategory(String categoryName, Color categoryColor)
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
	public final void sortTasks(SortType ordering) {
		dc.sortTasks(ordering);
	}

	/**
	 * This methods retrieves task list form datamodel
	 * 
	 * @return
	 */
	public final List<Task> getTaskList() {
		return dc.getTaskList();
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
	public final void addTask(String name, Date date, Task.Priority priority,
			Boolean completed, String categoryName, String description) {
		dc.addTask(name, date, priority, completed, categoryName, description);
	}

	/**
	 * This method is called with a task and new modified values
	 * 
	 * @param task
	 * @param name
	 * @param sdf
	 * @param date
	 * @param priority
	 * @param completed
	 * @param categoryName
	 * @param description
	 * @throws InvalidCategoryException
	 * @throws InvalidDateException
	 */
	public final void editTask(Task task, String name, SimpleDateFormat sdf,
			String date, Priority priority, Boolean completed,
			String categoryName, String description)
			throws InvalidCategoryException, InvalidDateException {
		dc.editTask(task, name, sdf, date, priority, completed, categoryName,
				description);
	}

	/**
	 * Remove a task from the data model
	 * 
	 * @param task
	 */
	public final void deleteTask(Task task) {
		dc.deleteTask(task);
	}

	/**
	 * Retrieves category map from datamodel
	 * 
	 * @return
	 */
	public final Map<String, Category> getCategories() {
		return dc.getCategories();
	}

	/**
	 * Retrieves an action from the Controller
	 * 
	 * @param actionName
	 * @return
	 */
	public final Action getAction(ActionName actionName) {
		return ac.getAction(actionName);
	}

	/**
	 * * This method retrieves the property from the dataModel
	 * 
	 * @param key
	 * @return
	 */
	public final String getProperty(String key) {
		return pc.getProperty(key);
	}

	/**
	 * * This method sets a property on the dataModel
	 * 
	 * @param key
	 * @param value
	 * @param notifyObservers indicates wheter the property change should fire an event
	 * @return
	 */
	public final void setProperty(String key, String value, boolean notifyObservers) {
		pc.setProperty(key, value, notifyObservers);
	}

	/**
	 * Retrieves current dateFormat
	 * 
	 * @return
	 */
	public final SimpleDateFormat getDateFormat() {
		return dateFormats[Integer
				.parseInt(getProperty(GlobalValues.DATEFORMATKEY))];
	}

	/**
	 * Set a new dateFormat for displaying date
	 * 
	 * @param df
	 */
	public final void setDateFormat(DateFormat df) {

		setProperty(GlobalValues.DATEFORMATKEY, Integer.toString(df.ordinal()), true);
	}

	/**
	 * This method retrieves the current setted languageBundle from dataModel
	 * 
	 * @return
	 */
	public final ResourceBundle getLanguageBundle() {
		return pc.getLanguageBundle();
	}

	/**
	 * This method is called when new language is selected
	 * 
	 * @param index
	 */
	public final void setLanguage(Languages language) {

		// System.out.println(language);
		pc.setLanguage(language);

		// XXX should i call the setlanguage on the actioncontroller as well?
		// Or better: we should have the view explicitly call this method? to
		// discuss
		ac.refreshLanguage();
	}

	/**
	 * This method is called to register as an observer on the datamodel
	 * 
	 * @param o
	 */
	public final void registerAsObserver(Observer o) {
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
	public URL getResource(String name) {
		return cl.getResource(name);
	}

	// TODO fix exceptions
	/**
	 * This method is called to save into files the application state.
	 */
	public final void saveDB() {
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

}
