package view.main_window;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.awt.*;


public class ToDoMainFrame extends JFrame {
	
	private JPanel basePanel;
	private ToDoMainTopPanel topPanel;
	private ToDoMainMiddlePanel middlePanel;
	private ToDoMainBottomPanel bottomPanel;
	
	public ToDoMainFrame(int width, int height) {
		super();
		
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		
		basePanel = new JPanel();
		basePanel.setBackground(Color.white);
		basePanel.setLayout(new GridBagLayout());
		basePanel.setPreferredSize(new Dimension(width, height));
		
		addTopPanel();
		addMiddlePanel();
		addBottomPanel();

		//getContentPane().add(basePanel); 
		setContentPane(basePanel);
      
		pack();                                    
        setVisible(true);                          
        setDefaultCloseOperation(EXIT_ON_CLOSE); //OVERRIDE WTIH CUSTOM CODE TO SAVE ETC.
	}
	
	public static void main(String[] args) {
		ToDoMainFrame toDoFrame = new ToDoMainFrame(800, 600);
	}

	private void addTopPanel() {
		GridBagConstraints topCons = new GridBagConstraints();
		topPanel = new ToDoMainTopPanel();
		topCons.gridx = 0;
		topCons.gridy = 0;
		topCons.weightx = 1;
		topCons.weighty = 0;
		topCons.fill = GridBagConstraints.BOTH;
		basePanel.add(topPanel, topCons);
	}
	
	private void addMiddlePanel() {
		GridBagConstraints middleCons = new GridBagConstraints();
		middlePanel = new ToDoMainMiddlePanel();
		middleCons.gridx = 0;
		middleCons.gridy = 1;
		middleCons.weightx = 1;
		middleCons.weighty = 1;
		middleCons.insets = new Insets(0, 10, 0, 10);
		middleCons.fill = GridBagConstraints.BOTH;
		basePanel.add(middlePanel, middleCons);
	}
	
	private void addBottomPanel() {
		GridBagConstraints bottomCons = new GridBagConstraints();
		bottomPanel = new ToDoMainBottomPanel();
		bottomCons.gridx = 0;
		bottomCons.gridy = 2;
		bottomCons.weightx = 1;
		bottomCons.weighty = 0;
		bottomCons.fill = GridBagConstraints.BOTH;
		basePanel.add(bottomPanel, bottomCons);
	}
}
