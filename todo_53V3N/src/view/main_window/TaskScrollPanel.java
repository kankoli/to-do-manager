package view.main_window;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.ControllerInterface;

import model.DataModel;
import model.Task;

// TODO: log

//-----------------

//TOTAL ASSIGNMENT 2 MARCO:
// 29.30h

//-------------------------------------
//-------------------------------------
//-------------------------------------
//-------------------------------------

// assignment 3


// 3 hours TOT so far








//--------------------
//--------------------
//--------------------
//--------------------

// 1) metti drop down list, con aggiunta categorie e controlli dinamici. Sistema layout...
// sistema priority

// 1) need button to add category, will open the dialog i created with color chooser. WHERE?????
// 2) crea controller, da discutere col team! sorter, checker
// 3) metti pulsanti immagini..
// 4) metti priority, pulsanti animati???
// 5) fix layout!!!!

/**
 * This class represent the task scrollable panel, wich contains the list of
 * TaskRow
 * 
 * @author Marco Dondio
 * 
 */
@SuppressWarnings("serial")
public final class TaskScrollPanel extends JScrollPane implements Observer {

	private ControllerInterface controller;
	private JPanel viewPort;
	private List<TaskRow> taskList; // we store our child here, useful to access
									// them
	private TaskRow curSelected; // here we have reference to currently selected
									// TaskRow

	/**
	 * Constructor
	 * 
	 * @param offsets
	 *            the offsets of textarea fields, not used now (check with
	 *            Magnus)
	 * @param controller
	 *            the controller object
	 */
	public TaskScrollPanel(int[] offsets, ControllerInterface controller) {
		super();
		this.controller = controller;

		// register as observer
		controller.registerAsObserver(this);

		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

		refreshView();
	}

	/**
	 * This method set the content of viewport using actuale data model state
	 */
	public final void refreshView() {

		viewPort = new JPanel();
		taskList = new LinkedList<TaskRow>();
		curSelected = null;

		// TODO layout is not ready yet
		// http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html

		viewPort.setLayout(new BoxLayout(viewPort, BoxLayout.PAGE_AXIS));

		// Load from datamodel tasks
		for (Task t : controller.getTaskList()) {
			TaskRow row = new TaskRow(controller, this, t);

			// Now i add one row to the panel and to my List
			taskList.add(row);
			viewPort.add(row);
		}

		// Finally set the new viewport, new panel with all rows
		setViewportView(viewPort);
	}

	// public final DataModel getDataModel(){
	// return dataModel;
	// }

	/**
	 * Called to resize internal text areas. Unused yet
	 * 
	 * @param offsets
	 */
	public final void resizeColumnsContent(int[] offsets) {

		// TODO check with Magnus
	}

	/**
	 * This method is called by a row, to signal that he is selected. This
	 * method will make the panel aware of who is the currently selected Row
	 * 
	 * @param row
	 *            the row wich is selected
	 */
	protected final void selectTaskRow(TaskRow row) {

		// Deselect previous TaskRow
		if (curSelected != null)
			curSelected.setSelected(false);

		// And then make selected current row
		curSelected = row;
	}

	/**
	 * This method is called by a TaskRow when user deletes it. This makes
	 * TaskScrollPanel delete it from viewport and removes it also in the
	 * datamodel itself.
	 */
	protected final void deleteTask() {

		Task t = curSelected.getTask();

		// JOptionPane.showMessageDialog(null, "Task \"" + t.getName()
		// + "\" removed!");

		// Removes
		taskList.remove(curSelected);
		viewPort.remove(curSelected);
		curSelected = null;

		// remove from datamodel
		controller.deleteTask(t);
	}

	// TODO add come observer
	public final void update(Observable o, Object arg) {

		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		// TODO
		// Redraw panel, redraw row! riorganizzare! da discutere con team

		refreshView();
		// viewPort.revalidate();
		// viewPort.repaint();
		//
		// revalidate();
		// repaint();

	}
}
