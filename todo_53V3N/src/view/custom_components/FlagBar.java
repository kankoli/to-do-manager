package view.custom_components;

import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import utility.GlobalValues;

import control.ControllerInterface;

@SuppressWarnings("serial")
public class FlagBar extends JPanel implements Observer {

	protected GlobalValues.Languages language;
	
	protected static ImageIcon f_en_set = new ImageIcon(ControllerInterface.getResource("assets/Icons/F_UK_set.gif"));
	protected static ImageIcon f_en_def = new ImageIcon(ControllerInterface.getResource("assets/Icons/F_UK.png"));
	protected static ImageIcon f_swe_set = new ImageIcon(ControllerInterface.getResource("assets/Icons/F_Sweden_set.gif"));
	protected static ImageIcon f_swe_def = new ImageIcon(ControllerInterface.getResource("assets/Icons/F_Sweden.png"));
	protected static ImageIcon f_it_set = new ImageIcon(ControllerInterface.getResource("assets/Icons/F_Italy_set.gif"));
	protected static ImageIcon f_it_def = new ImageIcon(ControllerInterface.getResource("assets/Icons/F_Italy.png"));
	
	private FlagButton btn1;
	private FlagButton btn2;
	private FlagButton btn3;
	
	public FlagBar() {
		this.language = GlobalValues.Languages.valueOf(ControllerInterface.getProperty(GlobalValues.LANGUAGEKEY));
		
		// XXX Marco: why not a FlagButton[] btns = new FlagButton[3];
		// and then btns[0] = new...
		// ...
//		btn1 = new FlagButton(this, GlobalValues.Languages.EN, f_en_def, f_en_def, f_en_set, f_en_set);
		btn1 = new FlagButton(this, GlobalValues.Languages.EN, f_en_def, f_en_def, f_en_set, f_en_set, 
				ControllerInterface.getAction(ControllerInterface.ActionName.CHANGELANG));
		btn2 = new FlagButton(this, GlobalValues.Languages.SWE, f_swe_def, f_swe_def, f_swe_set, f_swe_set, 
				ControllerInterface.getAction(ControllerInterface.ActionName.CHANGELANG));
		btn3 = new FlagButton(this, GlobalValues.Languages.IT, f_it_def, f_it_def, f_it_set, f_it_set, 
				ControllerInterface.getAction(ControllerInterface.ActionName.CHANGELANG));
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(0);
		setLayout(flowLayout);

		add(btn1);
		add(btn2);
		add(btn3);
			
		setButtons();
		
		ControllerInterface.registerAsObserver(this);
	}
	
	protected void setButtons() {
		if (!isEnabled())
			return;
		switch (language)
		{
			case EN:
				btn1.setIcon(f_en_set);
				btn2.setIcon(f_swe_def);
				btn3.setIcon(f_it_def);
				btn1.setClicked(true);
				btn2.setClicked(false);
				btn3.setClicked(false);
				break;
			case SWE:
				btn1.setIcon(f_en_def);
				btn2.setIcon(f_swe_set);
				btn3.setIcon(f_it_def);
				btn1.setClicked(false);
				btn2.setClicked(true);
				btn3.setClicked(false);
				break;
			case IT:
				btn1.setIcon(f_en_def);
				btn2.setIcon(f_swe_def);
				btn3.setIcon(f_it_set);
				btn1.setClicked(false);
				btn2.setClicked(false);
				btn3.setClicked(true);
				break;
			default:
				System.out.println("Whaaa");
				break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		if (msg == ControllerInterface.ChangeMessage.CHANGED_PROPERTY) {
			this.language = GlobalValues.Languages.valueOf(ControllerInterface.getProperty(GlobalValues.LANGUAGEKEY));
			setButtons();
		}
	}
}
