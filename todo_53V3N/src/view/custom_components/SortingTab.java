package view.custom_components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.Action;

/**
 * This class is used to represent a tab used by the class SortingBar.
 * It is made to store individual tab information, only concerning this tab.
 * 
 * @author Magnus Larsson
 * @version 1.0
 * 
 * In future:
 * 	Might inherit from a suitable swing component.
 */

public class SortingTab {
	
	private String name;
	
	private int width;
	private int height;
	private int xPos; //Position in relation to sorting bar
	private int yPos; //Position in relation to sorting bar
	private int minimumWidth;
	
	private Color inFocusColor;
	private Color outOfFocusColor;
	
	private boolean inFocus;
	private boolean fixedLeftEdge; //Locked/unlocked
	private boolean fixedRightEdge; //Locked/unlocked
	private boolean isMinimumWidth;
	
	private Action action; //Action to fire of
	
	/**
	 * 
	 * @param name name of tab, will be displayed on tab
	 * @param width width of tab (in pixels)
	 * @param height height of tab (in pixels)
	 * @param xPos Position in relation to sorting bar
	 * @param yPos Position in relation to sorting bar
	 * @param inFocus selected/unselected
	 * @param selectedColor color of tab when selected
	 * @param notSelectedColor color of tab when not selected
	 */
	//Add checkers for values
	public SortingTab(String name, int width, int height, int xPos, int yPos, boolean inFocus, Color selectedColor, Color notSelectedColor) {
		this(name, width, height, xPos, yPos, inFocus, 20, false, false, selectedColor, notSelectedColor);
	}
	
	/**
	 * 
	 * @param name name of tab, will be displayed on tab
	 * @param width width of tab (in pixels)
	 * @param height height of tab (in pixels)
	 * @param xPos Position in relation to sorting bar
	 * @param yPos Position in relation to sorting bar
	 * @param inFocus selected/unselected
	 * @param minimumWidth minimum width of a tab (in pixels)
	 * @param fixedLeftEdge locked/unlocked left edge of tab
	 * @param fixedRightEdge locked/unlocked right edge of tab
	 * @param selectedColor color of tab when selected
	 * @param notSelectedColor color of tab when not selected
	 */
	//Add checkers for values FIX DEFENSIVE MEASUREMENTS
	public SortingTab(String name, int width, int height, int xPos, int yPos, boolean inFocus, int minimumWidth, boolean fixedLeftEdge, boolean fixedRightEdge, Color selectedColor, Color notSelectedColor) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.inFocus = inFocus;
		this.minimumWidth = minimumWidth;
		this.fixedLeftEdge = fixedLeftEdge;
		this.fixedRightEdge = fixedRightEdge;
		
		if (width == minimumWidth) {
			isMinimumWidth = true;
		} else if (width < minimumWidth) {
			this.width = minimumWidth;
			isMinimumWidth = true;
		} else {
			isMinimumWidth = false;
		}
		
		inFocusColor = selectedColor;
		outOfFocusColor = notSelectedColor;
	}
	
	/**
	 * Set an action to the tab
	 * @param action 
	 */
	public void setAction(Action action) {
		this.action = action;
	}
	
	/**
	 * Called from SortingBar when tab is considered to be clicked,
	 * action is then performed.
	 */
	public void performClickAction() {
		if (action != null) {
			action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getName()));
		}
		
	}
	
	/**
	 * Called to set the name of the tab
	 * @param name name of tab
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Called to get the name of the tab
	 * @return returns the name of the tab
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Called to get the x-position, in relation to the SortingBar, of the tab
	 * @return returns the xPos (x-position)
	 */
	public int getXPos() {
		return xPos;
	}
	
	/**
	 * Called to set the x-position, in relation to the SortingBar, of the tab
	 * @param xPos desired x-position
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	/**
	 * Called to move the x-position, in relation to the SortingBar, of the tab
	 * @param xPos desired x-position
	 * In future:
	 * 	Might not be used, use setXPos(int xPos) instead
	 */
	public void moveXPos(int xPos) {
		/*if (!fixedLeftEdge && xPos >= 0) {
			if (fixedRightEdge && (getMinimumWidth() <= (getWidth() - (xPos - getXPos())))) { // Must maintain minimum width
				setWidth(getWidth() - (xPos - getXPos()));
				setXPos(xPos);
			} else if (!fixedRightEdge) {
				setXPos(xPos);
			} else {}
		}*/
		
		setXPos(xPos);
	}
	
	/**
	 * Called to get the y-position, in relation to the SortingBar, of the tab
	 * @return yPos (y-position)
	 */
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * Called to set the y-position, in relation to the SortingBar, of the tab
	 * @param yPos
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	/**
	 * Called to get the width of the tab (in pixels)
	 * @return the width of the tab (in pixels)
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Called to set the width of the tab (in pixels)
	 * @param width the desired width (in pixels)
	 */
	public void setWidth(int width) {
		
		if (getWidth() <= getMinimumWidth()) { //Width if right now allowed to be less than minimumWidth due to lack of time to fix other bugs.
			isMinimumWidth = true;
		}
		this.width = width;
	
	}
	
	/**
	 * Called to set the minimumWidth of the tab (in pixels)
	 * @param minimumWidth desired minimum width (in pixels)
	 */
	public void setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
		if (minimumWidth >= getWidth()) { //Might be removed due to handling of this in SortingBar.
			setWidth(minimumWidth);
			isMinimumWidth = true;
		}
	}
	
	/**
	 * Called to get the minimum width of the tab (in pixels)
	 * @return minimum width in pixels
	 */
	public int getMinimumWidth() {
		return minimumWidth;
	}
	
	/**
	 * Called to get the height of the tab (in pixels)
	 * @return height of tab in pixels
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Called to set the height of the tab (in pixels)
	 * @param height desired height of tab (in pixels)
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * Called to set the tab to selected/unselected state
	 * @param inFocus true if selected, false if not
	 */
	public void setFocus(boolean inFocus) {
		this.inFocus = inFocus;
	}
	
	/**
	 * Called to set the unselected color of the tab
	 * @param color desired unselected color of the tab
	 */
	public void setOutOfFocusColor(Color color) {
		this.outOfFocusColor = color;
	}
	
	/**
	 * Called to get the unselected color of the tab
	 * @return the unselected color of the tab
	 */
	public Color getOutOfFocusColor() {
		return outOfFocusColor;
	}
	
	/**
	 * Called to set the selected color of the tab
	 * @param color desired selected color of the tab
	 */
	public void setInFocusColor(Color color) {
		this.inFocusColor = color;
	}
	
	/**
	 * Called to get the selected color of the tab
	 * @return the selected color of the tab
	 */
	public Color getInFocusColor() {
		return inFocusColor;
	}
	
	/**
	 * Called to set the left edge of the tab to a locked/unlocked position
	 * @param fixedLeftEdge true for locked, false for unlocked
	 */
	public void setFixedLeftEdge(boolean fixedLeftEdge) {
		this.fixedLeftEdge = fixedLeftEdge;
	}
	
	/**
	 * Called to get the locked/unlocked state of the left edge of the tab
	 * @return true if locked, false if unlocked
	 */
	public boolean hasFixedLeftEdge() {
		return fixedLeftEdge;
	}
	
	/**
	 * Called to set the right edge of the tab to a locked/unlocked position
	 * @param fixedRightEdge true for locked, false for unlocked
	 */
	public void setFixedRightEdge(boolean fixedRightEdge) {
		this.fixedRightEdge = fixedRightEdge;
	}
	
	/**
	 * Called to get the locked/unlocked state of the right edge of the tab
	 * @return true if locked, false if unlocked
	 */
	public boolean hasFixedRightEdge() {
		return fixedRightEdge;
	}
	
	/**
	 * This is used to check if one point is within the tab or not
	 * @param xPos x-position in relation to SortingBar
	 * @param yPos y-position in relation to SortingBar
	 * @return true if inside tab, false if not
	 */
	public boolean contains(int xPos, int yPos) {
		if ((xPos >= this.xPos && xPos <= this.xPos + width) && (yPos >= this.yPos && yPos <= this.yPos + height)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This is used to check if the mouse cursor is within a small range from the tab right edge
	 * @param xPos x-position in relation to SortingBar
	 * @param yPos y-position in relation to SortingBar
	 * @return true if within small range, false if not
	 */
	// TODO: maybe one method??
	public boolean isAtRightEdge(int xPos, int yPos) {
		if (contains(xPos, yPos) && (this.xPos + width) - xPos <= 5) {
			return true;
		} else {
			return false;
		}
	}

	/**
	* This is used to check if the mouse cursor is within a small range from the tab's left edge
	 * @param xPos x-position in relation to SortingBar
	 * @param yPos y-position in relation to SortingBar
	 * @return true if within small range, false if not
	 */

	public boolean isAtLeftEdge(int xPos, int yPos) {
		if (contains(xPos, yPos) && xPos - this.xPos <= 5) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Called to check if tab is in minimumWidth state
	 * @return true if in minimumWidth state, false if not
	 */
	public boolean isMinimumWidth() {
		return isMinimumWidth;
	}
	
	/**
	 * Called to increase(/decrease) the width of the tab
	 * @param increasedWidth increased(/decreased) width (in pixels)
	 */
	public void resizeWidth(int increasedWidth) {
		setWidth(getWidth() + increasedWidth);
	}
	
	/**
	 * Called to increase(/decrease) the width of the tab, but with a fixed right edge
	 * @param increasedWidth increased(/decreased) width (in pixels)
	 */
	public void resizeAnchoredRight(int increasedWidth) {
		setWidth(getWidth() + increasedWidth);
		moveXPos(getXPos() - increasedWidth);
	}
	
	
	/**
	 * This will handle all custom painting behavior for this component
	 * @param comp Not used in current version
	 * @param g 
	 * @param xPos x-position to draw the tab on (in pixels)
	 * @param yPos y-position to draw the tab on (in pixels)
	 */
	public void paintTab (Component comp, Graphics g, int xPos, int yPos) {
		g.setClip(xPos, yPos, getWidth() + 1, getHeight() + 1); //Sets the clip so we only have to repaint a certain area
		
		if (inFocus) {
			g.setColor(inFocusColor);
		} else {
			g.setColor(outOfFocusColor);
		}
		
		g.fillRect(xPos, yPos, getWidth(), getHeight()); //Draw background
		g.setColor(Color.BLACK); //Color to draw text, will be implemented by setting color through constructor/other method instead
		g.draw3DRect(xPos, yPos, getWidth(), getHeight(), true); //Draw the border
		g.drawString(getName(), xPos + 2, yPos + (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2)); //Draw the name of the tab on the middle left side of the tab, in future set font in constructor/other method
		

	}
	
}
