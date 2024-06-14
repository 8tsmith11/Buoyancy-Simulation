package main;

import processing.core.PApplet;
import processing.core.PImage;

public class Simulation extends PApplet {
	public static float GRAVITY = -9.80665f;
	
	// pixels per meter
	public static float SCALE = 150;
	
	private Box box;
	private Fluid fluid;
	private Axes axes;
	private int lastMillis;
	private boolean dragging;
	
	private Slider widthSlider;
	private Slider heightSlider;
	private Slider depthSlider;

	public static void main(String[] args) {
		PApplet.main("main.Simulation");
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void setup() {
		box = new Box(this, getSolidImage("Cedar"), width / 2 / SCALE - 0.5f, 8, 1f, 1f, 900f);
		fluid = new Fluid(this, 3, 997);
		axes = new Axes(this, 1);
		dragging = false;
		
		widthSlider = new Slider(this, width / 12, height * 0.08f, width / 10, height / 20, "Box Width (m)", 0.1f, width / SCALE / 2);
		heightSlider = new Slider(this, width / 12, height * 0.16f, width / 10, height / 20, "Box Height (m)", 0.1f, height / SCALE / 2);
		depthSlider = new Slider(this, width / 12, height * 0.24f, width / 10, height / 20, "Fluid Depth (m)", 0, height / SCALE);
		widthSlider.useDecimals(true);
		heightSlider.useDecimals(true);
		depthSlider.useDecimals(true);
		widthSlider.setValue(1);
		heightSlider.setValue(1);
		depthSlider.setValue(3);
	}
	
	public void draw() {
		int millis = millis();
		if (dragging) {
			dragBox();
		}
		else {
			box.update((millis - lastMillis));
		}

		background(0);
		axes.draw();
		fluid.applyForces(box);
		box.draw();
		fluid.draw();
		handleUI();
		lastMillis = millis;
	}
	
	private void handleUI() {
		widthSlider.draw();
		heightSlider.draw();
		depthSlider.draw();
		if (mousePressed) {
			box.setDimensions(widthSlider.getValue(), heightSlider.getValue());
			fluid.setDepth(depthSlider.getValue());
		}
		
		
	}
	
	public void dragBox() {
		float dx = (mouseX - pmouseX) / SCALE;
		float dy = (pmouseY - mouseY) / SCALE;
		box.move(dx, dy);
	}
	
	public void mousePressed() {
		if (mouseX >= box.getX() * SCALE && mouseX <= (box.getX() + box.getWidth()) * SCALE
				&& mouseY >= height - ((box.getY() + box.getHeight()) * SCALE) && mouseY <= height - ((box.getY()) * SCALE))
			dragging = true;
	}
	
	public void mouseReleased() {
		dragging = false;
	}
	
	public float getFluidColor(String fluid) {
		return color(2);
	}
	
	public float getDensity(String material) {
		return 1000;
	}
	
	public PImage getSolidImage(String solid) {
		if (solid.equals("Cedar"))
			return loadImage("images/cedar.jpg");
		
		return null;
	}
}
