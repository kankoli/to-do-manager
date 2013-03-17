package view.main_window;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import model.DataModel;
import control.ControllerInterface;


/**
 * This class represents a custom version of the class 'SortingBar'.
 * It is extended from SortingBar to be able to act as an observer for
 * language changes in the model, and keep the language updated.
 * 
 * @author Magnus Larsson
 *
 */
public class CustomSortingBar extends SortingBar implements Observer {
	
	private ResourceBundle languageBundle;
	
	/**
	 * Constructor
	 * 
	 * @param tabNames array with names to be displayed on tabs
	 * @param tabWidths array of the tab widths (in pixels)
	 * @param minTabWidths array of the tab's minimum widths (in pixels)
	 * @param tabHeights the height if the tabs (in pixels)
	 * @param selectedTabColor color on selected tab
	 * @param notSelectedTabColor color on non selected tabs
	 * @param languageBundle bundle including key/value pairs for managing the language
	 */
	public CustomSortingBar(String[] tabNames, int[] tabWidths, int[] minTabWidths, int tabHeights, Color selectedTabColor, Color notSelectedTabColor,ResourceBundle languageBundle) {
		super(tabNames, tabWidths, minTabWidths, tabHeights, selectedTabColor, notSelectedTabColor); 
		
		this.languageBundle = languageBundle;
		
		ControllerInterface.registerAsObserver(this);
	}
	
	/**
	 * Updates the language (tab text) using the language bundle
	 */
	private void updateLanguage() {
		setTabName(0, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.title.name"));
		setTabName(1, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.date.name"));
		setTabName(2, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.category.name"));
		setTabName(3, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"));
	}
	
	/**
	 * Method called when a change is made in the model
	 */
	public void update(Observable o, Object arg) {
		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg;

		if (msg == DataModel.ChangeMessage.CHANGED_PROPERTY) {
			languageBundle = ControllerInterface.getLanguageBundle();
			updateLanguage();
			
			revalidate();
			repaint();
		}
	}
}
