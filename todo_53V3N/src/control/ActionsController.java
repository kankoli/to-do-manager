package control;

import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import javax.swing.Action;
import javax.swing.ImageIcon;

import control.ControllerInterface.ActionName;
import shared_actions.ChangeLanguageAction;
import shared_actions.ExitAction;
import shared_actions.NewCategoryAction;
import shared_actions.NewTaskAction;
import shared_actions.SortTaskAction;
import shared_actions.TimerAction;
import model.DataModel;

/**
 * This class will implement the actions controller part.
 * 
 * @author Marco Dondio
 * 
 */

public final class ActionsController {

	private DataModel dataModel;

	// Here all actions we need
	private Action changeLanguage;
	private Action exitAction;
	private Action newTask;
	private Action newCategory;
	private Action sortTasks;
	private Action timerAction;

	private static ImageIcon addtask = new ImageIcon(
			ControllerInterface.getResource("assets/Icons/I_Plus.png"));
	
	public ActionsController(DataModel dataModel) {

		this.dataModel = dataModel;

		// Instantiate actions using current dataModel language
		ResourceBundle lang = dataModel.getLanguageBundle();

		changeLanguage = new ChangeLanguageAction(
				lang.getString("shared_actions.changelanguageaction.text"),
				null,
				lang.getString("shared_actions.changelanguageaction.desc"),
				KeyEvent.VK_L);

		exitAction = new ExitAction(
				lang.getString("shared_actions.exitaction.text"), null,
				lang.getString("shared_actions.exitaction.desc"),
				KeyEvent.VK_Q);

		newTask = new NewTaskAction(
				lang.getString("shared_actions.newtaskaction.text"), addtask,
				lang.getString("shared_actions.newtaskaction.desc"),
				KeyEvent.VK_T);


		newCategory = new NewCategoryAction(
				lang.getString("shared_actions.newcategoryaction.text"), null,
				lang.getString("shared_actions.newcategoryaction.desc"),
				KeyEvent.VK_C);

		sortTasks = new SortTaskAction(
				lang.getString("shared_actions.sorttaskaction.text"), null,
				lang.getString("shared_actions.sorttaskaction.desc"),
				KeyEvent.VK_O);
		
		timerAction = new TimerAction(); 

	}

	/**
	 * Retrieves an action from the Controller
	 * 
	 * @param actionName
	 * @return
	 */

	public final Action getAction(ActionName actionName) {

		switch (actionName) {
		case CHANGELANG:
			return changeLanguage;
		case EXIT:
			return exitAction;
		case NEWTASK:
			return newTask;
		case NEWCAT:
			return newCategory;
		case SORT:
			return sortTasks;
		case TIMER:
			return timerAction;
		default:
			return null;
		}
	}

	/**
	 * This method is called to change actions language using current setted
	 * language
	 */
	public final void refreshLanguage() {

		ResourceBundle lang = dataModel.getLanguageBundle();

//		changeLanguage.putValue(Action.NAME,
//				lang.getString("shared_actions.changelanguageaction.text"));
//		changeLanguage.putValue(Action.SHORT_DESCRIPTION,
//				lang.getString("shared_actions.changelanguageaction.desc"));

		exitAction.putValue(Action.NAME,
				lang.getString("shared_actions.exitaction.text"));
		exitAction.putValue(Action.SHORT_DESCRIPTION,
				lang.getString("shared_actions.exitaction.desc"));

		newTask.putValue(Action.NAME,
				lang.getString("shared_actions.newtaskaction.text"));
		newTask.putValue(Action.SHORT_DESCRIPTION,
				lang.getString("shared_actions.newtaskaction.desc"));

		newCategory.putValue(Action.NAME,
				lang.getString("shared_actions.newcategoryaction.text"));
		newCategory.putValue(Action.SHORT_DESCRIPTION,
				lang.getString("shared_actions.newcategoryaction.desc"));

//		sortTasks.putValue(Action.NAME,
//				lang.getString("shared_actions.sorttaskaction.text"));
//		sortTasks.putValue(Action.SHORT_DESCRIPTION,
//				lang.getString("shared_actions.sorttaskaction.desc"));

	}
}
