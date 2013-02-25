package view.main_window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.JPanel;

import control.ControllerInterface;
import control.ControllerInterface.ActionName;

/**
 * This class (UNDER DEVELOPMENT!!!) will represent the custom made sorting bar
 * for the task. It is resizable (dragging), you will be able to sort task by
 * clicking on tab sections.
 * 
 * @author Magnus Larsson
 * 
 */
@SuppressWarnings("serial")
public class SortingBar extends JPanel implements Observer {

	private SortingTab activeTab;
	private List<SortingTab> sortingTabs;
	private SortingBarMouseListener mouseListener;
	private ControllerInterface controller;

	// XXX this is just for testing, will be an array
	SortingTab tab1;
	SortingTab tab2;
	SortingTab tab3;
	SortingTab tab4;

	private class SortingBarMouseListener implements MouseListener,
			MouseMotionListener {

		private int tabInFocusIndex;
		private boolean leftEdgeInFocus = false;
		private boolean rightEdgeInFocus = false;
		private int mouseDraggedPosX;

		@Override
		// This listener will set the current selected tab as active
		public void mouseClicked(MouseEvent me) {

			// XXX Marco: Magnus, im using this way to simulate an event.. 
			// check code, it's temporary solution, works well
			ResourceBundle lang = controller.getLanguageBundle();
			String[] names = {
					"mainFrame.middlePanel.sortingBar.tab.title.name",
					"mainFrame.middlePanel.sortingBar.tab.date.name",
					"mainFrame.middlePanel.sortingBar.tab.category.name",
					"mainFrame.middlePanel.sortingBar.tab.priority.name" };

			// for (SortingTab tab : sortingTabs) {
			for (int i = 0; i < sortingTabs.size(); i++) {

				SortingTab tab = sortingTabs.get(i);

				if (tab.contains(me.getX(), me.getY())) {
					activeTab.setFocus(false);
					tab.setFocus(true);

					// We create an actionevent using correct name...
					Action a = controller.getAction(ActionName.SORT);
					a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, lang
							.getString(names[i])));

					activeTab = tab;
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

	public SortingBar(ControllerInterface controller) {

		mouseListener = new SortingBarMouseListener();

		ResourceBundle lang = controller.getLanguageBundle();

		// TEST
		tab1 = new SortingTab(
				lang.getString("mainFrame.middlePanel.sortingBar.tab.title.name"),
				100, 30, 0, 0, true, 20, true, false);
		tab2 = new SortingTab(
				lang.getString("mainFrame.middlePanel.sortingBar.tab.date.name"),
				100, 30, 100, 0, false);
		tab3 = new SortingTab(
				lang.getString("mainFrame.middlePanel.sortingBar.tab.category.name"),
				100, 30, 200, 0, false);
		tab4 = new SortingTab(
				lang.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"),
				100, 30, 300, 0, false, 20, false, true);

		sortingTabs = new ArrayList<SortingTab>();

		sortingTabs.add(tab1);
		sortingTabs.add(tab2);
		sortingTabs.add(tab3);
		sortingTabs.add(tab4);

		activeTab = tab1;
		this.controller = controller;

		this.setPreferredSize(new Dimension(401, 32));
		this.setMinimumSize(new Dimension(401, 32));
		this.setBackground(Color.WHITE);

		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

		controller.registerAsObserver(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SortingTab tab : sortingTabs) {
			tab.paintTab(this, g, tab.getXPos(), tab.getYPos());
		}
	}

	// This is needed to observe the language changings, to change the labels on
	// the tabs
	public void update(Observable o, Object arg) {
		ControllerInterface.ChangeMessage msg = (ControllerInterface.ChangeMessage) arg;

		if (msg == ControllerInterface.ChangeMessage.CHANGED_LANG) {
			ResourceBundle lang = controller.getLanguageBundle();
			tab1.setName(lang
					.getString("mainFrame.middlePanel.sortingBar.tab.title.name"));
			tab2.setName(lang
					.getString("mainFrame.middlePanel.sortingBar.tab.date.name"));
			tab3.setName(lang
					.getString("mainFrame.middlePanel.sortingBar.tab.category.name"));
			tab4.setName(lang
					.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"));
			revalidate();
			repaint();
		}
	}
}
