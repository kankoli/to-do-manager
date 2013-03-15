package view.custom_components;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.ImageIcon;

import control.ControllerInterface;

import utility.GlobalValues;

/***
 * A single flag button used in FlagBar class.
 * @author Kadir & Madelen
 *
 */
@SuppressWarnings("serial")
public class FlagButton extends ImageButton {

	private GlobalValues.Languages language;
	private FlagBar parent;
	
	FlagButton(FlagBar p, GlobalValues.Languages l, ImageIcon p_def, ImageIcon p_hover, ImageIcon p_pressed, ImageIcon p_clicked, final Action action) {

		this.def = p_def;
		this.hover = p_hover;
		this.pressed = p_pressed;
		this.clicked = p_clicked;
		this.isClicked = false; // clicked bool is initialized.

		this.language = l; // The language this button represents.
		this.parent = p;	// The parent object
		
		setIcon(this.def);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!isClicked) { // If the button is not already clicked.
					isClicked = true;
					// parent language is changed and buttons are updated with setButtons().
					parent.language = language;
					parent.setButtons();

					ResourceBundle lang = ControllerInterface.getLanguageBundle();
					String[] names = {
							"mainFrame.menubar.language.swedish",
							"mainFrame.menubar.language.english",
							"mainFrame.menubar.language.italian"};

					// The language change action is fired.
					action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, lang.getString(names[language.ordinal()])));
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) { }

			@Override
			public void mouseExited(MouseEvent arg0) { }

			@Override
			public void mousePressed(MouseEvent arg0) {	}

			@Override
			public void mouseReleased(MouseEvent arg0) { }
		});
	}

	protected void setClicked(boolean clicked) {
		this.isClicked = clicked;
	}
}
