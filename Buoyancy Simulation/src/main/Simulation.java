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
	private boolean dragging;

	public static void main(String[] args) {
		PApplet.main("main.Simulation");
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void setup() {
		box = new Box(this, width / 2 / SCALE - 0.5f, 8, 1f, 1f, 900f);
		fluid = new Fluid(this, 3, 997);
		axes = new Axes(this, 1);
		dragging = false;
	}
	
	public void draw() {
		if (dragging) {
			dragBox();
		}
		else {
			box.update((millis() - lastMillis));
		}

		background(0);
		axes.draw();
		fluid.applyForces(box);
		box.draw();
		fluid.draw();
		lastMillis = millis();
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
}
