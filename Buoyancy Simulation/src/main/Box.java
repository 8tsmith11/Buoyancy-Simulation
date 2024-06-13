package main;

import processing.core.PApplet;

public class Box {
	private PApplet parent;
	private float width, height, volume;
	private float density, mass;
	private float y;
	private float vy;
	private float[] forces;
	
	public Box(PApplet parent, float y, float width, float height, float density) {
		this.y = y;
		this.parent = parent;
		this.width = width;
		this.height = height;
		volume = width * width * height;
		this.density = density;
		mass = volume * density;
		vy = 0;
		forces = new float[2];
		forces[0] = Simulation.GRAVITY * mass;
		
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
		float drawX = parent.width / 2 - (width * Simulation.SCALE) / 2;
		float drawY = parent.height - ((y + height) * Simulation.SCALE);
		parent.rect(drawX, drawY, width * Simulation.SCALE, height * Simulation.SCALE);
	}
	
	public float getY() { return y; }
	public float getHeight() { return height; }
	public float getVolume() { return volume; }
	
	public void applyBuoyantForce(float force) {
		forces[1] = force;
	}
}
