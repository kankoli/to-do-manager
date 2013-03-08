package view.urgent_task_dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import utility.GlobalValues;
import view.custom_components.FlagBar;

import model.Task;

import control.ControllerInterface;

public class UrgentTaskDialog extends JDialog {
	private ControllerInterface controller;

	public UrgentTaskDialog(final ControllerInterface controller) {
		super();
		this.controller = controller;

		setTitle("Urgent Tasks");

		JPanel pane = (JPanel) getContentPane();
		pane.setMinimumSize(new Dimension(300, 275));
		pane.setPreferredSize(new Dimension(300, 275));
		pane.setBackground(Color.WHITE);

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;

		JPanel pnlHeader = new JPanel();
		pnlHeader.setMinimumSize(new Dimension(270, 32));
		pnlHeader.setPreferredSize(new Dimension(270, 32));
		pnlHeader.setLayout(new GridLayout(0, 2));

		final ObserverComboBox cmbTasks = new ObserverComboBox(controller);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		pane.add(cmbTasks, c);
		
		JButton btn = new JButton();
		btn.setIcon(new ImageIcon(controller
				.getResource("assets/Icons/I_AddUrgent.png")));
		btn.setMinimumSize(new Dimension(45, 45));
		btn.setPreferredSize(new Dimension(45, 45));
		btn.setBorder(null);
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
		
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
		JLabel lbl = new JLabel("Name");
		lbl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		pnlHeader.add(lbl);

		lbl = new JLabel("Date");
		lbl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.darkGray));
		pnlHeader.add(lbl);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		pane.add(pnlHeader, c);

		final JList<Task> lst = new JList<Task>();

		ObserverListModel dlm = new ObserverListModel(controller);
		lst.setModel(dlm);
		lst.setCellRenderer(new UrgentCellRenderer(controller));
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
					//TODO show context menu
					JPopupMenu menu = new JPopupMenu();
					JMenuItem item = new JMenuItem("Delete");
					item.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							controller.setUrgent(lst.getSelectedValue(), false);
						}
					});
					menu.add(item);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		pane.add(lst, c);
		
		
		int minHeight = 270;
		// minHeight += nameField.getHeight();
		// minHeight += descriptionField.getHeight();
		// minHeight += nameField.getHeight();
		// minHeight += dateField.getHeight();
		// minHeight += cmbCategory.getHeight();
		// minHeight += button.getHeight();
		// minHeight *= 1.2;
		//
		int minWidth = 300;
		// minWidth += nameField.getWidth();
		// minWidth *= 1.2;

		// setPreferredSize(new Dimension(minWidth,minHeight));
		// setMinimumSize(new Dimension(minWidth,minHeight));
		setPreferredSize(new Dimension(minWidth, minHeight));
		setMinimumSize(new Dimension(minWidth, minHeight));

		// Retrieve last (main frame) size from state
		double sizeX = Double.parseDouble(controller
				.getProperty(GlobalValues.WINXSIZEKEY));
		double sizeY = Double.parseDouble(controller
				.getProperty(GlobalValues.WINYSIZEKEY));

		// retrieve last (main frame) location from state
		double posX = Double.parseDouble(controller
				.getProperty(GlobalValues.WINXPOSKEY));
		double posY = Double.parseDouble(controller
				.getProperty(GlobalValues.WINYPOSKEY));

		setLocation((int) (posX + ((sizeX - minWidth) / 2)),
				(int) (posY + ((sizeY - minHeight) / 2)));

		pack();
		setVisible(true);
	}
}
