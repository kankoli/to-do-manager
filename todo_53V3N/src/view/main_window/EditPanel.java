package view.main_window;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.ControllerInterface;
import control.ControllerInterface.ActionName;
import control.ControllerInterface.DateFormat;

import shared_actions.TaskValueProvider;
import utility.GlobalValues;
import view.custom_components.DatePicker;
import view.custom_components.PriorityBar;

import model.Category;
import model.DataModel;
import model.Task;
import model.Task.Priority;

/**
 * This class is used to represent a panel which used for showing
 * and editing the content of a task.
 * The task is selected in TaskTable.
 * 
 * The class implements the Observer interface to be able to
 * listen for changes in the model.
 * 
 * The class implements PropertyChangeListener to be able to
 * listen for task selection in TaskTable.
 * 
 * The class implements TaskValueProvider to be able to provide
 * methods to a shared action for editing tasks.
 * 
 * @author Magnus Larsson
 * 
 */
@SuppressWarnings("serial")
public class EditPanel extends JPanel implements Observer,
		PropertyChangeListener, TaskValueProvider {

	private JLabel noTaskSelectedLabel;
	private JTextField nameTextField;
	private JTextArea descrTextArea;
	private JScrollPane descrScrollPane;
	private DatePicker datePicker;

	private JComboBox<String> categoryComboBox; //For choosing/displaying category
	private PriorityBar prioBar;
	private JButton updateButton;

	private boolean showingTask;
	private Task selectedTask;

	private ResourceBundle languageBundle; //Stores key/value pairs for multiple language support

	/**
	 * Setting layout
	 * Registering as observer to model
	 * Retrieving bundle for language support
	 * Creating and adding all needed visuals
	 * Updates language to match current language
	 */
	public EditPanel() {
		super();
		setLayout(new GridBagLayout());

		ControllerInterface.registerAsObserver(this);
		languageBundle = ControllerInterface.getLanguageBundle();

		showingTask = false;

		addNoTaskSelectedLabel();
		noTaskSelectedLabel.setVisible(true);
		addTaskSelectedComponents();
		updateLanguagePresentation(); //Sets the correct language variables
	}

	/**
	 * Adding and creating components used to display task information/interaction
	 * for when a task is selected
	 */
	private void addTaskSelectedComponents() {
		addNameTextField();
		addDescrTextArea();
		addDatePicker();
		addCategoryComboBox();
		addPrioBar();
		addUpdateButton();
	}

	/**
	 * Adding a JLabel to display when no task is selected
	 */
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
		constr.ipady = 100; //100 pixels padding above label

		noTaskSelectedLabel.setVisible(false);
		add(noTaskSelectedLabel, constr);
	}

	/**
	 * Adding a text field to be used for displaying/editing a selected task's name
	 */
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

		nameTextField.setColumns(GlobalValues.TASKROW_DESC_COLS); //Sets the amount of columns in the field to a standard value
		nameTextField.setVisible(false);
		add(nameTextField, constr);

	}
	
	/**
	 * Adding a description text area to be used for displaying/editing a selected task's description
	 */
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
				GlobalValues.TASKROW_DESC_COLS); //Creates a JTextArea with a defined standard amount of rows/columns

		descrTextArea.setLineWrap(true); //If lines are too long to fit the width => creates new line.
		descrTextArea.setWrapStyleWord(true); //Starts new lines for complete words, not single characters.


		descrScrollPane = new JScrollPane(descrTextArea); //Makes area scroll-able

		descrScrollPane.setVisible(false);
		add(descrScrollPane, constr);
	}
	
	/**
	 * Adding a date picker to be used for displaying/editing a selected task's date
	 */
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

	/**
	 * Adding a category combo box to be used for displaying/editing a selected task's category
	 */
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

		categoryComboBox.setVisible(false);

		updateCategoryComboBox(); // Initialize from controller
		add(categoryComboBox, constr);
	}
	
	/**
	 * Called to refresh the combo box's content
	 */
	private void updateCategoryComboBox() {

		// If we already have a listener, it means that
		// we are refreshing list, remove listener and all elements
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

	/**
	 * Adding a priority bar to be used for displaying/editing a selected task's priority
	 */
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

		prioBar.setBorder(BorderFactory.createTitledBorder("Priority"));// TODO Load
																		// from
																		// language
																		// bundle
		prioBar.setVisible(false);

		add(prioBar, constr);
	}
	
	/**
	 * Adding a update button to be used for performing an update of a selected task's content
	 */
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
		updateButton.setVisible(false);

		Action a = ControllerInterface
				.getAction(ActionName.EDITTASK); //Creating/getting a shared action for editing
		
		a.putValue("tpp", this); //Put a reference to this object in the actions properties.
		
		updateButton.setAction(a);
		add(updateButton, constr);
	}

	/**
	 * Called to select a task, updates the visuals to display/show interaction
	 * for the selected task.
	 * @param task the task to be displayed/interacted with
	 */
	private void setSelectedTask(Task task) {
		selectedTask = task;

		if (!showingTask) {
			setTaskPresentationVisible(true);
		}

		updateTaskPresentation(selectedTask);
	}
	
	/**
	 * Called to set the components, which are used to display a task's information/interaction,
	 * visible.
	 * 
	 * Hides the label which is showing when no task is selected.
	 * 
	 * @param visible
	 */
	private void setTaskPresentationVisible(boolean visible) {
		nameTextField.setVisible(visible);
		descrScrollPane.setVisible(visible);
		datePicker.setVisible(visible);
		categoryComboBox.setVisible(visible);
		prioBar.setVisible(visible);
		updateButton.setVisible(visible);

		noTaskSelectedLabel.setVisible(!visible);

		showingTask = visible;
	}

	/**
	 * Called to set the information about a selected task to
	 * the visual and editable components.
	 * @param task the task to display/edit
	 */
	private void updateTaskPresentation(Task task) {
		nameTextField.setText(task.getName());
		descrTextArea.setText(task.getDescription());
		datePicker.updateDate(task.getDate());
		categoryComboBox.setSelectedItem(task.getCategory().getName());
		prioBar.setPriority(task.getPrio());
	}
	
	/**
	 * Called when the model has changed.
	 * 
	 * Used to take notice of a change of language, and takes action on it
	 * by updating the presented language.
	 * 
	 * Used to take notice when a task has been deleted, and takes action on it
	 * by not showing a selected task anymore.
	 * 
	 * Used to take notice when a new category has been added, and takes action
	 * on it by updating the category combo box.
	 */
	public void update(Observable o, Object arg) {

		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg;

		if (msg == DataModel.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			updateLanguagePresentation();
			revalidate();
			repaint();
		}
		// TODO to be fixed.. temporary behaviour
		if (msg == DataModel.ChangeMessage.DELETED_TASK) {
			showingTask = false;
			setTaskPresentationVisible(false);
		}

		// Force reload of side panel, to refresh with new category
		if (msg == DataModel.ChangeMessage.NEW_CATEGORY) {
			updateCategoryComboBox();
		}

	}
	
	/**
	 * Called to update the language presentation in the visuals,
	 * will switch language to match the current language.
	 */
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
	
	/**
	 * Called when a property change event is fired in TaskTable (which this object listens to).
	 * Sets the selected task to the task that was selected in TaskTable.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("tableCellEditor")) {
			TaskTable tt = (TaskTable) evt.getSource();
			setSelectedTask(((TaskRow) tt.getEditorComponent()).getTask()); //Sets selected task
		}

	}

	/**
	 * Called to get the selected task.
	 */
	public Task getTask() {
		return selectedTask;
	}
	
	/**
	 * Called to get the selected task's name.
	 */
	public String getTaskName() {
		return nameTextField.getText();
	}

	/**
	 * Called to get the selected task's date.
	 */
	public Date getDate() {
		return datePicker.getDate();
	}

	/**
	 * Called to get the selected task's priority.
	 */
	public Priority getPrio() {
		return prioBar.getPriority();
	}

	/**
	 * Called to get the selected task's completed status.
	 */
	public Boolean getCompleted() {
		return selectedTask.getCompleted();
	}

	/**
	 * Called to get the selected task's category name.
	 */
	public String getCategoryName() {
		return (String)categoryComboBox.getSelectedItem();
	}

	/**
	 * Called to get the selected task's description.
	 */
	public String getDescription() {
		return descrTextArea.getText();
	}
}
