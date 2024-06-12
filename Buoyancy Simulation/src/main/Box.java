package main;

import processing.core.PApplet;

public class Box {
	private PApplet parent;
	private float width, height, volume;
	private float density, mass;
	private float x, y;
	private float vy;
	private float[] forces;
	
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
		forces = new float[2];
		forces[0] = Simulation.GRAVITY * mass;
	}
	
	public void update(int dt) {
		float netForce = 0;
		for (float force : forces)
			netForce += force;
		
		vy += (netForce / mass) * (dt / 1000f);
		
		if (y + height >= parent.height) {
			vy = 0;
			y = parent.height - height;
		}
		y += vy;
		
		draw();
	}
	
	public void draw() {
		parent.fill(255, 0, 0);
		parent.rect(x, y, width, height);
	}
	
	public float getY() { return y; }
	public float getHeight() { return height; }
	public float getVolume() { return volume; }
	
	public void applyBuoyantForce(float force) {
		forces[1] = force;
	}
}
