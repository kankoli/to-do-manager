package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToDoSortingBar extends JPanel {

	private ActionListener actionListener;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private List<JButton> tabList;
	private JButton activeTab;
	
	JButton tempButton;
	
	private class sortingBarMouseListener implements MouseListener, MouseMotionListener {

		private int mouseDraggedPosX;
		private JButton buttonInFocus;
		
		@Override
		public void mouseClicked(MouseEvent me) {
			activeTab.setBackground(Color.LIGHT_GRAY);
			activeTab = (JButton) me.getComponent();
			activeTab.setBackground(Color.WHITE);
		}

		@Override
		public void mouseEntered(MouseEvent me) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent me) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent me) {
			mouseDraggedPosX = me.getX();
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			buttonInFocus = null;
		}

		@Override
		public void mouseDragged(MouseEvent me) {
			
			for (int k = 0; k < tabList.size(); k++) {
				if (me.getSource() == tabList.get(k)) {
					buttonInFocus = tabList.get(k);
					int xDiff = me.getX() - mouseDraggedPosX;
					//buttonInFocus.setPreferredSize(new Dimension(buttonInFocus.getWidth()+xDiff, buttonInFocus.getHeight()));
					//buttonInFocus.setText("WTF");
					//System.out.println(xDiff);
					repaint();
				}
			}	
		}

		@Override
		public void mouseMoved(MouseEvent me) {
		}
		
	}
	
	public ToDoSortingBar(ActionListener actionListener, List<String> tabText, List<Integer> tabWidths, int tabHeight) {
		this.actionListener = actionListener;
		this.mouseListener = new sortingBarMouseListener();
		this.mouseMotionListener = new sortingBarMouseListener();
		tabList = new ArrayList<JButton>();
		
		setLayout(new GridBagLayout());
		GridBagConstraints tabCons = new GridBagConstraints();
		tabCons.weighty = 1;
		tabCons.fill = GridBagConstraints.VERTICAL;
		tabCons.anchor = GridBagConstraints.FIRST_LINE_START;
		
		for(int k = 0; k < tabText.size()-1; k++) {
			tempButton = new JButton(tabText.get(k));
			tempButton.addActionListener(actionListener);
			//tempButton.setContentAreaFilled(false);
			tempButton.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.darkGray));
			tempButton.setOpaque(true);
			tempButton.setBackground(Color.LIGHT_GRAY);
			tempButton.setPreferredSize(new Dimension(tabWidths.get(k), tabHeight));
			tempButton.setMinimumSize(new Dimension(tabWidths.get(k), tabHeight));
			tabCons.gridx = k;
			tempButton.addMouseListener(mouseListener);
			tempButton.addMouseMotionListener(mouseMotionListener);
			add(tempButton, tabCons);
			tabList.add(tempButton);
		}
		
		tempButton = new JButton(tabText.get(tabText.size()-1));
		tempButton.addActionListener(actionListener);
		//tempButton.setContentAreaFilled(false);
		tempButton.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.darkGray));
		tempButton.setOpaque(true);
		tempButton.setBackground(Color.LIGHT_GRAY);
		tempButton.setPreferredSize(new Dimension(tabWidths.get(tabWidths.size()-1), tabHeight));
		tempButton.setMinimumSize(new Dimension(tabWidths.get(tabWidths.size()-1), tabHeight));
		tabCons.gridx = tabText.size()-1;
		tabCons.weightx = 1;
		tempButton.addMouseListener(mouseListener);
		tempButton.addMouseMotionListener(mouseMotionListener);
		add(tempButton, tabCons);
		tabList.add(tempButton);
		
		activeTab = tabList.get(0);
	}
	
	/*private void addTabs() {
		
	}*/
	
}
