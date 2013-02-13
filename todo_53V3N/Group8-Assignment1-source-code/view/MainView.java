package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * 
 * @author Emma Rangert
 *
 */

@SuppressWarnings("serial")

public class MainView extends JFrame {
	
	private static int windowHeight = 600;
	private static int windowWidth = 500;
	
	private static void addMenuBar(JFrame frame) {
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu file = new JMenu("File");
		menuBar.add(file);
		JMenu edit = new JMenu("Edit");
		menuBar.add(edit);
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		
	}
	
	private static void addComponentsToPane(Container pane) {
		
		
		// Labels
		JLabel item = new JLabel("Item");
		JLabel category = new JLabel("Category");
		JLabel priority = new JLabel("Priority");
	    JLabel deadline = new JLabel("Deadline");
		
		// Table
		JTable table = new JTable();
		
		// Scroll pane
	    ScrollPane scrollPane = new ScrollPane();

	    // Panels
	    JPanel northPanel = new JPanel();
	    JPanel southPanel = new JPanel();
	    
	    // Buttons
	    JButton addBtn = new JButton("Add");
	    //TODO: tmp for presentation
	    addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditTaskFrame editview = new EditTaskFrame("Edit tasks");
				editview.setSize(400,400);
				editview.setVisible(true);
			}
		});
	   
	    // Text fields
	    JTextField inputFld = new JTextField();
	    
	    pane.setLayout(new BorderLayout());
	    scrollPane.add(table);
	    
	    // Add to pane
	    pane.add(scrollPane, BorderLayout.CENTER);
		pane.add(northPanel, BorderLayout.NORTH);
	    pane.add(southPanel, BorderLayout.SOUTH);
	    
	    // Set layouts
	    southPanel.setLayout(new BorderLayout());
	    northPanel.setLayout(new GridLayout());
	    
	    // Setting background color for help
        //northPanel.setBackground(Color.ORANGE);
        //southPanel.setBackground(Color.GREEN);
        //scrollPane.setBackground(Color.YELLOW);
	    
	    inputFld.setHorizontalAlignment(JTextField.LEFT);

	    // Adding to southPanel
	    southPanel.add(inputFld, BorderLayout.CENTER);
	    southPanel.add(addBtn, BorderLayout.EAST);
        southPanel.setBorder(BorderFactory.createTitledBorder("Add to do"));

	    // Adding to northPanel
	    northPanel.add(item);
	    northPanel.add(category);
	    northPanel.add(priority);
	    northPanel.add(deadline);
	    northPanel.setBorder(BorderFactory.createTitledBorder("To do's"));
	    
	    table.setBorder(BorderFactory.createTitledBorder(""));	    
	}

//	private static void createAndShowGUI() {
	public void createAndShowGUI() {
		
		JFrame frame = new JFrame("The Greight TODO Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth, windowHeight);
		
		addComponentsToPane(frame.getContentPane());
		addMenuBar(frame);
		
		frame.setVisible(true);
		frame.pack();
    }
	
//	public static void main(String[] args) {
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
}
