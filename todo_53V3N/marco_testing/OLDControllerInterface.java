package control;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.ResourceBundle;

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

public final class OLDControllerInterface {

	public static enum SortType {
		DATE, CATEGORY, PRIORITY, NONE
	};

	// TODO we were discussing if we could use dataModel in a static way
	private DataModel dataModel;


	// TODO
	// all subcontrollers here
	
	public OLDControllerInterface(DataModel dataModel) {

		this.dataModel = dataModel;
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
		Map<String, Category> categories = dataModel.getCategories();
		if (categories.containsKey(categoryName))
			throw new InvalidCategoryException("Category already exists!");

		Category c = new Category(categoryName, categoryColor);

		dataModel.addCategory(c);
	}

	/**
	 * This method sort Task List
	 * 
	 * @param ordering
	 *            type of ordering
	 * @return
	 */
	public final void sortTasks(SortType ordering) {

		switch (ordering) {
		case DATE:
			dataModel.sortTasks(new TaskDateComparator());
			break;
		case CATEGORY:
			dataModel.sortTasks(new TaskCategoryComparator());

			break;
		case PRIORITY:
			dataModel.sortTasks(new TaskPriorityComparator());
			break;
		default: // NONE or others, no ordering
			break;
		}
	}

	/**
	 * This methods retrieves task list form datamodel
	 * 
	 * @return
	 */
	public final List<Task> getTaskList() {
		return dataModel.getTaskList();
	}

	/**
	 * * This method retrieves the property from the dataModel
	 * 
	 * @param key
	 * @return
	 */
	public final String getProperty(String key) {
		return dataModel.getProperty(key);
	}

	/**
	 * * This method sets a property on the dataModel
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public final void setProperty(String key, String value) {
		dataModel.setProperty(key, value);
	}

	/**
	 * This method retrieves the current setted languageBundle from dataModel
	 * 
	 * @return
	 */
	public final ResourceBundle getLanguageBundle() {
		return dataModel.getLanguageBundle();
	}

	/**
	 * This method is called when new language is selected
	 * 
	 * @param index
	 */
	public final void setLanguage(Languages language) {
		Languages oldLanguage = Languages.valueOf(dataModel
				.getProperty(GlobalValues.LANGUAGEVAL));

		if (oldLanguage != language)
			dataModel.setLanguage(language);
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
	public final void addTask(String name, Date date, String priority,
			Boolean completed, String categoryName, String description) {

		// TODO maybe it's better to pass a task object to this method???
		// instead of building it here

		Task t = new Task();

		t.setName(name);
		t.setDate(date);
		t.setPrio(Task.Priority.valueOf(priority)); // TODO to fix
		t.setCompleted(completed);
		t.setCategory(dataModel.getCategories().get(categoryName)); // TODO fix:
																	// reference
																	// to
																	// category
																	// object,
																	// not
																	// string
		t.setDescription(description);

		dataModel.addTask(t);
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
			String date, String priority, Boolean completed,
			String categoryName, String description)
			throws InvalidCategoryException, InvalidDateException,
			IllegalArgumentException {

		// TODO
		// may do some checkings?? throw exceptions???
		// also qui fa la validazione della data???
		// TODO deve ricevere anche il dateformat???

		Date d = null;
		try {
			d = sdf.parse(date);

		} catch (ParseException e) {

			d = task.getDate();
			throw new InvalidDateException("Invalid date!");
		}

		// TODO will be a radiobutton?
		Priority prio = Task.Priority.valueOf(priority);

		Category c = dataModel.getCategories().get(categoryName);

		if (c == null)
			throw new InvalidCategoryException("Category doesn't exists!");

		// TODO maybe before calling datamodel method it can verify
		// if ANY data has changed with respect to old values
		// this will avoid useless notifications
		dataModel.editTask(task, name, d, prio, completed, c, description);
	}

	/**
	 * Remove a task from the data model
	 * 
	 * @param task
	 */
	public final void deleteTask(Task task) {
		dataModel.deleteTask(task);
	}

	/**
	 * Retrieves category map from datamodel
	 * 
	 * @return
	 */
	public final Map<String, Category> getCategories() {
		return dataModel.getCategories();
	}

	// TODO other methods: not implemented, just ideas
	// getOptions
	// setoption

	// maybe: method to edit task?? will receive a reference to a task and new
	// values, will do some checks
	// and throw exceptions with needed.. instead of doing this in view i move
	// this here

	// TODO add method in datamodel to receive a signal to signal update

	/**
	 * This method is called to register as an observer on the datamodel
	 * 
	 * @param o
	 */
	public final void registerAsObserver(Observer o) {
		dataModel.addObserver(o);
	}

	/**
	 * This method is called to delete an observer from the datamodel
	 * 
	 * @param o
	 */
	public final void deleteObserver(Observer o) {
		dataModel.deleteObserver(o);
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
