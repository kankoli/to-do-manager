package view.main_window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Task;

import control.ControllerInterface;

/**
 * This class represent a table holding tasks. It will listen for changes on
 * dataModel and on the SortingBar.
 * 
 * @author Marco Dondio, Magnus Larsson
 * 
 */
@SuppressWarnings("serial")
public class TaskTable extends JTable implements Observer,
		PropertyChangeListener {

	private DefaultTableModel taskTableModel;

	private HashMap<Task, TaskRow> taskPanelsMap;

	private int curTaskRowIndex; // index of cur selected row!

	private int[] offsets; // stores data from sortingbar object

	/**
	 * This inner class is our the renderer / editor for each row
	 * 
	 * @author Marco Dondio, Magnus Larsson
	 */


	/**
	 * 
	 * @param controller
	 *            The controller of the application
	 * @param offsets
	 *            The array of integer storing the offsets for displaying task
	 *            correctly
	 */
	public TaskTable(int[] offsets) {
		super();

		this.offsets = offsets;

		// register as observer
		ControllerInterface.registerAsObserver(this);

		// Now some list behaviour
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		setRowHeight(60); // TODO make global constant

		// this.setro

		this.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		setTableHeader(null); // hide header, we use our own

		// XXX i dont like to associate to Object rendering my renderer
		// it should be TaskRow.class... to be checked when we have time!
		// define renderer and editor
		TaskRowEditorRenderer myRenderer = new TaskRowEditorRenderer();
		setDefaultRenderer(Object.class, myRenderer);
		setDefaultEditor(Object.class, myRenderer);
		// setDefaultRenderer(TaskRow.class, myRenderer);
		// setDefaultEditor(TaskRow.class, myRenderer);

		// Now mouse motion behaviour: we implement row selection
		// also in that way.
		addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseMoved(MouseEvent e) {

				curTaskRowIndex = rowAtPoint(e.getPoint());		

				// Signal it selected, will be used when rendering
				setRowSelectionInterval(curTaskRowIndex, curTaskRowIndex);
			}
	});
		
		// create empty map
		taskPanelsMap = new HashMap<Task, TaskRow>();

		// Finally retrieve data and load into table
		refreshView();
	}

	/**
	 * This method fills the table model by reloading data from controller
	 */
	// TODO magari ci va un boolean per indicare pending o completed????
	public final void refreshView() {

		// Create new DefaultdataModel object
		taskTableModel = new DefaultTableModel();
		taskTableModel.addColumn("Task"); // needed or table wont be drawed
		setModel(taskTableModel);

		// Add data: build taskRows panels
		// and add each row to the tasklistModel

		List<Task> tl = ControllerInterface.getFilteredTaskList();

		// TODO do otpimization later, here we check if
		// hashmap has more element this means we did delete
		Iterator<Task> iter = taskPanelsMap.keySet().iterator();
		while (iter.hasNext()) {

			Task t = iter.next();
			if (!tl.contains(t))
				iter.remove();

		}

		// XXX
		// int[] sizes = { 100, 20, 30, 40 };

		// Now create or retrieve panel
		for (Task t : tl) {
			// TaskRow row = new TaskRow(controller, this, t);

			TaskRow row = taskPanelsMap.get(t);
			if (row == null) {
				row = new TaskRow(t, offsets);
			//	row.addPropertyChangeListener(this); // register as listener on
														// row
				taskPanelsMap.put(t, row);

			}

			taskTableModel.addRow(new TaskRow[] { row });
		}
	}

	/**
	 * This method is called by a row, to signal that he is selected. This
	 * method will make the panel aware of who is the currently selected Row
	 * 
	 * @param newtable_TaskRow
	 *            the row wich is selected
	 */
	protected final void selectTaskRow(TaskRow TaskRow) {

		// TODO to be fixed

		// Deselect previous TEST_TaskRow
		// if (curSelected != null)
		// curSelected.setSelected(false);
		//
		// // And then make selected current row
		// curSelected = row;
	}

	/**
	 * This method is called by a TEST_TaskRow when user deletes it. This makes
	 * TaskScrollPanel delete it from viewport and removes it also in the
	 * datamodel itself.
	 */
	protected final void deleteTask() {

		// TODO to be fixed
		// problema: view index e datamodel index

		System.out.println("[deleteTask] Removing " + curTaskRowIndex);



		taskTableModel.removeRow(convertRowIndexToModel(curTaskRowIndex));

		// taskTableModel.fireTableRowsDeleted(curTaskRowIndex,
		// curTaskRowIndex);

		// taskTableModel.
		// tableConvertRowIndexToModel(1);

		// Task t = curSelected.getTask();
		//
		// // JOptionPane.showMessageDialog(null, "Task \"" + t.getName()
		// // + "\" removed!");
		//
		// // Removes
		// taskList.remove(curSelected);
		// viewPort.remove(curSelected);
		// curSelected = null;
		//
		// // remove from datamodel
		// controller.deleteTask(t);

		// curTaskRow;

		// taskTableModel.

		// TODO

		// revalidate();
		// repaint();
	}

	// TODO
	public final void resizeRow(int size) {

		this.setRowHeight(curTaskRowIndex, size);
	}

	/**
	 * Called to resize internal text areas. Unused yet
	 * 
	 * @param offsets
	 */
	public final void resizeColumnsContent(int[] offsets) {

		// TODO check with Magnus
	}

	/**
	 * This method will be fired when dataModel changes
	 */
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		refreshView();

		// TODO check if we need or not
		// this.revalidate();
		// this.repaint();

	}

	/**
	 * This method will be fired when a property on the sortingBar changes
	 */
	public void propertyChange(PropertyChangeEvent evt) {

		// TODO we can have many source
		if (evt.getSource() instanceof TaskRow) {
			{
				
//				NEW_TaskRow tr = 					(NEW_TaskRow) evt.getSource();
				
				if (evt.getPropertyName().equals("Status")) {
					TaskRow.TaskRowStatus curStatus = (TaskRow.TaskRowStatus) evt
							.getNewValue();

					if (curStatus == TaskRow.TaskRowStatus.OPENED
							|| curStatus == TaskRow.TaskRowStatus.EDITING)
						this.setRowHeight(curTaskRowIndex, 143);

					
//					if (!tr.getSelected()  || curStatus == NEW_TaskRow.TaskRowStatus.CLOSED)
//						this.setRowHeight(curTaskRowIndex, 60);

					
					if (curStatus == TaskRow.TaskRowStatus.CLOSED)
						this.setRowHeight(curTaskRowIndex, 60);

				}

			}
		}

	}

}
