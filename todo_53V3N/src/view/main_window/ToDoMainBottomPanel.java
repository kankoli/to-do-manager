package view.main_window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class ToDoMainBottomPanel extends JPanel {
	
	private JLabel statusLabel;
	
	public ToDoMainBottomPanel() {
		super();
		
		setLayout(new GridBagLayout());		
		setPreferredSize(new Dimension(800, 50));
		setMinimumSize(new Dimension(800, 50));
		setBackground(Color.WHITE);
		
		addStatusLabel();
		
		setVisible(true);
	}
	
	private void addStatusLabel() {
		GridBagConstraints statusLabelCons = new GridBagConstraints();
		statusLabel = new JLabel("STATUS");
		statusLabelCons.weightx = 1;
		statusLabelCons.weighty = 1;
		statusLabelCons.insets = new Insets(10, 50, 0, 0);
		statusLabelCons.anchor = GridBagConstraints.FIRST_LINE_START;
		add(statusLabel, statusLabelCons);
	}

}
