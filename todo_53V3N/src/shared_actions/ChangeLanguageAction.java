package shared_actions;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import utility.GlobalValues;
import utility.GlobalValues.Languages;
import control.ControllerInterface;

/**
 * This action represents a ChangeLanguage action.
 * @author Marco Dondio
 *
 */
@SuppressWarnings("serial")
public final class ChangeLanguageAction extends AbstractAction {

	public ChangeLanguageAction(String text, ImageIcon icon, String desc,
			Integer mnemonic) {
		super(text, icon);
	}

	public void actionPerformed(ActionEvent arg0) {

		ResourceBundle lang = ControllerInterface.getLanguageBundle();

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

		ControllerInterface.setLanguage(l);

	}
}
