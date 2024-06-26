package main;

import processing.core.PApplet;
import processing.core.PConstants;

public class Button {

	private PApplet parent;
	private float x, y, size;
	private String name;
	private String shape;
	private boolean pressed;
	
	public Button(PApplet parent, float x, float y, float size, String name, String shape, boolean pressed) {
		this.parent = parent;
		this.size = size;
		this.name = name;
		this.shape = shape;
		if(shape.equals("Circle")) {
			this.x = x+size/2f;
			this.y = y+size/2f;
		}else {
			this.x = x;
			this.y = y;
		}
		
		this.pressed = pressed;
	}
	
	public boolean getValue() {return pressed;}
	
	public void setValue(boolean pressed) {this.pressed = pressed;}
	
	public void mouseClicked() {
		if(shape.equals("Square")) {
			if(parent.mouseX > x && parent.mouseX < x + size && parent.mouseY > y && parent.mouseY < y + size) {
				if(!pressed) {
					pressed = true;
				}
			}
		}
		else if(shape.equals("Circle")) {
				if(Math.sqrt(Math.pow((parent.mouseX - x), 2) + Math.pow((parent.mouseY - y), 2)) < size/2) {
					if(!pressed) {
						pressed = true;
					}
				}
		}
	}
	
	public void draw() {
		if(shape.equals("Square"))
			drawSquare();
		else if(shape.equals("Circle"))
			drawCircle();
		
	}
	
	public void drawSquare() {
		parent.rect(x, y, size, size);
		if(pressed) {
			parent.fill(0);
			parent.rect(x+5, y+5, size-10, size-10);
			parent.fill(255);
		}
		parent.text(name, x+size/2, y-10);
	}
	
	public void drawCircle() {
		parent.fill(255);
		parent.ellipse(x, y, size, size);
		if(pressed) {
			parent.fill(0);
			parent.ellipse(x, y, size-30, size-30);
		}
		parent.fill(220);
		parent.text(name, x, (y-size/2) - 8);
	}
	
	public void clearClick() {
		pressed = false;
	}
}
