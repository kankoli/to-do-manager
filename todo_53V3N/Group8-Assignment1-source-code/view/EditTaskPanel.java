package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

/**
 * The add/edit task panel.
 * @author Mattias
 */
@SuppressWarnings("serial")
public class EditTaskPanel extends JPanel {
    
    //Labels
    private JLabel titleLabel            = new JLabel("Title:");
    private JLabel dateLabel             = new JLabel("Date:");
    private JLabel timeLabel             = new JLabel("Time:");
    private JLabel descriptionLabel      = new JLabel("Description:");
    private JLabel categoryLabel         = new JLabel("Category:");
    private JLabel reminderLabel         = new JLabel("Reminder:");
    private JLabel priorityLabel         = new JLabel("Priority:");

    //TextFields
    private JTextField titleField        = new JTextField(10);
    private JTextField dateFromField     = new JTextField(5);
    private JTextField dateToField       = new JTextField(5);
    private JTextField startTimeField    = new JTextField(5);
    private JTextField endTimeField      = new JTextField(5);
    private JTextField categoryField     = new JTextField(5);
    private JTextField remStartField     = new JTextField(5);
    private JTextField remEndField       = new JTextField(5);
    
    //Text area
    private JTextArea descriptionArea    = new JTextArea(0,0);
    private JScrollPane	scrollArea 		 = new JScrollPane(descriptionArea);
    
    //Buttons
    private JButton saveButton           = new JButton("Save");
    private JButton cancelButton         = new JButton("Cancel");        

    //Priority JRadioButtons
    private ButtonGroup priorityGroup    = new ButtonGroup();
    private JRadioButton lowPriority     = new JRadioButton("Low", true);
    private JRadioButton mediumPriority  = new JRadioButton("Medium", false);
    private JRadioButton highPriority    = new JRadioButton("High", false);

    //Extra Panels
    private JPanel datePanel			 = new JPanel();
    private JPanel timePanel			 = new JPanel();
    private JPanel categoryPanel		 = new JPanel();
    private JPanel bottomPanel           = new JPanel();
    private JPanel bottomLabelPanel		 = new JPanel();
    private JPanel priorityPanel	 	 = new JPanel();
    private JPanel reminderPanel		 = new JPanel();
    private JPanel subBottomPanel		 = new JPanel();
    private JPanel bottomCenterPanel	 = new JPanel();
    private JPanel topPanel              = new JPanel();
    private JPanel subTopPanel			 = new JPanel();
    private JPanel topLabelPanel		 = new JPanel();
    private JPanel topCenterPanel 	     = new JPanel();
    private JPanel buttonPanel			 = new JPanel();
    
    //JDates
    private JDateChooser reminderCal	 = new JDateChooser();
    private JDateChooser fromDateCal	 = new JDateChooser();
    private JDateChooser toDateCal		 = new JDateChooser();
    
    EditTaskPanel(String borderTitle){

    	//Setup the different layoutManagers
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(borderTitle));
        topPanel.setLayout(new BorderLayout());
        subTopPanel.setLayout(new BorderLayout());
        topLabelPanel.setLayout(new BoxLayout(topLabelPanel, BoxLayout.PAGE_AXIS));
        topCenterPanel.setLayout(new BoxLayout(topCenterPanel,BoxLayout.PAGE_AXIS));
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.LINE_AXIS));
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.LINE_AXIS));
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.LINE_AXIS));
        bottomPanel.setLayout(new BorderLayout());
        bottomLabelPanel.setLayout(new BoxLayout(bottomLabelPanel, BoxLayout.PAGE_AXIS));
        bottomCenterPanel.setLayout(new BoxLayout(bottomCenterPanel, BoxLayout.PAGE_AXIS));
        subBottomPanel.setLayout(new BorderLayout());
        priorityPanel.setLayout(new BoxLayout(priorityPanel, BoxLayout.LINE_AXIS));
        reminderPanel.setLayout(new BoxLayout(reminderPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        
        //Configuration for topLabelPanel
        topLabelPanel.add(Box.createRigidArea(new Dimension(0,10)));
        topLabelPanel.add(titleLabel);
        topLabelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        topLabelPanel.add(dateLabel);
        topLabelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        topLabelPanel.add(timeLabel);
        topLabelPanel.add(Box.createRigidArea(new Dimension(0,10)));
        topLabelPanel.add(descriptionLabel);
        
        
        //Configuration for datePanel
        datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        datePanel.add(Box.createRigidArea(new Dimension(5,0)));
        datePanel.add(Box.createRigidArea(new Dimension(5,0)));
        datePanel.add(fromDateCal);
        datePanel.add(Box.createRigidArea(new Dimension(5,0)));
        datePanel.add(new JLabel("To"));
        datePanel.add(Box.createRigidArea(new Dimension(5,0)));
        datePanel.add(Box.createRigidArea(new Dimension(5,0)));
        datePanel.add(toDateCal);
        datePanel.add(Box.createRigidArea(new Dimension(60,0)));
        
        
        //Configuration for timePanel
        timePanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        timePanel.add(startTimeField);
        timePanel.add(Box.createRigidArea(new Dimension(10,0)));
        timePanel.add(new JLabel("-"));
        timePanel.add(Box.createRigidArea(new Dimension(10,0)));
        timePanel.add(endTimeField);
        
        
        //Configuration for category panel
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createRigidArea(new Dimension(10,0)));
        categoryPanel.add(categoryField);
        
        
        //Configuration for topCenterPanel
        topCenterPanel.add(Box.createRigidArea(new Dimension(0,8)));
        titleField.setAlignmentX(Component.RIGHT_ALIGNMENT);
        topCenterPanel.add(titleField);
        topCenterPanel.add(Box.createRigidArea(new Dimension(0,2)));
        topCenterPanel.add(datePanel);
        topCenterPanel.add(Box.createRigidArea(new Dimension(0,2)));
        topCenterPanel.add(timePanel);
        topCenterPanel.add(Box.createRigidArea(new Dimension(0,2)));
        topCenterPanel.add(categoryPanel);
        topCenterPanel.add(Box.createRigidArea(new Dimension(0,5)));

        
        //Configuration for bottomLabels
        bottomLabelPanel.add(Box.createRigidArea(new Dimension(0,10)));
        bottomLabelPanel.add(reminderLabel);
        bottomLabelPanel.add(Box.createRigidArea(new Dimension(0,5)));
        bottomLabelPanel.add(priorityLabel);
        
        
        //Configuration for reminderPanel
        reminderPanel.add(Box.createRigidArea(new Dimension(5,0)));
        reminderPanel.add(reminderCal);
        reminderPanel.add(Box.createRigidArea(new Dimension(5,0)));
        reminderPanel.add(remStartField);
        reminderPanel.add(Box.createRigidArea(new Dimension(5,0)));
        reminderPanel.add(new JLabel("-"));
        reminderPanel.add(Box.createRigidArea(new Dimension(5,0)));
        reminderPanel.add(remEndField);
        
        
        //Configuration for priorityPanel
        priorityGroup.add(lowPriority);
        priorityGroup.add(mediumPriority);
        priorityGroup.add(highPriority);
        priorityPanel.add(lowPriority);
        priorityPanel.add(Box.createRigidArea(new Dimension(5,0)));
        priorityPanel.add(mediumPriority);
        priorityPanel.add(Box.createRigidArea(new Dimension(5,0)));
        priorityPanel.add(highPriority);

        
        //Configuration for buttonPanel
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(30,0)));
        buttonPanel.add(cancelButton);
       
        
        //Configuration for bottomCenterPanel
        bottomCenterPanel.add(Box.createRigidArea(new Dimension(0,10)));
        bottomCenterPanel.add(reminderPanel);
        bottomCenterPanel.add(priorityPanel);
        bottomCenterPanel.add(buttonPanel);
        
        
        //Add everything together in the topPanel and bottomPanel
        subTopPanel.add(topLabelPanel,BorderLayout.LINE_START);
        subTopPanel.add(topCenterPanel,BorderLayout.CENTER);
        topPanel.add(subTopPanel,BorderLayout.PAGE_START);
        topPanel.add(Box.createRigidArea(new Dimension(0,5)));
        descriptionArea.setLineWrap(true);
        topPanel.add(scrollArea, BorderLayout.CENTER);
        subBottomPanel.add(bottomLabelPanel, BorderLayout.LINE_START);
        subBottomPanel.add(bottomCenterPanel,BorderLayout.CENTER);
        //subBottomPanel.add(buttonPanel, BorderLayout.PAGE_END);
        bottomPanel.add(subBottomPanel, BorderLayout.PAGE_END);  /// CENTER????
        
        ////////Configuration for the main panel, EditTaskPanel
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.PAGE_END);
    }
}
