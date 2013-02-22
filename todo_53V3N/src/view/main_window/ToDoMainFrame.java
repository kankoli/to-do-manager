package view.main_window;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.DataModel;

import org.jdom2.JDOMException;

import utility.GlobalValues;

import control.ControllerInterface;

/**
 * This class represents the main frame and base panel of the ToDo application
 * 
 * @author Magnus Larsson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ToDoMainFrame extends JFrame implements Observer {

	private JPanel basePanel;
	private ToDoMainTopPanel topPanel;
	private ToDoMainMiddlePanel middlePanel;
	private ToDoMainBottomPanel bottomPanel;

	// TODO Marco: also a timer wich saves DB periodically.. see here
	// http://www.cab.u-szeged.hu/WWW/java/tutorial/ui/swing/timer.html
	private ControllerInterface controller;

	/**
	 * 
	 * @param width
	 *            width of frame in pixels
	 * @param height
	 *            height of frame in pixels
	 */
	public ToDoMainFrame(int width, int height) {
		super();

		DataModel db = null; // TODO exception handling, exception throwing..
		try {
			db = new DataModel();

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		controller = new ControllerInterface(db);

		// TODO this will be changed into action control class

		// exit.putValue(Action.NAME, "new name");

		// exit.putValue("text", "asdsad");
		// exit = new

		// controller.getLanguageBundle().getKeys()""

		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));

		basePanel = new JPanel();
		basePanel.setBackground(Color.white);
		basePanel.setLayout(new GridBagLayout());
		basePanel.setPreferredSize(new Dimension(width, height));

		addTopPanel();
		addMiddlePanel();
		addBottomPanel();
		addMenu();

		setContentPane(basePanel);

		pack();
		setVisible(true);

		// Override default behaviour
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				controller.getAction(ControllerInterface.ActionName.EXIT)
						.actionPerformed(null);
			}
		});

		controller.registerAsObserver(this);
	}

	public static void main(String[] args) {
		// ToDoMainFrame toDoFrame = // XXX Marco: i commented it, do we need a
		// reference to Frame?
		new ToDoMainFrame(800, 600);
	}

	private void addMenu() {

		// TODO

		ResourceBundle lang = controller.getLanguageBundle();
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);

		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		getJMenuBar().add(menu);

		JMenuItem menuItem = new JMenuItem(
				lang.getString("mainFrame.menubar.import"));
		menuItem.setMnemonic(KeyEvent.VK_M);
		menu.add(menuItem);

		menuItem = new JMenuItem(lang.getString("mainFrame.menubar.export"));
		menuItem.setMnemonic(KeyEvent.VK_X);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.EXIT));
		menu.add(menuItem);

		menu = new JMenu(lang.getString("mainFrame.menubar.edit"));
		menu.setMnemonic(KeyEvent.VK_E);
		getJMenuBar().add(menu);

		JMenu subMenu = new JMenu(lang.getString("mainFrame.menubar.add"));
		subMenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.NEWTASK));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.NEWCAT));
		subMenu.add(menuItem);
		menu.add(subMenu);

		subMenu = new JMenu(lang.getString("mainFrame.menubar.language"));
		subMenu.setMnemonic(KeyEvent.VK_L);
		menu.add(subMenu);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(GlobalValues.Languages.EN.toString());
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(GlobalValues.Languages.SWE.toString());
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(GlobalValues.Languages.IT.toString());
		subMenu.add(menuItem);

		subMenu = new JMenu(lang.getString("mainFrame.menubar.sort"));
		subMenu.setMnemonic(KeyEvent.VK_L);
		menu.add(subMenu);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(ControllerInterface.SortType.NAME.toString());
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(ControllerInterface.SortType.DATE.toString());
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(ControllerInterface.SortType.CATEGORY.toString());
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(ControllerInterface.SortType.PRIORITY.toString());
		subMenu.add(menuItem);

		// ********************************

		menu = new JMenu(lang.getString("mainFrame.menubar.help"));
		menu.setMnemonic(KeyEvent.VK_H);
		getJMenuBar().add(menu);

	}

	private void addTopPanel() {
		GridBagConstraints topCons = new GridBagConstraints();
		topPanel = new ToDoMainTopPanel(controller);
		topCons.gridx = 0;
		topCons.gridy = 0;
		topCons.weightx = 1;
		topCons.weighty = 0;
		topCons.fill = GridBagConstraints.BOTH;
		basePanel.add(topPanel, topCons);
	}

	private void addMiddlePanel() {
		GridBagConstraints middleCons = new GridBagConstraints();
		middlePanel = new ToDoMainMiddlePanel(controller);
		middleCons.gridx = 0;
		middleCons.gridy = 1;
		middleCons.weightx = 1;
		middleCons.weighty = 1;
		middleCons.insets = new Insets(0, 10, 0, 10);
		middleCons.fill = GridBagConstraints.BOTH;
		basePanel.add(middlePanel, middleCons);
	}

	private void addBottomPanel() {
		GridBagConstraints bottomCons = new GridBagConstraints();
		bottomPanel = new ToDoMainBottomPanel();
		bottomCons.gridx = 0;
		bottomCons.gridy = 2;
		bottomCons.weightx = 1;
		bottomCons.weighty = 0;
		bottomCons.fill = GridBagConstraints.BOTH;
		basePanel.add(bottomPanel, bottomCons);
	}

	// TODO
	public void update(Observable o, Object arg) {

		addMenu();
		revalidate();
		repaint();
	}
}
