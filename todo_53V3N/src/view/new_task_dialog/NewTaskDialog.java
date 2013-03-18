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

import model.DataModel;
import model.Task;

import utility.GlobalValues;
import view.custom_components.DatePicker;
import view.custom_components.PriorityBar;

import control.ControllerInterface;
import control.ControllerInterface.DateFormat;

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

	private JTextField nameField;
	private JTextArea descriptionField;
	private DatePicker datePicker;
	private JComboBox<String> cmbCategory;
	private PriorityBar bar; //the custom made "stop/redlight" priority bar

	private JButton butOk;
	private JButton butCanc;
	
	ResourceBundle languageBundle;
	
	public NewTaskDialog() {
		super(); //Initialize the superclass J Diaolog  
		languageBundle = ControllerInterface.getLanguageBundle(); //language suppport

		setTitle(languageBundle.getString("newTaskDialog.title"));

		JPanel pane = (JPanel) getContentPane();
		pane.setBackground(Color.WHITE);

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); //GridbagConstraints contain information about how items i placed
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
				.getString("task.taskInput.date")
				+ " "
				+ ControllerInterface.getDateFormat().toPattern()));

		
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		pane.add(datePicker, c);
		
		//Creating and list the categories 
		cmbCategory = new JComboBox<String>();
		
		
		//---------

		// we retrieve the hashtable (containing key - value pair).
		// then we retrieve the keyset (all stored categories name)
		// we add them as item in combobox
//		for (String catName : ControllerInterface.getCategories().keySet())
//			cmbCategory.addItem(catName);
//
//		
//		cmbCategory.setBorder(BorderFactory.createTitledBorder(languageBundle
//				.getString("task.taskInput.category")));
		

		// we retrieve the hashtable (containing key - value pair).
		// then we retrieve the keyset (all stored categories name)
		// we add them as item in combobox
		for (String catName : ControllerInterface.getCategories().keySet())
			cmbCategory.addItem(catName);

		// Add this special value for adding a task, will register listener
		cmbCategory.addItem(languageBundle
				.getString("shared_actions.newcategoryaction.text"));

		cmbCategory.addActionListener(new ActionListener() {

			// If last "special item" is selected, open add category dialog
			// will be an action, because also NewTaskDialog will use this
			public void actionPerformed(ActionEvent e) {

				// Note: this method fails is another category is called
				// "New Category..."
				// because it returns an index which is not the last one
				// XXX how do i prevent this problem? Check on values?
				if (cmbCategory.getSelectedIndex() == (cmbCategory
						.getItemCount() - 1)) {
					ControllerInterface.getAction(
							ControllerInterface.ActionName.NEWCAT)
							.actionPerformed(null);
				}
			}
		});

		
		//---------
		
		
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

		butCanc = new JButton(
				languageBundle.getString("newTaskDialog.cancel"));
		butCanc.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(10, 0, 0, 0); // top padding
		c.gridx = 0; // aligned with button 2
		c.gridy = 5; // sixth row
		c.gridwidth = 1;
		pane.add(butCanc, c);

		butOk = new JButton(languageBundle.getString("newTaskDialog.ok"));

		// This action saves the new task 
		butOk.addActionListener(new ActionListener() {

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
		pane.add(butOk, c);

		pack();
		setVisible(true);
		// The minimum height and width are approximated dynamically 
		int minHeight = 0;
		minHeight += nameField.getHeight();
		minHeight += descriptionField.getHeight();
		minHeight += nameField.getHeight();
		minHeight += datePicker.getHeight();

		minHeight += cmbCategory.getHeight();
		minHeight += butOk.getHeight();
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

		// Here the object is register as an observer,
		//added to the observer list of theobservarble 
		ControllerInterface.registerAsObserver(this); 
	}
	
	//Update dialog by reload the text of components with the current language
	
	private void updateLanguagePresentation() {

		
		languageBundle = ControllerInterface.getLanguageBundle();
		
		setTitle(languageBundle.getString("newTaskDialog.title"));

		nameField.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.name")));

		descriptionField.setBorder(BorderFactory
				.createTitledBorder(languageBundle
						.getString("task.taskInput.desc")));

		datePicker.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.date")
				+ " "
				+ ControllerInterface.getDateFormat().toPattern()));

		datePicker.switchDateFormat(DateFormat.values()[Integer
				.parseInt(ControllerInterface
						.getProperty(GlobalValues.DATEFORMATKEY))]);

		
		cmbCategory.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.category")));

		butOk.setText(languageBundle.getString("newTaskDialog.ok"));
		butCanc.setText(languageBundle.getString("newTaskDialog.cancel"));

	}

	
	// Behaviour of the NewtaskDialog when it receives a language change
	@Override 
	public void update(Observable o, Object arg) { 
		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg;

		if (msg == DataModel.ChangeMessage.CHANGED_PROPERTY) {
			
			updateLanguagePresentation();
			revalidate();
			repaint();
		}
		
		// Force reload of side panel, to refresh with new category
		if (msg == DataModel.ChangeMessage.NEW_CATEGORY) {
			updateCategoryComboBox();
		}

	}

	/**
	 * Called to refresh the combo box's content
	 */
	private void updateCategoryComboBox() {

		// If we already have a listener, it means that
		// we are refreshing list, remove listener and all elements
		if (cmbCategory.getActionListeners().length > 0) {
			cmbCategory.removeActionListener(cmbCategory
					.getActionListeners()[0]);
			cmbCategory.removeAllItems();

		}

		// Then retrieve all values from categories
		for (String c : ControllerInterface.getCategories().keySet()) {
			cmbCategory.addItem(c);
		}

		// Add this special value for adding a task, will register listener
		cmbCategory.addItem(languageBundle
				.getString("shared_actions.newcategoryaction.text"));

		cmbCategory.addActionListener(new ActionListener() {

			// If last "special item" is selected, open add category dialog
			// will be an action, because also NewTaskDialog will use this
			public void actionPerformed(ActionEvent e) {

				// Note: this method fails is another category is called
				// "New Category..."
				// because it returns an index which is not the last one
				// XXX how do i prevent this problem? Check on values?
				if (cmbCategory.getSelectedIndex() == (cmbCategory
						.getItemCount() - 1)) {
					ControllerInterface.getAction(
							ControllerInterface.ActionName.NEWCAT)
							.actionPerformed(null);
				}
			}
		});

	}}
