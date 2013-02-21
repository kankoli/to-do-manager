package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import utility.GlobalValues;
import utility.GlobalValues.Languages;
import view.new_task_dialog.NewTaskDialog;
import control.ControllerInterface;

@SuppressWarnings("serial")
public final class ChangeLanguageAction extends AbstractAction {

	private ControllerInterface controller;
	
	public ChangeLanguageAction(String text, ImageIcon icon, String desc,
			Integer mnemonic, ControllerInterface controller) {
		super(text, icon);

		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Languages lang = GlobalValues.Languages.valueOf(arg0.getActionCommand());
		controller.setLanguage(lang);
//		System.out.println(arg0.getActionCommand());

	
	}
}
