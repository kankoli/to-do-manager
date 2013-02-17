package control;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observer;

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

// TODO
// this class makes all controls and exception raising, when no exception occurs
// it will operate on datamodel
// through interface calls
public final class Controller {

	public static enum SortType {
		DATE, CATEGORY, PRIORITY, NONE
	};

	// TODO we were discussing if we could use dataModel in a static way
	private DataModel dataModel;

	public Controller(DataModel dataModel) {

		this.dataModel = dataModel;
	}

	// XXX: se sorto la lista viene modificata nel datamodel...
	/**
	 * This method retrieves Task List, after ordering it
	 * 
	 * @param ordering
	 *            type of ordering
	 * @return
	 */
	public final List<Task> getSortedTasks(SortType ordering) {

		List<Task> taskList = dataModel.getTaskList();
		switch (ordering) {
		case DATE:
			Collections.sort(taskList, new TaskDateComparator());
			break;
		case CATEGORY:
			Collections.sort(taskList, new TaskCategoryComparator());
			break;
		case PRIORITY:
			Collections.sort(taskList, new TaskPriorityComparator());
			break;
		default: // NONE or others, no ordering
			break;
		}
		// TODO Change return type to void and tell the model to signal task
		// changes for the 'observers'
		return taskList;
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
	 * This methods retrieves task list form datamodel
	 * 
	 * @return
	 */
	public List<Task> getTaskList() {
		return dataModel.getTaskList();
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

}
