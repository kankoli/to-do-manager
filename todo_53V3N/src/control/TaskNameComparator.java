package control;

import java.util.Comparator;

import model.Task;

/**
 * This is a name comparator for Task class.
 * 
 * @author Marco Dondio
 * 
 */
public final class TaskNameComparator implements Comparator<Task> {

	public int compare(Task arg0, Task arg1) {		
		// default alphabetical sorting
		
		return arg1.getName().compareTo(arg0.getName());
	}
}