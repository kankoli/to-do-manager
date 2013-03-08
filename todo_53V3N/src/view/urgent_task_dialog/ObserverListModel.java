package view.urgent_task_dialog;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;

import com.sun.org.glassfish.external.statistics.annotations.Reset;

import control.ControllerInterface;

import model.Task;

public class ObserverListModel extends DefaultListModel implements Observer {

	private ControllerInterface controller;
	public ObserverListModel(ControllerInterface ci) {
		super();
		this.controller = ci;
		List<Task> tasks = controller.getTaskListUrgent(true);
		for (Task t : tasks)
			addElement(t);
		controller.registerAsObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg1;

		if (msg == ControllerInterface.ChangeMessage.EDIT_URGENT) {
			removeAllElements();
			List<Task> tasks = controller.getTaskListUrgent(true);
			for (Task t : tasks)
				addElement(t);
		}
	}
}
