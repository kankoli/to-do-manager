package view.custom_components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import model.Task;


@SuppressWarnings("serial")
public class PriorityButton extends ImageButton {

	private Task.Priority prio;
	private PriorityBar parent;

	PriorityButton(PriorityBar p, Task.Priority tp, String p_def, String p_hover, String p_pressed, String p_clicked) {
		this.def = new ImageIcon(p_def);
		this.hover = new ImageIcon(p_hover);
		this.pressed = new ImageIcon(p_pressed);
		this.clicked = new ImageIcon(p_clicked);
		this.isClicked = false;

		this.prio = tp;
		this.parent = p;
		
		setIcon(this.def);
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (isClicked) {
					isClicked = false;

					parent.prio = Task.Priority.NOT_SET;
					parent.setButtons();
					
					
					
				}
				else {
					isClicked = true;
					parent.prio = prio;
					parent.setButtons();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				parent.setButtons(prio);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				parent.setButtons();
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
