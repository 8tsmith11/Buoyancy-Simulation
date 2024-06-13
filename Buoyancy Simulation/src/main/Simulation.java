package main;

import processing.core.PApplet;

public class Simulation extends PApplet {
	public static float GRAVITY = -9.80665f;
	
	// pixels per meter
	public static float SCALE = 150;
	
	private Box box;
	private Fluid fluid;
	private Axes axes;
	private int lastMillis;

	public static void main(String[] args) {
		PApplet.main("main.Simulation");
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void setup() {
		box = new Box(this, 8, 1f, 2f, 400f);
		fluid = new Fluid(this, 3, 997);
		axes = new Axes(this, 1);
	}
	
	public void draw() {
		background(0);
		axes.draw();
		box.update((millis() - lastMillis));
		fluid.applyForce(box);
		box.draw();
		fluid.draw();
		lastMillis = millis();
	}
}
