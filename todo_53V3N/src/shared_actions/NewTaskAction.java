package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.new_task_dialog.NewTaskDialog;

/**
 * This task represents the opening of new task dialog.
 * @author Marco Dondio
 *
 */
@SuppressWarnings("serial")
public final class NewTaskAction extends AbstractAction {
	
	public NewTaskAction(String text, ImageIcon icon, String desc,
			Integer mnemonic) {
		super(text, icon);
	}

	public void actionPerformed(ActionEvent arg0) {
		new NewTaskDialog();
	}
}
