package control;

import java.util.ResourceBundle;

import utility.GlobalValues;
import utility.GlobalValues.Languages;

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
	 * @return
	 */
	public final void setProperty(String key, String value) {
		dataModel.setProperty(key, value);
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
				.getProperty(GlobalValues.LANGUAGEVAL));

		if (oldLanguage != language)
			dataModel.setLanguage(language);
	}
}
