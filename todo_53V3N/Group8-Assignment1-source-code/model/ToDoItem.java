package model;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

/**
 * JavaBean that represents a ToDo-list item.
 * @author simon
 *
 */
@SuppressWarnings("serial") //The title is used as a unique attribute, 
							//the model makes sure that not two ToDoItems
							//with the same title get created
public class ToDoItem implements Serializable, Comparable<ToDoItem> {

	private String	title;
	private String	description;
	private Date	dueDate;
	private int		priority;
	private EnumSet<Category>	categories;
	private boolean	done;
	private boolean	deleted;
	private Date	creationDate;
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title of the ToDo-item to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description of the ToDo-item to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @return the List of all categories
	 */
	public EnumSet<Category> getCategories() {
		return categories;
	}
	/**
	 * @param categories the list of all categories to set
	 */
	public void setCategories(EnumSet<Category> categories) {
		this.categories = categories;
	}
	/**
	 * @param category the category to add to the list of categories
	 */
	public void addCategory(Category category) {
		this.categories.add(category);
	}
	/**
	 * @return the status if item is done
	 */
	public boolean isDone() {
		return done;
	}
	/**
	 * @param done the status if item is done to set
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	/**
	 * @return the status if item is deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the status if item is deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Overrides the method from Comparable. As compareTo should be consistent
	 * with equals, we'll compare here only the title.
	 * 
	 * @param o the ToDoItem to compare this to
	 * @return int the comparison result
	 */
	@Override
	public int compareTo(ToDoItem o) {
		if(this == o)
			return 0;
		return this.getTitle().compareTo(o.getTitle());
	}
	
	/**
	 * Overridden method from object to test if two ToDoItems are equal.
	 * As the title attribute is the only mandatory attribute and as it
	 * must be unique, we only test if the title differs.
	 * 
	 * @param obj the object to test against.
	 * @return boolean the result of the test
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        
        ToDoItem item = (ToDoItem)obj;
		if(this.getTitle().equals(item.getTitle()))
			return true;
		else
			return false;
	}
	
	/**
	 * This method overrides the hashCode generation method from the Object class.
	 * As the title attribute must be unique, it should be enough to calculate a
	 * hash. This might not be the fastest/best way to do it...
	 * @return int the calculated hasCode
	 */
	@Override
	public int hashCode() {
		return this.getTitle().hashCode();
	}
	
}
