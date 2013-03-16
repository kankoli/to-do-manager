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

	public ExitAction(String text, ImageIcon icon, String desc,
			Integer mnemonic) {
		super(text, icon);
	}

	public void actionPerformed(ActionEvent e) {
		ControllerInterface.saveDB();
		System.exit(0);
	}
}
