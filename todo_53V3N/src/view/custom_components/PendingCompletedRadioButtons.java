package view.custom_components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import model.DataModel;

import control.ControllerInterface;

/**
 * Class for pending/completed radio buttons used to filter the task table.
 * Code adopted from:
 *  http://docs.oracle.com/javase/tutorial/uiswing/examples/components/RadioButtonDemoProject/src/components/RadioButtonDemo.java
 * @author Kadir & Madelen
 *
 */
@SuppressWarnings("serial")
public class PendingCompletedRadioButtons extends JPanel implements
		ActionListener, Observer {

	private JRadioButton btnPending;
	private JRadioButton btnIncomplete;
	public PendingCompletedRadioButtons () {
		super(new BorderLayout());
		
		ResourceBundle lang = ControllerInterface.getLanguageBundle();
		String pendingName = lang.getString("mainFrame.middlePanel.button.pending.name");
		String completeName = lang.getString("mainFrame.middlePanel.button.completed.name");
		
		btnPending = new JRadioButton(pendingName);
		btnPending.setActionCommand("false");
		btnPending.setSelected(true);
		btnPending.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
 
        btnIncomplete = new JRadioButton(completeName);
        btnIncomplete.setActionCommand("true");
        btnIncomplete.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.darkGray));
        
        ButtonGroup group = new ButtonGroup();
        group.add(btnPending);
        group.add(btnIncomplete);
        
        btnPending.addActionListener(this);
        btnIncomplete.addActionListener(this);
        
        add(btnPending, BorderLayout.PAGE_START);
        add(btnIncomplete, BorderLayout.PAGE_END);
        
        ControllerInterface.registerAsObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ControllerInterface.setIsViewingCompletedTasks(e.getActionCommand() == "true" ? true : false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		DataModel.ChangeMessage msg = (DataModel.ChangeMessage) arg1;
		
		if (msg == DataModel.ChangeMessage.CHANGED_PROPERTY) {
			ResourceBundle lang = ControllerInterface.getLanguageBundle();
			btnPending.setText(lang
					.getString("mainFrame.middlePanel.button.completed.name"));
			btnIncomplete.setText(lang
					.getString("mainFrame.middlePanel.button.pending.name"));

		}
	}

}
