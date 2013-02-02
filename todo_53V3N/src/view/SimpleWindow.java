//==============================================================================
//File SimpleWindow.java
//==============================================================================
//Package declaration
//
package view;

//Imports
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
* The SimpleWindow is a self-contained application that creates a window, with
* a fixed size. The window uses a few Panels, buttons and a Menubar to show how
* the features are used in a very simple setting.
*
* The contents of this file can be considered to be use-ware. Feel free to
* adapt the content to your own needs. I take no responsibilities for any
* misuse or errors within this file.
*
* @author Lars Oestreicher
*/
public class SimpleWindow extends JFrame {

 // ==============================================================================
 // INSTANCE VARIABLE DECLARATIONS
 // ==============================================================================
 // Class-wide definition of the panels, this is to eleiminate scope problems.
 //
 private JPanel basePanel;                           // The main panel, background to the user interface
 private JPanel canvas;                              // A panel used for drawing on.

 // ==============================================================================
 // CONSTRUCTORS
 // ==============================================================================
 /**
  * Default Constructor. The default name "SimpleWindow" is shown in the
  * Title bar of the window.
  */
 public SimpleWindow() {

     // Calling the main constructor with the default name "SimpleWindow".
     //
     this("SimpleWindow");
 }

 /**
  * Main constructor.
  *
  * @param name the string to show as name of the Window.
  */
 public SimpleWindow(String name) {

     // Calling the parent constructor
     //
     super(name);

     // This is the best way of setting the Panel size.
     //
     this.setPreferredSize(new Dimension(800, 600));

     basePanel = new JPanel();                       // Create the basic JPanel
     basePanel.setBackground(Color.PINK);            // Color the panel background Pink

     basePanel.setLayout(new BorderLayout());        // Set the Layout to BorderLayout.

     // Adding a central panel for drawing on.
     //
     canvas = new JPanel();
     canvas.setBackground(Color.GREEN);              // Set the background colour of the panel to Green

     basePanel.add(canvas, BorderLayout.CENTER);     // Add the canvas to the centre location of the basePanel.


     // Add the Buttons to the basePanel
     //
     addControls(basePanel);

     // Set up the Menus.
     //
     addMenus();

     // Finalizing the window frame.
     //
     this.getContentPane().add(basePanel);           // Add the base panel to the frame (contentPane).
     this.pack();                                    // Size the window and its components.
     this.setVisible(true);                          // Show the window.
     this.setDefaultCloseOperation(EXIT_ON_CLOSE);   // Clicking the close box EXITS the whole program.
     // Not Mac-like behaviour.
 }

 // ==============================================================================
 // INSTANCE METHODS
 // ==============================================================================
 /**
  * The method sets up the Panel, to contain the proper Layout, two buttons
  * and a painting Panel
  *
  * @param basePanel
  */
 private JPanel addControls(JPanel panel) {

     // Create a panel to show the buttons. Use the default layoutmanager
     //
     JPanel buttons = new JPanel();

     buttons.setBackground(Color.red);               // Set the background color to red

     // Add the buttons panel to the lower field in the BorderLayout.
     //
     panel.add(buttons, BorderLayout.SOUTH);

     // Adding buttons to the lower part of the window.
     //
     // The green button listens to mouseclicks and paints the canvas background green.
     //
     JButton greenButton = new JButton("Green");
     greenButton.addActionListener(
             new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {

             // Here you can't use "this",
             // because of scope problems
             //
             canvas.setBackground(Color.green);
         }
     });

     // The yellow button listens to mouseclicks and paints the canvas background yellow.
     //
     JButton yellowButton = new JButton("Yellow");
     yellowButton.addActionListener(
             new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
             System.out.println("Yellow button pressed");

             canvas.setBackground(Color.yellow);
         }
     });
     buttons.add(greenButton);
     buttons.add(yellowButton);


     return canvas;
 }

 /**
  * Adds the menubar to the window. This may be done directly in the
  * Constructor, but has been separated for readability.
  */
 private void addMenus() {

     // Create the menubar and a Menu.
     //
     JMenuBar mb = new JMenuBar();
     JMenu aMenu = new JMenu("File");

     // Add the menu to the Menubar and set up the menubar.
     //
     this.setJMenuBar(mb);
     getJMenuBar().add(aMenu);

     // The quit Menu is created. It will perform an immediate exit.
     //
     JMenuItem quitM = new JMenuItem("Quit");
     quitM.addActionListener(
             new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent ae) {
             System.out.println("System exiting!");
             System.exit(0);
         }
     });

     // Create a Print menu item. This will not work yet.
     //
     JMenuItem printM = new JMenuItem("Print...");
     //
     // TODO - call a method for printing.
     //

     // Add the menu items to the menu.
     //
     aMenu.add(printM);
     aMenu.addSeparator();
     aMenu.add(quitM);
 }

 // ==============================================================================
 // CLASS METHODS
 // ==============================================================================
 /**
  * Testing method. Can be deleted when not needed any more.
  *
  * @param args All arguments are ignored.
  */
}
//=============================================================================
//END OF File SimpleWindow.java
//=============================================================================