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
	
	public void applyForces(Box box) {
		float volume;
		
		if (box.getY() >= height || height == 0) {
			volume = 0;
			box.applyDragForce(0);
		}
		
		else if (box.getY() + box.getHeight() <= height) {
			volume = box.getVolume();
			box.applyDragForce(density);
		}
		else {
			float portionSubmerged = (height - box.getY()) / box.getHeight();
			volume = portionSubmerged * box.getVolume();
			box.applyDragForce(density);
		}
		
		box.applyBuoyantForce(density * volume * -Simulation.GRAVITY);
	}
	
	public void draw() {
		parent.noStroke();
		parent.fill(20, 100, 230, 150);
		parent.rect(0, parent.height - (height * Simulation.SCALE), parent.width, parent.height);
	}
	
	public void setDepth(float depth) {
		height = depth;
	}
}
