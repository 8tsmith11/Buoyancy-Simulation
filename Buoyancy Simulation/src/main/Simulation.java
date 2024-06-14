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
	
	private Button waterButton;
	private Button honeyButton;
	private Button oilButton;
	private Button mercuryButton;
	
	private Button cedarButton;
	private Button steelButton;
	private Button iceButton;
	private Button rubberButton;

	public static void main(String[] args) {
		PApplet.main("main.Simulation");
	}
	
	public void settings() {
		fullScreen();
	}
	
	public void setup() {
		box = new Box(this, getSolidImage("Cedar"), width / 2 / SCALE - 0.5f, 8, 1f, 1f, getDensity("Cedar"));
		fluid = new Fluid(this, getFluidColor("Honey"), 3, getDensity("Honey"));
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
		setupUI();
	}
	
	private void setupUI() {
		waterButton = new Button(this, width * 0.2f, height * 0.1f, width * 0.05f, "Water", "Circle", true);
		honeyButton = new Button(this, width * 0.3f, height * 0.1f, width * 0.05f, "Honey", "Circle", false);
		oilButton = new Button(this, width * 0.4f, height * 0.1f, width * 0.05f, "Oil", "Circle", false);
		mercuryButton = new Button(this, width * 0.5f, height * 0.1f, width * 0.05f, "Mercury", "Circle", false);
		
		cedarButton = new Button(this, width * 0.2f, height * 0.22f, width * 0.05f, "Cedar", "Circle", true);
		steelButton = new Button(this, width * 0.3f, height * 0.22f, width * 0.05f, "Steel", "Circle", false);
		iceButton = new Button(this, width * 0.4f, height * 0.22f, width * 0.05f, "Ice", "Circle", false);
		rubberButton = new Button(this, width * 0.5f, height * 0.22f, width * 0.05f, "Rubber", "Circle", false);
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
		drawData();
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
		
//		waterButton.draw();
//		honeyButton.draw();
//		oilButton.draw();
//		mercuryButton.draw();
//		cedarButton.draw();
//		iceButton.draw();
//		steelButton.draw();
//		rubberButton.draw();
		

	}
	
	private void drawData() {
		fill(220);
		textSize(20);
		textAlign(LEFT, CENTER);
		text("Fluid Density: " + fluid.getDensity() + " kg/m^3", width * 0.8f, 50);
		text("Box Density: " + box.getDensity() + " kg/m^3", width * 0.8f, 70);
		text("Box Mass: " + box.getMass() + " kg", width * 0.8f, 90);
		text("Force of Gravity: " + box.getForces()[0] + " N", width * 0.8f, 110);
		text("Buoyant Force: " + box.getForces()[1] + " N", width * 0.8f, 130);
		text("Drag Force: " + box.getForces()[2] + " N", width * 0.8f, 150);
		text("Velocity: " + box.getVelocity() + " m/s", width * 0.8f, 170);
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
		
		waterButton.mouseClicked();
		oilButton.mouseClicked();
		honeyButton.mouseClicked();
		mercuryButton.mouseClicked();
		iceButton.mouseClicked();
		cedarButton.mouseClicked();
		steelButton.mouseClicked();
		rubberButton.mouseClicked();
		
		if (waterButton.getValue()) {
			honeyButton.clearClick();
			oilButton.clearClick();
			mercuryButton.clearClick();
			changeFluid("Water");
		}
		else if (honeyButton.getValue()) {
			waterButton.clearClick();
			oilButton.clearClick();
			mercuryButton.clearClick();
			changeFluid("Honey");
		}
		else if (oilButton.getValue()) {
			honeyButton.clearClick();
			waterButton.clearClick();
			mercuryButton.clearClick();
			changeFluid("Oil");
		}
		else if (mercuryButton.getValue()) {
			honeyButton.clearClick();
			oilButton.clearClick();
			waterButton.clearClick();
			changeFluid("Mercury");
		}
	}
	
	public void mouseReleased() {
		dragging = false;
		box.setDimensions(widthSlider.getValue(), heightSlider.getValue());
		fluid.setDepth(depthSlider.getValue());
	}
	
	private int getFluidColor(String material) {
		if (material.equals("Water"))
			return color(20, 100, 230, 150);
		else if (material.equals("Oil"))
			return color(20, 20, 20, 255);
		else if (material.equals("Honey"))
			return color(231, 154, 63, 225);
		return color(0);
	}
	
	private float getDensity(String material) {
		if (material.equals("Cedar"))
			return 400;
		else if (material.equals("Water"))
			return 1000;
		else if (material.equals("Oil"))
			return 825;
		else if (material.equals("Steel"))
			return 7500;
		else if (material.equals("Honey"))
			return 1500;
		return 1000;
	}
	
	private PImage getSolidImage(String material) {
		if (material.equals("Cedar"))
			return loadImage("images/cedar.jpg");
		else if (material.equals("Steel"))
			return loadImage("images/steel.png");
		
		return null;
	}
	
	private void changeSolid(String material) {
		box.setImage(getSolidImage(material));
		box.setDensity(getDensity(material));
	}
	
	private void changeFluid(String material) {
		fluid.setDensity(getDensity(material));
		fluid.setColor(getFluidColor(material));
	}
}
