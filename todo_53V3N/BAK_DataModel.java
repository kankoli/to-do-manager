package model;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.ResourceBundle;

import model.Task.Priority;
import model.TaskComparators.DateComparator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


import utility.GeneralFunctions;
import utility.GlobalValues;
import utility.GlobalValues.Languages;
import utility.GlobalValues.Themes;

/**
 * This class represent the dataModel of our application. It contains all data
 * needed. This class manages, in transparent way, the I/O with the DB (wich is
 * a simple XML file). XSD Validation scheme is provided, so file will be
 * checked against a valid XSD schema to be sure data is valid. This class
 * builds our internal structures using some defined classes. It offers a clear
 * interface to the controller part
 * 
 * @author Marco Dondio
 * @version 1.0
 * 
 */

public final class DataModel extends Observable {

	// datetime formate used in xs:datetime format
	// NOTE: im using two small private function below to handle this..
	// https://www.ibm.com/developerworks/mydeveloperworks/blogs/HermannSW/entry/java_simpledateformat_vs_xs_datetime26?lang=en
	private static final SimpleDateFormat RFC822DATETIME = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	// Messages for notifying relevant observer classes.
	public static enum ChangeMessage {
		INIT, CHANGED_THEME, SORTED_TASK, CHANGED_PROPERTY, NEW_TASK, NEW_CATEGORY, DELETED_TASK, DELETED_CATEGORY, EDIT_TASK, CHANGED_FILTER, EDIT_URGENT
	};

	// Internal data structures
	private List<Task> taskList;
	private Map<String, Category> categories;

	// Properties files: will store options and preferences from last execution
	private Properties props;

	// For language support
	private ResourceBundle languageBundle;

	private boolean isViewingCompletedTasks;

	/**
	 * Constructor initializes our DB connector by reading data and storing them
	 * into local state, in a more conveniente representation using our classes.
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 * @throws ParseException
	 */
	public DataModel() throws JDOMException, IOException, ParseException {
		RFC822DATETIME.setLenient(false); // This makes a date like
											// "31-13-2013 17:45" not valid!

		// Load database from XML file
		loadDB();

		// load properties
		props = loadProperties();

		// Now load language using the properties: retrieve index
		int i = GlobalValues.Languages.valueOf(
				props.getProperty(GlobalValues.LANGUAGEKEY)).ordinal();

		languageBundle = ResourceBundle.getBundle(GlobalValues.LANGUAGEFILE,
				GlobalValues.supportedLocales[i]);

		// Set view to default view: pending
		isViewingCompletedTasks = false;
		// Signal the view part we finished loading all internal structure

		hasChanged(ChangeMessage.INIT);
	}

	/**
	 * This This method loads properties from the property file. If properties
	 * cannot be read from file we retrieve a default properties object.
	 * 
	 * @return a properties object.
	 */
	private final Properties loadProperties() {

		Properties p = new Properties();

		try { // try to load from file
			p.load(new FileInputStream(GlobalValues.PROPSFILE));

		} catch (Exception e) { // Some error occurred, use default values

			p.setProperty(GlobalValues.LANGUAGEKEY, GlobalValues.LANGUAGEVAL);
			p.setProperty(GlobalValues.WINXSIZEKEY, GlobalValues.WINXSIZEVAL);
			p.setProperty(GlobalValues.WINYSIZEKEY, GlobalValues.WINYSIZEVAL);
			p.setProperty(GlobalValues.WINXPOSKEY, GlobalValues.WINXPOSVAL);
			p.setProperty(GlobalValues.WINYPOSKEY, GlobalValues.WINYPOSVAL);

			p.setProperty(GlobalValues.DATEFORMATKEY,
					GlobalValues.DATEFORMATVAL);

			p.setProperty(GlobalValues.THEMEKEY, GlobalValues.THEMEVAL);
		}

		return p;
	}

	/**
	 * This method reloads DB content. Can be called to be sure data is
	 * consistant.
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 * @throws ParseException
	 */
	public final void loadDB() throws JDOMException, IOException,
			ParseException {

		// Read DB file enabling XSD validation
		SAXBuilder builder = new SAXBuilder(XMLReaders.XSDVALIDATING);
		Document doc = builder.build(new File(GlobalValues.DBFILE));
		Element root = doc.getRootElement();

		// Import categories
		categories = retrieveCategories(root.getChild("categories"));

		// Now import tasks
		taskList = retrieveTaskList(root.getChild("tasks"));
	}

	/**
	 * This method retrieves the taskList from a JDOM element
	 * 
	 * @param tasksNode
	 *            containing task elements
	 * @return
	 * @throws ParseException
	 */
	private final List<Task> retrieveTaskList(Element tasksNode)
			throws ParseException {

		List<Task> tl = new LinkedList<Task>();

		for (Element taskNode : tasksNode.getChildren()) {
			Task t = new Task();

			t.setName(taskNode.getChildText("name"));
			t.setDate(RFC822DATETIME.parse(GeneralFunctions
					.ISO8601toRFC822(taskNode.getChildText("date"))));
			t.setPrio(Task.Priority.valueOf(taskNode.getChildText("priority")));
			t.setCompleted(Boolean.parseBoolean(taskNode
					.getChildText("completed")));

			// if a category is new, add it with default color
			// it makes program more flexible!
			Category c;
			String cat = taskNode.getChildText("category");
			if ((c = categories.get(cat)) == null) {

				c = new Category(cat, Color.BLACK);
				categories.put(cat, c);

				System.out.println("added: " + c.getName());

			}

			// System.out.println(c);
			t.setCategory(c);
			t.setDescription(taskNode.getChildText("description"));
			t.setUrgent(Boolean.parseBoolean(taskNode.getChildText("urgent")));
			// System.out.println()

			tl.add(t);

			// System.out.println("load: " + t.toString() + " - " +
			// t.getName());

		}
		return tl;
	}

	/**
	 * This method retrieves the categories from a JDOM element
	 * 
	 * @param categoriesNode
	 *            containing categories elements
	 * @return
	 * @throws ParseException
	 */
	private final Map<String, Category> retrieveCategories(
			Element categoriesNode) {

		Map<String, Category> cs = new HashMap<String, Category>();

		for (Element categoryNode : categoriesNode.getChildren()) {
			String name = categoryNode.getChildText("name");
			Color color = new Color(Integer.parseInt(categoryNode
					.getChildText("color")));

			cs.put(name, new Category(name, color));
		}
		return cs;
	}

	/**
	 * This method stores into the XML file the actual internal state of the
	 * DataModel.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public final void saveDB() throws FileNotFoundException, IOException {

		// Now build DOM representation and store it
		Document doc = new Document();

		Element root = new Element("todoManagerData");
		// we need xsi namespace and header for validation
		Namespace xsi = Namespace.getNamespace("xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		root.addNamespaceDeclaration(xsi);

		root.setAttribute("noNamespaceSchemaLocation", GlobalValues.DBXSDFILE,
				xsi);

		doc.setRootElement(root);

		// Now create all tasks...
		Element tasks = new Element("tasks");

		// Sort in date order before storing (default ordering)
		sortTasks(new DateComparator());

		// now for each task in the sorted tasklist, create a task element node
		for (Task t : taskList)
			tasks.addContent(createTaskElement(t));

		// Add tasks element to the root element
		doc.getRootElement().addContent(tasks);

		// Now write all categories in same way
		Element categoriesNode = new Element("categories");

		for (Category c : categories.values())
			categoriesNode.addContent(createCategoryElement(c));

		doc.getRootElement().addContent(categoriesNode);

		// Write the JDOM to file
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(doc, new FileOutputStream(GlobalValues.DBFILE));

		// save also properties
		props.store(new FileOutputStream(GlobalValues.PROPSFILE), null);
	}

	/**
	 * This method builds a Task Element from a given Task
	 * 
	 * @param t
	 *            the task to represent
	 * @return
	 */
	private final Element createTaskElement(Task t) {

		Element task = new Element("task");
		task.addContent(new Element("name").setText(t.getName()));
		task.addContent(new Element("date").setText(GeneralFunctions
				.RFC822toISO8601(RFC822DATETIME.format(t.getDate()))));
		task.addContent(new Element("priority").setText(t.getPrio().name()));
		task.addContent(new Element("completed").setText(Boolean.toString(t
				.getCompleted())));
		task.addContent(new Element("category").setText(t.getCategory()
				.getName()));
		task.addContent(new Element("description").setText(t.getDescription()));
		task.addContent(new Element("urgent").setText(Boolean.toString(t
				.getUrgent())));

		return task;
	}

	/**
	 * This method builds a Category Element from a given category
	 * 
	 * @param c
	 *            the category to represent
	 * @return
	 */
	private final Element createCategoryElement(Category c) {

		Element category = new Element("category");

		category.addContent(new Element("name").setText(c.getName()));
		category.addContent(new Element("color").setText(Integer.toString(c
				.getColor().getRGB())));

		return category;
	}

	/**
	 * This method sorts the list in the dataModel
	 * 
	 * @param comparator
	 */
	public final void sortTasks(Comparator<Task> comparator) {
		Collections.sort(taskList, comparator);
		hasChanged(ChangeMessage.SORTED_TASK);
	}

	/**
	 * Retrieves the list of stored tasks.
	 * 
	 * @return list of tasks
	 */
	public final List<Task> getTaskList() {
		return taskList;
	}

	/**
	 * Retrieves the list of complete/incomplete tasks.
	 * 
	 * @return list of filtered tasks, using currently selected display mode
	 */
	public final List<Task> getFilteredTaskList() {
		List<Task> filteredList = new LinkedList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getCompleted() == isViewingCompletedTasks)
				filteredList.add(taskList.get(i));
		}
		return filteredList;
	}

	/**
	 * Retrieves the list of urgent tasks.
	 * 
	 * @return list of tasks
	 */
	public final List<Task> getTaskListUrgent(boolean b) {
		List<Task> filteredList = new LinkedList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getUrgent() == b)
				filteredList.add(taskList.get(i));
		}
		return filteredList;
	}

	/**
	 * This method retrieves the property
	 * 
	 * @param key
	 * @return
	 */
	public final String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * * This method sets a property
	 * 
	 * @param key
	 * @param value
	 * @param notifyObservers
	 *            indicates wheter the property change should fire an event
	 * @return
	 */
	public final void setProperty(String key, String value,
			boolean notifyObservers) {
		props.setProperty(key, value);

		if (notifyObservers)
			hasChanged(ChangeMessage.CHANGED_PROPERTY);
	}

	/**
	 * This method retrieves the current setted languageBundle
	 * 
	 * @return
	 */
	public final ResourceBundle getLanguageBundle() {
		return languageBundle;
	}

	/**
	 * This method set the new languageBundle
	 * @param language
	 */
	public final void setLanguage(Languages language) {

		languageBundle = ResourceBundle.getBundle(GlobalValues.LANGUAGEFILE,
				GlobalValues.supportedLocales[language.ordinal()]);

		setProperty(GlobalValues.LANGUAGEKEY, language.toString(), true);
	}

	/**
	 * This method is called when new theme is selected
*	@param theme
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public final void setTheme(Themes theme) throws FileNotFoundException,
			IOException {

		setProperty(GlobalValues.THEMEKEY, theme.toString(), false);
		hasChanged(ChangeMessage.CHANGED_THEME);
	}

	/**
	 * This method is called to add a new task to the data Model
	 * 
	 * @param task The task to be added 
	 */
	public final void addTask(Task task) {
		taskList.add(task);
		hasChanged(ChangeMessage.NEW_TASK);
	}

	/**
	 * This method will be called to edit an existing task with the passed values
	 * @param task
	 * @param name
	 * @param date
	 * @param prio
	 * @param completed
	 * @param c
	 * @param description
	 */
	public final void editTask(Task task, String name, Date date,
			Priority prio, Boolean completed, Category c, String description) {

		// Now i can store into task
		task.setName(name);
		task.setPrio(prio);
		task.setDate(date);
		task.setCompleted(completed);
		task.setCategory(c);
		task.setDescription(description);
		hasChanged(ChangeMessage.EDIT_TASK);
	}

	/**
	 * This method is needed to remove a Task object from data Model
	 * 
	 * @param task
	 */
	public final void deleteTask(Task task) {
		taskList.remove(task);
		hasChanged(ChangeMessage.DELETED_TASK);
	}

	/**
	 * Returns categories
	 * 
	 * @return
	 * @return categories
	 */
	public final Map<String, Category> getCategories() {
		return categories;
	}

	/**
	 * This is called to add a category
	 * 
	 * @param c
	 */
	public final void addCategory(Category c) {
		categories.put(c.getName(), c);
		hasChanged(ChangeMessage.NEW_CATEGORY);
	}

	/**
	 * This method is needed to remove a Task object from data Model
	 * 
	 * @param task
	 */
	public final void deleteCategory(Category c) {

		// TODO vedi che fare: magari un flag che modifica
		// tutti i task con quella categoria (es delete?)
		// si fa nel controller magari??'

		categories.remove(c);
		hasChanged(ChangeMessage.DELETED_CATEGORY);
	}

	/**
	 * This method sets the viewing mode of completed task to viewMode
	 * @param viewMode The boolean indicating wheter we want completed or not task to be displayed
	 */
	public final void setIsViewingCompletedTasks(boolean viewMode) {
		this.isViewingCompletedTasks = viewMode;
		hasChanged(ChangeMessage.CHANGED_FILTER);
	}

	/**
	 * This method is called by either datamodel or controller, to trigger the
	 * change process.
	 * 
	 * @param msg
	 *            the cause
	 */
	public final void hasChanged(ChangeMessage msg) {

		// System.out.println("[DataModel]hasChanged -> " + msg);
		setChanged();
		notifyObservers(msg);
	}

	/**
	 * This method marks urgentStatus of task as urgentStatus
	 * @param task
	 * @param b
	 */
	public final void setUrgent(Task task, boolean urgentStatus) {
		task.setUrgent(urgentStatus);
		hasChanged(ChangeMessage.EDIT_URGENT);
	}

	/**
	 * This method will switch completed status of a task.
	 * @param task The task to be switched
	 */
	public final void toggleCompleted(Task task) {
		task.setCompleted(!task.getCompleted());
		hasChanged(ChangeMessage.EDIT_TASK);
	}
}
