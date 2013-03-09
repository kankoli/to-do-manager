package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import view.custom_components.PriorityBar;

import model.Task;

import control.ControllerInterface;

/**
 * This component represent one row (one task) in the task scroll panel, with
 * wich user can interact.
 * 
 * @author Marco Dondio, Magnus Larsson
 * 
 */

@SuppressWarnings("serial")
public final class TaskRow extends JPanel implements Observer {

	private Task task;
	private TaskRowStatus curStatus;

	private int[] offsets;
	private boolean isSelected;

	private static ImageIcon editIcon = new ImageIcon(ControllerInterface.getResource("assets/Icons/I_Write.png"));
	private static ImageIcon doneIcon = new ImageIcon(ControllerInterface.getResource("assets/Icons/I_Ok.png"));
	private static ImageIcon deleteIcon = new ImageIcon(ControllerInterface.getResource("assets/Icons/I_Cancel.png"));

	// TODO MAKE ARRAY OF COMPONENTS!!!

	// Always displayed
	private JButton doneBtn;
	
	// Buttons
	private JButton deleteBtn;

	// "Passive components"
	private JLabel nameLbl;
	private JLabel shortDescLbl;
	private JLabel categoryLbl;
	private JLabel dateLbl;
	private PriorityBar bar;

	
	public static enum TaskRowStatus {
		CLOSED, OPENED, EDITING
	};

	public TaskRow(final Task task, int[] offsets) {
		this.task = task;

//		this.curStatus = TaskRowStatus.CLOSED;
//		this.setBackground(Color.red);// XXX debug

//		this.setMinimumSize(new Dimension(900, 60));
		
		this.offsets = offsets;
		this.isSelected = false;

		this.setLayout(null);

		setBackground(Color.lightGray);

		initComponents();

	}

	private void initComponents() {

		// Always there
		doneBtn = new JButton("");
		doneBtn.setIcon(doneIcon);
		doneBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				ControllerInterface.toggleCompleted(task);

				// TODO
				// This button will notify the parent (scrollpanel component)
				// that this TaskRow has to be moved to completed or pending
				// section.

				
			}
		});

		// completionBtn.setLocation(0, 0);
		this.add(doneBtn);
		// completionBtn.setLocation(0, 0);

		deleteBtn = new JButton("");
		deleteBtn.setIcon(deleteIcon);
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ControllerInterface.deleteTask(task);

			}
		});

		this.add(deleteBtn);

		// passive components
		nameLbl = new JLabel(task.getName());
		add(nameLbl);
		shortDescLbl = new JLabel("Hover for description"); // TODO exception?
		// shortDescLbl = new JLabel(task.getDescription().substring(0, 15) );
		// // TODO exception?

		shortDescLbl.setToolTipText(task.getDescription());

		add(shortDescLbl);
		categoryLbl = new JLabel(task.getCategory().getName());
		add(categoryLbl);

		// TODO will not be updated, to be solved...
		dateLbl = new JLabel(ControllerInterface.getDateFormat().format(task.getDate()));
		add(dateLbl);

		bar = new PriorityBar(task.getPrio());
		bar.setEnabled(false);
		add(bar);
	};

	/**
	 * This method is called to change status of the TaskRow.
	 * 
	 * @param taskRowStatus
	 *            The status to be setted on the TaskRow
	 */
	// TODO is really needed? why public?
	public void setStatus(TaskRow.TaskRowStatus newTaskRowStatus) {

		this.curStatus = newTaskRowStatus;
		
		this.firePropertyChange("Status", null, curStatus);
	}

	/**
	 * This method retrieves
	 * 
	 * @return TaskRow status
	 */
	public TaskRowStatus getStatus() {
		return curStatus;
	}

	// TODO paint method

	/**
	 * This method is called to change offsets
	 * 
	 * @param offsets
	 *            The offsets to be
	 */
	public void setOffsets(int[] offsets) {

		// TODO
	}

	// TODO method: needed to reload if task changes, then display of task
	// should be updated
	// ... settext...
	// private void refreshComponents();

	// This method will handle different rendering, depending on status
	private void refreshRendering() {

		// XXX: note, we make this AFTER supercall (wich uses layoutmanager)

		// Always here

		Dimension sizes = doneBtn.getPreferredSize();
		doneBtn.setBounds(0, 0, sizes.width, sizes.height);

		if (isSelected)
				renderClosed();
		else
			renderNotSelected();
	}

	private void renderNotSelected() {

		Dimension sizes = nameLbl.getPreferredSize();
		nameLbl.setBounds(offsets[0], 20, sizes.width, sizes.height);

		sizes = shortDescLbl.getPreferredSize();
		shortDescLbl.setBounds(offsets[0] + 20 + nameLbl.getWidth(), 20,
				sizes.width, sizes.height);

		sizes = dateLbl.getPreferredSize();
		dateLbl.setBounds(offsets[1], 20, sizes.width, sizes.height);

		sizes = categoryLbl.getPreferredSize();
		categoryLbl.setBounds(offsets[2]+15, 20, sizes.width, sizes.height);

		sizes = bar.getPreferredSize();
		bar.setBounds(offsets[3], 6, sizes.width, sizes.height);

		deleteBtn.setVisible(false);

		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
//		setBackground(Color.gray);
	}

	private void renderClosed() {

//
//		Dimension sizes = nameLbl.getPreferredSize();
//		nameLbl.setBounds(offsets[0], 10, sizes.width, sizes.height);
//
//		sizes = shortDescLbl.getPreferredSize();
//		shortDescLbl.setBounds(offsets[0] + 20 + nameLbl.getWidth(), 10,
//				sizes.width, sizes.height);
//
//		sizes = dateLbl.getPreferredSize();
//		dateLbl.setBounds(offsets[1], 10, sizes.width, sizes.height);
//
//		sizes = categoryLbl.getPreferredSize();
//		categoryLbl.setBounds(offsets[2], 10, sizes.width, sizes.height);
//
//		sizes = bar.getPreferredSize();
//		bar.setBounds(offsets[3], 6, sizes.width, sizes.height);
//
//
		Dimension sizes = deleteBtn.getPreferredSize();
		deleteBtn.setBounds(this.getWidth() - 10 - sizes.width, 13,
				sizes.width, sizes.height);
		deleteBtn.setVisible(true);
//		
		
//		setBackground(Color.white);

			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

//		setBackground(Color.red);
	}

	/**
	 * This method will be called by the renderer to update the selection status
	 * 
	 * @param isSelected
	 *            The boolean which indicates selection status
	 */
	public void setSelected(boolean isSelected) {

		this.isSelected = isSelected;
		refreshRendering();
	}

	public boolean getSelected(){
		return isSelected;
	}
	
	//
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // XXX is it really needed?

		
		g.setColor(task.getCategory().getColor());
		g.fillRect(offsets[2], 0, 10, 60);
		
		// Now handle my rendering using current status
		refreshRendering();
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		// XXX : remember that we r observer for dateformat events
		// WHAT ELSE???

	}
	
	public String toString(){
		return task.getName();
	}

	public Task getTask() {
		return task;
	}
}