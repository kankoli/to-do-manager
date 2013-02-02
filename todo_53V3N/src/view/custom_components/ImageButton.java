package view.custom_components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageButton extends JLabel {
	protected ImageIcon def;
	protected ImageIcon hover;
	protected ImageIcon pressed;
	protected ImageIcon clicked;

	protected boolean isClicked;

	ImageButton() {};
	
	public ImageButton(String p_def, String p_hover, String p_pressed, String p_clicked) {
		this.def = new ImageIcon(p_def);
		this.hover = new ImageIcon(p_hover);
		this.pressed = new ImageIcon(p_pressed);
		this.clicked = new ImageIcon(p_clicked);
		this.isClicked = false;

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
