package view.main_window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class represents a custom made bar with tabs used to sort the task rows in TaskScrollPanel.
 * NOTE: Never mind this class right now, it will go through a complete revamp together with TaskRow and TaskScrollPanel.
 * It is under heavy development.
 * 
 * @author Magnus Larsson
 * @version 1.0
 */

@SuppressWarnings("serial")
public class ToDoSortingBar extends JPanel {

	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private List<JButton> tabList;
	private JButton activeTab;
	
	
	/**
	 * Inner class which defines methods to handle mouse interaction on the sorting bar
	 * 
	 * @author Magnus Larsson
	 * @version 1.0
	 */
	private class sortingBarMouseListener implements MouseListener, MouseMotionListener {

//		private int mouseDraggedPosX; //helpful variable to calculate distance moved in x-axis
//		private JButton tabInFocus; //helpful variable to keep track of which tab is in focus
		
		@Override
		public void mouseClicked(MouseEvent me) {
			activeTab.setBackground(Color.LIGHT_GRAY);
			activeTab = (JButton) me.getComponent();
			activeTab.setBackground(Color.WHITE);
		}

		@Override
		public void mouseEntered(MouseEvent me) {
		}

		@Override
		public void mouseExited(MouseEvent me) {
		}

		@Override
		public void mousePressed(MouseEvent me) {
//			mouseDraggedPosX = me.getX();
		}

		@Override
		public void mouseReleased(MouseEvent me) {
	//		tabInFocus = null;
		}

		/**
		 * Has no use right now, will in future version extend/withdraw area of tabs
		 */
		@Override
		public void mouseDragged(MouseEvent me) {
			
			/*for (int k = 0; k < tabList.size(); k++) {
				if (me.getSource() == tabList.get(k)) {
					tabInFocus = tabList.get(k);
					int xDiff = me.getX() - mouseDraggedPosX;
					repaint();
				} else {}
			}	*/
		}

		@Override
		public void mouseMoved(MouseEvent me) {
		}
		
	}
	
	/**
	 * 
	 * @restriction The Lists tabText and tabWidths has to be of the same size
	 * @restriction tabHeight must be of a positive value or zero
	 * @param tabText List<String> of text to display on tabs (in order of appearing tab)
	 * @param tabWidths List<Integer> of integer values for desired width in pixels of tabs (in order of appearing tab)
	 * @param tabHeight height of tab bar in pixels
	 * @throws Exception  restrictions not followed
	 */
	public ToDoSortingBar(List<String> tabText, List<Integer> tabWidths, int tabHeight) throws Exception {
		
		if ((tabText.size()!=tabWidths.size()) || (tabHeight < 0)) {
			throw new Exception("Follow restrictions!");
		}
		mouseListener = new sortingBarMouseListener();
		mouseMotionListener = new sortingBarMouseListener();
		tabList = new ArrayList<JButton>();

		setLayout(new GridBagLayout());
		
		addTabs(tabText, tabWidths, tabHeight);
		
	}
	
	/**
	 * Currently creating and adding tabs and putting them on x-axis (UP FOR A BIG REVAMP)
	 * 
	 * @param tabText List<String> of text to display on tabs (in order of appearing tab)
	 * @param tabWidths List<Integer> of integer values for desired width in pixels of tabs (in order of appearing tab)
	 * @param tabHeight height of tab bar in pixels
	 */
	private void addTabs(List<String> tabText, List<Integer> tabWidths, int tabHeight) {
		GridBagConstraints tabCons = new GridBagConstraints();
		tabCons.weighty = 1;
		tabCons.fill = GridBagConstraints.VERTICAL;
		tabCons.anchor = GridBagConstraints.FIRST_LINE_START;
		
		// adding each tab (except the last one) in order
		JButton tempButton;
		for(int k = 0; k < tabText.size()-1; k++) {
			tempButton = new JButton(tabText.get(k));
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
		
		// finally adding the last button in order but sets tabCons.weightx to 1 to make sure excess space is located at the right side of the last tab
		tempButton = new JButton(tabText.get(tabText.size()-1));
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
		
		activeTab = tabList.get(0); //currently setting the first tab as active (for demonstration)
		activeTab.setBackground(Color.WHITE);
	}
}