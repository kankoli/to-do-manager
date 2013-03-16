package model;

import java.util.Date;



/**
 * This class represents a basic task, with name, date, type and priority. It
 * contains getter (to retrieve values) and setters (to store new values)
 * 
 * @author Marco Dondio
 * @version 1.1
 * 
 */
public final class Task {

	public static enum Priority {
		HIGH, NORMAL, LOW, NOT_SET
	}; 

	private String name;
	private Date date;
	private Category category;
	private Priority prio;
	private boolean completed;
	private String description;
	private boolean urgent;

	public Task(){
		
	}
	
	/**
	 * Constructor for a task.
	 * @param name The name of task
	 * @param date The date of task
	 * @param category The category of task
	 * @param prio The priority level
	 * @param description The description
	 */
	public Task(String name, Date date, Category category, Priority prio, String description) {
		this.name = name;
		this.date = date;
		this.category = category;
		this.prio = prio;
		this.completed = false;
		this.description = description;
		this.urgent = false;
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

	public final void setCompleted(boolean b) {
		this.completed = b;
	}
	
	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public final boolean getUrgent() {
		return urgent;
	}

	public final void setUrgent(boolean b) {
		this.urgent = b;
	}
	
	public final String toString() {
		return getName();
	}
}
