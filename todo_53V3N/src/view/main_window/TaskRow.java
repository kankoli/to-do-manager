package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
 * NOTE: this component has to be heavily optimized, we used many old designs...
 * even some useless things still there This component represent one row (one
 * task) in the task scroll panel, with wich user can interact.
 * 
 * @author Marco Dondio, Magnus Larsson
 * 
 */

// TODO: lot of debugging, code should be better organized.. no time..
@SuppressWarnings("serial")
public final class TaskRow extends JPanel {

	private Task task;

	private int[] offsets;
	private boolean isSelected;

	private static ImageIcon doneIcon = new ImageIcon(
			ControllerInterface.getResource("assets/Icons/I_Ok.png"));
	private static ImageIcon deleteIcon = new ImageIcon(
			ControllerInterface.getResource("assets/Icons/I_Cancel.png"));

	// TODO MAKE ARRAY OF COMPONENTS!!!
	// no time unfortunately

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

	public TaskRow(final Task task, int[] offsets) {
		this.task = task;

		this.offsets = offsets;
		this.isSelected = false;

		this.setLayout(null);

		setBackground(Color.lightGray);

		initComponents();

	}

	// This methods initializes components, we r not using any layout manager
	private void initComponents() {

		doneBtn = new JButton();
		doneBtn.setIcon(doneIcon);
		doneBtn.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				ControllerInterface.toggleCompleted(task);
			}
		});

		this.add(doneBtn);

		deleteBtn = new JButton();
		deleteBtn.setIcon(deleteIcon);
		deleteBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showOptionDialog(null,
						"Are you sure you want to delete \"" + task.getName()
								+ "\" ?", "Confirm task deletion",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
						null, null, null) == JOptionPane.YES_OPTION) {

				ControllerInterface.deleteTask(task);
				}
			}
		});

		this.add(deleteBtn);

		nameLbl = new JLabel(task.getName());
		add(nameLbl);

		String shortDesc = task.getDescription();

		shortDescLbl = new JLabel(
				(shortDesc.length() <= 15) ? shortDesc.substring(0,
						Math.min(15, shortDesc.length())) : shortDesc
						.substring(0, Math.min(15, shortDesc.length())) + "...");

		shortDescLbl.setToolTipText(task.getDescription());

		add(shortDescLbl);
		categoryLbl = new JLabel(task.getCategory().getName());
		add(categoryLbl);

		dateLbl = new JLabel(ControllerInterface.getDateFormat().format(
				task.getDate()));
		add(dateLbl);

		bar = new PriorityBar(task.getPrio());
		bar.setEnabled(false);
		add(bar);
	};

	/**
	 * This method is called to change offsets after dragging sortingbar
	 * 
	 * @param offsets
	 *            The offsets to be
	 */
	public void setOffsets(int[] offsets) {
		// TODO
		// we didnt finish in time
	}

	// XXX We r using our own painting behaviour...
	private void refreshRendering() {
		
		System.out.println("[Task: "+ task.getName()+"] refreshRendering " + isSelected);
		Dimension sizes = doneBtn.getPreferredSize();
		doneBtn.setBounds(0, 0, sizes.width, sizes.height);

		if (isSelected)
			renderSelected();
		else
			renderNotSelected();
	}

	// This renders the row when not selected
	private void renderNotSelected() {

		Dimension sizes = nameLbl.getPreferredSize();
		nameLbl.setBounds(offsets[0], 20, sizes.width, sizes.height);

		sizes = shortDescLbl.getPreferredSize();
		shortDescLbl.setBounds(offsets[0] + 20 + nameLbl.getWidth(), 20,
				sizes.width, sizes.height);

		sizes = dateLbl.getPreferredSize();
		dateLbl.setBounds(offsets[1], 20, sizes.width, sizes.height);

		sizes = categoryLbl.getPreferredSize();
		categoryLbl.setBounds(offsets[2] + 15, 20, sizes.width, sizes.height);

		sizes = bar.getPreferredSize();
		bar.setBounds(offsets[3], 6, sizes.width, sizes.height);

		deleteBtn.setVisible(false);

		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	// This renders the row when selected
	private void renderSelected() {

		Dimension sizes = deleteBtn.getPreferredSize();
		deleteBtn.setBounds(this.getWidth() - 10 - sizes.width, 13,
				sizes.width, sizes.height);

		
		deleteBtn.setVisible(true);

		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
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

	public boolean getSelected() {
		return isSelected;
	}

	/**
	 * NOTE: We r NOT using a layout manager, because we want to simulate a
	 * dragging behaviour of a table header, we make our own component
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Add color stripe
		g.setColor(task.getCategory().getColor());
		g.fillRect(offsets[2], 0, 10, 60);
		
		refreshRendering();
	}

	public String toString() {
		return task.getName();
	}

	public Task getTask() {
		return task;
	}
}