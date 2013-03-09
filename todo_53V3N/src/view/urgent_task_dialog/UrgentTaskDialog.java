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
import javax.swing.SwingUtilities;

import utility.GlobalResources;
import utility.GlobalValues;

import model.Task;

import control.ControllerInterface;

@SuppressWarnings("serial")
public class UrgentTaskDialog extends JDialog implements Observer {

	private ResourceBundle languageBundle;
	private JLabel lblName;
	private JLabel lblDate;

	@SuppressWarnings("unchecked")
	public UrgentTaskDialog() {
		super();

		languageBundle = ControllerInterface.getLanguageBundle();

		// setTitle("Urgent Tasks");
		setTitle(languageBundle.getString("urgentTaskDialog.title"));

		// TODO should be in globalvalues?
		int minHeight = 400;
		int minWidth = 300;

		JPanel pane = (JPanel) getContentPane();

		// pane.setBackground(Color.WHITE);

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;

		JPanel pnlHeader = new JPanel();
		pnlHeader.setMinimumSize(new Dimension(270, 32));
		pnlHeader.setPreferredSize(new Dimension(270, 32));
		pnlHeader.setLayout(new GridLayout(0, 2));

		final ObserverComboBox cmbTasks = new ObserverComboBox();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		pane.add(cmbTasks, c);

		JButton btn = new JButton();
		btn.setIcon(GlobalResources.addUrgentIcon);
		btn.setMinimumSize(new Dimension(45, 45));
		btn.setPreferredSize(new Dimension(45, 45));
		btn.setBorder(null);
		// btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				cmbTasks.setUrgent();
			}
		});
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		pane.add(btn, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		lblName = new JLabel(
				languageBundle.getString("urgentTaskDialog.labelName"));
		lblName.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		pnlHeader.add(lblName);

		lblDate = new JLabel(
				languageBundle.getString("urgentTaskDialog.labelDate"));
		lblDate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		pnlHeader.add(lblDate);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		pane.add(pnlHeader, c);

		final JList<Task> lst = new JList<Task>();

		ObserverListModel<Task> dlm = new ObserverListModel<Task>();
		lst.setModel(dlm);
		lst.setCellRenderer(new UrgentCellRenderer());
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		lst.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				handle(e);
			}

			public void mouseReleased(MouseEvent e) {
				handle(e);
			}

			public void handle(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
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
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		// TODO, note by Marco & Magnus:
		// PLEASE, Show list inside JScrollPane! smtn like that:
		// JScrollPane scrollPane = new JScrollPane();
		// scrollPane.add(lst);
		// pane.add(scrollPane, c);

		pane.add(lst, c);

		// minWidth += nameField.getWidth();
		// minWidth *= 1.2;

		// setPreferredSize(new Dimension(minWidth,minHeight));
		// setMinimumSize(new Dimension(minWidth,minHeight));
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

		setLocation((int) (posX + ((sizeX - minWidth) / 2)),
				(int) (posY + ((sizeY - minHeight) / 2)));

		pack();
		setVisible(true);

		ControllerInterface.registerAsObserver(this);
	}

	@Override
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

		setTitle(languageBundle.getString("urgentTaskDialog.title"));
		lblName.setText(languageBundle.getString("urgentTaskDialog.labelName"));
		lblDate.setText(languageBundle.getString("urgentTaskDialog.labelDate"));
	}
}
