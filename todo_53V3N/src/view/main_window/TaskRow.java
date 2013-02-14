package view.main_window;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import utility.GlobalValues;

import model.Task;

/**
 * This component represent one row (one task) in the task scroll panel, with
 * wich user can interact.
 * 
 * @author Marco Dondio
 * 
 */

public final class TaskRow extends JPanel {

	private static final long serialVersionUID = 1L;

	// makes easier managing data
	// see JavaDoc for other formats
	// TODO this will read an option (defined into database) for desired format
	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm");

	private TaskScrollPanel taskScrollPanel; // reference to panel
	private Task t; // reference to task in datamodel

	private JButton doneBut;
	private JTextField nameField; // TODO Maybe doing an JTextField[] is better?
	private JTextField dateField;
	private JTextField categoryField;
	private JTextField priorityField;
	private JScrollPane descriptionPane;
	private JTextArea descriptionArea;
	private JButton editBut;
	private JButton deleteBut;

	boolean isSelected = false;

	public TaskRow(final TaskScrollPanel taskScrollPanel, Task ta) {
		super();
		t = ta;
		this.taskScrollPanel = taskScrollPanel;

		sdf.setLenient(false); // This makes a date like "31-13-2013 17:45" not
								// valid!

		// TODO layout has to be fixed a lot! I didnt mind about it now
		this.setLayout(new FlowLayout(FlowLayout.LEFT,
				GlobalValues.TASKROW_ELEMENT_SPACING_X,
				GlobalValues.TASKROW_ELEMENT_SPACING_Y));

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
		add(doneBut);

		nameField = new JTextField(t.getName());
		nameField.setEnabled(false);
		// nameField.setBackground(Color.LIGHT_GRAY);
		nameField.setFont(new Font(null, Font.BOLD, 30));
		// nameArea.setDisabledTextColor(Color.BLACK);
		// nameField.setBorder(null);
		add(nameField);

		// date format
		dateField = new JTextField(sdf.format(t.getDate()));
		dateField.setEnabled(false);
		// dateField.setBackground(Color.GREEN);
		// dateField.setDisabledTextColor(Color.BLACK);
		// dateField.setBorder(null);
		add(dateField);

		categoryField = new JTextField(t.getCategory());
		// categoryArea.addMouseListener(my);
		categoryField.setEnabled(false);
		// categoryField.setBackground(Color.GREEN);
		// categoryField.setDisabledTextColor(Color.BLACK);
		// categoryField.setBorder(null);
		add(categoryField);

		priorityField = new JTextField(t.getPrio().toString());
		// priorityArea.addMouseListener(my);
		priorityField.setEnabled(false);
		// priorityField.setBackground(Color.GREEN);
		// priorityField.setDisabledTextColor(Color.BLACK);
		// priorityField.setBorder(null);
		add(priorityField);

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
		add(descriptionPane);

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

				} else {

					// Now i store back into Task object modifications: first
					// check values

					// TODO
					// priority will be a radiobutton, for the moment it's just
					// some text
					// did this for quick prototype
					Date d = null;
					try {
						d = sdf.parse(dateField.getText());

					} catch (ParseException e) {

						d = t.getDate();
						dateField.setText(sdf.format(d));

						JOptionPane
								.showMessageDialog(
										null,
										"Date is not valid! Using old date for this task",
										"Invalid date",
										JOptionPane.WARNING_MESSAGE);

					}

					// Now i can store into task
					t.setName(nameField.getText());
					t.setDate(d);
					t.setCategory(categoryField.getText());
					t.setPrio(Task.Priority.valueOf(priorityField.getText()));
					t.setDescription(descriptionArea.getText());

					// JOptionPane.showMessageDialog(null,
					// "Editing succesfully");
					nameField.setBackground(Color.WHITE);
					dateField.setBackground(Color.WHITE);
					categoryField.setBackground(Color.WHITE);
					priorityField.setBackground(Color.WHITE);
					descriptionArea.setBackground(Color.WHITE);
					editBut.setText("Edit");
				}

				nameField.setEnabled(!nameField.isEnabled());
				dateField.setEnabled(!dateField.isEnabled());
				categoryField.setEnabled(!categoryField.isEnabled());
				priorityField.setEnabled(!priorityField.isEnabled());
				descriptionArea.setEnabled(!descriptionArea.isEnabled());

			}
		});

		editBut.setVisible(false);
		add(editBut);

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

					taskScrollPanel.deleteTask();
				}
			}

		});
		deleteBut.setVisible(false);
		add(deleteBut);

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
	 * @return current Task object
	 */
	public final Task getTask() {
		return t;
	}

}