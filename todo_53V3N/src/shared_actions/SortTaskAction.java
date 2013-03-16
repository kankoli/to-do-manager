package shared_actions;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import control.ControllerInterface;
import control.ControllerInterface.SortType;

/**
 * This class represent the sorting action.
 * @author Marco Dondio
 *
 */
@SuppressWarnings("serial")
public final class SortTaskAction extends AbstractAction {

	public SortTaskAction(String text, ImageIcon icon, String desc,
			Integer mnemonic) {
		super(text, icon);
	}

	public void actionPerformed(ActionEvent arg0) {

		ResourceBundle lang = ControllerInterface.getLanguageBundle();

		SortType ordering = SortType.NONE;

		if ((lang.getString("mainFrame.middlePanel.sortingBar.tab.title.name"))
				.equals(arg0.getActionCommand()))
			ordering = SortType.NAME;
		else if ((lang
				.getString("mainFrame.middlePanel.sortingBar.tab.date.name"))
				.equals(arg0.getActionCommand()))
			ordering = SortType.DATE;
		else if ((lang
				.getString("mainFrame.middlePanel.sortingBar.tab.category.name"))
				.equals(arg0.getActionCommand()))
			ordering = SortType.CATEGORY;
		else if ((lang
				.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"))
				.equals(arg0.getActionCommand()))
			ordering = SortType.PRIORITY;

		ControllerInterface.sortTasks(ordering);
	}
}
