package control;

import java.util.Comparator;

import model.Task;

/**
 * This is a category comparator for Task class.
 * 
 * @author Marco Dondio
 * 
 */
public final class TaskCategoryComparator implements Comparator<Task> {

	public int compare(Task arg0, Task arg1) {		
		// default alphabetical sorting
		return arg0.getCategory().getName().compareTo(arg1.getCategory().getName());
	}
}