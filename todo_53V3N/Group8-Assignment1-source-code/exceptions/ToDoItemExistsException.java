package exceptions;

import model.ToDoItem;

/**
 * This exception is used to indicate, that the same {@link ToDoItem} already exists.
 * (In plain words: the only mandatory attribute is the title, thus this exception 
 * will be thrown if someone tries to create a second {@link ToDoItem} with an already
 * existing title.)
 * @author simon
 *
 */
@SuppressWarnings("serial")
public class ToDoItemExistsException extends Exception {

}
