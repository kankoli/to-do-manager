package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.jdom2.JDOMException;

import model.DataModel;

import utility.GlobalValues;
import view.custom_components.FlagBar;
import view.custom_components.PendingCompletedRadioButtons;
import view.urgent_task_dialog.UrgentTaskDialog;
import control.ControllerInterface;
import control.ControllerInterface.ActionName;

@SuppressWarnings("serial")
/**
 * This class is the main class of the project.
 * It constitutes the main window of the application, holds all other components in JPanels inside a GridBagLayout.
 * @author Kadir
 *
 */
public class MainWindow extends JFrame implements Observer {

	private static ImageIcon urgentIcon = new ImageIcon(
			ControllerInterface.getResource("assets/Icons/I_Exclamation.png"));
	private static DataModel db;

	private ResourceBundle languageBundle;

	private int sizes[] = { 70, 300, 500, 620 }; // TODO Temporary offset
													// values.. no time...

	public static void main(String[] args) {
		new MainWindow();
	}

	public MainWindow() {
		super();
		// Now we build DataModel passing some Imput stream values
		try {
			// XXX: changed constructor to URL to avoid problem when opening
			// properties at beginning (if file wasn't existant)
			db = new DataModel(new File(GlobalValues.DBFILE).toURI().toURL(),
					true, new File(GlobalValues.PROPSFILE).toURI().toURL());

		} catch (JDOMException | IOException | ParseException e1) {
			e1.printStackTrace();
		}

		ControllerInterface.init(db);

		languageBundle = ControllerInterface.getLanguageBundle();

		addMenu();

		// Retrieve last size from state
		double sizeX = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINXSIZEKEY));
		double sizeY = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINYSIZEKEY));

		setPreferredSize(new Dimension((int) sizeX, (int) sizeY));
		setMinimumSize(new Dimension(GlobalValues.MINXSIZE,
				GlobalValues.MINYSIZE));

		// retrieve last location from state
		double posX = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINXPOSKEY));
		double posY = Double.parseDouble(ControllerInterface
				.getProperty(GlobalValues.WINYPOSKEY));
		setLocation((int) posX, (int) posY);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		JPanel pnlFrame = new JPanel();

		pnlFrame.setLayout(new GridBagLayout());

		JPanel pnlTop = getTopPanel();

		JPanel pnlBottom = new JPanel(new GridBagLayout());

		JPanel pnlSortBar = getSortBarPanel();
		EditPanel pnlTaskEdit = getTaskEditPanel();
		JScrollPane pnlTaskTable = getTaskTablePanel(pnlTaskEdit);
		JPanel pnlStatus = getStatusPanel();

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlBottom.add(pnlSortBar, c);

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlBottom.add(pnlTaskTable, c);

		c.weightx = 0;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 2;
		pnlBottom.add(pnlTaskEdit, c);

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		pnlBottom.add(pnlStatus, c);

		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlFrame.add(pnlTop, c);

		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		pnlFrame.add(pnlBottom, c);

		setContentPane(pnlFrame);
		pack();
		setVisible(true);

		// Override default behaviour
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				ControllerInterface.getAction(
						ControllerInterface.ActionName.EXIT).actionPerformed(
						null);
			}
		});

		// XXX quick way... will figure a better one.. no time
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				// This is only called when the user releases the mouse button.

				JFrame f = (JFrame) e.getSource();
				Dimension d = f.getSize();

				ControllerInterface.setProperty(GlobalValues.WINXSIZEKEY,
						Double.toString(d.getWidth()), false);
				ControllerInterface.setProperty(GlobalValues.WINYSIZEKEY,
						Double.toString(d.getHeight()), false);
			}

			public void componentMoved(ComponentEvent e) {
				JFrame f = (JFrame) e.getSource();

				Point p = f.getLocation();

				ControllerInterface.setProperty(GlobalValues.WINXPOSKEY,
						Double.toString(p.getX()), false);
				ControllerInterface.setProperty(GlobalValues.WINYPOSKEY,
						Double.toString(p.getY()), false);
			}

		});

		// Set as observer, for language listening
		ControllerInterface.registerAsObserver(this);
	}

	// This method creates and initializes menu
	private void addMenu() {

		languageBundle = ControllerInterface.getLanguageBundle();

		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);

		JMenu menu = new JMenu(
				languageBundle.getString("mainFrame.menubar.file"));
		menu.setMnemonic(KeyEvent.VK_F);
		getJMenuBar().add(menu);

		JMenuItem menuItem = new JMenuItem(
				languageBundle.getString("mainFrame.menubar.import"));
		menuItem.setMnemonic(KeyEvent.VK_M);
		menu.add(menuItem);

		menuItem = new JMenuItem(
				languageBundle.getString("mainFrame.menubar.export"));
		menuItem.setMnemonic(KeyEvent.VK_X);
		menu.add(menuItem);

		menu.addSeparator();

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.EXIT));
		menu.add(menuItem);

		menu = new JMenu(languageBundle.getString("mainFrame.menubar.edit"));
		menu.setMnemonic(KeyEvent.VK_E);
		getJMenuBar().add(menu);

		JMenu subMenu = new JMenu(
				languageBundle.getString("mainFrame.menubar.add"));
		subMenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.NEWTASK));
		menuItem.setIcon(null);

		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.NEWCAT));
		subMenu.add(menuItem);
		menu.add(subMenu);

		subMenu = new JMenu(
				languageBundle.getString("mainFrame.menubar.language"));
		subMenu.setMnemonic(KeyEvent.VK_L);
		menu.add(subMenu);

		// TODO move constants from globalvalues to other place
		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(languageBundle
				.getString("mainFrame.menubar.language.english"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(languageBundle
				.getString("mainFrame.menubar.language.swedish"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.CHANGELANG));
		menuItem.setText(languageBundle
				.getString("mainFrame.menubar.language.italian"));
		subMenu.add(menuItem);

		subMenu = new JMenu(languageBundle.getString("mainFrame.menubar.sort"));
		subMenu.setMnemonic(KeyEvent.VK_L);
		menu.add(subMenu);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(languageBundle
				.getString("mainFrame.middlePanel.sortingBar.tab.title.name"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(languageBundle
				.getString("mainFrame.middlePanel.sortingBar.tab.date.name"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(languageBundle
				.getString("mainFrame.middlePanel.sortingBar.tab.category.name"));
		subMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.SORT));
		menuItem.setText(languageBundle
				.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"));
		subMenu.add(menuItem);

		subMenu = new JMenu(
				languageBundle.getString("mainFrame.menubar.dateformat"));
		subMenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem(
				languageBundle
						.getString("mainFrame.menubar.dateformat.italian"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControllerInterface
						.setDateFormat(ControllerInterface.DateFormat.DDMMYYY);

			}
		});
		subMenu.add(menuItem);

		menuItem = new JMenuItem(
				languageBundle
						.getString("mainFrame.menubar.dateformat.swedish"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControllerInterface
						.setDateFormat(ControllerInterface.DateFormat.YYYYMMDD);

			}
		});
		subMenu.add(menuItem);

		menu.add(subMenu);

		// TODO
		// Themes are not implemented correctly, we didnt have time to finish on
		// time..
		subMenu = new JMenu(
				languageBundle.getString("mainFrame.menubar.theme.name"));
		subMenu.setMnemonic(KeyEvent.VK_A);

		menuItem = new JMenuItem(
				languageBundle
						.getString("mainFrame.menubar.theme.default.name"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// // TODO to be fixed, quick way
				// try {
				// controller.setTheme(GlobalValues.Themes.DEFAULT);
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }

				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
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
				languageBundle
						.getString("mainFrame.menubar.theme.another.name"));
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO to be fixed, quick way... no time

				try {
					ControllerInterface.setTheme(GlobalValues.Themes.THEME_0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		subMenu.add(menuItem);

		menuItem = new JMenuItem(
				languageBundle
						.getString("mainFrame.menubar.theme.another1.name"));
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO to be fixed, quick way

				// try {
				// controller.setTheme(GlobalValues.Themes.THEME_1);
				//
				//
				//
				// } catch (IOException e1) {
				// e1.printStackTrace();
				// }
				try {

					// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

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

		menu = new JMenu(languageBundle.getString("mainFrame.menubar.help"));
		menu.setMnemonic(KeyEvent.VK_H);
		getJMenuBar().add(menu);

		menuItem = new JMenuItem(
				languageBundle.getString("mainFrame.menubar.help.about"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "Authors:\n"
						+ "Kadir Yozgyur\n" + "Madelen Pettersson\n"
						+ "Magnus Larsson\n" + "Marco Dondio");

			}
		});
		menu.add(menuItem);

	}

	private JPanel getTopPanel() {
		JPanel pnlTop = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// TODO here we will place our clock
		// JLabel lblClock = new JLabel();
		// c.gridx = 0;
		// c.gridy = 0;
		// c.weightx = 1;
		// c.weighty = 0;
		// c.insets = new Insets(10, 10, 10, 10);
		// c.anchor = GridBagConstraints.WEST;
		// pnlTop.add(lblClock, c);
		//
		FlagBar flagBar = new FlagBar();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.EAST;
		pnlTop.add(flagBar, c);

		JButton urgentButton = new JButton();
		urgentButton.setIcon(urgentIcon);
		urgentButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				new UrgentTaskDialog();
			}
		});
		urgentButton.setMinimumSize(new Dimension(60, 60));
		urgentButton.setPreferredSize(new Dimension(60, 60));
		urgentButton.setText("");
		urgentButton.setBorder(null);
		// urgentButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.darkGray));
		urgentButton.setBackground(Color.WHITE);
		urgentButton.setOpaque(true);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.WEST;
		pnlTop.add(urgentButton, c);

		JButton newTaskButton = new JButton();
		newTaskButton.setAction(ControllerInterface
				.getAction(ControllerInterface.ActionName.NEWTASK));
		newTaskButton.addPropertyChangeListener("text",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent e) {
						((JButton) e.getSource()).setText("");
					}

				});
		newTaskButton.setMinimumSize(new Dimension(60, 60));
		newTaskButton.setPreferredSize(new Dimension(60, 60));
		newTaskButton.setText("");
		// newTaskButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.darkGray));
		newTaskButton.setBorder(null);
		// newTaskButton.set
		newTaskButton.setBackground(Color.WHITE);
		newTaskButton.setOpaque(true);
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.EAST;
		pnlTop.add(newTaskButton, c);

		PendingCompletedRadioButtons btns = new PendingCompletedRadioButtons();
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.WEST;
		pnlTop.add(btns, c);

		return pnlTop;
	}

	private JPanel getSortBarPanel() {

		String[] tabNames = {
				languageBundle
						.getString("mainFrame.middlePanel.sortingBar.tab.title.name"),
				languageBundle
						.getString("mainFrame.middlePanel.sortingBar.tab.date.name"),
				languageBundle
						.getString("mainFrame.middlePanel.sortingBar.tab.category.name"),
				languageBundle
						.getString("mainFrame.middlePanel.sortingBar.tab.priority.name") };

		// TODO temporary we didnt have time to fix it
		int[] tabWidths = { 300, 200, 120, 120 };

		int[] minTabWidths = { 25, 25, 25, 25 };
		int tabHeights = 30;
		Color selectedColor = Color.WHITE; // Retreive from theme
		Color notSelectedColor = Color.LIGHT_GRAY; // See above

		CustomSortingBar pnlSortBar = new CustomSortingBar(tabNames, tabWidths,
				minTabWidths, tabHeights, selectedColor, notSelectedColor,
				languageBundle);

		for (int k = 0; k < tabNames.length; k++) {
			pnlSortBar.setTabAction(k,
					ControllerInterface.getAction(ActionName.SORT));
		}

		// pnlSortBar.setBackground(Color.yellow);
		return pnlSortBar;
	}

	private EditPanel getTaskEditPanel() {
		EditPanel pnlTaskEdit = new EditPanel();
		pnlTaskEdit.setMinimumSize(new Dimension(300, 300));
		return pnlTaskEdit;
	}

	private JScrollPane getTaskTablePanel(EditPanel pnlTaskEdit) {
		TaskTable table = new TaskTable(sizes);
		table.addPropertyChangeListener(pnlTaskEdit);
		JScrollPane scrollPane = new JScrollPane(table);
		return scrollPane;
	}

	private JPanel getStatusPanel() {
		JPanel pnlStatus = new JPanel();
		pnlStatus.setBackground(Color.darkGray);
		return pnlStatus;
	}

	private void reloadTheme() {
		SwingUtilities.updateComponentTreeUI(this);
	}

	/**
	 * This method is called to change theme using currently setted theme
	 */
	private void loadTheme() {

		System.out.println("[loadTheme] Theme changed, theme color is: "
				+ ControllerInterface.getProperty(GlobalValues.THEMEKEY));
		// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Now get my theme.. collection of key - value pair
		Properties theme = ControllerInterface.getThemeBundle();

		// UIDefaults defaults = UIManager.getLookAndFeel().getDefaults();
		// System.out.println("[DEFAULT] "
		// + defaults.getColor("Button.background"));

		String s = null;

		if ((s = theme.getProperty("Button.background")) != null) {
			System.out.println("Cambiato a "
					+ new ColorUIResource(new Color(Integer.parseInt(s))));

			UIManager.put("Button.background", new Color(Integer.parseInt(s)));
		} else {
			System.out.println("default button background");
		}
	}

	public void update(Observable o, Object arg) {

		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg;

		if (msg == DataModel.ChangeMessage.CHANGED_PROPERTY) {
			addMenu();
			revalidate();
			repaint();
		}

		else if (msg == DataModel.ChangeMessage.CHANGED_THEME) {
			loadTheme();
		}

	}
}
