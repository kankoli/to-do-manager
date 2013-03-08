package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdom2.JDOMException;

import model.DataModel;

import view.custom_components.FlagBar;
import view.custom_components.PendingCompletedRadioButtons;
import view.urgent_task_dialog.UrgentTaskDialog;
import control.ControllerInterface;

@SuppressWarnings("serial")
public class TestMainWindow extends JFrame {

	private static ImageIcon urgentIcon = new ImageIcon(ControllerInterface.getResource("assets/Icons/I_Exclamation.png"));
	private static DataModel db;
	
	public static void main(String[] args) {
		new TestMainWindow();
	}
	
	public TestMainWindow() {
		super();
		
		try {
			db = new DataModel();

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ControllerInterface.init(db);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JPanel pnlFrame = new JPanel();
		pnlFrame.setLayout(new GridBagLayout());
		
		JPanel pnlTop = getTopPanel();
		
		JPanel pnlBottom = new JPanel(new GridBagLayout());
		
		JPanel pnlSortBar = getSortBarPanel();
		EditPanel pnlTaskEdit = getTaskEditPanel();
		JScrollPane pnlTaskTable = getTaskTablePanel(pnlTaskEdit);
		JPanel pnlStatus = getStatusPanel();
		
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlBottom.add(pnlSortBar, c);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlBottom.add(pnlTaskTable, c);

		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		pnlBottom.add(pnlTaskEdit, c);
		
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		pnlBottom.add(pnlStatus, c);
		
		

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlFrame.add(pnlTop, c);
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlFrame.add(pnlBottom, c);
		
		setContentPane(pnlFrame);
		pack();
		setVisible(true);
	}

	private JPanel getTopPanel() {
		JPanel pnlTop = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel lblClock = new JLabel("ClOcK");
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10,10,10,10);
		c.anchor = GridBagConstraints.WEST;
		pnlTop.add(lblClock, c);
		
		FlagBar flagBar = new FlagBar();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10,10,10,10);
		c.anchor = GridBagConstraints.EAST;
		pnlTop.add(flagBar, c);
		
		JButton urgentButton = new JButton();
		urgentButton.setIcon(urgentIcon);
		urgentButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) { 
				new UrgentTaskDialog();
			}
		});
		urgentButton.setMinimumSize(new Dimension(60, 60));
		urgentButton.setPreferredSize(new Dimension(60, 60));
		urgentButton.setText("");
		urgentButton.setBorder(null);
		// urgentButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.darkGray));
		urgentButton.setBackground(Color.WHITE);
		urgentButton.setOpaque(true);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10,10,10,10);
		c.anchor = GridBagConstraints.WEST;
		pnlTop.add(urgentButton, c);
		
		JButton newTaskButton = new JButton();
		newTaskButton.setAction(ControllerInterface.getAction(ControllerInterface.ActionName.NEWTASK));
		newTaskButton.addPropertyChangeListener("text", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent e) {
				((JButton) e.getSource()).setText("");
			}
			
		});
		newTaskButton.setMinimumSize(new Dimension(60, 60));
		newTaskButton.setPreferredSize(new Dimension(60, 60));
		newTaskButton.setText("");
		// newTaskButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.darkGray));
		newTaskButton.setBorder(null);
		// newTaskButton.set
		newTaskButton.setBackground(Color.WHITE);
		newTaskButton.setOpaque(true);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10,10,10,10);
		c.anchor = GridBagConstraints.EAST;
		pnlTop.add(newTaskButton, c);
		
		PendingCompletedRadioButtons btns = new PendingCompletedRadioButtons();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10,10,10,10);
		c.anchor = GridBagConstraints.WEST;
		pnlTop.add(btns, c);
		
		return pnlTop;
	}

	private JPanel getSortBarPanel() {
		SortingBar pnlSortBar = new SortingBar(new int[]{100,100,100,100});
		pnlSortBar.setBackground(Color.yellow);
		return pnlSortBar;
	}

	private EditPanel getTaskEditPanel() {
		EditPanel pnlTaskEdit = new EditPanel();
		return pnlTaskEdit;
	}
	
	private JScrollPane getTaskTablePanel(EditPanel pnlTaskEdit) {
		TaskTable table = new TaskTable(new int[]{100,100,100,100});
		table.addPropertyChangeListener(pnlTaskEdit);
		JScrollPane scrollPane = new JScrollPane(table);
		return scrollPane;
	}
	
	private JPanel getStatusPanel() {
		JPanel pnlStatus = new JPanel();
		pnlStatus.setBackground(Color.darkGray);
		return pnlStatus;
	}
}
