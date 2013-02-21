package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import control.ControllerInterface;

import view.new_category_dialog.AddCategoryDialog;

public class NewCategoryAction extends AbstractAction {

	private ControllerInterface controller;

	public NewCategoryAction(String text, ImageIcon icon, String desc,
			Integer mnemonic, ControllerInterface controller) {
		super(text, icon);

		this.controller = controller;
	}

	@Override
	// If last "special item" is selected, open add category dialog
	// will be an action, because also NewTaskDialog will use this
	public void actionPerformed(ActionEvent e) {
		// JComboBox<String> source = ((JComboBox<String>) e.getSource());

		// Note: this method fails is another category is called
		// "New Category..."
		// because it returns an index wich is not the last one
		// TODO how do i prevent this problem? Check on values?
		// if (source.getSelectedIndex() == (source.getItemCount() - 1)){

		// TODO open add category dialog
		// modify through controller
		// view will be updated by observer call
		// System.out.println("ultimo!");

		new AddCategoryDialog(controller);

	}
}