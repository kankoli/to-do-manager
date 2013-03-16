package model.TaskComparators;

import java.util.Comparator;

import model.Task;

/**
 * This is a priority comparator for Task class.
 * 
 * @author Marco Dondio
 * 
 */
public final class PriorityComparator implements Comparator<Task> {

	// We compare using priority level.
	public final int compare(Task arg0, Task arg1) {
		int val0 = arg0.getPrio().ordinal();
		int val1 = arg1.getPrio().ordinal();

		return (val0 > val1 ? 1 : (val0 < val1 ? -1 : 0));
	}
}