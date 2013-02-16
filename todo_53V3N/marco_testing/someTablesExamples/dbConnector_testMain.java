package marco_tests.someTablesExamples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import model.TaskImproved;
import model.dbConnectorImproved;

import org.jdom2.JDOMException;

/**
 * This class is just a test file to show how the dbConnector objects works.
 * 
 * @author Donde
 * 
 */
public class dbConnector_testMain {

	// makes easier managing data
	// see JavaDoc for other formats
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"dd-MM-yyyy:HH:mm");

	/**
	 * @param args
	 * @throws ParseException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ParseException,
			FileNotFoundException, IOException {

		// Load data: notice we r doing some XSD validation, to be sure to read
		// valid data
		dbConnectorImproved myDB;
		try {
			myDB = new dbConnectorImproved();

			// First i define a collection of task
			// these task will be inserted at runtime with GUI
			List<TaskImproved> tasks = myDB.getTaskList();

			System.out.println("\nTasks stored in DB:");
			for (TaskImproved t : tasks) {
				System.out.println(t.getName() + " -> "
						+ sdf.format(t.getDate()) + ", " + t.getCategory()
						+ ", " + t.getPrio());
			}

			// Now we try to add a task...
			// TODO we need some checks to avoid overlapping events?
			tasks.add(new TaskImproved("New task!", sdf.parse("31-01-2013:17:45"),
					"fun", TaskImproved.Priority.HIGH, "some brief text."));

			// finally save back!

			// this method (setTaskLists) replaces entirely the internal data
			// structure
			// we dont have to call it if we operate on same data structure
			// retrieved by DB, it's already linked and modified in real time
			// myDB.setTaskList(tasks);

			// This methd stores internal representation to file
			myDB.saveDB();
		} catch (JDOMException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}