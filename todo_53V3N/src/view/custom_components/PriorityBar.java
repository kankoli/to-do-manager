package view.custom_components;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.ControllerInterface;

import model.Task;

/**
 * Implements the priority bars for tasks.
 * @author Kadir & Madelen
 *
 */
@SuppressWarnings("serial")
public class PriorityBar extends JPanel {


	protected static ImageIcon def = new ImageIcon(
			ControllerInterface.getResource("assets/prio_def.png"));
	protected static ImageIcon hover1 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_1_hover.png"));
	protected static ImageIcon pressed1 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_1_set.png"));
	protected static ImageIcon clicked1 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_1_set.png"));
	protected static ImageIcon hover2 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_2_hover.png"));
	protected static ImageIcon pressed2 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_2_set.png"));
	protected static ImageIcon clicked2 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_2_set.png"));
	protected static ImageIcon hover3 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_3_hover.png"));
	protected static ImageIcon pressed3 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_3_set.png"));
	protected static ImageIcon clicked3 = new ImageIcon(
			ControllerInterface.getResource("assets/prio_3_set.png"));

	protected Task.Priority prio;

	private PriorityButton btn1;
	private PriorityButton btn2;
	private PriorityButton btn3;

	public PriorityBar(Task.Priority prio) {

		this.prio = prio;

		// The buttons are created with corresponding images.
		btn1 = new PriorityButton(this, Task.Priority.LOW, def, hover1,
				pressed1, clicked1);
		btn2 = new PriorityButton(this, Task.Priority.NORMAL, def, hover2,
				pressed2, clicked2);
		btn3 = new PriorityButton(this, Task.Priority.HIGH, def, hover3,
				pressed3, clicked3);

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(0);
		setLayout(flowLayout);

		add(btn1);
		add(btn2);
		add(btn3);

		setButtons();
	}
	
	/**
	 * Sets the button images according to the selected priority.
	 */
	public void setButtons() {

		if (!isEnabled())
			return;

		switch (prio) {
		case NOT_SET:
			btn1.setIcon(def);
			btn2.setIcon(def);
			btn3.setIcon(def);
			btn1.setClicked(false);
			btn2.setClicked(false);
			btn3.setClicked(false);
			break;
		case LOW:
			btn1.setIcon(clicked1);
			btn2.setIcon(def);
			btn3.setIcon(def);
			btn1.setClicked(true);
			btn2.setClicked(false);
			btn3.setClicked(false);
			break;
		case NORMAL:
			btn1.setIcon(clicked2);
			btn2.setIcon(clicked2);
			btn3.setIcon(def);
			btn1.setClicked(false);
			btn2.setClicked(true);
			btn3.setClicked(false);
			break;
		case HIGH:
			btn1.setIcon(clicked3);
			btn2.setIcon(clicked3);
			btn3.setIcon(clicked3);
			btn1.setClicked(false);
			btn2.setClicked(false);
			btn3.setClicked(true);
			break;
		default:
			System.out.println("Whaaa");
			break;
		}
	}
	
	/**
	 * Sets the hover button images according to the given priority.
	 */
	protected void setHoverButtons(Task.Priority tp) {
		if (!isEnabled())
			return;

		switch (tp) {
		case NOT_SET:
			btn1.setIcon(def);
			btn2.setIcon(def);
			btn3.setIcon(def);
			break;
		case LOW:
			btn1.setIcon(hover1);
			btn2.setIcon(def);
			btn3.setIcon(def);
			break;
		case NORMAL:
			btn1.setIcon(hover2);
			btn2.setIcon(hover2);
			btn3.setIcon(def);
			break;
		case HIGH:
			btn1.setIcon(hover3);
			btn2.setIcon(hover3);
			btn3.setIcon(hover3);
			break;
		default:
			System.out.println("Whaaa");
			break;
		}
	}

	public Task.Priority getPriority() {
		return this.prio;
	}

	public void setPriority(Task.Priority prio) {
		this.prio = prio;
		setButtons();
	}
}
