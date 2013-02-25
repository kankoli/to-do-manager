package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import control.ControllerInterface;

import view.new_task_dialog.NewTaskDialog;

@SuppressWarnings("serial")
public final class NewTaskAction extends AbstractAction {

	private ControllerInterface controller;

	public NewTaskAction(String text, ImageIcon icon, String desc,
			Integer mnemonic, ControllerInterface controller) {
		super(text, icon);

		this.controller = controller;
	}

	public void actionPerformed(ActionEvent arg0) {
		new NewTaskDialog(controller);
	}
}
