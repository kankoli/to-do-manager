package view.custom_components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/***
 * Implements an image button extending a JLabel. An image button gets four images for four implemented states:
 * Default, Hover, Pressed, Clicked.
 * 
 * @author Kadir & Madelen
 *
 */
@SuppressWarnings("serial")
public class ImageButton extends JLabel {
	protected ImageIcon def;
	protected ImageIcon hover;
	protected ImageIcon pressed;
	protected ImageIcon clicked;

	protected boolean isClicked;

	ImageButton() {};
	
	/**
	 * Constructor.
	 * The images are stored and an action listener to change through images is added.
	 * 
	 * @param p_def - Default image
	 * @param p_hover - Hover image
	 * @param p_pressed - Pressed image
	 * @param p_clicked - Clicked image
	 */
	public ImageButton(ImageIcon p_def, ImageIcon p_hover, ImageIcon p_pressed, ImageIcon p_clicked) {
		this.def = p_def;
		this.hover = p_hover;
		this.pressed = p_pressed;
		this.clicked = p_clicked;
		this.isClicked = false; // clicked bool is initialized.
		
		setIcon(this.def);
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (isClicked) {
					isClicked = false;
					setIcon(def);
				}
				else {
					isClicked = true;
					setIcon(clicked);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setIcon(hover);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				returnToDefault();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setIcon(pressed);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setIcon(def);
			}
		});
	}


	private void returnToDefault() {
		if (isClicked)
			setIcon(clicked);
		else {
			setIcon(def);
		}
	}
}
