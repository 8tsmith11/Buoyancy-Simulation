package main;

import processing.core.PApplet;

public class Fluid {
	private PApplet parent;
	private float density;
	private float width, height;
	private float x, y;
	
	public Fluid(PApplet parent, float x, float y, float width, float height, float density) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.density = density;
	}
	
	
	public void draw() {
		parent.fill(20, 100, 180, 100);
		parent.rect(x, y, width, height);
	}
	
	public void applyForce(Box box) {
		float volume;
		
		if (box.getY() >= y) {
			volume = box.getVolume();
		}
		else if (box.getY() + height <= y)
			volume = 0;
		else {
			float portionSubmerged = (box.getY() + box.getHeight() - y) / box.getHeight();
			volume = portionSubmerged * box.getVolume();
		}
		
		box.applyBuoyantForce(-density * volume * Simulation.GRAVITY);
	}
}
