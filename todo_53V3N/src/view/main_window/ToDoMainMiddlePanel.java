package view.main_window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jdom2.JDOMException;

import model.dbConnector;


public class ToDoMainMiddlePanel extends JPanel {
	
	private JButton pendingButton;
	private JButton completedButton;
	private JScrollPane taskPanel;
	private ToDoSortingBar sortingBar;
	
	public ToDoMainMiddlePanel() {
		super();
		
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
	
	private void addSortingBar() {
		GridBagConstraints sortingBarCons = new GridBagConstraints();
		
		sortingBar = new ToDoSortingBar(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
			}
			
		}, Arrays.asList("Name", "Date", "Category", "Priority"), Arrays.asList(50, 50, 50, 50), 25);
		
		sortingBarCons.gridy = 1;
		sortingBarCons.weightx = 1;
		sortingBarCons.gridwidth = 2;
		sortingBarCons.anchor = GridBagConstraints.FIRST_LINE_START;
		//sortingBarCons.fill = GridBagConstraints.HORIZONTAL;
		add(sortingBar, sortingBarCons);
	}
	
	private void addTaskPanel() {
		GridBagConstraints taskPanelCons = new GridBagConstraints();
		
		int sizes[] = { 10, 20, 30, 40};
		dbConnector db = null;
		try {
			db = new dbConnector();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		taskPanel = new TaskScrollPanel(sizes, db);
		taskPanelCons.gridy = 2;
		taskPanelCons.weightx = 0;
		taskPanelCons.weighty = 1;
		taskPanelCons.gridwidth = 2;
		taskPanelCons.fill = GridBagConstraints.BOTH;
		taskPanelCons.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taskPanel, taskPanelCons);
	}

}
