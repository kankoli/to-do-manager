package view.main_window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


import control.Controller;
/**
 * This class represents the middle part of the ToDo application
 * 
 * @author Magnus Larsson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ToDoMainMiddlePanel extends JPanel {
	
	private JButton pendingButton;
	private JButton completedButton;
	private JScrollPane taskPanel;
	private ToDoSortingBar sortingBar;
	
	private Controller controller;
	
	public ToDoMainMiddlePanel(Controller controller) {
		super();

		this.controller = controller;
		setLayout(new GridBagLayout());
		
		addPendingCompletedButtons();
		addSortingBar();
		addTaskPanel();
		
		setPreferredSize(new Dimension(800, 200));
		setMinimumSize(new Dimension(800, 200));
		setBackground(Color.WHITE);
		setVisible(true);
	}
	
	
	private void addPendingCompletedButtons() {
		GridBagConstraints pendingCompletedButtonsCons = new GridBagConstraints();
		
		pendingButton = new JButton("Pending");
		pendingButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		pendingButton.setOpaque(true);
		pendingButton.setPreferredSize(new Dimension(100, 50));
		pendingButton.setMinimumSize(new Dimension(100, 50));
		pendingCompletedButtonsCons.gridy = 0;
		pendingCompletedButtonsCons.anchor = GridBagConstraints.LINE_START;
		add(pendingButton, pendingCompletedButtonsCons);
		
		completedButton = new JButton("Completed");
		completedButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		completedButton.setOpaque(true);
		completedButton.setPreferredSize(new Dimension(100, 50));
		completedButton.setMinimumSize(new Dimension(100, 50));
		pendingCompletedButtonsCons.gridx = 1;
		pendingCompletedButtonsCons.weightx = 1;
		pendingCompletedButtonsCons.anchor = GridBagConstraints.LINE_START;
		add(completedButton, pendingCompletedButtonsCons);
	}
	
	private void addSortingBar() { //ToDoSortingBar will be completely revamped
		GridBagConstraints sortingBarCons = new GridBagConstraints();
		
		try {
			sortingBar = new ToDoSortingBar(Arrays.asList("Name", "Date", "Category", "Priority"), Arrays.asList(50, 50, 50, 50), 25);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sortingBarCons.gridy = 1;
		sortingBarCons.weightx = 1;
		sortingBarCons.gridwidth = 2;
		sortingBarCons.anchor = GridBagConstraints.FIRST_LINE_START;
		//sortingBarCons.fill = GridBagConstraints.HORIZONTAL;
		add(sortingBar, sortingBarCons);
	}
	
	private void addTaskPanel() { //Will be revamped in future release
		GridBagConstraints taskPanelCons = new GridBagConstraints();
		
		int sizes[] = { 10, 20, 30, 40}; // TODO Temporary offset values for area sizes of TaskScrollPanel.

		
		taskPanel = new TaskScrollPanel(sizes, controller);
		taskPanelCons.gridy = 2;
		taskPanelCons.weightx = 0;
		taskPanelCons.weighty = 1;
		taskPanelCons.gridwidth = 2;
		taskPanelCons.fill = GridBagConstraints.BOTH;
		taskPanelCons.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taskPanel, taskPanelCons);
	}

}