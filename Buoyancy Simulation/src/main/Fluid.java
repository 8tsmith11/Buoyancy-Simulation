package main;

import processing.core.PApplet;

public class Fluid {
	private PApplet parent;
	private float density;
	private float height;
	private int color;
	
	public Fluid(PApplet parent, int color, float height, float density) {
		this.color = color;
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
		parent.fill(color);
		parent.rect(0, parent.height - (height * Simulation.SCALE), parent.width, parent.height);
	}
	
	public void setDepth(float depth) {
		height = depth;
	}
	
	public void setDensity(float density) {
		this.density = density;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public float getDensity() { return density; }
}
