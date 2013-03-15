package view.new_task_dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Category;
import model.Task;

import utility.GlobalValues;
import view.custom_components.DatePicker;
import view.custom_components.PriorityBar;

import control.ControllerInterface;

/**
 * 
 * This class represents the add new task window with the
 *function to add information to the new task.
 * 
 * @author Madde & Kadir
 * 
 */
@SuppressWarnings("serial")
public class NewTaskDialog extends JDialog implements Observer {

	private ResourceBundle languageBundle;
	private JTextField nameField;
	private JTextArea descriptionField;
	private DatePicker datePicker;
	private JComboBox<String> cmbCategory;
	private PriorityBar bar; //the custom made "stop/redlight" priority bar

	public NewTaskDialog() {
		super(); //Initialize the superclass J Diaolog  
		languageBundle = ControllerInterface.getLanguageBundle(); //language suppport

		setTitle(languageBundle.getString("newTaskDialog.title"));

		JPanel pane = (JPanel) getContentPane();
		pane.setBackground(Color.WHITE);

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); //GridbagConstraints uses together with the item
		c.fill = GridBagConstraints.HORIZONTAL; // fill the whole Jpanel horizontal 

		nameField = new JTextField();
		nameField.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.name")));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		pane.add(nameField, c);

		descriptionField = new JTextArea();
		descriptionField.setBorder(BorderFactory
				.createTitledBorder(languageBundle
						.getString("task.taskInput.desc")));
		descriptionField.setRows(3);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		
		// Line wrapping for usability to see the whole description 
		descriptionField.setLineWrap(true); 
		descriptionField.setWrapStyleWord(true);
		pane.add(descriptionField, c);

		datePicker = new DatePicker();
		datePicker.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.date")));
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		pane.add(datePicker, c);
		
		//Creating and list the categories 
		cmbCategory = new JComboBox<String>();
		
		for (Category ca : ControllerInterface.getCategories().values())
			cmbCategory.addItem(ca.getName());

		cmbCategory.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.category")));
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		cmbCategory.setBackground(Color.WHITE);
		pane.add(cmbCategory, c);

		bar = new PriorityBar(Task.Priority.NOT_SET);
		bar.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.priority")));
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		bar.setBackground(Color.WHITE);
		pane.add(bar, c);

		JButton button = new JButton(
				languageBundle.getString("newTaskDialog.cancel"));
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 0; // aligned with button 2
		c.gridy = 5; // third row
		c.gridwidth = 1;
		pane.add(button, c);

		button = new JButton(languageBundle.getString("newTaskDialog.ok"));

		// This action saves the new task 
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String name = nameField.getText();

				String description = descriptionField.getText();

				Date date = datePicker.getDate();

				Task.Priority priority = bar.getPriority();

				boolean completed = false;

				String categoryName = ((String) cmbCategory.getSelectedItem());

				ControllerInterface.addTask(name, date, priority, completed,
						categoryName, description);

				dispose();
			}
		});

		//Position for OK button 
		c.weighty = 1.0; // request any extra vertical space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 1; 
		c.gridy = 5; 
		c.gridwidth = 1;
		pane.add(button, c);

		pack();
		setVisible(true);
		// The minimum height and width are approximated dynamically 
		int minHeight = 0;
		minHeight += nameField.getHeight();
		minHeight += descriptionField.getHeight();
		minHeight += nameField.getHeight();
		minHeight += datePicker.getHeight();

		minHeight += cmbCategory.getHeight();
		minHeight += button.getHeight();
		minHeight *= 1.2;

		int minWidth = 0;
		minWidth += nameField.getWidth();
		minWidth *= 1.2;

		setPreferredSize(new Dimension(minWidth, minHeight));
		setMinimumSize(new Dimension(minWidth, minHeight));
		setResizable(false);

		// Retrieve last (main frame) size from state
		double sizeX = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINXSIZEKEY));
		double sizeY = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINYSIZEKEY));

		// retrieve last (main frame) location from state
		double posX = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINXPOSKEY));
		double posY = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINYPOSKEY));
		// location is set to the centre of the main frame
		setLocation((int) (posX + ((sizeX - minWidth) / 2)),
				(int) (posY + ((sizeY - minHeight) / 2)));

	}

	@Override
	public void update(Observable o, Object arg) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			revalidate();
			repaint();
		}
	}
}
