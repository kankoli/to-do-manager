package marco_tests.taskScrollImproved;

import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



// TODO
// LAVORATO CIRCA 1H 20
// ho implementato la drop down, colori che cambiano...

// 1) metti drop down list, con aggiunta categorie e controlli dinamici. Sistema layout...
// sistema priority
// need button to add category

// 2) crea controller, da discutere col team! sorter, checker
// 3) metti pulsanti immagini..
// 4) metti priority

/**
 * This class represent the task scrollable panel, wich contains the list of TaskRow
 * 
 * @author Marco Dondio
 * 
 */
@SuppressWarnings("serial")
public final class TaskScrollPanelImproved extends JScrollPane {

	private dbConnectorImproved dataModel;
	private JPanel viewPort;
	private List<TaskRowImproved> taskList;	// we store our child here, useful to access them
	private TaskRowImproved curSelected;	// here we have reference to currently selected TaskRow

	/**
	 * Constructor
	 * 
	 * @param offsets
	 *            the offsets of textarea fields, not used now (check with Magnus)
	 * @param dataModel
	 *            the dataModel object
	 */
	public TaskScrollPanelImproved(int[] offsets, dbConnectorImproved dataModel) {
		super();
		this.dataModel = dataModel;
		
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

		refreshView();

	}

	/**
	 * This method set the content of viewport using actuale data model state
	 */
	public final void refreshView() {

		viewPort = new JPanel();
		taskList = new LinkedList<TaskRowImproved>();
		curSelected = null;


// TODO layout is not ready yet		
		// http://docs.oracle.com/javase/tutorial/uiswing/layout/box.html

		viewPort.setLayout(new BoxLayout(viewPort, BoxLayout.PAGE_AXIS));

		// Load from datamodel tasks
		for (TaskImproved t : dataModel.getTaskList()) {
			TaskRowImproved row = new TaskRowImproved(this, t);

			// Now i add one row to the panel and to my List
			taskList.add(row);
			viewPort.add(row);
		}

		// Finally set the new viewport, new panel with all rows
		setViewportView(viewPort);
	}

	public final void setDataModel(dbConnectorImproved dataModel) {
		this.dataModel = dataModel;
	}

	public final dbConnectorImproved getDataModel(){
		return dataModel;
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
	 * This method is called by a row, to signal that he is selected.
	 * This method will make the panel aware of who is the currently selected Row
	 * 
	 * @param row the row wich is selected
	 */
	protected final void selectTaskRow(TaskRowImproved row) {

		// Deselect previous TaskRow
		if (curSelected != null)
			curSelected.setSelected(false);

		// And then make selected current row
		curSelected = row;
	}

	/**
	 * This method is called by a TaskRow when user deletes it.
	 * This makes TaskScrollPanel delete it from viewport and removes it also in 
	 * the datamodel itself.
	 */
	protected final void deleteTask() {

		TaskImproved t = curSelected.getTask();

		// JOptionPane.showMessageDialog(null, "Task \"" + t.getName()
		// + "\" removed!");

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
	 * This method is called by a TaskRow when user has finished editing it.
	 * It just forces the viewport of this ScrollPanel to be revalidated and repainted.
	 * Modifications on Task are reflected automatically in dataModel, because
	 * we are accessing same objects.
	 */
	protected final void editedTask() {

		// Redraw panel
		viewPort.revalidate();
		viewPort.repaint();
	}

}
