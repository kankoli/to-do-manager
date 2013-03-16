package model.TaskComparators;

import java.util.Comparator;

import model.Task;

/**
 * This is a category comparator for Task class.
 * 
 * @author Marco Dondio
 * 
 */
public final class CategoryComparator implements Comparator<Task> {

	// default alphabetical sorting
	public final int compare(Task arg0, Task arg1) {
		return arg0.getCategory().getName()
				.compareTo(arg1.getCategory().getName());
	}
}