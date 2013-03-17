package view.custom_components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JPanel;


/**
 * This class is used to represent a sorting bar, which a user can use at the users own
 * will.
 * It is totally customizable with size, actions, coloring.
 * It is providing resizing (with use of a mouse) of the separators of the tabs, you can also
 * listen to property change events to merge this feature with your other class/classes.
 * In the future you will also be able to change font of the name displayed on the tabs.
 * In the future you will also be able to set more visuals.
 * @author Magnus Larsson
 *
 *@todo Change cursor while hovering separators
 *@todo Make recalculateTabSpace() method take notice/handle tabs' minimal sizes
 */
@SuppressWarnings("serial")
public class SortingBar extends JPanel {

	private SortingTab activeTab; //Tab currently selected
	private int barWidth; //Width of bar in pixels
	
	private int INSET = 0; //Used to store the constant 'insets' 
							//which is the constant area between the right end of the tab's 
							//to the panel end
	
	private List<SortingTab> sortingTabs;
	private SortingBarMouseListener mouseListener;

	/**
	 * Inner class which inherits MouseListener and MouseMotionListener.
	 * Used to calculate and provide mouse interaction with the SortingBar.
	 * Provides tab clicking and dragging of separators.
	 * @author Magnus Larsson
	 *
	 */
	private class SortingBarMouseListener implements MouseListener,
			MouseMotionListener {

		private int tabInFocusIndex; //used to know which tab is being focused by the mouse
		private boolean leftEdgeInFocus = false; //used to know if left edge is in focus of the tab that is in focus
		private boolean rightEdgeInFocus = false; //used to know if left edge is in focus of the tab that is in focus
		private int mouseDraggedPosX; //used to calculate mouse movement for resizing

		/**
		 * This is triggered when a mouse is clicked, it then
		 * finds out which tab was clicked and sets the clicked tab to being focused
		 * (sets old focused tab to be unfocused)
		 */
		@Override
		public void mouseClicked(MouseEvent me) {

			 for (SortingTab tab : sortingTabs) {
				
				if (tab.contains(me.getX(), me.getY())) {
					activeTab.setFocus(false); //Setting previous focused tab to not focused
					tab.setFocus(true); //Setting clicked tab to focused
					activeTab = tab;
					tab.performClickAction();
					repaint();
					break;
				}
			}
		}
		
		/**
		 * Not used
		 */
		@Override
		public void mouseEntered(MouseEvent me) {
		}

		/**
		 * Not used
		 */
		@Override
		public void mouseExited(MouseEvent me) {
		}

		/**
		 * This is triggered when a mouse being pressed somewhere on the bar.
		 * It stores the mouse position for calculations and sets variables to
		 * know which tab / where the tab is pressed. 
		 */
		public void mousePressed(MouseEvent me) {
			mouseDraggedPosX = me.getX();

			for (int k = 0; k < sortingTabs.size(); k++) {

				if (sortingTabs.get(k).isAtRightEdge(me.getX(), me.getY())) {
					tabInFocusIndex = k;
					rightEdgeInFocus = true;
					leftEdgeInFocus = false;
					break;
				} else if (sortingTabs.get(k)
						.isAtLeftEdge(me.getX(), me.getY())) {
					tabInFocusIndex = k;
					rightEdgeInFocus = false;
					leftEdgeInFocus = true;
					break;
				}
			}
		}

		/**
		 * This is triggered when the bar registers that mouse has been released.
		 * I resets the edges that might have been focused to false.
		 */
		@Override
		public void mouseReleased(MouseEvent me) {
			rightEdgeInFocus = false;
			leftEdgeInFocus = false;
		}

		/**
		 * This is triggered when the mouse is dragged.
		 * If an edge is in focus dragTabs(MouseEvent me) is being called.
		 * (see dragTabs method)
		 */
		@Override
		public void mouseDragged(MouseEvent me) {
			if (leftEdgeInFocus || rightEdgeInFocus) {
				dragTabs(me);
			}
		}

		/**
		 * This method is used to manage the dragging behavior.
		 * You can drag tabs by pressing a separator (left/right edge of tab) and drag to the
		 * right or left. You can't drag the separator so that the minimum size is compromised.
		 * You can't drag a separator if it's left or right "edge" is fixed (locked)
		 * @param me
		 */
		private void dragTabs(MouseEvent me) {

			int xDiff = me.getX() - mouseDraggedPosX; //Used to calculate how far mouse is dragged
			SortingTab currentTab = sortingTabs.get(tabInFocusIndex);

			if (xDiff > 0) { // mouse moved to right
				int movingDistance = distanceAbleToMoveRight(tabInFocusIndex,
						xDiff); // calculate how much we can move

				if (movingDistance > 0) {
					if (leftEdgeInFocus) { // if left edge is grabbed and we can
											// move to the right
											// we resize this tab and the
											// previous (on the left) tab
						currentTab.resizeAnchoredRight(-movingDistance);
						sortingTabs.get(tabInFocusIndex - 1).resizeWidth(
								movingDistance);
					} else if (rightEdgeInFocus) { // if right edge is grabbed
													// and we can move to the
													// right
						// we resize this tab and the next (on the right)
						// tab
						currentTab.resizeWidth(movingDistance);
						sortingTabs.get(tabInFocusIndex + 1)
								.resizeAnchoredRight(-movingDistance);
					}
				}
			} else { // mouse moved to the left
				int movingDistance = distanceAbleToMoveLeft(tabInFocusIndex,
						-xDiff); //calculate distance we can move
				if (movingDistance > 0) {
					if (leftEdgeInFocus) { // if left edge is grabbed and we can
											// move to the left
											// we resize this tab and the
											//previous (on the left) tab
						currentTab.resizeAnchoredRight(movingDistance);
						sortingTabs.get(tabInFocusIndex - 1).resizeWidth(
								-movingDistance);
					} else if (rightEdgeInFocus) { // if right edge is grabbed
													// and we can move to the right
													// we resize this tab and the next
													// (on the right) tab
						currentTab.resizeWidth(-movingDistance);
						sortingTabs.get(tabInFocusIndex + 1)
								.resizeAnchoredRight(movingDistance);
					}
				}
			}
			mouseDraggedPosX = me.getX(); //Update stored mouse position after dragging.
			repaint();

			firePropertyChange("offsets", null, getTabOffsets()); //Notify listeners for a change of offsets
		}
		

		/**
		 * This is called to calculate the distance you are able to move a dragged separator
		 * to the left. It handles indexes and fixed/locked edges as well.
		 * @param currentTabIndex Index of the tab that is focused (might as well use the field
		 *			in the class if wanted).
		 * @param desiredDistance The distance you want to move the separator to the left. >= 0!
		 * @return returns the distance you are able to move the separator to the left.
		 */
		private int distanceAbleToMoveLeft(int currentTabIndex,
				int desiredDistance) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) { // if tab is not the first (first
											// cannot resize it's left separator)
					if (!currentTab.hasFixedLeftEdge()
							&& !sortingTabs.get(currentTabIndex - 1)
									.hasFixedRightEdge()) { // if the previous
															// tab has no fixed
															// right position
						return Math.min(
								sortingTabs.get(currentTabIndex - 1).getWidth()
										- sortingTabs.get(currentTabIndex - 1)
												.getMinimumWidth(),
								desiredDistance); //Returns the smallest value of the desired distance to
												  //move the separator, to the left, and the space you can move,
												  //to the left, without breaking the tab to the left's minimum width.
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) { // if tab is not the last
																// (last tab cannot resize it's right separator)
					if (!currentTab.hasFixedRightEdge()
							&& !sortingTabs.get(currentTabIndex + 1)
									.hasFixedLeftEdge()) {// if the next tab has
															// no fixed left
															// position
						return Math.min(
								currentTab.getWidth()
										- currentTab.getMinimumWidth(),
								desiredDistance); //Returns the smallest value of the desired distance to
												//move the separator, to the left, and the space you can move,
						  						//to the left, without breaking the current tab's minimum width.
					}
				}
			}
			return 0; // you cannot move
		}

		/**
		 * This is called to calculate the distance you are able to move a dragged separator
		 * to the right. It handles indexes and fixed/locked edges as well.
		 * @param currentTabIndex Index of the tab that is focused (might as well use the field
		 *			in the class if wanted).
		 * @param desiredDistance The distance you want to move the separator to the right. >= 0!
		 * @return returns the distance you are able to move the separator to the right.
		 */
		private int distanceAbleToMoveRight(int currentTabIndex, //Works in almost the same way as distanceAbleToMoveLeft().
																 //See comments in that method for a more closer description.
																 //The big difference is that you have to consider the tab to the right.
																 
				int desiredDistance) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) {
					if (!currentTab.hasFixedLeftEdge()
							&& !sortingTabs.get(currentTabIndex - 1)
									.hasFixedRightEdge()) {
						return Math.min(
								currentTab.getWidth()
										- currentTab.getMinimumWidth(),
								desiredDistance);
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) {
					if (!currentTab.hasFixedRightEdge()
							&& !sortingTabs.get(currentTabIndex + 1)
									.hasFixedLeftEdge()) {
						return Math.min(
								sortingTabs.get(currentTabIndex + 1).getWidth()
										- sortingTabs.get(currentTabIndex + 1)
												.getMinimumWidth(),
								desiredDistance);
					}
				}
			}
			return 0;
		}

		/**
		 * Not used
		 */
		@Override
		public void mouseMoved(MouseEvent me) {
		}

	}

	/**
	 * Checking that the array-arguments given to the constructor is the same length.
	 * Instantiating listener and variables/objects.
	 * Adding tabs to the bar with given arguments.
	 * Setting the size of the bar.
	 * Set's the first tab to selected as default.
	 * Adding mouse listeners and a component listener for
	 * automatically resizing of the bar/tabs if the panel changes size.
	 * Changes the cursor looks when hovering the bar.
	 * 
	 * @param tabNames name of the tabs
	 * @param tabWidths widths of the tabs (in pixels)
	 * @param minTabWidths minimum width of the tabs (in pixels)
	 * @param tabHeights height of the tabs (in pixels)
	 * @param selectedTabColor color of the selected/focused tab
	 * @param notSelectedTabColor color of the not selected/ not focused tab
	 * @throws IllegalArgumentException Thrown if the arrays provided as arguments are of different sizes.
	 */
	//TODO DEFENSIVE!
	public SortingBar(String[] tabNames, int[] tabWidths, int[] minTabWidths, int tabHeights, Color selectedTabColor, Color notSelectedTabColor) throws IllegalArgumentException {
		
		checkArguments(tabNames, tabWidths, minTabWidths); //checking the array lengths so that they match.
		
		mouseListener = new SortingBarMouseListener();
		sortingTabs = new ArrayList<SortingTab>();
		
		addSortingTabs(tabNames, tabWidths, minTabWidths, tabHeights, selectedTabColor, notSelectedTabColor);
		
		setBarSize(tabHeights);
		
		activeTab = sortingTabs.get(0); //First tab selected/focused as default
		sortingTabs.get(0).setFocus(true);

		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		this.addComponentListener(new ComponentAdapter() { //For noticing panel resizing
			public void componentResized(ComponentEvent e) {

				if (getSize().width == 0) { //First time width and height is 0 so ignore that time.
					return;
				}
				recalculateTabSpace(); //Resizing the widths of the tabs to scale with the panel size change.
			}
		});
		
		
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Called to retrieve tab offsets, in relation to the sorting bar,
	 * for the use of aligning other content with separators.
	 * 
	 * @return
	 */
	public int[] getTabOffsets() {
		int[] offsets = new int[sortingTabs.size()];
		
		int offSet = 0;
		for (int i = 0; i < offsets.length; i++) { //Adding offsets, in relation to sorting bar,
													//to an array. For each index you store the
													//corresponding tab's right position +
													//the previous tabs' widths.
			int curtTabWidth = sortingTabs.get(i).getWidth();
			offsets[i] = curtTabWidth + offSet;
			offSet += curtTabWidth;
		}

		return offsets;
	}
	
	/**
	 * Called to get the List<SortingTab> of all the tabs.
	 * @return
	 */
	public List<SortingTab> getSortingTabs() {
		return sortingTabs;
	}
	
	/**
	 * Called to set a specific tab's name (using index to identify).
	 * @param index the index of tab to set name to.
	 * @param newName the new name of the tab.
	 */
	//TODO DEFENSIVE
	public void setTabName(int index, String newName) {
		sortingTabs.get(index).setName(newName);
	}
	
	/**
	 * Called to set a specific tab's action (using index to identify)
	 * @param index the index of tab to set action to.
	 * @param action the new action of the tab.
	 */
	//TODO DEFENSIVE
	public void setTabAction(int index, Action action) {
		sortingTabs.get(index).setAction(action);
	}
	
	/**
	 * Used by constructor to add sorting tabs to the sorting bar.
	 * See the constructor for parameter descriptions.
	 * @param tabNames
	 * @param tabWidths
	 * @param minTabWidths
	 * @param tabHeights
	 * @param selectedTabColor
	 * @param notSelectedTabColor
	 * @throws IllegalArgumentException
	 */
	private void addSortingTabs(String[] tabNames, int[] tabWidths, int[] minTabWidths, int tabHeights, Color selectedTabColor, Color notSelectedTabColor) throws IllegalArgumentException {
		
		int offSet = 0; //Used to find out where to put the tabs in horizontal position.
		for (int k = 0; k < tabNames.length; k++) {
			sortingTabs.add(new SortingTab(tabNames[k], tabWidths[k], tabHeights, offSet, 0, false, minTabWidths[k], false, false, selectedTabColor, notSelectedTabColor));
			offSet += tabWidths[k];
		}

		barWidth = offSet;
		
		sortingTabs.get(0).setFixedLeftEdge(true);
		sortingTabs.get(sortingTabs.size() - 1).setFixedRightEdge(true);
	}
	
	/**
	 * Used by the constructor to set the size of the bar (in pixels).
	 * @param barHeight height of bar (in pixels).
	 */
	private void setBarSize(int barHeight) {
		int barWidth = 0;

		for (SortingTab tab: sortingTabs) { //Checking all tabs to calculate their total width.
			barWidth += tab.getWidth();
		}
			
		this.setPreferredSize(new Dimension(barWidth + 1, barHeight));
		this.setMinimumSize(new Dimension(barWidth + 1, barHeight));
	}
	
	/**
	 * Called by a component listener in order to recalculate/resize the widths of tabs.
	 * WARNING: does not consider tab's minimum width for the moment. Needs to be fixed.
	 */
	private void recalculateTabSpace() {

		int panelWidth = getWidth();
		if(INSET == 0) //Used to instantiate the constant INSET
			INSET  = panelWidth - barWidth;

		int newBarWidth = panelWidth - INSET; //Calculates the x-space all the tabs must share.
		int spaceDifference = newBarWidth - barWidth; //Calculates the difference between the new and old x-space.
		barWidth = newBarWidth; //This is because we reuse barWidth next time this method is called.


	
		int tabDifference = Math.round(spaceDifference / sortingTabs.size()); //Calculating individual tab x-space diff.
		
		int offSet = 0; //Need to store offSet to know how much extra we have to move tabs in relation to previously moved tabs.
		for (int k = 0; k < sortingTabs.size(); k++) {	// moving and resizing tabs
			SortingTab curTab = sortingTabs.get(k);
			curTab.moveXPos(offSet);
			curTab.resizeWidth(tabDifference);
			offSet += curTab.getWidth();
		}
		
		//Because of integer division and casting we add the lost/excessive pixels to the last bar
		sortingTabs.get(sortingTabs.size() - 1).resizeWidth(newBarWidth - offSet);  
		
		firePropertyChange("offsets", null, getTabOffsets()); //Notify listeners on new offsets
		repaint();
	}
	
	/**
	 * Used to check that given arrays are of a length larger than 0, and that they are of equal length.
	 * @param tabNames
	 * @param tabWidths
	 * @param minTabWidths
	 * @throws IllegalArgumentException throws this exception of arguments are not of equal length nor with a length larger than 0.
	 */
	private void checkArguments(String[] tabNames, int[] tabWidths, int[] minTabWidths) throws IllegalArgumentException {
		int len = tabNames.length;
		
		if	(len == 0 || tabWidths.length != len || minTabWidths.length != len) {
			throw new IllegalArgumentException("Arrays of different sizes provided!");
		}	
	}
	
	/**
	 * Overriding the default paint behavior of a panel.
	 * Used to take advantage of the tabs' individual information and paint methods to paint them at correct locations.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SortingTab tab : sortingTabs) {
			tab.paintTab(this, g, tab.getXPos(), tab.getYPos());
		}
	}

	
}
