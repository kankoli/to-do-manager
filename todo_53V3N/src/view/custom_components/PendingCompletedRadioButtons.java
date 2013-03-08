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

import control.ControllerInterface;

public class PendingCompletedRadioButtons extends JPanel implements
		ActionListener, Observer {

	private ControllerInterface controller;
	private JRadioButton btnPending;
	private JRadioButton btnIncomplete;
	
	// http://docs.oracle.com/javase/tutorial/uiswing/examples/components/RadioButtonDemoProject/src/components/RadioButtonDemo.java
	public PendingCompletedRadioButtons (ControllerInterface ci) {
		super(new BorderLayout());
		
		this.controller = ci;
		ResourceBundle lang = controller.getLanguageBundle();
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
        
        controller.registerAsObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.setIsViewingCompletedTasks(e.getActionCommand() == "true" ? true : false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg1;
		
		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
			ResourceBundle lang = controller.getLanguageBundle();
			btnPending.setText(lang
					.getString("mainFrame.middlePanel.button.completed.name"));
			btnIncomplete.setText(lang
					.getString("mainFrame.middlePanel.button.pending.name"));

		}
	}

}
