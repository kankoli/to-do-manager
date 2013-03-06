package view.custom_components;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import control.ControllerInterface;

import utility.GlobalValues;

import model.Task;


@SuppressWarnings("serial")
public class FlagButton extends ImageButton {

	private GlobalValues.Languages language;
	private FlagBar parent;
	
	FlagButton(final ControllerInterface ci, FlagBar p, GlobalValues.Languages l, ImageIcon p_def, ImageIcon p_hover, ImageIcon p_pressed, ImageIcon p_clicked, final Action action) {
		this.def = p_def;
		this.hover = p_hover;
		this.pressed = p_pressed;
		this.clicked = p_clicked;
		this.isClicked = false;

		this.language = l;
		this.parent = p;
		
		setIcon(this.def);
		
		// XXX Marco: instead of MouseListener, why not MouseAdapter? So you dont need to override
		// useless methods, MouseAdapter does that automatically u just override what needed
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (!isClicked) {
					isClicked = true;
					parent.language = language;
					parent.setButtons();

					ResourceBundle lang = ci.getLanguageBundle();
					String[] names = {
							"mainFrame.menubar.language.swedish",
							"mainFrame.menubar.language.english",
							"mainFrame.menubar.language.italian"};

					action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, lang.getString(names[language.ordinal()])));
				}
				
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected void setClicked(boolean clicked) {
		this.isClicked = clicked;
	}
}
