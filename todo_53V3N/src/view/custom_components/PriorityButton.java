package view.custom_components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import model.Task;

/**
 * A single priority button used in PriorityBar class.
 * @author Kadir & Madelen
 *
 */
@SuppressWarnings("serial")
public class PriorityButton extends ImageButton {

	private Task.Priority prio;
	private PriorityBar parent;

	PriorityButton(PriorityBar p, Task.Priority tp, ImageIcon p_def, ImageIcon p_hover, ImageIcon p_pressed, ImageIcon p_clicked) {
		this.def = p_def;
		this.hover = p_hover;
		this.pressed = p_pressed;
		this.clicked = p_clicked;
		this.isClicked = false;

		this.prio = tp; // The priority value of the button is set.
		this.parent = p; // The parent object is kept.
		
		setOpaque(false);
		setIcon(this.def);
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!isClicked) { // If the button is not already clicked
					isClicked = true;
					// set parent priority and update buttons.
					parent.prio = prio;
					parent.setButtons();
				}
				else { // If it was clicked before
					isClicked = false;
					
					// Un-set the parent priority and update buttons.
					parent.prio = Task.Priority.NOT_SET;
					parent.setButtons();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				parent.setHoverButtons(prio);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				parent.setButtons();
			}

			@Override
			public void mousePressed(MouseEvent arg0) { }

			@Override
			public void mouseReleased(MouseEvent arg0) { }
		});
	}

	protected void setClicked(boolean clicked) {
		this.isClicked = clicked;
	}
}
