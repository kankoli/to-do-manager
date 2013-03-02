package view.custom_components;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import utility.GlobalValues;

import control.ControllerInterface;

@SuppressWarnings("serial")
public class FlagBar extends JPanel {

	protected GlobalValues.Languages language;
	
	protected static ImageIcon f_en_set;
	protected static ImageIcon f_en_def;
	protected static ImageIcon f_swe_set;
	protected static ImageIcon f_swe_def;
	protected static ImageIcon f_it_set;
	protected static ImageIcon f_it_def;
	
	protected static boolean imagesLoaded = false;
	
	private FlagButton btn1;
	private FlagButton btn2;
	private FlagButton btn3;
	
	public FlagBar(ControllerInterface ci) {
		
		if(!imagesLoaded) {
			loadImages(ci);
			imagesLoaded = true;
		}			
		
		btn1 = new FlagButton(this, GlobalValues.Languages.EN, f_en_def, f_en_def, f_en_set, f_en_set);
		btn2 = new FlagButton(this, GlobalValues.Languages.SWE, f_swe_def, f_swe_def, f_swe_set, f_swe_set);
		btn3 = new FlagButton(this, GlobalValues.Languages.IT, f_it_def, f_it_def, f_it_set, f_it_set);
		
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(0);
		setLayout(flowLayout);

		add(btn1);
		add(btn2);
		add(btn3);
			
		setButtons();
	}
	
	private void loadImages(ControllerInterface ci) {
		this.f_en_def = new ImageIcon(ci.getResource("assets/Icons/F_UK.png"));
		this.f_swe_def = new ImageIcon(ci.getResource("assets/Icons/F_Sweden.png"));
		this.f_it_def = new ImageIcon(ci.getResource("assets/Icons/F_Italy.png"));
		this.f_en_set = new ImageIcon(ci.getResource("assets/Icons/F_UK.png"));
		this.f_swe_set = new ImageIcon(ci.getResource("assets/Icons/F_Sweden.png"));
		this.f_it_set = new ImageIcon(ci.getResource("assets/Icons/F_Italy.png"));
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
				btn1.setClicked(false);
				btn2.setClicked(false);
				btn3.setClicked(false);
				break;
			case SWE:
				btn1.setIcon(f_en_def);
				btn2.setIcon(f_swe_set);
				btn3.setIcon(f_it_def);
				btn1.setClicked(true);
				btn2.setClicked(false);
				btn3.setClicked(false);
				break;
			case IT:
				btn1.setIcon(f_en_def);
				btn2.setIcon(f_swe_def);
				btn3.setIcon(f_it_set);
				btn1.setClicked(false);
				btn2.setClicked(true);
				btn3.setClicked(false);
				break;
			default:
				System.out.println("Whaaa");
				break;
		}
	}
}
