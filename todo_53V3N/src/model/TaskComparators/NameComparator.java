package model.TaskComparators;

import java.util.Comparator;

import model.Task;

/**
 * This is a name comparator for Task class.
 * 
 * @author Marco Dondio
 * 
 */
public final class NameComparator implements Comparator<Task> {

	// we compare using default alphabetical sorting
	public final int compare(Task arg0, Task arg1) {
		return arg1.getName().compareTo(arg0.getName());
	}
}