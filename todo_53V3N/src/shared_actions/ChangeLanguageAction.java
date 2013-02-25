package shared_actions;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import utility.GlobalValues;
import utility.GlobalValues.Languages;
import control.ControllerInterface;

@SuppressWarnings("serial")
public final class ChangeLanguageAction extends AbstractAction {

	private ControllerInterface controller;

	public ChangeLanguageAction(String text, ImageIcon icon, String desc,
			Integer mnemonic, ControllerInterface controller) {
		super(text, icon);

		this.controller = controller;
	}

	public void actionPerformed(ActionEvent arg0) {

		ResourceBundle lang = controller.getLanguageBundle();

		Languages l = null;

		if ((lang.getString("mainFrame.menubar.language.english")).equals(arg0
				.getActionCommand()))
			l = GlobalValues.Languages.EN;
		else if ((lang.getString("mainFrame.menubar.language.swedish"))
				.equals(arg0.getActionCommand()))
			l = GlobalValues.Languages.SWE;
		else if ((lang.getString("mainFrame.menubar.language.italian"))
				.equals(arg0.getActionCommand()))
			l = GlobalValues.Languages.IT;

		controller.setLanguage(l);

	}
}
