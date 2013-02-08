package view.custom_components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;


@SuppressWarnings("serial")
public class PriorityButton extends ImageButton {

	private int value;

	PriorityButton(int p_value, String p_def, String p_hover, String p_pressed, String p_clicked) {
		this.def = new ImageIcon(p_def);
		this.hover = new ImageIcon(p_hover);
		this.pressed = new ImageIcon(p_pressed);
		this.clicked = new ImageIcon(p_clicked);
		this.isClicked = false;

		this.value = p_value;

		setIcon(this.def);
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (isClicked) {
					isClicked = false;

					PriorityBar.value = 0;
					PriorityBar.setButtons();
					
					
					
				}
				else {
					isClicked = true;
					PriorityBar.value = value;
					PriorityBar.setButtons();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				PriorityBar.setButtons(value);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				PriorityBar.setButtons();
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
