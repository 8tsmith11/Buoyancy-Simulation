package main;

import processing.core.PApplet;

public class Box {
	private PApplet parent;
	private float width, height, volume;
	private float density, mass;
	private float x, y;
	private float vy;
	private float[] forces;
	private float baseArea;
	
	private static float DRAGCOEFFICIENT = 1.05f;
	
	public Box(PApplet parent, float x, float y, float width, float height, float density) {
		this.x = x;
		this.y = y;
		this.parent = parent;
		this.width = width;
		this.height = height;
		volume = width * width * height;
		this.density = density;
		mass = volume * density;
		vy = 0;
		forces = new float[3];
		forces[0] = Simulation.GRAVITY * mass;
		baseArea = width * width;
		
		System.out.println("New Box Created");
		System.out.println("Volume: " + volume);
		System.out.println("Mass: " + mass);
		System.out.println("Density: " + density);
	}
	
	public void update(int dt) {
		float netForce = 0;
		for (float force : forces)
			netForce += force;
		
		vy += (netForce / mass) * (dt / 1000f);
		
		if (y < 0) {
			vy = 0;
			y = 0;
		}
		
		y += vy * (dt / 1000f);
	}
	
	public void draw() {
		parent.stroke(0);
		parent.fill(255, 0, 0);
		float drawX = x * Simulation.SCALE;
		float drawY = parent.height - ((y + height) * Simulation.SCALE);
		parent.rect(drawX, drawY, width * Simulation.SCALE, height * Simulation.SCALE);
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	public float getVolume() { return volume; }
	
	public void applyBuoyantForce(float force) {
		forces[1] = force;
	}
	
	public void applyDragForce(float density) {
		forces[2] = -vy * Math.abs(vy) * 0.5f * density * baseArea * DRAGCOEFFICIENT;
	}
	
	public void move(float dx, float dy) {
		x += dx;
		y += dy;
		vy = 0;
	}
}
