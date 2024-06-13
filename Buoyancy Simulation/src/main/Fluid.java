package main;

import processing.core.PApplet;

public class Fluid {
	private PApplet parent;
	private float density;
	private float height;
	
	public Fluid(PApplet parent, float height, float density) {
		this.parent = parent;
		this.height = height;
		this.density = density;
	}
	
	public void applyForce(Box box) {
		float volume;
		
		if (box.getY() + box.getHeight() <= height) {
			volume = box.getVolume();
		}
		else if (box.getY() >= height)
			volume = 0;
		else {
			float portionSubmerged = (height - box.getY()) / box.getHeight();
			volume = portionSubmerged * box.getVolume();
		}
		
		box.applyBuoyantForce(density * volume * -Simulation.GRAVITY);
	}
	
	public void draw() {
		parent.noStroke();
		parent.fill(20, 100, 230, 150);
		parent.rect(0, parent.height - (height * Simulation.SCALE), parent.width, parent.height);
	}
}
