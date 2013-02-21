package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import control.ControllerInterface;
import control.ControllerInterface.SortType;

@SuppressWarnings("serial")
public final class SortTaskAction extends AbstractAction {

	private ControllerInterface controller;
	
	public SortTaskAction(String text, ImageIcon icon, String desc,
			Integer mnemonic, ControllerInterface controller) {
		super(text, icon);

		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		SortType ordering = ControllerInterface.SortType.valueOf(arg0.getActionCommand());

		//System.out.println(ordering);

		controller.sortTasks(ordering);

	
	}
}
