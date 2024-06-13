package main;

import processing.core.PApplet;

public class Axes {
	
	private PApplet parent;
	private float thickness = 3f;
	private float length = 10f;
	private float spacing;
	private float width;
	private float height;
	
	
	// spacing is in meters
	public Axes(PApplet parent, float spacing) {
		this.parent = parent;
		this.spacing = spacing;
		width = parent.width / Simulation.SCALE;
		height = parent.width / Simulation.SCALE;
	}
	
	public void draw() {
		parent.noStroke();
		parent.fill(230);
		parent.textAlign(parent.CENTER);
		drawX();
		drawY();
	}
	
	private void drawX() {
		for(int i = 0; i < width / spacing; i++) {
			float x = i * spacing * Simulation.SCALE;
			parent.rect(x, parent.height - length, thickness, length);
			if (i > 0)
				parent.text((double)i * spacing + " m", x, parent.height - length * 1.5f);
		}
	}
	
	private void drawY() {
		for(int i = 0; i < height / spacing; i++) {
			float y = parent.height - i * spacing * Simulation.SCALE - thickness;
			parent.rect(0, y, length, thickness);
			if (i > 0)
				parent.text((double)i * spacing + " m", length * 3f, y);
		}
	}
}
