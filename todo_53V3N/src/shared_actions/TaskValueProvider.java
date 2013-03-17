package shared_actions;

import java.util.Date;

import model.Task;
import model.Task.Priority;

/**
 * This interface represents an object that will be able to provide
 * the values of a task being edited.
 * @author Magnus & Marco
 *
 */
public interface TaskValueProvider {

	public Task getTask();

	public String getTaskName();

	public Date getDate();

	public Priority getPrio();

	public Boolean getCompleted();

	public String getCategoryName();

	public String getDescription();
}
