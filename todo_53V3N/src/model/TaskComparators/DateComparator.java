package model.TaskComparators;

import java.util.Comparator;
import java.util.Date;

import model.Task;

/**
 * This is a date comparator for Task class.
 * 
 * @author Marco Dondio
 * 
 */
public final class DateComparator implements Comparator<Task> {

	public int compare(Task arg0, Task arg1) {

		Date val0 = arg0.getDate();
		Date val1 = arg1.getDate();

		// now im using default Java date comparison
		return val0.compareTo(val1);
	}
}