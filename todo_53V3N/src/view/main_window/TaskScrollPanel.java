package view.main_window;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Task;
import model.dbConnector;

/**
 * This class represent the task scrollable panel
 * 
 * @author Marco Dondio
 * 
 */
public class TaskScrollPanel extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private dbConnector dataModel;

	private JPanel viewPort;
	private List<TaskRow> taskList;
	private TaskRow curSelected;

	/**
	 * Constructor
	 * 
	 * @param offsets
	 *            the offsets of textarea fields
	 * @param dataModel
	 *            the dataModel object
	 */
	public TaskScrollPanel(int[] offsets, dbConnector dataModel) {
		super();
		this.dataModel = dataModel;

		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

		refreshView();

	}

	/**
	 * This method accesses dataModel to retrieve data and
	 */
	public void refreshView() {

		viewPort = new JPanel();
		taskList = new LinkedList<TaskRow>();
		curSelected = null;

		// box layout ideale

		// http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
		// TODO spippola con layout e distanze fra vari oggetti...

		viewPort.setLayout(new BoxLayout(viewPort, BoxLayout.PAGE_AXIS));

		// poi qui loada dal model/controller i dati iniziali!
		for (Task t : dataModel.getTaskList()) {
			// TODO
			// TaskRow row = new TaskRow(t.getName());
			TaskRow row = new TaskRow(this, t);

			// Now i add one row to the panel and to my List
			taskList.add(row);
			viewPort.add(row);
		}

		// Finally set the new viewport, new panel with all rows
		// TODO controlla che refreshi
		setViewportView(viewPort);

		// setVisible(true);
	}

	public void setDataModel(dbConnector dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Called to resize internal text areas
	 * 
	 * @param offsets
	 */
	public void resizeColumnsContent(int[] offsets) {

	}

	/**
	 * This method is called by a row
	 * 
	 * @param row
	 */
	protected void selectTaskRow(TaskRow row) {

		// Deselect previous TaskRow
		if (curSelected != null)
			curSelected.setSelected(false);

		// And then make selected current row
		curSelected = row;
	}

	/**
	 * This method is called by a TaskRow when user deletes it
	 */
	protected void deleteTask() {

		Task t = curSelected.getTask();

	//	JOptionPane.showMessageDialog(null, "Task \"" + t.getName()
		//		+ "\" removed!");

		// Removes
		taskList.remove(curSelected);
		viewPort.remove(curSelected);

		// remove also in datamodel
		dataModel.removeTask(t);
		curSelected = null;

		// Redraw panel
		viewPort.revalidate();
		viewPort.repaint();
	}

	/**
	 * This method is called by a TaskRow when user has finished editing it
	 */
	protected void editedTask() {

		// TODO
		// TODO
		// not sure is needed
		// in pratica che devo fare?
		// lastSelected

		// Redraw panel
		viewPort.revalidate();
		viewPort.repaint();
	}

}
