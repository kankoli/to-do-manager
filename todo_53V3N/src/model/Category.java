package model;

import java.awt.Color;

/**
 * This class represents category, in our idea each task row 
 * will be displayed in some color according to category.
 * @author Marco Dondio
 *
 */
public final class Category {

	private String name;
	private Color color;		// XXX note: encoding/decoding of Color into integer seems slow
	
	public Category(String name, Color color){
		this.name = name;
		this.color = color;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final Color getColor() {
		return color;
	}

	public final void setColor(Color color) {
		this.color = color;
	}
	
	public final String toString(){
		return name;
	}
}
