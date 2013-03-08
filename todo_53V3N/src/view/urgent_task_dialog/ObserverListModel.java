package view.urgent_task_dialog;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import control.ControllerInterface;

import model.Task;

@SuppressWarnings({ "serial", "rawtypes" })
public class ObserverListModel<T> extends DefaultListModel implements Observer {

	@SuppressWarnings("unchecked")
	public ObserverListModel() {
		super();
		List<Task> tasks = ControllerInterface.getTaskListUrgent(true);
		for (Task t : tasks)
			addElement(t);
		ControllerInterface.registerAsObserver(this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg1;

		if (msg == ControllerInterface.ChangeMessage.EDIT_URGENT) {
			removeAllElements();
			List<Task> tasks = ControllerInterface.getTaskListUrgent(true);
			for (Task t : tasks)
				addElement(t);
		}
	}
}
