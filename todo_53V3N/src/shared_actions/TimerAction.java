package shared_actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import control.ControllerInterface;


// TODO: NOT A PRIORITY!!!!

// XXX: Marco & Magnus:we agreed on defining an action useful for storing database but NOT properties
// we think that state should be saved only once (for example thw windows size)


//TODO Marco: also a timer wich saves DB periodically.. see here
	// http://www.cab.u-szeged.hu/WWW/java/tutorial/ui/swing/timer.html
	
@SuppressWarnings("serial")
public class TimerAction extends AbstractAction {
	
	public TimerAction() {
		super("", null);
	}

	
	public void actionPerformed(ActionEvent e) {
		ControllerInterface.saveDB();
	}
}
