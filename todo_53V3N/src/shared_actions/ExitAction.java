package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import control.ControllerInterface;

/**
 * This action will be executed when exiting the application, through window control or menu.
 * @author Marco Dondio
 *
 */
@SuppressWarnings("serial")
public class ExitAction extends AbstractAction {

	private ControllerInterface controller;
	

	public ExitAction(String text, ImageIcon icon, String desc,
			Integer mnemonic, ControllerInterface controller) {
		super(text, icon);

		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {

		// TODO here coded needed at closing

		controller.saveDB();
		System.exit(0);
	}

}
