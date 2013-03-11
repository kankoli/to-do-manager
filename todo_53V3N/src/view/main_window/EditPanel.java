package view.main_window;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.ControllerInterface;
import control.ControllerInterface.DateFormat;
import exceptions.InvalidCategoryException;

import utility.GlobalValues;
import view.custom_components.DatePicker;
import view.custom_components.PriorityBar;

import model.Category;
import model.Task;

/**
 * 
 * @author Magnus Larsson
 * 
 */
@SuppressWarnings("serial")
public class EditPanel extends JPanel implements Observer,
		PropertyChangeListener {

	private JLabel noTaskSelectedLabel;
	private JTextField nameTextField;
	private JTextArea descrTextArea;
	private JScrollPane descScrollPane;
	private DatePicker datePicker;

	private JComboBox<String> categoryComboBox;
	private PriorityBar prioBar;
	private JButton updateButton;

	private boolean showingTask;
	private Task selectedTask;

	private ResourceBundle languageBundle;

	public EditPanel() {
		super();
		setLayout(new GridBagLayout());

		ControllerInterface.registerAsObserver(this);
		languageBundle = ControllerInterface.getLanguageBundle();

		showingTask = false;

		addNoTaskSelectedLabel();
		noTaskSelectedLabel.setVisible(true);
		addTaskSelectedComponents();

		updateLanguagePresentation();

	}

	private void addTaskSelectedComponents() {
		addNameTextField();
		addDescrTextArea();

		addDatePicker();

		addCategoryComboBox();

		addPrioBar();

		addUpdateButton();

	}

	private void addNoTaskSelectedLabel() {
		GridBagConstraints constr = new GridBagConstraints();
		noTaskSelectedLabel = new JLabel();

		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridheight = 0;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 1;
		constr.anchor = GridBagConstraints.NORTHWEST;
		constr.ipady = 100;

		noTaskSelectedLabel.setVisible(false);
		add(noTaskSelectedLabel, constr);
	}

	private void addNameTextField() {
		GridBagConstraints constr = new GridBagConstraints();
		nameTextField = new JTextField();

		constr.gridx = 0;
		constr.gridy = 0;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 0;
		constr.anchor = GridBagConstraints.NORTHWEST;

		nameTextField.setColumns(GlobalValues.TASKROW_DESC_COLS);
		nameTextField.setVisible(false);
		add(nameTextField, constr);

	}

	private void addDescrTextArea() {
		GridBagConstraints constr = new GridBagConstraints();
		descrTextArea = new JTextArea();

		constr.gridx = 0;
		constr.gridy = 1;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 0;
		constr.anchor = GridBagConstraints.NORTHWEST;

		descrTextArea = new JTextArea(GlobalValues.TASKROW_DESC_ROWS,
				GlobalValues.TASKROW_DESC_COLS);

		descrTextArea.setLineWrap(true);
		descrTextArea.setWrapStyleWord(true);

		// descrTextArea.setAlignmentX(CENTER_ALIGNMENT);

		descScrollPane = new JScrollPane(descrTextArea);

		descScrollPane.setVisible(false);
		add(descScrollPane, constr);
	}

	private void addDatePicker() {

		GridBagConstraints constr = new GridBagConstraints();

		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 0;
		constr.anchor = GridBagConstraints.NORTHWEST;

		datePicker = new DatePicker();
		datePicker.setVisible(false);

		add(datePicker, constr);

	}

	private void addCategoryComboBox() {
		GridBagConstraints constr = new GridBagConstraints();
		categoryComboBox = new JComboBox<String>();

		constr.gridx = 0;
		constr.gridy = 3;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 0;
		constr.anchor = GridBagConstraints.NORTHWEST;

		categoryComboBox.setBackground(Color.WHITE);// Let theme handle
		// categoryBox.setEnabled(false);

		categoryComboBox.setVisible(false);

		updateCategoryComboBox(); // Initialize from controller
		add(categoryComboBox, constr);
	}

	// This method is called to refresh combobox content
	private void updateCategoryComboBox() {

		// If we alrady have a listeners, it means that
		// we are refreshing list, remove listner and all elements
		if (categoryComboBox.getActionListeners().length > 0) {
			categoryComboBox.removeActionListener(categoryComboBox
					.getActionListeners()[0]);
			categoryComboBox.removeAllItems();

		}

		// Then retrieve all values from categories
		for (Category c : ControllerInterface.getCategories().values()) {
			categoryComboBox.addItem(c.getName());
		}

		// Add this special value for adding a task, will register listener
		categoryComboBox.addItem(languageBundle
				.getString("shared_actions.newcategoryaction.text"));

		categoryComboBox.addActionListener(new ActionListener() {

			// If last "special item" is selected, open add category dialog
			// will be an action, because also NewTaskDialog will use this
			public void actionPerformed(ActionEvent e) {

				// Note: this method fails is another category is called
				// "New Category..."
				// because it returns an index which is not the last one
				// XXX how do i prevent this problem? Check on values?
				if (categoryComboBox.getSelectedIndex() == (categoryComboBox
						.getItemCount() - 1)) {
					ControllerInterface.getAction(
							ControllerInterface.ActionName.NEWCAT)
							.actionPerformed(null);
				}
			}
		});

	}

	private void addPrioBar() {
		GridBagConstraints constr = new GridBagConstraints();
		prioBar = new PriorityBar(Task.Priority.NOT_SET);

		constr.gridx = 0;
		constr.gridy = 4;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 0;
		constr.anchor = GridBagConstraints.NORTHWEST;

		prioBar.setBorder(BorderFactory.createTitledBorder("Priority"));// Load
																		// from
																		// language
																		// bundle
		prioBar.setBackground(Color.WHITE);// Let theme handle
		prioBar.setVisible(false);

		add(prioBar, constr);
	}

	private void addUpdateButton() {
		GridBagConstraints constr = new GridBagConstraints();
		updateButton = new JButton();

		constr.gridx = 0;
		constr.gridy = 5;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 1;
		constr.anchor = GridBagConstraints.NORTHWEST;

		
		updateButton.setText(languageBundle.getString("task.taskInput.update"));
//		updateButton.setText("Update"); // Load from language bundle
		// Shared action here?
		updateButton.setVisible(false);
		updateButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String name = nameTextField.getText();
//				String date = dateTextField.getText();

				Task.Priority priority = prioBar.getPriority();
				// XXX: category should contain not string, but category
				String categoryName = (String) categoryComboBox
						.getSelectedItem();
				String description = descrTextArea.getText();

				try {
					ControllerInterface.editTask(selectedTask, name, datePicker.getDate(),
							priority, selectedTask.getCompleted(),
							categoryName, description);
				} catch (InvalidCategoryException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(),
							"Category Problem", JOptionPane.WARNING_MESSAGE);

				} 
			}

		});

		add(updateButton, constr);
	}

	private void setSelectedTask(Task task) {
		selectedTask = task;

		if (!showingTask) {
			setTaskPresentationVisible(true);
		}

		updateTaskPresentation(selectedTask);
	}

	private void setTaskPresentationVisible(boolean visible) {
		nameTextField.setVisible(visible);
		descScrollPane.setVisible(visible);
		datePicker.setVisible(visible);
		categoryComboBox.setVisible(visible);
		prioBar.setVisible(visible);
		updateButton.setVisible(visible);

		noTaskSelectedLabel.setVisible(!visible);

		showingTask = visible;
	}

	private void updateTaskPresentation(Task task) {

		nameTextField.setText(task.getName());

		descrTextArea.setText(task.getDescription());

		datePicker.updateDate(task.getDate());

		categoryComboBox.setSelectedItem(task.getCategory().getName());

		prioBar.setPriority(task.getPrio());
	}

	public void update(Observable o, Object arg) {

		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			updateLanguagePresentation();
			revalidate();
			repaint();
		}
		// TODO to be fixed.. temporary behaviour
		if (msg == ControllerInterface.ChangeMessage.DELETED_TASK) {
			showingTask = false;
			setTaskPresentationVisible(false);
		}

		// XXX Marco: i added this
		// Force reload of side panel, to refresh with new category
		if (msg == ControllerInterface.ChangeMessage.NEW_CATEGORY) {
			updateCategoryComboBox();
		}

	}

	private void updateLanguagePresentation() {

		noTaskSelectedLabel.setText(languageBundle
				.getString("task.taskInput.selectTask"));

		nameTextField.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.name")));
		descrTextArea.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.desc")));

		datePicker.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.date")
				+ " "
				+ ControllerInterface.getDateFormat().toPattern()));

		datePicker.switchDateFormat(DateFormat.values()[Integer
				.parseInt(ControllerInterface
						.getProperty(GlobalValues.DATEFORMATKEY))]);

		categoryComboBox.setBorder(BorderFactory
				.createTitledBorder(languageBundle
						.getString("task.taskInput.category")));
		prioBar.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.priority")));
		updateButton.setText(languageBundle.getString("task.taskInput.update"));

	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("tableCellEditor")) {
			TaskTable tt = (TaskTable) evt.getSource();
			setSelectedTask(((TaskRow) tt.getEditorComponent()).getTask());
		}

	}
}
