package view.main_window;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
 * dataModel and on the SortingBar. NOT FINISHED YET, STILL DEVELOPING...
 * 
 * @author Marco Dondio, Magnus Larsson
 * 
 */
@SuppressWarnings("serial")
public class TaskTable extends JTable implements Observer,
		PropertyChangeListener {

	private DefaultTableModel taskTableModel;

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

		this.setBackground(Color.red);
		
		// register as observer
		ControllerInterface.registerAsObserver(this);

		// Now some list behaviour
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		setRowHeight(60); // XXX make global constant

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

				// keep index and
				// Signal it selected, will be used when rendering
				curTaskRowIndex = rowAtPoint(e.getPoint());
				setRowSelectionInterval(curTaskRowIndex, curTaskRowIndex);
			}
		});

		// Finally retrieve data and load into table
		refreshView();
	}

	/**
	 * This method fills the table model by reloading data from controller
	 */
	public final void refreshView() {

		// Create new DefaultdataModel object
		taskTableModel = new DefaultTableModel();
		taskTableModel.addColumn("Task"); // needed or table wont be drawed
		setModel(taskTableModel);

		// Add data: build taskRows panels
		// and add each row to the tasklistModel

		List<Task> tl = ControllerInterface.getFilteredTaskList();

		// Now create or retrieve panel
		for (Task t : tl) {

			// XXX Marco: IMPORTANT, OPTIMIZE: instead of creating a new
			// taskrow, we should
			// have
			// one and use like a updateTask.. we avoid instantiate each time a
			// new panel

			TaskRow row = new TaskRow(t, offsets);

			taskTableModel.addRow(new TaskRow[] { row });

		}
	}

	/**
	 * Called to resize internal text areas. NOT READY YET!
	 * 
	 * @param offsets
	 *            The offsets for resizing behaviour
	 */
	public final void resizeColumnsContent(int[] offsets) {

		// TODO NOT READY YET!!!
		// it will implement the dragging behaviour
		// TODO check with Magnus
	}

	/**
	 * This method will be fired when dataModel changes. It will reload the data
	 * in the table
	 */
	public void update(Observable o, Object arg) {
		refreshView();
//		revalidate();
//		repaint();
	}

	/**
	 * This method will be fired when a property on the sortingBar changes
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO
		// no time unfortunately... :(
	}

}