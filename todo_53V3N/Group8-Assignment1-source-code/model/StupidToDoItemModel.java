package model;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

import exceptions.ToDoItemExistsException;

/**
 * This is just a very stupid model with some inputed data
 * for debugging and a first test...
 * @author simon
 *
 */
public class StupidToDoItemModel extends ToDoItemModel {

	private HashSet<ToDoItem> items;
	
	/**
	 * default constructor setting up an initial set of {@link ToDoItem}s
	 */
	public StupidToDoItemModel() {
		this.items = new HashSet<ToDoItem>();
		
		ToDoItem i = new ToDoItem();
		i.setCreationDate(new Date());
		i.setTitle("Remember the milk");
		i.addCategory(Category.PRIVATE);
		i.setDescription("Buy a liter of organic milk from Uppland.");
		i.setDueDate(new GregorianCalendar(2013,1,10).getTime());
		i.setPriority(1);
		this.items.add(i);
		
		i = new ToDoItem();
		i.setCreationDate(new Date());
		i.setTitle("Learn for the exam");
		i.addCategory(Category.UNIVERSITY);
		i.setDescription("Don't forget the book.");
		i.setDueDate(new GregorianCalendar(2013,2,1).getTime());
		i.setPriority(3);
		this.items.add(i);

		i = new ToDoItem();
		i.setCreationDate(new Date());
		i.setTitle("Hand in thesis");
		i.addCategory(Category.UNIVERSITY);
		i.setDescription("What shall I write about?");
		i.setDueDate(new GregorianCalendar(2014,6,1).getTime());
		i.setPriority(2);
		this.items.add(i);

		i = new ToDoItem();
		i.setCreationDate(new Date());
		i.setTitle("Feed the cat");
		i.addCategory(Category.PRIVATE);
		i.setDescription("and give something to drink too...");
		i.setDueDate(new GregorianCalendar(2013,1,10).getTime());
		i.setPriority(1);
		this.items.add(i);
	}
	
	@Override
	public HashSet<ToDoItem> getAllToDoItems() {
		return this.items;
	}

	@Override
	public void updateToDoItem(ToDoItem oldItem, ToDoItem newItem) {
		this.items.remove(oldItem);
		this.items.add(newItem);
	}

	@Override
	public ToDoItem createToDoItem(String title) throws ToDoItemExistsException {
		for (ToDoItem item : this.items) {
			if(item.getTitle().equals(title))
				throw new ToDoItemExistsException();
		}
		ToDoItem item = new ToDoItem();
		item.setTitle(title);
		this.items.add(item);
		return item;
	}

	@Override
	public void markToDoItemAsDone(ToDoItem item) {
		for (ToDoItem item1 : this.items) {
			if(item1.equals(item)) {
				this.items.remove(item1);
				item.setDone(true);
				this.items.add(item);
			}	
		}
	}

	@Override
	public void markToDoItemAsUndone(ToDoItem item) {
		for (ToDoItem item1 : this.items) {
			if(item1.equals(item)) {
				this.items.remove(item1);
				item.setDone(false);
				this.items.add(item);
			}	
		}
	}

	@Override
	public void deleteToDoItem(ToDoItem item) {
		for (ToDoItem item1 : this.items) {
			if(item1.equals(item)) {
				this.items.remove(item1);
				item.setDeleted(true);
				this.items.add(item);
			}	
		}
	}

	@Override
	public void restoreToDoItem(ToDoItem item) {
		for (ToDoItem item1 : this.items) {
			if(item1.equals(item)) {
				this.items.remove(item1);
				item.setDeleted(false);
				this.items.add(item);
			}	
		}
	}
}
