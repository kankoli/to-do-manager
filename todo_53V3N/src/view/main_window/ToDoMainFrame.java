package view.main_window;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;

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
	public ToDoMainFrame() {
		super();

		// String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		// System.out.println(lookAndFeel);

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

		// Retrieve last size from state
		double sizeX = Double.parseDouble(controller
				.getProperty(GlobalValues.WINXSIZEKEY));
		double sizeY = Double.parseDouble(controller
				.getProperty(GlobalValues.WINYSIZEKEY));

		setPreferredSize(new Dimension((int) sizeX, (int) sizeY));
		setMinimumSize(new Dimension(GlobalValues.MINXSIZE,
				GlobalValues.MINYSIZE));

		// retrieve last location from state
		double posX = Double.parseDouble(controller
				.getProperty(GlobalValues.WINXPOSKEY));
		double posY = Double.parseDouble(controller
				.getProperty(GlobalValues.WINYPOSKEY));
		setLocation((int) posX, (int) posY);

		// setPreferredSize(new Dimension(width, height));
		// setMinimumSize(new Dimension(width, height));

		basePanel = new JPanel();
		basePanel.setBackground(Color.white);
		basePanel.setLayout(new GridBagLayout());
		// TODO
		// basePanel.setPreferredSize(new Dimension(width, height));

		addTopPanel();
		addMiddlePanel();
		addBottomPanel();
		addMenu();

		setContentPane(basePanel);

		// XXX Marco: is good place to call it BEFORE adding components?
		// First we set theme using last one
//		loadTheme();

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

		// TODO quick way... will figure a better one
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// This is only called when the user releases the mouse button.

				JFrame f = (JFrame) e.getSource();
				Dimension d = f.getSize();

				controller.setProperty(GlobalValues.WINXSIZEKEY,
						Double.toString(d.getWidth()), false);
				controller.setProperty(GlobalValues.WINYSIZEKEY,
						Double.toString(d.getHeight()), false);
			}

			public void componentMoved(ComponentEvent e) {
				JFrame f = (JFrame) e.getSource();

				Point p = f.getLocation();

				controller.setProperty(GlobalValues.WINXPOSKEY,
						Double.toString(p.getX()), false);
				controller.setProperty(GlobalValues.WINYPOSKEY,
						Double.toString(p.getY()), false);
			}

		});

		controller.registerAsObserver(this);
	}

	public static void main(String[] args) {
		// ToDoMainFrame toDoFrame = // XXX Marco: i commented it, do we need a
		// reference to Frame?
		// new ToDoMainFrame(900, 600);
		new ToDoMainFrame();

	}

	private void addMenu() {

		// TODO

		ResourceBundle lang = controller.getLanguageBundle();
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);

		JMenu menu = new JMenu(lang.getString("mainFrame.menubar.file"));
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
		menuItem.setIcon(null);

		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.NEWCAT));
		subMenu.add(menuItem);
		menu.add(subMenu);

		subMenu = new JMenu(lang.getString("mainFrame.menubar.language"));
		subMenu.setMnemonic(KeyEvent.VK_L);
		menu.add(subMenu);

		// TODO move constants from globalvalues to other place
		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(lang.getString("mainFrame.menubar.language.english"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(lang.getString("mainFrame.menubar.language.swedish"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(lang.getString("mainFrame.menubar.language.italian"));
		subMenu.add(menuItem);

		subMenu = new JMenu(lang.getString("mainFrame.menubar.sort"));
		subMenu.setMnemonic(KeyEvent.VK_L);
		menu.add(subMenu);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.title.name"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.date.name"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.category.name"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(controller
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"));
		subMenu.add(menuItem);

		subMenu = new JMenu(lang.getString("mainFrame.menubar.dateformat"));
		subMenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem(
				lang.getString("mainFrame.menubar.dateformat.italian"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller
						.setDateFormat(ControllerInterface.DateFormat.ITALIAN);

			}
		});
		subMenu.add(menuItem);

		menuItem = new JMenuItem(
				lang.getString("mainFrame.menubar.dateformat.swedish"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller
						.setDateFormat(ControllerInterface.DateFormat.SWEDISH);

			}
		});
		subMenu.add(menuItem);

		menu.add(subMenu);

		subMenu = new JMenu(lang.getString("mainFrame.menubar.theme.name"));
		subMenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem(
				lang.getString("mainFrame.menubar.theme.default.name"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				// TODO to be fixed, quick way
//				try {
//					controller.setTheme(GlobalValues.Themes.DEFAULT);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}

				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					reloadTheme();

				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		subMenu.add(menuItem);

		menuItem = new JMenuItem(
				lang.getString("mainFrame.menubar.theme.another.name"));
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO to be fixed, quick way

				try {
					controller.setTheme(GlobalValues.Themes.THEME_0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		subMenu.add(menuItem);

		menuItem = new JMenuItem(
				lang.getString("mainFrame.menubar.theme.another1.name"));
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO to be fixed, quick way

//				try {
//					controller.setTheme(GlobalValues.Themes.THEME_1);
//					
//					
//					
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
				try {

//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

					reloadTheme();

				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			
			}
			


		});
		
		
		subMenu.add(menuItem);

		menu.add(subMenu);

		menu = new JMenu(lang.getString("mainFrame.menubar.help"));
		menu.setMnemonic(KeyEvent.VK_H);
		getJMenuBar().add(menu);

		menuItem = new JMenuItem(lang.getString("mainFrame.menubar.help.about"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "Authors:\n"
						+ "Kadir Yozgyur\n" + "Madelen Pettersson\n"
						+ "Magnus Larsson\n" + "Marco Dondio");

			}
		});
		menu.add(menuItem);

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

	
	private void reloadTheme(){
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	/**
	 * This method is called to change theme using currently setted theme
	 */
	private void loadTheme() {

		// TODO we should have this one as default???


			System.out.println("[loadTheme] Theme changed, theme color is: "
					+ controller.getProperty(GlobalValues.THEMEKEY));
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			try {
				UIManager.setLookAndFeel(new MetalLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		

		// TODO this is table of default values for THIS theme

		// http://tips4java.wordpress.com/2008/10/09/uimanager-defaults/
		// http://home.tiscali.nl/bmc88/java/sbook/061.html

		// lista
		// http://alvinalexander.com/java/java-uimanager-color-keys-list

		// TODO now list all default values
		// UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		//
		// for (Object v : defaults.keySet()) {
		// System.out.println(v);
		// }

		// Now get my theme.. collection of key - value pair
		Properties theme = controller.getThemeBundle();

//		UIDefaults defaults = UIManager.getLookAndFeel().getDefaults();
//		System.out.println("[DEFAULT] "
//				+ defaults.getColor("Button.background"));

		// TODO now we have a list of properties taken from themeù

		String s = null;

		if ((s = theme.getProperty("Button.background")) != null) {
			System.out.println("Cambiato a "
					+ new ColorUIResource(new Color(Integer.parseInt(s))));

			 UIManager.put("Button.background", new
			 Color(Integer.parseInt(s)));

//			 UIManager.put("Button.background", new
//			 Color(Integer.parseInt(s)));
//
			 
			// UIManager.put("Button.background", new ColorUIResource (new
			// Color(Integer.parseInt(s))));
//			 UIManager.getLookAndFeelDefaults().put("Button.background", new Color(Integer.parseInt(s)));

			// defaults.put("Button.background", new
			// Color(Integer.parseInt(s)));


		} else {
			// TODO imponi la default
			System.out.println("default button background");
		}

		

		// TODO problema???? runtime non va!
//		SwingUtilities.updateComponentTreeUI(this);
//		revalidate();
//		repaint();

//		SwingUtilities.updateComponentTreeUI(topPanel);
//		SwingUtilities.updateComponentTreeUI(middlePanel);
//		SwingUtilities.updateComponentTreeUI(bottomPanel);

//		pack();
	}

	public void update(Observable o, Object arg) {

		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {

			addMenu();
			revalidate();
			repaint();
		}

		else if (msg == ControllerInterface.ChangeMessage.CHANGED_THEME) {
//			loadTheme();
		}

	}
}
