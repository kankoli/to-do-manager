package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import control.ControllerInterface;
import exceptions.InvalidCategoryException;


/**
 * This task represents the Editing of a task. We are using a special defined
 * interface.
 * 
 * @author Marco Dondio
 * 
 */
@SuppressWarnings("serial")
public final class EditTaskAction extends AbstractAction {

	
	public EditTaskAction(String text, ImageIcon icon, String desc,
			Integer mnemonic) {
		super(text, icon);
	}

	public void actionPerformed(ActionEvent arg0) {

		// cast to interface we built
		try {
			
			TaskValueProvider tpp = (TaskValueProvider) this.getValue("tpp");
			
			System.out.println("editing called");

			System.out.println(tpp.getDescription());

			
			ControllerInterface.editTask(tpp.getTask(), tpp.getTaskName(),
					tpp.getDate(), tpp.getPrio(), tpp.getCompleted(),
					tpp.getCategoryName(), tpp.getDescription());
		} catch (InvalidCategoryException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"Category Problem", JOptionPane.WARNING_MESSAGE);
		}
	}
}
