package control;

import java.util.Observer;

import model.DataModel;

/**
 * This class will implement the actions controller part.
 * 
 * @author Marco Dondio
 * 
 */

public final class ObserverController {

	private DataModel dataModel;

	public ObserverController(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * This method is called to register as an observer on the datamodel
	 * 
	 * @param o
	 */
	public final void registerAsObserver(Observer o) {
		dataModel.addObserver(o);
	}

	/**
	 * This method is called to delete an observer from the datamodel
	 * 
	 * @param o
	 */
	public final void deleteObserver(Observer o) {
		dataModel.deleteObserver(o);
	}

}
