package view.main_window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * This class is used to represent a tab used by the class SortingBar.
 * 
 * @author Magnus Larsson
 * @version 1.0
 */

public class SortingTab {
	
	private String name;
	
	private int width;
	private int height;
	private int xPos;
	private int yPos;
	private int minimumWidth;
	
	private Color inFocusColor;
	private Color outOfFocusColor;
	
	private boolean inFocus;
	private boolean fixedLeftEdge;
	private boolean fixedRightEdge;
	private boolean isMinimumWidth;
	
	
	//Add checkers for values
	public SortingTab(String name, int width, int height, int xPos, int yPos, boolean inFocus) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.inFocus = inFocus;
		
		minimumWidth = 20;
		fixedLeftEdge = false;
		fixedRightEdge = false;
		
		if (width == minimumWidth) {
			isMinimumWidth = true;
		} {
			isMinimumWidth = false;
		}
		
		inFocusColor = Color.WHITE;
		outOfFocusColor = Color.LIGHT_GRAY;
	}
	
	//Add checkers for values
	public SortingTab(String name, int width, int height, int xPos, int yPos, boolean inFocus, int minimumWidth, boolean fixedLeftEdge, boolean fixedRightEdge) {
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
		} {
			isMinimumWidth = false;
		}
		
		inFocusColor = Color.WHITE;
		outOfFocusColor = Color.LIGHT_GRAY;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
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
	
	public int getYPos() {
		return yPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		/*if (width >= minimumWidth) {
			this.width = width;
		} else {
			this.width = minimumWidth;
			isMinimumWidth = true;
		}*/
		
		if (width >= minimumWidth) {
			this.width = width;
		} else {
			this.width = width;
			isMinimumWidth = true;
		}
	}
	
	public void setMinimumWidth(int minimumWidth) {
		this.minimumWidth = minimumWidth;
		if (minimumWidth >= getWidth()) {
			setWidth(minimumWidth);
			isMinimumWidth = true;
		}
	}
	
	public int getMinimumWidth() {
		return minimumWidth;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setFocus(boolean inFocus) {
		this.inFocus = inFocus;
	}
	
	public void setOutOfFocusColor(Color color) {
		this.outOfFocusColor = color;
	}
	
	public Color getOutOfFocusColor() {
		return outOfFocusColor;
	}
	
	public void setInFocusColor(Color color) {
		this.inFocusColor = color;
	}
	
	public Color getInFocusColor() {
		return inFocusColor;
	}
	
	public void setFixedLeftEdge(boolean fixedLeftEdge) {
		this.fixedLeftEdge = fixedLeftEdge;
	}
	
	public boolean hasFixedLeftEdge() {
		return fixedLeftEdge;
	}
	
	public void setFixedRightEdge(boolean fixedLeftEdge) {
		this.fixedLeftEdge = fixedLeftEdge;
	}
	
	public boolean hasFixedRightEdge() {
		return fixedRightEdge;
	}
	
	/**
	 * This is used to check if one point is within the tab or not
	 * @param xPos
	 * @param yPos
	 * @return
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
	 * @param xPos
	 * @param yPos
	 * @return
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
	 * This is used to check if the mouse cursor is within a small range from the tab left edge
	 * @param xPos
	 * @param yPos
	 * @return
	 */

	public boolean isAtLeftEdge(int xPos, int yPos) {
		if (contains(xPos, yPos) && xPos - this.xPos <= 5) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMinimumWidth() {
		return isMinimumWidth;
	}
	
	public void resizeWidth(int increasedWidth) {
		//if (!fixedRightEdge) {
			setWidth(getWidth() + increasedWidth);
		//}
	}
	
	public void resizeAnchoredRight(int increasedWidth) {
		setWidth(getWidth() + increasedWidth);
		moveXPos(getXPos() - increasedWidth);
	}
	
	
	/**
	 * This will handle all custom painting behaviour for this component
	 * @param comp
	 * @param g
	 * @param xPos
	 * @param yPos
	 */
	public void paintTab (Component comp, Graphics g, int xPos, int yPos) {
		g.setClip(getXPos(), getYPos(), getWidth() + 1, getHeight() + 1);
		
		if (inFocus) {
			g.setColor(inFocusColor);
		} else {
			g.setColor(outOfFocusColor);
		}
		
		g.fillRect(getXPos(), getYPos(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.draw3DRect(getXPos(), getYPos(), getWidth(), getHeight(), true);
		g.drawString(getName(), xPos + 2, yPos + (getHeight() / 2) + (g.getFontMetrics().getHeight() / 2));
		

	}
	
}
