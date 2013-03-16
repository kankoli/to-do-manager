package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import view.new_category_dialog.AddCategoryDialog;

/**
 * This action represent the new category opening dialog action.
 * @author Marco Dondio
 *
 */
@SuppressWarnings("serial")
public class NewCategoryAction extends AbstractAction {

	public NewCategoryAction(String text, ImageIcon icon, String desc,
			Integer mnemonic) {
		super(text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new AddCategoryDialog();
	}
}
