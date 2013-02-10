package marco_tests.taskScrollImproved;

import java.util.Date;

import model.Category;

/**
 * This class represents a basic task, with name, date, type and priority. It
 * contains getter (to retrieve values) and setters (to store new values)
 * 
 * @author Marco Dondio
 * @version 1.1
 * 
 */
public final class TaskImproved {

	public enum Priority {
		HIGH, NORMAL, LOW
	}; 
	private String name;
	private Date date;
	private Category category;		// XXX perch� non direttamente reference a category???
	private Priority prio;
	private boolean completed;
	private String description;

	public TaskImproved(){
		
	}
	
	public TaskImproved(String name, Date date, Category category, Priority prio, String description) {
		this.name = name;
		this.date = date;
		this.category = category;
		this.prio = prio;
		this.completed = false;
		this.description = description;
	}


	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final Date getDate() {
		return date;
	}

	public final void setDate(Date date) {
		this.date = date;
	}

	public final Category getCategory() {
		return category;
	}

	public final void setCategory(Category category) {
		this.category = category;
	}

	public final Priority getPrio() {
		return prio;
	}

	public final void setPrio(Priority prio) {
		this.prio = prio;
	}

	public final boolean getCompleted() {
		return completed;
	}

	public final void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

}
