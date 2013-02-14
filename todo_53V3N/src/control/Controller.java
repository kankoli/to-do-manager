package control;

import java.awt.Color;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import exceptions.InvalidCategoryException;

import model.Category;
import model.Task;
import model.dbConnector;

// TODO: discuss with team! This is just a basic implementation to start with!
/**
 * This class will implement the controller part of our application. It will
 * receive call from view part through GUI usage, will operate and model and
 * add, modify, retrieve data.
 * 
 * @author Marco Dondio
 * 
 */
public final class Controller {

	public static enum SortType {
		DATE, CATEGORY, PRIORITY, NONE
	};

	private dbConnector dataModel;

	public Controller(dbConnector dataModel) {

		this.dataModel = dataModel;

	}

	// TODO: se sorto la lista nel datamodel, la lista rimane ordinata????
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
		categories.put(categoryName, c);
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
		t.setPrio(Task.Priority.valueOf(priority)); 	// TODO to fix
		t.setCompleted(completed);
		t.setCategory(categoryName); 					// TODO fix: reference to category object, not string
		t.setDescription(description);

		dataModel.getTaskList().add(t);
	}

	/**
	 * Remove a task from the data model
	 * @param task
	 */
	public final void deleteTask(Task task) {
		dataModel.getTaskList().remove(task);
	}

	// TODO other methods: not implemented, just ideas
	// getOptions
	// setoption
	
	// maybe: method to edit task?? will receive a reference to a task and new values, will do some checks
	// and throw exceptions with needed.. instead of doing this in view i move this here
}