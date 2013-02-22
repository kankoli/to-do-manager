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

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import control.ControllerInterface.ChangeMessage;
import control.TaskDateComparator;

import utility.GeneralFunctions;
import utility.GlobalValues;
import utility.GlobalValues.Languages;

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

// XXX category can be null in a task, remember

// TODO: come gestici editing???

public final class DataModel extends Observable {


	// datetime formate used in xs:datetime format

	// NOTE: im using two small private function below to handle this..
	// https://www.ibm.com/developerworks/mydeveloperworks/blogs/HermannSW/entry/java_simpledateformat_vs_xs_datetime26?lang=en
	private static final SimpleDateFormat RFC822DATETIME = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	// Internal data structures
	private List<Task> taskList;
	private Map<String, Category> categories;

	// TODO: we r thinking about removing options, we have properties!
	// why we need to have this?? is just key value, can be handled 
	// TODO: should change XSD file
	private Map<String, Integer> options;		

	// Properties files: will store options and preferences from last execution
	private Properties props;

	// For language support
	private ResourceBundle languageBundle;

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

		// load preferences, if not use default values defined in GlobalValues
		props = new Properties();

		try {
			// XXX: Marco: attention to this!!! This file goes NOT in src folder
			// like languages, but in main folder...
			props.load(new FileInputStream(GlobalValues.PROPSFILE));

		} catch (Exception e) {
			System.out.println("where is properties???");

			props.setProperty(GlobalValues.LANGUAGEKEY,
					GlobalValues.LANGUAGEVAL);
			props.setProperty(GlobalValues.WINXSIZEKEY,
					GlobalValues.WINXSIZEKEY);
			props.setProperty(GlobalValues.WINYSIZEKEY,
					GlobalValues.WINYSIZEKEY);
			props.setProperty(GlobalValues.WINXPOSKEY, GlobalValues.WINXPOSVAL);
			props.setProperty(GlobalValues.WINYPOSKEY, GlobalValues.WINYPOSVAL);
		}

		// TODO load language bundle: use locale read from props, how???
		// System.out.println(Locale.ENGLISH);

		// XXX: Marco: attention to this!!! where put properties files???
		// https://blogs.oracle.com/chengfang/entry/p_java_util_missingresourceexception_can

		// Now load language using the properties: retrieve index
		int i = GlobalValues.Languages.valueOf(
				props.getProperty(GlobalValues.LANGUAGEKEY)).ordinal();

		languageBundle = ResourceBundle.getBundle(GlobalValues.LANGUAGEFILE,
				GlobalValues.supportedLocales[i]);

		// debug
		// String value = languageBundle
		// .getString("mainFrame.topPanel.button.urgentTasks.name");
		//
		// System.out.println(value);

		// Signal the view part we finished loading all internal structure
		hasChanged(ChangeMessage.INIT);
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

		// Import categories and options
		categories = retrieveCategories(root.getChild("categories"));
		options = retrieveOptions(root.getChild("options"));

		// Now import tasks
		taskList = retrieveTaskList(root.getChild("tasks"));

	}

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
			// XXX it makes program more flexible!
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

			// System.out.println()

			tl.add(t);

			// System.out.println("load: " + t.toString() + " - " +
			// t.getName());

		}
		return tl;
	}

	private final Map<String, Category> retrieveCategories(
			Element categoriesNode) {

		Map<String, Category> cs = new HashMap<String, Category>();

		for (Element categoryNode : categoriesNode.getChildren()) {
			String name = categoryNode.getChildText("name");
			Color color = new Color(Integer.parseInt(categoryNode
					.getChildText("color")));

			// System.out.println("cat added: " + name);
			cs.put(name, new Category(name, color));
		}
		return cs;
	}

	private final Map<String, Integer> retrieveOptions(Element optionsNode) {

		Map<String, Integer> os = new HashMap<String, Integer>();

		for (Element optionNode : optionsNode.getChildren()) {
			String key = optionNode.getChildText("key");
			int value = Integer.parseInt(optionNode.getChildText("value"));

			os.put(key, value);
		}
		return os;
	}

	/**
	 * This method stores into the XML file the actual internal state.
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

		// Sort in date order, may be useful (default sorting)
		Collections.sort(taskList, new Comparator<Task>() {
			public int compare(Task arg0, Task arg1) {

				// now im using default Java date comparison
				Date val0 = arg0.getDate();
				Date val1 = arg1.getDate();
				return val0.compareTo(val1);
			}
		});

		for (Task t : taskList) {
			Element task = new Element("task");
			// task.setAttribute("id", "" + task.getId());
			task.addContent(new Element("name").setText(t.getName()));
			task.addContent(new Element("date").setText(GeneralFunctions
					.RFC822toISO8601(RFC822DATETIME.format(t.getDate()))));
			task.addContent(new Element("priority").setText(t.getPrio().name()));
			task.addContent(new Element("completed").setText(Boolean.toString(t
					.getCompleted())));
			task.addContent(new Element("category").setText(t.getCategory()
					.getName()));
			task.addContent(new Element("description").setText(t
					.getDescription()));

			tasks.addContent(task);
		}

		doc.getRootElement().addContent(tasks);

		// Now write all categories
		Element categoriesNode = new Element("categories");

		for (Category c : categories.values()) {
			Element category = new Element("category");

			category.addContent(new Element("name").setText(c.getName()));
			category.addContent(new Element("color").setText(Integer.toString(c
					.getColor().getRGB())));

			categoriesNode.addContent(category);
		}

		doc.getRootElement().addContent(categoriesNode);

		// Finally write all options
		Element optionsNode = new Element("options");

		for (String key : options.keySet()) {
			Element option = new Element("option");

			option.addContent(new Element("key").setText(key));
			option.addContent(new Element("value").setText(Integer
					.toString(options.get(key))));

			optionsNode.addContent(option);
		}

		doc.getRootElement().addContent(optionsNode);

		// Write the JDOM to file
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(doc, new FileOutputStream(GlobalValues.DBFILE));

		// save also properties
		props.store(new FileOutputStream(GlobalValues.PROPSFILE), null);
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
	 * * This method retrieves the property
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
	 * @return
	 */
	public final void setProperty(String key, String value) {
		props.setProperty(key, value);
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
	 * 
	 * @param index
	 */
	public final void setLanguage(Languages language) {

		languageBundle = ResourceBundle.getBundle(GlobalValues.LANGUAGEFILE,
				GlobalValues.supportedLocales[language.ordinal()]);

		setProperty(GlobalValues.LANGUAGEKEY, language.toString());

		hasChanged(ChangeMessage.CHANGED_LANG);
	}

	/**
	 * This method is called to add a new task to the data Model
	 * 
	 * @param task
	 */
	public final void addTask(Task task) {
		taskList.add(task);
		hasChanged(ChangeMessage.NEW_TASK);
	}

	/**
	 * This method is called to edit an existing task
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
	 * This method is called by either datamodel or controller, to trigger the
	 * change process.
	 * 
	 * @param msg
	 *            the cause
	 */
	public final void hasChanged(ChangeMessage msg) {
		setChanged();
		notifyObservers(msg);
	}
}
