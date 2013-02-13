package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

/**
 * The window for the add/edit task panel.
 * @author Mattias
 */
@SuppressWarnings("serial")
public class EditTaskFrame extends JFrame {
    
    private EditTaskPanel editTaskPanel = new EditTaskPanel("Add/Edit Task");
    
    public EditTaskFrame(String title){
        super(title);
        Container c = getContentPane();
        c.add(editTaskPanel, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
    }
    
}
