package view.main_window;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import control.ControllerInterface;

import view.custom_components.FlagBar;


public class TestMainWindow extends JFrame {
	
	public static void main(String[] args) {
		// ToDoMainFrame toDoFrame = // XXX Marco: i commented it, do we need a
		// reference to Frame?
		// new ToDoMainFrame(900, 600);
		new TestMainWindow();

	}
	
	public TestMainWindow() {
		super();
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JPanel pnlFrame = new JPanel();
		pnlFrame.setLayout(new GridBagLayout());
		
		JPanel pnlTop = new JPanel(new GridBagLayout());
		pnlTop.setBackground(Color.red);
		JButton btn = new JButton("test");
		pnlTop.add(btn);
		
		JPanel pnlBottom = new JPanel(new GridBagLayout());
		
		JPanel pnlSortBar = new JPanel();
		pnlSortBar.setBackground(Color.yellow);
		JPanel pnlTaskTable = new JPanel();
		pnlTaskTable.setBackground(Color.green);
		JPanel pnlTaskEdit = new JPanel();
		pnlTaskEdit.setBackground(Color.blue);
		JPanel pnlStatus = new JPanel();
		pnlStatus.setBackground(Color.darkGray);
		
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlBottom.add(pnlSortBar, c);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlBottom.add(pnlTaskTable, c);

		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		pnlBottom.add(pnlTaskEdit, c);
		
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		pnlBottom.add(pnlStatus, c);
		
		

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlFrame.add(pnlTop, c);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlFrame.add(pnlBottom, c);
		
		setContentPane(pnlFrame);
		pack();
		setVisible(true);
	}
}
