package control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

import utility.GlobalValues;
import utility.GlobalValues.Languages;
import utility.GlobalValues.Themes;

import model.DataModel;

/**
 * This class will implement the properties controller part.
 * 
 * @author Marco Dondio
 * 
 */

public final class PropertiesController {

	private DataModel dataModel;

	public PropertiesController(DataModel dataModel) {

		this.dataModel = dataModel;
	}

	/**
	 * * This method retrieves the property from the dataModel
	 * 
	 * @param key
	 * @return
	 */
	public final String getProperty(String key) {
		return dataModel.getProperty(key);
	}

	/**
	 * * This method sets a property on the dataModel
	 * 
	 * @param key
	 * @param value
	 * @param notifyObservers
	 *            indicates wheter the property change should fire an event
	 * @return
	 */
	public final void setProperty(String key, String value,
			boolean notifyObservers) {
		dataModel.setProperty(key, value, notifyObservers);
	}

	/**
	 * This method retrieves the current setted languageBundle from dataModel
	 * 
	 * @return
	 */
	public final ResourceBundle getLanguageBundle() {
		return dataModel.getLanguageBundle();
	}

	/**
	 * This method is called when new language is selected
	 * 
	 * @param index
	 */
	public final void setLanguage(Languages language) {
		Languages oldLanguage = Languages.valueOf(dataModel
				.getProperty(GlobalValues.LANGUAGEKEY));

		if (oldLanguage != language)
			dataModel.setLanguage(language);
	}

	/**
	 * This method is called when new theme is selected
	 * 
	 * @param index
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public final void setTheme(Themes theme) throws FileNotFoundException, IOException {
			
			dataModel.setTheme(theme);
	}

}
