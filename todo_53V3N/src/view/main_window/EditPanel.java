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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import control.ControllerInterface;

import view.custom_components.PriorityBar;

import model.Category;
import model.Task;

//import model.Task;
//import view.custom_components.PriorityBar;

//import model.Category;

//import control.ControllerInterface;

//import view.custom_components.PriorityBar;

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
	private JTextField dateTextField;
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
		addDateTextField();
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
		constr.gridwidth = 0;
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

		descrTextArea.setRows(10);
		descrTextArea.setLineWrap(true);
		descrTextArea.setWrapStyleWord(true);

		descrTextArea.setVisible(false);
		add(descrTextArea, constr);
	}

	private void addDateTextField() { // DO
		GridBagConstraints constr = new GridBagConstraints();
		dateTextField = new JTextField();

		constr.gridx = 0;
		constr.gridy = 2;
		constr.gridheight = 1;
		constr.gridwidth = 1;
		constr.weightx = 1;
		constr.weighty = 0;
		constr.anchor = GridBagConstraints.NORTHWEST;

		dateTextField.setVisible(false);
		add(dateTextField, constr);
	}

	private void addCategoryComboBox() { // DO
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

		add(categoryComboBox, constr);
	}

	private void fillCategoryComboBox() {

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
				// TODO how do i prevent this problem? Check on values?
				if (categoryComboBox.getSelectedIndex() == (categoryComboBox
						.getItemCount() - 1)) {
					ControllerInterface.getAction(
							ControllerInterface.ActionName.NEWCAT)
							.actionPerformed(null);
				}
			}
		});

	}

	private void addPrioBar() { // DO
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

		updateButton.setText("Update"); // Load from language bundle
		// Shared action here?
		updateButton.setVisible(false);

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
		descrTextArea.setVisible(visible);
		dateTextField.setVisible(visible);
		categoryComboBox.setVisible(visible);
		prioBar.setVisible(visible);
		updateButton.setVisible(visible);

		noTaskSelectedLabel.setVisible(!visible);

		showingTask = visible;
	}

	private void updateTaskPresentation(Task task) { // FIX
		
		nameTextField.setText(task.getName());
		
		descrTextArea.setText(task.getDescription());
		dateTextField.setText(ControllerInterface.getDateFormat().format(
				task.getDate()));

		fillCategoryComboBox(); // retrieves from controller all categories
		categoryComboBox.setSelectedItem(task.getCategory().getName());

		prioBar = new PriorityBar(task.getPrio());
	}

	private void updateDone(Task task) {

		// TODO
		// controllerInterface.editTask(task, name, sdf, date, priority,
		// completed, categoryName, description)

	}

	public void update(Observable o, Object arg) {

		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			updateLanguagePresentation();
			revalidate();
			repaint();
		}
	}

	private void updateLanguagePresentation() {

		noTaskSelectedLabel.setText(languageBundle
				.getString("task.taskInput.selectTask"));

		nameTextField.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.name")));
		descrTextArea.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.desc")));
		dateTextField.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.date")));
		categoryComboBox.setBorder(BorderFactory
				.createTitledBorder(languageBundle
						.getString("task.taskInput.category")));
		prioBar.setBorder(BorderFactory.createTitledBorder(languageBundle
				.getString("task.taskInput.priority")));
		updateButton.setText(languageBundle.getString("task.taskInput.update"));

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//When clicking on editor
		if (evt.getPropertyName().equals("tableCellEditor")) {
			TaskTable tt = (TaskTable) evt.getSource();
			setSelectedTask(((TaskRow) tt.getEditorComponent()).getTask());
		}
	}
}
