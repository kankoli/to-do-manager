package view.main_window;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import control.Controller;
import exceptions.InvalidCategoryException;
import exceptions.InvalidDateException;

import utility.GlobalValues;
import view.new_category_dialog.AddCategoryDialog;

import model.Category;
import model.Task;

/**
 * This component represent one row (one task) in the task scroll panel, with
 * wich user can interact.
 * 
 * @author Marco Dondio
 * 
 */

// TODO fix layout

public final class TaskRow extends JPanel {

	private static final long serialVersionUID = 1L;

	// makes easier managing data
	// see JavaDoc for other formats
	// TODO this will read an option (defined into database) for desired format
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm");

	private TaskScrollPanel taskScrollPanel; // reference to panel
	private Controller controller;

	private Task t; // reference to task in datamodel

	private JButton doneBut;
	private JTextField nameField; // TODO Maybe doing an JTextField[] is better?
	private JTextField dateField;
	private JComboBox<String> categoryBox;

	private JTextField priorityField;
	private JScrollPane descriptionPane;
	private JTextArea descriptionArea;
	private JButton editBut;
	private JButton deleteBut;

	boolean isSelected = false;

	public TaskRow(final Controller controller,
			final TaskScrollPanel taskScrollPanel, Task ta) {
		super();
		t = ta;
		this.controller = controller;
		this.taskScrollPanel = taskScrollPanel;

		sdf.setLenient(false); // This makes a date like "31-13-2013 17:45" not
								// valid!

		// TODO layout has to be fixed a lot! I didnt mind about it now
		// this.setLayout(new FlowLayout(FlowLayout.LEFT,
		// GlobalValues.TASKROW_ELEMENT_SPACING_X,
		// GlobalValues.TASKROW_ELEMENT_SPACING_Y));

		this.setLayout(new GridBagLayout());

		// preferences
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK, ));

		// Now add my components: done Button
		doneBut = new JButton("Done");
		doneBut.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				t.setCompleted(!t.getCompleted());

				// TODO
				// This button will notify the parent (scrollpanel component)
				// that this TaskRow has to be moved to completed or pending
				// section.

				JOptionPane
						.showMessageDialog(
								null,
								"This button will edit task status: completed/pending\nMaybe it should inform scrollpanel.. TODO");
			}
		});
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.weightx = 1.0;
		// con.insets = new Insets(0, 100, 0, 0);
		con.anchor = GridBagConstraints.LINE_START;
		// con.anchor = GridBagConstraints.LINE_END;

		add(doneBut, con);

		nameField = new JTextField(t.getName());
		// nameField.setHorizontalAlignment(JTextField.TRAILING);
		nameField.setEnabled(false);
		// nameField.setBackground(Color.LIGHT_GRAY);
		nameField.setFont(new Font(null, Font.BOLD, 30));
		// nameArea.setDisabledTextColor(Color.BLACK);
		// nameField.setBorder(null);
		con = new GridBagConstraints();
		con.gridx = 1;
		con.gridy = 0;
		con.weightx = 1.0;
		// con.fill = GridBagConstraints.WEST;
		// con.insets = new Insets(0, 0, 0, 100);
		// con.insets = Insets.WEST_INSETS : EAST_INSETS;

		con.anchor = GridBagConstraints.LINE_START;
		add(nameField, con);

		// date format
		dateField = new JTextField(sdf.format(t.getDate()));
		dateField.setEnabled(false);
		// dateField.setBackground(Color.GREEN);
		// dateField.setDisabledTextColor(Color.BLACK);
		// dateField.setBorder(null);
		con = new GridBagConstraints();
		con.gridx = 2;
		con.gridy = 0;
		// con.insets = new Insets(0, 0, 0, 300);
		// con.anchor = GridBagConstraints.LINE_START;
		add(dateField, con);

		// now build category ComboBox
		// TODO come fare ad aggiungere categorie???
		// maybe is better a button? Then simple dialog with colorpicker???
		categoryBox = new JComboBox<String>();
		for (Category c : controller.getCategories().values()) {
			categoryBox.addItem(c.getName());
		}

		// Add this special value for adding a task, will register listener
		categoryBox.addItem("New Category...");

		categoryBox.addActionListener(new ActionListener() {

			// If last "special item" is selected, open add category dialog
			// will be an action, because also NewTaskDialog will use this
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = ((JComboBox<String>) e.getSource());

				// Note: this method fails is another category is called
				// "New Category..."
				// because it returns an index wich is not the last one
				// TODO how do i prevent this problem? Check on values?
				if (source.getSelectedIndex() == (source.getItemCount() - 1)) {

					// TODO open add category dialog
					// modify through controller
					// view will be updated by observer call
					// System.out.println("ultimo!");

					new AddCategoryDialog(controller);
				}
			}
		});

		// Select actual category
		categoryBox.setSelectedItem(t.getCategory());
		categoryBox.setEnabled(false);

		// categoryBox.setEditable(true);
		// patternList.addActionListener(this);

		con = new GridBagConstraints();
		con.gridx = 3;
		con.gridy = 0;
		// con.insets = new Insets(0, 0, 0, 300);
		// con.anchor = GridBagConstraints.LINE_START;
		add(categoryBox, con);

		// And set my background color!
		setBackground(t.getCategory().getColor());

		priorityField = new JTextField(t.getPrio().toString());
		// priorityArea.addMouseListener(my);
		priorityField.setEnabled(false);
		// priorityField.setBackground(Color.GREEN);
		// priorityField.setDisabledTextColor(Color.BLACK);
		// priorityField.setBorder(null);
		con = new GridBagConstraints();
		con.gridx = 4;
		con.gridy = 0;
		// con.insets = new Insets(0, 0, 0, 300);
		// con.anchor = GridBagConstraints.LINE_START;
		add(priorityField, con);

		descriptionArea = new JTextArea(t.getDescription(),
				GlobalValues.TASKROW_DESC_ROWS, GlobalValues.TASKROW_DESC_COLS);

		// descriptionArea = new JTextArea(t.getDescription());
		// descriptionArea.setBounds( 0, 0, 200, 200 );
		// descriptionArea.addMouseListener(my);
		descriptionArea.setEnabled(false);
		// descriptionArea.setBackground(Color.GREEN);
		// descriptionArea.setDisabledTextColor(Color.BLACK);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);

		descriptionArea.setAlignmentX(CENTER_ALIGNMENT); // TODO not sure it
															// works.. once
															// again check
															// layout

		descriptionPane = new JScrollPane(descriptionArea);
		descriptionPane.setVisible(false);
		con = new GridBagConstraints();
		con.gridx = 5;
		con.gridy = 0;
		if (!descriptionArea.getText().isEmpty())
			descriptionPane.setVisible(true);

		add(descriptionPane, con);

		// Now the edit button
		editBut = new JButton("Edit");
		editBut.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				// TODO in future, graphical icons instead of text button.
				if (editBut.getActionCommand().equals("Edit")) {

					// nameField.setBackground(Color.GREEN);
					// dateField.setBackground(Color.GREEN);
					// categoryField.setBackground(Color.GREEN);
					// priorityField.setBackground(Color.GREEN);
					// descriptionArea.setBackground(Color.GREEN);

					editBut.setText("Stop editing");

				} else { // call edit task method

					// TODO
					// priority will be a radiobutton, for the moment it's just
					// some text
					// did this for quick prototype
					String name = nameField.getText();

					String date = dateField.getText();
					// TODO change priorty to drop down list
					String priority = priorityField.getText();

					// TODO: non è meglio che categoryBox contenga direttamente
					// Category???
					// non stringhe!
					String categoryName = (String) categoryBox
							.getSelectedItem();
					String description = descriptionArea.getText();

					try {
						controller.editTask(t, name, sdf, date, priority,
								t.getCompleted(), categoryName, description);
					} catch (InvalidCategoryException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Category Problem", JOptionPane.WARNING_MESSAGE);

					} catch (InvalidDateException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Date problem", JOptionPane.WARNING_MESSAGE);

						dateField.setText(sdf.format(t.getDate()));
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(null, e.getMessage(),
								"Priority problem", JOptionPane.WARNING_MESSAGE);

						priorityField.setText(t.getPrio().toString());
					}

					nameField.setBackground(Color.WHITE);
					dateField.setBackground(Color.WHITE);
					categoryBox.setBackground(Color.WHITE);
					priorityField.setBackground(Color.WHITE);
					descriptionArea.setBackground(Color.WHITE);
					editBut.setText("Edit");
				}

				nameField.setEnabled(!nameField.isEnabled());
				dateField.setEnabled(!dateField.isEnabled());
				categoryBox.setEnabled(!categoryBox.isEnabled());
				priorityField.setEnabled(!priorityField.isEnabled());
				descriptionArea.setEnabled(!descriptionArea.isEnabled());
			}
		});

		editBut.setVisible(false);
		con = new GridBagConstraints();
		con.gridx = 6;
		con.gridy = 0;
		con.weightx = 1.0;
		con.anchor = GridBagConstraints.LINE_END;
		add(editBut, con);

		// Delete button apre un popup di conferma
		deleteBut = new JButton("Delete");
		deleteBut.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				// Display a dialog for confirmation and read answer
				// if yes, delete (call to parent, he knows already we r
				// focused)
				if (JOptionPane.showOptionDialog(null,
						"Are you sure you want to delete \"" + t.getName()
								+ "\"", "Confirm task deletion",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
						null, null, null) == JOptionPane.YES_OPTION) {

					taskScrollPanel.deleteTask(); // TODO changed
					// controller.deleteTask(t);
				}
			}

		});
		deleteBut.setVisible(false);
		con = new GridBagConstraints();
		// con.gridx = 7;
		// con.gridy = 0;
		add(deleteBut, con);

		// Finally add panel listeners
		addMouseListener(new MouseAdapter() {
			// public void mousePressed(MouseEvent me) {

			// XXX: to be discussed with team, do we really need a
			// fixed focus when someone clicks? I feel that the onfocus
			// event is enough!
			// }

			public void mouseEntered(MouseEvent e) {

				if (!isSelected) {
					setBorder(BorderFactory
							.createBevelBorder(BevelBorder.LOWERED));

					setSelected(true);
				}

			}

			public void mouseExited(MouseEvent e) {
				if (!isSelected) {
					setBorder(BorderFactory
							.createBevelBorder(BevelBorder.RAISED));

					setSelected(false);
				}
			}
		});

		// setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		// setBorder(BorderFactory.createLineBorder(Color.black, 50));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

	}

	public final void setSelected(boolean visible) {

		if (visible) {
			taskScrollPanel.selectTaskRow(this);

			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		} else
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		if (!descriptionArea.getText().isEmpty())
			descriptionPane.setVisible(visible);

		editBut.setVisible(visible);
		deleteBut.setVisible(visible);

		isSelected = visible;
	}

	/**
	 * Returns the current Task associated to this TaskRow object
	 * 
	 * @return current Task object
	 */
	public final Task getTask() {
		return t;
	}

}