package view.urgent_task_dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import utility.GlobalResources;
import utility.GlobalValues;

import model.DataModel;
import model.Task;

import control.ControllerInterface;

/**
 * This class represent the urgent task dialog, it's possible to add and delete
 * task to urgent list.
 * 
 * @author Kadir & Madelen
 * 
 */
@SuppressWarnings("serial")
public class UrgentTaskDialog extends JDialog implements Observer {

	private ResourceBundle languageBundle;
	private JLabel lblName;
	private JLabel lblDate;

	@SuppressWarnings("unchecked")
	public UrgentTaskDialog() {
		super();

		// Load for language support
		languageBundle = ControllerInterface.getLanguageBundle();

		setTitle(languageBundle.getString("urgentTaskDialog.title"));

		int minHeight = GlobalValues.URGENT_MINYSIZE;
		int minWidth = GlobalValues.URGENT_MINXSIZE;

		JPanel pane = (JPanel) getContentPane();

		// pane.setBackground(Color.WHITE);
		// Implement Gridbaglayout and anchor it to the top
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;

		JPanel pnlHeader = new JPanel();
		pnlHeader.setMinimumSize(new Dimension(270, 32));
		pnlHeader.setPreferredSize(new Dimension(270, 32));
		pnlHeader.setLayout(new GridLayout(0, 2));

		// put the Comboox in the right corner in cell (0,0)
		final ObserverComboBox cmbTasks = new ObserverComboBox();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		pane.add(cmbTasks, c);

		// create the addUrgentTask button
		JButton btn = new JButton();
		btn.setIcon(GlobalResources.addUrgentIcon);
		btn.setMinimumSize(new Dimension(45, 45));
		btn.setPreferredSize(new Dimension(45, 45));
		btn.setBorder(null);
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				cmbTasks.setUrgent();
			}
		});
		// The position for addUrgentButton
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		pane.add(btn, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		// Adding the name to the name column in the urgent task table
		lblName = new JLabel(
				languageBundle.getString("urgentTaskDialog.labelName")); // Language
																			// support
		lblName.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		pnlHeader.add(lblName);
		// adding the date to the date column in the urgent task table
		lblDate = new JLabel(
				languageBundle.getString("urgentTaskDialog.labelDate")); // Language
																			// support
		lblDate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		pnlHeader.add(lblDate);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		pane.add(pnlHeader, c);

		final JList<Task> lst = new JList<Task>(); // Creating the Urgent task
													// list

		ObserverListModel<Task> dlm = new ObserverListModel<Task>(); // Creatig
																		// a
																		// list
																		// model
																		// to
																		// fill
																		// in
																		// lst,
		lst.setModel(dlm); // in other words the actual data is find here
		lst.setCellRenderer(new UrgentCellRenderer()); // the custom cell
														// renderer is used
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		lst.addMouseListener(new MouseAdapter() {

			// Both pressed and released actions are handled the same.
			public void mousePressed(MouseEvent e) {
				handle(e);
			}

			public void mouseReleased(MouseEvent e) {
				handle(e);
			}

			public void handle(MouseEvent e) { // Function for right clicking
												// and deleting task from urgent
												// task list
				if (SwingUtilities.isRightMouseButton(e)) {
					// The item is set as selected
					lst.setSelectedIndex(lst.locationToIndex(e.getPoint()));
					// show context menu
					JPopupMenu menu = new JPopupMenu();
					JMenuItem item = new JMenuItem(languageBundle
							.getString("urgentTaskDialog.deleteBtn"));
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							ControllerInterface.setUrgent(
									lst.getSelectedValue(), false);
						}
					});
					menu.add(item);
					menu.show(e.getComponent(), e.getX(), e.getY()); // show
																		// menu
																		// at
																		// the
																		// click
																		// point
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(lst);
		pane.add(scrollPane, c);

		setPreferredSize(new Dimension(minWidth, minHeight));
		setMinimumSize(new Dimension(minWidth, minHeight));

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

		setLocation((int) (posX + ((sizeX - minWidth) / 2)), // Set location to
																// the pop up
																// window
																// "urgent Task"
				(int) (posY + ((sizeY - minHeight) / 2)));// middle of task
															// window

		pack();
		setVisible(true);

		ControllerInterface.registerAsObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg;

		if (msg == DataModel.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			updateLanguagePresentation();
			revalidate();
			repaint();
		}

	}

	private void updateLanguagePresentation() {

		setTitle(languageBundle.getString("urgentTaskDialog.title"));
		lblName.setText(languageBundle.getString("urgentTaskDialog.labelName"));
		lblDate.setText(languageBundle.getString("urgentTaskDialog.labelDate"));
	}
}
