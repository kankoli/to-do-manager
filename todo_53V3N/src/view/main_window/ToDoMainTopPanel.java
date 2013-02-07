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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.new_task_dialog.NewTaskDialog;


public class ToDoMainTopPanel extends JPanel {
	
	//private JPanel leftPanel;
	//private JPanel middlePanel;
	//private JPanel rightPanel;
	
	private JButton urgentButton;
	private JLabel timeLabel;
	private JButton newTaskButton;
	
	public ToDoMainTopPanel() {
		super();
		
		setLayout(new GridBagLayout()); //BOXLAYOUT?
	
		addUrgentButton();
		addTimeLabel();
		addNewTaskButton();
		
		setBackground(Color.WHITE);
		
		setPreferredSize(new Dimension(800, 100));
		setMinimumSize(new Dimension(800, 100));
		setVisible(true);
	}
	
	private void addUrgentButton() {
		GridBagConstraints urgentButtonCons = new GridBagConstraints();
		urgentButton = new JButton("Urgent");
		urgentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane
				.showMessageDialog(
						null,
						"This button will show urgent tasks");
			}
		});
		urgentButton.setMinimumSize(new Dimension(100, 100));
		urgentButton.setPreferredSize(new Dimension(100, 100));
		urgentButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
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
	
	private void addTimeLabel() {
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
		newTaskButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showDialog();
			}
		});
		newTaskButton.setMinimumSize(new Dimension(100, 100));
		newTaskButton.setPreferredSize(new Dimension(100, 100));
		newTaskButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
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
	
	private void showDialog() {

		JDialog dialog = new JDialog();
		NewTaskDialog.addComponentsToPane(dialog.getContentPane());
		
		//OR, you can do the following...
		//JDialog dialog = new JDialog();
		//dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		dialog.setBounds(350, 350, 220, 220);
		dialog.setVisible(true);
	}
	
	/*private void addLeftPanel() {
		GridBagConstraints leftPanelCons = new GridBagConstraints();
		leftPanel = new JPanel(new GridBagLayout()); //USE ANOTHER LAYOUT? 
		leftPanel.setMinimumSize(new Dimension(200, 100));
		leftPanel.setPreferredSize(new Dimension(200, 100));
		leftPanel.setBackground(Color.WHITE);
		leftPanelCons.gridx = 0;
		leftPanelCons.gridy = 0;
		leftPanelCons.weightx = 0;
		leftPanelCons.weighty = 0;
		leftPanelCons.anchor = GridBagConstraints.WEST;
		leftPanelCons.fill = GridBagConstraints.HORIZONTAL;
		add(leftPanel, leftPanelCons);
	}*/
	
	/*private void addMiddlePanel() {
		GridBagConstraints middlePanelCons = new GridBagConstraints();
		middlePanel = new JPanel(new GridBagLayout()); //USE ANOTHER LAYOUT?
		middlePanel.setMinimumSize(new Dimension(200, 100));
		middlePanel.setPreferredSize(new Dimension(200, 100));
		middlePanel.add(new JLabel("你好"));
		middlePanel.setBackground(Color.WHITE);
		middlePanelCons.gridx = 1;
		middlePanelCons.gridy = 0;
		middlePanelCons.weightx = 0;
		middlePanelCons.weighty = 0;
		middlePanelCons.anchor = GridBagConstraints.WEST;
		middlePanelCons.fill = GridBagConstraints.HORIZONTAL;
		add(middlePanel, middlePanelCons);
	}*/

	/*private void addRightPanel() {
		GridBagConstraints rightPanelCons = new GridBagConstraints();
		rightPanel = new JPanel(new GridBagLayout()); //USE ANOTHER LAYOUT?
		rightPanel.setMinimumSize(new Dimension(200, 100));
		rightPanel.setPreferredSize(new Dimension(200, 100));
		rightPanel.setBackground(Color.WHITE);
		rightPanelCons.gridx = 2;
		rightPanelCons.gridy = 0;
		rightPanelCons.weightx = 1;
		rightPanelCons.weighty = 0;
		rightPanelCons.anchor = GridBagConstraints.WEST;
		rightPanelCons.fill = GridBagConstraints.HORIZONTAL;
		add(rightPanel, rightPanelCons);
	}*/
	
	
}
