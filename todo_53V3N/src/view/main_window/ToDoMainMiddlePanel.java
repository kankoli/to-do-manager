package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.custom_components.PendingCompletedRadioButtons;

import control.ControllerInterface;

/**
 * This class represents the middle part of the ToDo application
 * 
 * @author Magnus Larsson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ToDoMainMiddlePanel extends JPanel implements Observer {

	private JScrollPane taskPanel;
	private SortingBar sortingBar;
	// private ToDoSortingBar sortingBar;

	private ControllerInterface controller;

	public ToDoMainMiddlePanel(ControllerInterface controller) {
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

		controller.registerAsObserver(this);
	}

	private void addPendingCompletedButtons() {
//		GridBagConstraints pendingCompletedButtonsCons = new GridBagConstraints();
//		ResourceBundle lang = controller.getLanguageBundle();
//
//		pendingButton = new JButton(
//				lang.getString("mainFrame.middlePanel.button.pending.name"));
//		pendingButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
//				Color.darkGray));
//		pendingButton.setOpaque(true);
//		pendingButton.setPreferredSize(new Dimension(100, 50));
//		pendingButton.setMinimumSize(new Dimension(100, 50));
//		pendingCompletedButtonsCons.gridy = 0;
//		pendingCompletedButtonsCons.anchor = GridBagConstraints.LINE_START;
//		add(pendingButton, pendingCompletedButtonsCons);

//		completedButton = new JButton(
//				lang.getString("mainFrame.middlePanel.button.completed.name"));
//		completedButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
//				Color.darkGray));
//		completedButton.setOpaque(true);
//		completedButton.setPreferredSize(new Dimension(100, 50));
//		completedButton.setMinimumSize(new Dimension(100, 50));
//		pendingCompletedButtonsCons.gridx = 1;
//		pendingCompletedButtonsCons.weightx = 1;
//		pendingCompletedButtonsCons.anchor = GridBagConstraints.LINE_START;
		
		
//		add(btns, pendingCompletedButtonsCons);		
		PendingCompletedRadioButtons btns = new PendingCompletedRadioButtons(controller);
		
		add(btns);
	}

	private void addSortingBar() { // ToDoSortingBar will be completely revamped
		GridBagConstraints sortingBarCons = new GridBagConstraints();

		try {
			sortingBar = new SortingBar(controller);
			// sortingBar = new ToDoSortingBar(Arrays.asList("Name", "Date",
			// "Category", "Priority"), Arrays.asList(50, 50, 50, 50), 25);

		} catch (Exception e) {
			e.printStackTrace();
		}

		sortingBarCons.gridy = 1;
		sortingBarCons.weightx = 1;
		sortingBarCons.gridwidth = 2;
		sortingBarCons.anchor = GridBagConstraints.FIRST_LINE_START;
		// sortingBarCons.fill = GridBagConstraints.HORIZONTAL;
		add(sortingBar, sortingBarCons);
	}

	private void addTaskPanel() { // Will be revamped in future release
		GridBagConstraints taskPanelCons = new GridBagConstraints();

		int sizes[] = { 10, 20, 30, 40 }; // TODO Temporary offset values for
											// area sizes of TaskScrollPanel.

		taskPanel = new TaskScrollPanel(sizes, controller);
		taskPanelCons.gridy = 2;
		taskPanelCons.weightx = 0;
		taskPanelCons.weighty = 1;
		taskPanelCons.gridwidth = 2;
		taskPanelCons.fill = GridBagConstraints.BOTH;
		taskPanelCons.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taskPanel, taskPanelCons);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

//		// DataModel.ChangeMessage.
//		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg1;
//
//		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
//			ResourceBundle lang = controller.getLanguageBundle();
//			completedButton.setText(lang
//					.getString("mainFrame.middlePanel.button.completed.name"));
//			pendingButton.setText(lang
//					.getString("mainFrame.middlePanel.button.pending.name"));
//
//		}
	}
}