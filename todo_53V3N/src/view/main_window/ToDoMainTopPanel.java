package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.ControllerInterface;

import view.new_task_dialog.NewTaskDialog;

/**
 * This class represents the top part of the ToDo application
 * 
 * @author Magnus Larsson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ToDoMainTopPanel extends JPanel {

	private JButton urgentButton; // to launch a new frame which shows urgent
									// tasks
	private JLabel timeLabel; // placeholder for a clock
	private JButton newTaskButton;

	private ControllerInterface controller;
	
	//private JFrame newTaskFrame; // XXX Marco: i dont like this here, read below

	public ToDoMainTopPanel(ControllerInterface controller) {
		super();
		this.controller = controller;

		setLayout(new GridBagLayout());

		addUrgentButton();
		addTimeLabel();
		addNewTaskButton();

		setBackground(Color.WHITE);

		setPreferredSize(new Dimension(800, 100));
		setMinimumSize(new Dimension(800, 100));

		//newTaskFrame = new JFrame();

		setVisible(true);
	}

	private void addUrgentButton() {
		GridBagConstraints urgentButtonCons = new GridBagConstraints();
		urgentButton = new JButton("Urgent");
		urgentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) { // This will in
															// future launch a
															// new frame to show
															// urgent tasks
				JOptionPane.showMessageDialog(null,
						"This button will show urgent tasks");
			}
		});
		urgentButton.setMinimumSize(new Dimension(100, 100));
		urgentButton.setPreferredSize(new Dimension(100, 100));
		urgentButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		urgentButton.setBackground(Color.WHITE);
		urgentButton.setOpaque(true);
		urgentButtonCons.gridx = 0;
		urgentButtonCons.gridy = 0;
		urgentButtonCons.weightx = 0;
		urgentButtonCons.weighty = 0;
		urgentButtonCons.insets = new Insets(0, 10, 0, 0);
		urgentButtonCons.anchor = GridBagConstraints.FIRST_LINE_START;
		add(urgentButton, urgentButtonCons);
	}

	private void addTimeLabel() { // Will be a clock instead in the future
		GridBagConstraints timeLabelCons = new GridBagConstraints();
		timeLabel = new JLabel("It's late! 睡觉吧！");
		timeLabel.setMinimumSize(new Dimension(200, 100));
		timeLabel.setPreferredSize(new Dimension(200, 100));
		timeLabel.setFont(new Font(null, Font.BOLD, 40));
		timeLabel.setBackground(Color.WHITE);
		timeLabel.setOpaque(true);
		timeLabelCons.gridx = 1;
		timeLabelCons.gridy = 0;
		timeLabelCons.weightx = 1;
		timeLabelCons.weighty = 0;
		timeLabelCons.anchor = GridBagConstraints.CENTER;
		add(timeLabel, timeLabelCons);
	}

	private void addNewTaskButton() {
		GridBagConstraints newTaskButtonCons = new GridBagConstraints();
		newTaskButton = new JButton("New Task");

		newTaskButton.setAction(controller.getAction(ControllerInterface.ActionName.NEWTASK));

		
		newTaskButton.setMinimumSize(new Dimension(100, 100));
		newTaskButton.setPreferredSize(new Dimension(100, 100));
		newTaskButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		newTaskButton.setBackground(Color.WHITE);
		newTaskButton.setOpaque(true);
		newTaskButtonCons.gridx = 2;
		newTaskButtonCons.gridy = 0;
		newTaskButtonCons.weightx = 0;
		newTaskButtonCons.weighty = 0;
		newTaskButtonCons.insets = new Insets(0, 0, 0, 10);
		newTaskButtonCons.anchor = GridBagConstraints.FIRST_LINE_END;
		add(newTaskButton, newTaskButtonCons);
	}
}