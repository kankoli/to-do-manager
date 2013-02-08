package view.custom_components;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PriorityBar extends JPanel {

	

	protected static int value;
	

	// XXX Marco note to Kadir and Madelen: Why using static variables? Im just being curious because i see few warnings from compiler
	// there, i tried to solve them but was getting too long, i didnt change.. it seems like is because we r using
	// static variables and not instance variables, there is good reason? Just to know!

	// Compiler gives this warning, for all fields below
	//The static field PriorityBar.hover2 should be accessed in a static way	PriorityBar.java	/todo_53V3N/src/view/custom_components	line 38	Java Problem
	// ...

	protected static ImageIcon def;
	protected static ImageIcon hover1;
	protected static ImageIcon pressed1;
	protected static ImageIcon clicked1;
	protected static ImageIcon hover2;
	protected static ImageIcon pressed2;
	protected static ImageIcon clicked2;
	protected static ImageIcon hover3;
	protected static ImageIcon pressed3;
	protected static ImageIcon clicked3;
	
	private static PriorityButton btn1;
	private static PriorityButton btn2;
	private static PriorityButton btn3;
	
	public PriorityBar(String def,
			String hover1, String pressed1, String clicked1, 
			String hover2, String pressed2, String clicked2,
			String hover3, String pressed3, String clicked3) {
		value = 0;
		
		this.def = new ImageIcon(def);
		this.hover1 = new ImageIcon(hover1);
		this.pressed1 = new ImageIcon(pressed1);
		this.clicked1 = new ImageIcon(clicked1);
		this.hover2 = new ImageIcon(hover2);
		this.pressed2 = new ImageIcon(pressed2);
		this.clicked2 = new ImageIcon(clicked2);
		this.hover3 = new ImageIcon(hover3);
		this.pressed3 = new ImageIcon(pressed3);
		this.clicked3 = new ImageIcon(clicked3);
		
		btn1 = new PriorityButton(1, def, hover1, pressed1, clicked1);
		btn2 = new PriorityButton(2, def, hover2, pressed2, clicked2);
		btn3 = new PriorityButton(3, def, hover3, pressed3, clicked3);
		
		FlowLayout flowLayout = new FlowLayout();
		setLayout(flowLayout);

		add(btn1);
		add(btn2);
		add(btn3);
	}
	
	
	protected static void setButtons() {
		if (value == 0) {
			btn1.setIcon(def);
			btn2.setIcon(def);
			btn3.setIcon(def);
			btn1.setClicked(false);
			btn2.setClicked(false);
			btn3.setClicked(false);
		}
		else if (value == 1) {
			btn1.setIcon(clicked1);
			btn2.setIcon(def);
			btn3.setIcon(def);
			btn1.setClicked(true);
			btn2.setClicked(false);
			btn3.setClicked(false);
		}
		else if (value == 2) {
			btn1.setIcon(clicked2);
			btn2.setIcon(clicked2);
			btn3.setIcon(def);
			btn1.setClicked(false);
			btn2.setClicked(true);
			btn3.setClicked(false);
		}
		else if (value == 3) {
			btn1.setIcon(clicked3);
			btn2.setIcon(clicked3);
			btn3.setIcon(clicked3);
			btn1.setClicked(false);
			btn2.setClicked(false);
			btn3.setClicked(true);
		}
		else {
			System.out.println("Whaaa");
		}
	}

	protected static void setButtons(int hoverValue) {
		if (hoverValue == 0) {
			btn1.setIcon(def);
			btn2.setIcon(def);
			btn3.setIcon(def);
		}
		else if (hoverValue == 1) {
			btn1.setIcon(hover1);
			btn2.setIcon(def);
			btn3.setIcon(def);
		}
		else if (hoverValue == 2) {
			btn1.setIcon(hover2);
			btn2.setIcon(hover2);
			btn3.setIcon(def);
		}
		else if (hoverValue == 3) {
			btn1.setIcon(hover3);
			btn2.setIcon(hover3);
			btn3.setIcon(hover3);
		}
		else {
			System.out.println("Whaaa");
		}
	}
}
