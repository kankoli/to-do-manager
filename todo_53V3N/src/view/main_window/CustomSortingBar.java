package view.main_window;

import java.awt.Color;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import model.DataModel;
import control.ControllerInterface;



public class CustomSortingBar extends SortingBar implements Observer {
	
	private ResourceBundle languageBundle;
	
	public CustomSortingBar(String[] tabNames, int[] tabWidths, int[] minTabWidths, int tabHeights, Color selectedTabColor, Color notSelectedTabColor,ResourceBundle languageBundle) {
		super(tabNames, tabWidths, minTabWidths, tabHeights, selectedTabColor, notSelectedTabColor); 
		
		this.languageBundle = languageBundle;
		
		ControllerInterface.registerAsObserver(this);
	}
	
	private void updateLanguage() {
		setTabName(0, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.title.name"));
		setTabName(1, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.date.name"));
		setTabName(2, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.category.name"));
		setTabName(3, languageBundle.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"));
	}
	
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
