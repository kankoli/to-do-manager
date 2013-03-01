package control;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


import control.ControllerInterface.SortType;

import exceptions.InvalidCategoryException;
import exceptions.InvalidDateException;

import model.Category;
import model.Task;
import model.DataModel;
import model.Task.Priority;
import model.TaskComparators.CategoryComparator;
import model.TaskComparators.DateComparator;
import model.TaskComparators.NameComparator;
import model.TaskComparators.PriorityComparator;

/**
 * This class will implement the data controller part.
 * 
 * @author Marco Dondio
 * 
 */

public final class DataController {

	// TODO we were discussing if we could use dataModel in a static way
	private DataModel dataModel;

	public DataController(DataModel dataModel) {

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

		if (categoryName == null || categoryName.isEmpty())
			throw new InvalidCategoryException(
					"Category name must not be empty!");

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
		case NAME:
			dataModel.sortTasks(new NameComparator());
			break;
		case DATE:
			dataModel.sortTasks(new DateComparator());
			break;
		case CATEGORY:
			dataModel.sortTasks(new CategoryComparator());
			break;
		case PRIORITY:
			dataModel.sortTasks(new PriorityComparator());
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

		// TODO maybe it's better to pass a task object to this method???
		// instead of building it here

		Task t = new Task();

		t.setName(name);
		t.setDate(date);
		t.setPrio(priority);
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
			String date, Priority priority, Boolean completed,
			String categoryName, String description)
			throws InvalidCategoryException, InvalidDateException {

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


		Category c = dataModel.getCategories().get(categoryName);

		if (c == null)
			throw new InvalidCategoryException("Category doesn't exists!");

		// TODO maybe before calling datamodel method it can verify
		// if ANY data has changed with respect to old values
		// this will avoid useless notifications
		dataModel.editTask(task, name, d, priority, completed, c, description);
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
}
