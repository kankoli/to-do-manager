package marco_tests.taskScrollImproved;
// Fri Oct 25 18:07:43 EST 2004
//
// Written by Sean R. Owens, sean at guild dot net, released to the
// public domain.  Share and enjoy.  Since some people argue that it is
// impossible to release software to the public domain, you are also free
// to use this code under any version of the GPL, LPGL, or BSD licenses,
// or contact me for use of another license.
// http://darksleep.com/player

// A very simple custom dialog that takes a string as a parameter,
// displays it in a JLabel, along with two Jbuttons, one labeled Yes,
// and one labeled No, and waits for the user to click one of them.

import javax.swing.JDialog; 

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;

public class ColorDialog extends JDialog implements ActionListener {
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private boolean answer = false;
    public boolean getAnswer() { return answer; }

    public ColorDialog(JFrame frame, boolean modal, String myMessage) {
        super(frame, modal);

        
        
         JButton ok = new JButton("OK");  
    JButton cancel = new JButton("Cancel");  
    
    getRootPane().setDefaultButton(ok);
    
    getContentPane().add(ok);  
    getContentPane().add(cancel);  
        
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        
        
        
		JTextField categoryField = new JTextField("", 20);
		categoryField.setBorder(BorderFactory
				.createTitledBorder("Choose a name"));

		categoryField.setEnabled(true);
		myPanel.add(categoryField);

		JColorChooser jcc = new JColorChooser();
		jcc.getSelectionModel().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				// TODO Auto-generated method stub

			}
		});

		jcc.setBorder(BorderFactory.createTitledBorder("Choose a Color"));
		myPanel.add(jcc);

		// panel.add(label);

        
        
        
        
        
        
//        myPanel.add(new JLabel(myMessage));
//        yesButton = new JButton("Yes");
//        yesButton.addActionListener(this);
//        myPanel.add(yesButton); 
//        noButton = new JButton("No");
//        noButton.addActionListener(this);
//        myPanel.add(noButton);  

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(yesButton == e.getSource()) {
            System.err.println("User chose yes.");
            answer = true;
            setVisible(false);
        }
        else if(noButton == e.getSource()) {
            System.err.println("User chose no.");
            answer = false;
            setVisible(false);
        }
    }
    
}