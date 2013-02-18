package view.main_window;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.DataModel;

import org.jdom2.JDOMException;

import control.Controller;

/**
 * This class represents the main frame and base panel of the ToDo application
 * 
 * @author Magnus Larsson
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ToDoMainFrame extends JFrame {

	private JPanel basePanel;
	private ToDoMainTopPanel topPanel;
	private ToDoMainMiddlePanel middlePanel;
	private ToDoMainBottomPanel bottomPanel;

	private Controller controller;

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

		controller = new Controller(db);
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
		setDefaultCloseOperation(EXIT_ON_CLOSE); // TODO OVERRIDE WTIH CUSTOM
													// CODE TO SAVE ETC.
	}

	public static void main(String[] args) {
		// ToDoMainFrame toDoFrame = // XXX Marco: i commented it, do we need a
		// reference to Frame?
		new ToDoMainFrame(800, 600);
	}

	private void addMenu() {
		
		int madde;
		
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);

		    JMenu menu = new JMenu("File");
		    menu.setMnemonic(KeyEvent.VK_F);
		    getJMenuBar().add(menu);

		    JMenuItem menuItem = new JMenuItem("Import...");
		    menuItem.setMnemonic(KeyEvent.VK_M);
		    menu.add(menuItem);

		    menuItem = new JMenuItem("Export...");
		    menuItem.setMnemonic(KeyEvent.VK_X);
		    menu.add(menuItem);

		    menu.addSeparator();
		    menuItem = new JMenuItem("Quit");
		    menuItem.setMnemonic(KeyEvent.VK_Q);
		    menuItem.addActionListener(
		            new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent ae) {
		                    System.out.println("System exiting...");
		                    // TODO Save data before exiting. Possibly modify to call the shared action.
		                    System.exit(0);
		                }

		            });
		    menu.add(menuItem);

		    //*****************************

		    menu = new JMenu("Edit");
		    menu.setMnemonic(KeyEvent.VK_E);
		    getJMenuBar().add(menu);

		    JMenu subMenu = new JMenu("Add...");
		    subMenu.setMnemonic(KeyEvent.VK_A);

		    menuItem = new JMenuItem("New Task");
		    menuItem.setMnemonic(KeyEvent.VK_T);
		    subMenu.add(menuItem);

		    menuItem = new JMenuItem("New Category");
		    menuItem.setMnemonic(KeyEvent.VK_C);
		    subMenu.add(menuItem);

		    menu.add(subMenu);

		    //********************************

		    menu = new JMenu("Help");
		    menu.setMnemonic(KeyEvent.VK_H);
		    getJMenuBar().add(menu);


		}
	private void addTopPanel() {
		GridBagConstraints topCons = new GridBagConstraints();
		topPanel = new ToDoMainTopPanel();
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
}