package model;

import java.util.HashSet;

import exceptions.ToDoItemExistsException;

/**
 * This model loads the {@link ToDoItem}-objects from a XML-file
 * TODO: nothing is implemented yet!
 * @author simon
 *
 */
public class XMLFileToDoItemModel extends ToDoItemModel {

	@Override
	public HashSet<ToDoItem> getAllToDoItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateToDoItem(ToDoItem oldItem, ToDoItem newItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public ToDoItem createToDoItem(String title) throws ToDoItemExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markToDoItemAsDone(ToDoItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markToDoItemAsUndone(ToDoItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteToDoItem(ToDoItem item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restoreToDoItem(ToDoItem item) {
		// TODO Auto-generated method stub

	}

}
