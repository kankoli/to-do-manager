package view.urgent_task_dialog;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import control.ControllerInterface;

import model.DataModel;
import model.Task;

/**
 * This class takes care of the data model for the JList used in the urgent tasks dialog
 * @author Kadir & Madelen 
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class ObserverListModel<T> extends DefaultListModel implements Observer {

	@SuppressWarnings("unchecked")
	public ObserverListModel() {
		super();
		// get urgent tasks from the controller and add them
		List<Task> tasks = ControllerInterface.getTaskListUrgent(true);
		for (Task t : tasks)
			addElement(t);
		ControllerInterface.registerAsObserver(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg1;

		// if urgent tasks are changed, reset and update the data model 
		if (msg == DataModel.ChangeMessage.EDIT_URGENT) {
			removeAllElements();
			List<Task> tasks = ControllerInterface.getTaskListUrgent(true);
			for (Task t : tasks)
				addElement(t);
		}
	}
}
