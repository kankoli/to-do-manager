package view.main_window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import control.ControllerInterface;

public class SortingBar extends JPanel implements Observer{
	
	private SortingTab activeTab;
	private List<SortingTab> sortingTabs;	
	private SortingBarMouseListener mouseListener;
	private ControllerInterface controller;
	
	//private TaskPane taskPane;
	
	//TEST
	
	SortingTab tab1;
	SortingTab tab2;
	SortingTab tab3;
	SortingTab tab4;
	
	
	private class SortingBarMouseListener implements MouseListener, MouseMotionListener {
		
		private int tabInFocusIndex;
		private boolean leftEdgeInFocus = false;
		private boolean rightEdgeInFocus = false;
		private int mouseDraggedPosX;
		
		@Override
		public void mouseClicked(MouseEvent me) {
			
			for (SortingTab tab: sortingTabs) { 
				if (tab.contains(me.getX(), me.getY())) {
					activeTab.setFocus(false);
					tab.setFocus(true);
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

		@Override
		public void mousePressed(MouseEvent me) {
			mouseDraggedPosX = me.getX();
			
			for (int k = 0; k < sortingTabs.size(); k++) {
				
				if (sortingTabs.get(k).isAtRightEdge(me.getX(), me.getY())) {
					tabInFocusIndex = k;
					rightEdgeInFocus = true;
					leftEdgeInFocus = false;
					break;
				} else if (sortingTabs.get(k).isAtLeftEdge(me.getX(), me.getY())) {
					tabInFocusIndex = k;
					rightEdgeInFocus = false;
					leftEdgeInFocus = true;
					break;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			rightEdgeInFocus = false;
			leftEdgeInFocus = false;
		}

		@Override
		public void mouseDragged(MouseEvent me) {
			if (leftEdgeInFocus || rightEdgeInFocus) {
				dragTabs(me);
			}
		}
		
		/*private void dragTabs(MouseEvent me) {
			int xDiff = me.getX() - mouseDraggedPosX;
			SortingTab currentTab = sortingTabs.get(tabInFocusIndex);
			
			if (xDiff > 0 && ableToMoveRight(tabInFocusIndex, xDiff)) {
				if (leftEdgeInFocus) {
					currentTab.resizeAnchoredRight(-xDiff);
					sortingTabs.get(tabInFocusIndex - 1).resizeWidth(xDiff);
				} else if (rightEdgeInFocus) {
					currentTab.resizeWidth(xDiff);
					sortingTabs.get(tabInFocusIndex + 1).resizeAnchoredRight(-xDiff);
				}
			} else if (xDiff < 0 && ableToMoveLeft(tabInFocusIndex, xDiff)) {
				if (leftEdgeInFocus) {
					currentTab.resizeAnchoredRight(-xDiff);
					sortingTabs.get(tabInFocusIndex - 1).resizeWidth(xDiff);
				} else if (rightEdgeInFocus) {
					currentTab.resizeWidth(xDiff);
					sortingTabs.get(tabInFocusIndex + 1).resizeAnchoredRight(-xDiff);
				}
			}
			mouseDraggedPosX = me.getX();
			repaint();
			
		}*/
		
		private void dragTabs(MouseEvent me) {
			int xDiff = me.getX() - mouseDraggedPosX;
			SortingTab currentTab = sortingTabs.get(tabInFocusIndex);
			
			if (xDiff > 0) {
				int movingDistance = distanceAbleToMoveRight(tabInFocusIndex, xDiff);
				if (movingDistance > 0) {
					if (leftEdgeInFocus) {
						currentTab.resizeAnchoredRight(-movingDistance);
						sortingTabs.get(tabInFocusIndex - 1).resizeWidth(movingDistance);
					} else if (rightEdgeInFocus) {
						currentTab.resizeWidth(movingDistance);
						sortingTabs.get(tabInFocusIndex + 1).resizeAnchoredRight(-movingDistance);
					}
				}
			} else if (xDiff < 0) {
				int movingDistance = distanceAbleToMoveLeft(tabInFocusIndex, -xDiff);
				if (movingDistance > 0) {
				if (leftEdgeInFocus) {
						currentTab.resizeAnchoredRight(movingDistance);
						sortingTabs.get(tabInFocusIndex - 1).resizeWidth(-movingDistance);
					} else if (rightEdgeInFocus) {
						currentTab.resizeWidth(-movingDistance);
						sortingTabs.get(tabInFocusIndex + 1).resizeAnchoredRight(movingDistance);
					}
				}
			}
			mouseDraggedPosX = me.getX();
			repaint();
			
		}
		
		/*private boolean ableToMoveLeft(int currentTabIndex, int xDiff) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) {
					if (!currentTab.hasFixedLeftEdge() 
							&& !sortingTabs.get(currentTabIndex - 1).hasFixedRightEdge()
							&& sortingTabs.get(currentTabIndex - 1).getXPos() + sortingTabs.get(currentTabIndex - 1).getMinimumWidth() <= currentTab.getXPos() + xDiff) {
						return true;
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) {
					if (!currentTab.hasFixedRightEdge() 
							&& !sortingTabs.get(currentTabIndex + 1).hasFixedLeftEdge()
							&& currentTab.getWidth() + xDiff >= currentTab.getMinimumWidth()) {
						return true;
					}
				}
			} 
			return false;
		}*/
		
		/*private boolean ableToMoveRight(int currentTabIndex, int xDiff) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) {
					if (!currentTab.hasFixedLeftEdge() 
							&& !sortingTabs.get(currentTabIndex - 1).hasFixedRightEdge()
							&& currentTab.getWidth() - xDiff >= currentTab.getMinimumWidth()) {
						return true;
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) {
					if (!currentTab.hasFixedRightEdge() 
							&& !sortingTabs.get(currentTabIndex + 1).hasFixedLeftEdge()
							&& sortingTabs.get(currentTabIndex + 1).getWidth() - xDiff >= sortingTabs.get(currentTabIndex + 1).getMinimumWidth()) {
						return true;
					}
				}
			} 
			return false;
		}*/
		
		private int distanceAbleToMoveLeft(int currentTabIndex, int desiredDistance) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) {
					if (!currentTab.hasFixedLeftEdge() 
							&& !sortingTabs.get(currentTabIndex - 1).hasFixedRightEdge()) {
						return Math.min(sortingTabs.get(currentTabIndex - 1).getWidth() - sortingTabs.get(currentTabIndex - 1).getMinimumWidth(), desiredDistance);
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) {
					if (!currentTab.hasFixedRightEdge() 
							&& !sortingTabs.get(currentTabIndex + 1).hasFixedLeftEdge()) {
						return Math.min(currentTab.getWidth() - currentTab.getMinimumWidth(), desiredDistance);
					}
				}
			} 
			return 0;
		}
		
		private int distanceAbleToMoveRight(int currentTabIndex, int desiredDistance) {
			SortingTab currentTab = sortingTabs.get(currentTabIndex);
			if (leftEdgeInFocus) {
				if (currentTabIndex != 0) {
					if (!currentTab.hasFixedLeftEdge()
							&& !sortingTabs.get(currentTabIndex - 1).hasFixedRightEdge()) {
						return Math.min(currentTab.getWidth() - currentTab.getMinimumWidth(), desiredDistance);
					}
				}
			} else if (rightEdgeInFocus) {
				if (currentTabIndex != sortingTabs.size() - 1) {
					if (!currentTab.hasFixedRightEdge() 
							&& !sortingTabs.get(currentTabIndex + 1).hasFixedLeftEdge()) {
						return Math.min(sortingTabs.get(currentTabIndex + 1).getWidth() - sortingTabs.get(currentTabIndex + 1).getMinimumWidth(), desiredDistance);
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
		
		//TEST
		tab1 = new SortingTab(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.title.name"), 100, 30, 0, 0, true, 20, true, false);
		tab2 = new SortingTab(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.date.name"), 100, 30, 100, 0, false);
		tab3 = new SortingTab(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.category.name"), 100, 30, 200, 0, false);
		tab4 = new SortingTab(lang
				.getString("mainFrame.middlePanel.sortingBar.tab.priority.name"), 100, 30, 300, 0, false, 20, false, true);
		
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
	
	/*public SortingBar (int width, int height) {
	 
	 }*/
	
	
	
	/*public SortingBar (TaskPane taskPane) {
		
	}*/
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (SortingTab tab: sortingTabs) { 
			tab.paintTab(this, g, tab.getXPos(), tab.getYPos());
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
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
			repaint();
		}
	}
}
