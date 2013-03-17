package view.main_window;

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
 * This class (UNDER DEVELOPMENT!!!) will represent the custom made sorting bar
 * for the task. It is resizable (dragging), you will be able to sort task by
 * clicking on tab sections.
 * 
 * Please note... it's not working yet
 * 
 * Will be extended for flexibility to add tabs after creation (and delete)
 * @author Magnus Larsson
 * 
 * 
 * Things to do:
 * 
 *  * Change cursor while hovering the separators
 *  * Make the recalculateTabSpace() method take notice to tabs minimal sizes
 * 
 */
@SuppressWarnings("serial")
public class SortingBar extends JPanel {

	private SortingTab activeTab;
	private int barWidth;
	
	private int inset = 0;
	
	private List<SortingTab> sortingTabs;
	private SortingBarMouseListener mouseListener;

	private class SortingBarMouseListener implements MouseListener,
			MouseMotionListener {

		private int tabInFocusIndex;
		private boolean leftEdgeInFocus = false;
		private boolean rightEdgeInFocus = false;
		private int mouseDraggedPosX;

		/*// TODO testing
		private SortingBar sb;

		public SortingBarMouseListener(SortingBar sb) {
			this.sb = sb;
		}*/

		@Override
		// This listener will set the current selected tab as active
		public void mouseClicked(MouseEvent me) {

			 for (SortingTab tab : sortingTabs) {
				
				if (tab.contains(me.getX(), me.getY())) {
					activeTab.setFocus(false);
					tab.setFocus(true);
					activeTab = tab;
					tab.performClickAction();
					repaint();
					break;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent me) {
		}

		@Override
		public void mouseExited(MouseEvent me) {
		}

		// This checks which tab is being pressed (and which edge)
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

		@Override
		// When mouse is released we unset the current focused edges
		public void mouseReleased(MouseEvent me) {
			rightEdgeInFocus = false;
			leftEdgeInFocus = false;
		}

		@Override
		// This checks if any edge is focused before calling the drag function
		public void mouseDragged(MouseEvent me) {
			if (leftEdgeInFocus || rightEdgeInFocus) {
				dragTabs(me);
			}
		}

		// this method is used to manage the dragging behaviour
		private void dragTabs(MouseEvent me) {

			int xDiff = me.getX() - mouseDraggedPosX;
			SortingTab currentTab = sortingTabs.get(tabInFocusIndex);

			if (xDiff > 0) { // mouse moved to right
				int movingDistance = distanceAbleToMoveRight(tabInFocusIndex,
						xDiff); // calculate how much we can move

				// TODO talk with marco
				// if(movingDIstance <= 0)
				// break;

				if (movingDistance > 0) {
					if (leftEdgeInFocus) { // if left edge is grabbed and we can
											// move to the right
											// we should resize this tab and the
											// previous (on the left) tab
						currentTab.resizeAnchoredRight(-movingDistance);
						sortingTabs.get(tabInFocusIndex - 1).resizeWidth(
								movingDistance);
					} else if (rightEdgeInFocus) { // if right edge is grabbed
													// and we can move to the
													// right
						// we should resize this tab and the next (on the right)
						// tab
						currentTab.resizeWidth(movingDistance);
						sortingTabs.get(tabInFocusIndex + 1)
								.resizeAnchoredRight(-movingDistance);
					}
				}
			} else { // mouse moved to the left
				int movingDistance = distanceAbleToMoveLeft(tabInFocusIndex,
						-xDiff);
				if (movingDistance > 0) {
					if (leftEdgeInFocus) {
						currentTab.resizeAnchoredRight(movingDistance);
						sortingTabs.get(tabInFocusIndex - 1).resizeWidth(
								-movingDistance);
					} else if (rightEdgeInFocus) {
						currentTab.resizeWidth(-movingDistance);
						sortingTabs.get(tabInFocusIndex + 1)
								.resizeAnchoredRight(movingDistance);
					}
				}
			}
			mouseDraggedPosX = me.getX();
			repaint();

			//Notify listeners for a change of offsets
			firePropertyChange("offsets", null, getTabOffsets());
		}
		

		// Calculate how much we can move this tab to the left
		private int distanceAbleToMoveLeft(int currentTabIndex,
				int desiredDistance) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) { // if we are not the first (first
											// cannot resize)
					if (!currentTab.hasFixedLeftEdge()
							&& !sortingTabs.get(currentTabIndex - 1)
									.hasFixedRightEdge()) { // if the previous
															// tab has no fixed
															// right position
						return Math.min(
								sortingTabs.get(currentTabIndex - 1).getWidth()
										- sortingTabs.get(currentTabIndex - 1)
												.getMinimumWidth(),
								desiredDistance);
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) { // if we are
																	// not the
																	// last one
																	// (we
																	// cannot
																	// resize)
					if (!currentTab.hasFixedRightEdge()
							&& !sortingTabs.get(currentTabIndex + 1)
									.hasFixedLeftEdge()) {// if the next tab has
															// no fixed left
															// position

						// we calculate if the desired resizing will make the
						// previous too small, if yes we move the maximum as we
						// can
						return Math.min(
								currentTab.getWidth()
										- currentTab.getMinimumWidth(),
								desiredDistance);
					}
				}
			}
			return 0; // you cannot move
		}

		// Calculate how much we can move this tab to the right
		private int distanceAbleToMoveRight(int currentTabIndex,
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

		@Override
		public void mouseMoved(MouseEvent me) {
		}

	}

	
	//TODO DEFENSE!
	public SortingBar(String[] tabNames, int[] tabWidths, int[] minTabWidths, int tabHeights, Color selectedTabColor, Color notSelectedTabColor) throws IllegalArgumentException {
		
		checkArguments(tabNames, tabWidths, minTabWidths);
		
		//mouseListener = new SortingBarMouseListener(this);
		mouseListener = new SortingBarMouseListener();
		sortingTabs = new ArrayList<SortingTab>();
		
		addSortingTabs(tabNames, tabWidths, minTabWidths, tabHeights, selectedTabColor, notSelectedTabColor);
		
		setBarSize(tabHeights);
		
		activeTab = sortingTabs.get(0);
		sortingTabs.get(0).setFocus(true);

		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {

				if (getSize().width == 0) {
					return;
				}
				recalculateTabSpace();
			}
		});
		
		
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Small method to retrieve tab offsets
	 * 
	 * @return
	 */
	public int[] getTabOffsets() {
		int[] offsets = new int[sortingTabs.size()];
		
		int offSet = 0;
		for (int i = 0; i < offsets.length; i++) {
			int curtTabWidth = sortingTabs.get(i).getWidth();
			offsets[i] = curtTabWidth + offSet;
			offSet += curtTabWidth;
		}

		return offsets;
	}
	
	public List<SortingTab> getSortingTabs() {
		return sortingTabs;
	}
	
	//DEFENSIVE
	public void setTabName(int index, String newName) {
		sortingTabs.get(index).setName(newName);
	}
	
	//DEFENSIVE
	public void setTabAction(int index, Action action) {
		sortingTabs.get(index).setAction(action);
	}
	
	private void addSortingTabs(String[] tabNames, int[] tabWidths, int[] minTabWidths, int tabHeights, Color selectedTabColor, Color notSelectedTabColor) throws IllegalArgumentException {
		
		int offSet = 0;
		for (int k = 0; k < tabNames.length; k++) {
			sortingTabs.add(new SortingTab(tabNames[k], tabWidths[k], tabHeights, offSet, 0, false, minTabWidths[k], false, false, selectedTabColor, notSelectedTabColor));
			offSet += tabWidths[k];
		}

		barWidth = offSet;
		
		sortingTabs.get(0).setFixedLeftEdge(true);
		sortingTabs.get(sortingTabs.size() - 1).setFixedRightEdge(true);
	}
	
	private void setBarSize(int barHeight) {
		int barWidth = 0;

		for (SortingTab tab: sortingTabs) {
			barWidth += tab.getWidth();
		}
			
		this.setPreferredSize(new Dimension(barWidth + 1, barHeight));
		this.setMinimumSize(new Dimension(barWidth + 1, barHeight));
	}
	
	private void recalculateTabSpace() {

		int panelWidth = getWidth();
		if(inset == 0)
			inset  = panelWidth - barWidth;

		int newBarWidth = panelWidth - inset;
		int spaceDifference = newBarWidth - barWidth;
		barWidth = newBarWidth;


		//		// moving and resizing tabs
		int tabDifference = Math.round(spaceDifference / sortingTabs.size());
		int offSet = 0;
		
		for (int k = 0; k < sortingTabs.size(); k++) {
			SortingTab curTab = sortingTabs.get(k);
			curTab.moveXPos(offSet);
			curTab.resizeWidth(tabDifference);
			offSet += curTab.getWidth();
		}
		
		//Because of integer division and casting we add the lost/excessive pixels to the last bar
		sortingTabs.get(sortingTabs.size() - 1).resizeWidth(newBarWidth - offSet);  
		
		firePropertyChange("offsets", null, getTabOffsets()); //Notify listeners on new offsets
		repaint();
		
		System.out.println("HI  " +( sortingTabs.get(0).getWidth() + sortingTabs.get(1).getWidth() + sortingTabs.get(2).getWidth() + sortingTabs.get(3).getWidth()));
	}
	
	private void checkArguments(String[] tabNames, int[] tabWidths, int[] minTabWidths) throws IllegalArgumentException {
		int len = tabNames.length;
		
		if	(len == 0 || tabWidths.length != len || minTabWidths.length != len) {
			throw new IllegalArgumentException("Arrays of different sizes provided!");
		}
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SortingTab tab : sortingTabs) {
			tab.paintTab(this, g, tab.getXPos(), tab.getYPos());
		}
	}

	
}
