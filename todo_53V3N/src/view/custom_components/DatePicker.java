package view.custom_components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;

import utility.GlobalValues;

import control.ControllerInterface;
import control.ControllerInterface.DateFormat;

/**
 * This class represents a (very) simple Date and Time Picker component. I found
 * an example online, i decided to extend and improve it.
 * http://stackoverflow.com
 * /questions/2895865/create-a-java-date-picker-component
 * 
 * @author Marco Dondio
 * 
 */

@SuppressWarnings("serial")
public final class DatePicker extends JPanel implements ActionListener {

	// Contains part of the date
	@SuppressWarnings("unchecked")
	private JComboBox<String>[] combos = new JComboBox[5];
	private Date date;

	private static final int ranges[][] = { { 2012, 2099 }, { 1, 12 },
			{ 1, 31 }, { 0, 23 }, { 0, 59 } };

	// Index in the comboboxes
	private static enum DatePart {
		YEAR, MONTH, DAY, HOUR, MINUTE
	};

	public DatePicker() {
		super();

		date = new Date();
		
		// Idea is to create all comboboxes, then switch display order later
		combos[0] = new JComboBox<String>();
		combos[1] = new JComboBox<String>();
		combos[2] = new JComboBox<String>();
		combos[3] = new JComboBox<String>();
		combos[4] = new JComboBox<String>();

		// Fill the combos
		for (int i = 0; i < combos.length; i++) {
			for (int j = ranges[i][0]; j <= ranges[i][1]; j++)
				combos[i].addItem((j < 10 ? "0" : "") + Integer.toString(j));

			// Remove the arrow button from the combo boxes (optional)
			combos[i].setUI(new BasicComboBoxUI() {
				protected JButton createArrowButton() {
					return new JButton() {
						public int getWidth() {
							return 0;
						}
					};
				}
			});
			
			// Finally add action listeners to handle user events
				combos[i].addActionListener(this);

		}
		// Set suitable layout
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// Now add date boxes, using order of current dateformat
		addDateBoxes(DateFormat.values()[Integer.parseInt(ControllerInterface
				.getProperty(GlobalValues.DATEFORMATKEY))]);
	}

	/**
	 * Constructor with date
	 * 
	 * @param date
	 *            Date for initialization
	 */
	public DatePicker(Date date) {
		this();
		updateDate(date);
	}

	/**
	 * This method is called to place comboboxes using right order reflecting
	 * dateFormat
	 * 
	 * @param dateFormat
	 *            The dateFormat of the date
	 */
	private void addDateBoxes(DateFormat dateFormat) {

		switch (dateFormat) {
		case ITALIAN:
			add(combos[DatePart.DAY.ordinal()]);
			add(combos[DatePart.MONTH.ordinal()]);
			add(combos[DatePart.YEAR.ordinal()]);
			break;

		case SWEDISH:
			add(combos[DatePart.YEAR.ordinal()]);
			add(combos[DatePart.MONTH.ordinal()]);
			add(combos[DatePart.DAY.ordinal()]);
			break;
		}

		add(combos[DatePart.HOUR.ordinal()]);
		add(combos[DatePart.MINUTE.ordinal()]);
	}

	/**
	 * This function sets and displays date on the DatePicker
	 * 
	 * @param d
	 *            The date to be displayed
	 */
	public void updateDate(Date date) {
		this.date = date;

		// Now disable listener to avoid useless events
		for (JComboBox<String> c : combos)
			c.removeActionListener(this);

		// Retrieve date part
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);

		// Set year content
		combos[DatePart.YEAR.ordinal()].setSelectedItem(String.valueOf(calendar
				.get(Calendar.YEAR)));

		// Set month content, we should add 0 to make it looks better
		int n = (calendar.get(Calendar.MONTH) + 1);
		combos[DatePart.MONTH.ordinal()].setSelectedItem((n < 10 ? "0"
				+ String.valueOf(n) : String.valueOf(n)));

		// Set day
		n = (calendar.get(Calendar.DAY_OF_MONTH));
		combos[DatePart.DAY.ordinal()].setSelectedItem((n < 10 ? "0"
				+ String.valueOf(n) : String.valueOf(n)));

		// Set hour
		n = (calendar.get(Calendar.HOUR_OF_DAY));
		combos[DatePart.HOUR.ordinal()].setSelectedItem((n < 10 ? "0"
				+ String.valueOf(n) : String.valueOf(n)));

		// Set min
		n = (calendar.get(Calendar.MINUTE));
		combos[DatePart.MINUTE.ordinal()].setSelectedItem((n < 10 ? "0"
				+ String.valueOf(n) : String.valueOf(n)));

		// Finally add action listeners to handle user events
		for (JComboBox<String> c : combos)
			c.addActionListener(this);

	}

	/**
	 * This method retrieves currently selected date
	 * 
	 * @return date
	 */
	public final Date getDate() {
		return date;
	}

	/**
	 * This method switches DatePicker to a dateFormat view
	 * 
	 * @param dateFormat
	 *            The dateFormat to use
	 */
	public void switchDateFormat(DateFormat dateFormat) {
		// Remove old boxes and places them again in correct order
		removeAll();
		addDateBoxes(dateFormat);
	}

	// Action listener refreshes data
	public void actionPerformed(ActionEvent e) {
		// This method stores data from combobox inside date field

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);

		// Update date content using combobox values
		calendar.set(Calendar.YEAR, Integer
				.parseInt((String) combos[DatePart.YEAR.ordinal()]
						.getSelectedItem()));
		calendar.set(Calendar.MONTH, (Integer
				.parseInt((String) combos[DatePart.MONTH.ordinal()]
						.getSelectedItem()) - 1)); // remember -1
		calendar.set(Calendar.DAY_OF_MONTH, Integer
				.parseInt((String) combos[DatePart.DAY.ordinal()]
						.getSelectedItem()));
		calendar.set(Calendar.HOUR_OF_DAY, Integer
				.parseInt((String) combos[DatePart.HOUR.ordinal()]
						.getSelectedItem()));
		calendar.set(Calendar.MINUTE, Integer
				.parseInt((String) combos[DatePart.MINUTE.ordinal()]
						.getSelectedItem()));

		// Store back updated date
		date = calendar.getTime();
		// System.out.println(date.toString());
	}

}