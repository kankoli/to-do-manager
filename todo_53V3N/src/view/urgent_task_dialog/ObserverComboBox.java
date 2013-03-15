package view.urgent_task_dialog;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JComboBox;

import model.Task;

import control.ControllerInterface;

/**
 * This class lists the non urgent task. 
 * Listner for changes in the urgent tasks. 
 * 
 * @author Kadir & Madelen 
 *
 */
@SuppressWarnings("serial")
public class ObserverComboBox extends JComboBox<Object> implements Observer {
	
	private ResourceBundle languageBundle;

	public ObserverComboBox() {
		super();
		
		// TODO need to retrieve language bundle!!!!
		
		languageBundle = ControllerInterface.getLanguageBundle();

	
	
		insertItemAt(languageBundle.getString("task.taskInput.selectTask"), 0);	
		List<Task> nonUrgentTasks = ControllerInterface.getTaskListUrgent(false);
		for (int i = 0; i < nonUrgentTasks.size(); i++) {
			addItem(nonUrgentTasks.get(i));
		}
		setSelectedIndex(0);
		
		ControllerInterface.registerAsObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg1;

		if (msg == ControllerInterface.ChangeMessage.EDIT_URGENT) {
			removeAllItems();
			insertItemAt("Select task to add...", 0);
			List<Task> nonUrgentTasks = ControllerInterface.getTaskListUrgent(false);
			for (int i = 0; i < nonUrgentTasks.size(); i++) {
				addItem(nonUrgentTasks.get(i));
			}
			setSelectedIndex(0);
		}
		else if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			revalidate();
			repaint();
			}
		}

	public void setUrgent() {
		if (getSelectedIndex() != 0)
			ControllerInterface.setUrgent((Task)getSelectedItem(), true);
	}
}
