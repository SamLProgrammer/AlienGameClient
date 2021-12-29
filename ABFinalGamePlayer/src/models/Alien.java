package models;

public class Alien{
	
	private boolean alive;
	private double x;
	private double y;
	private double radius;
	private boolean falling;
	
	public boolean isFalling() {
		return falling;
	}

	public Alien(double width, double height) {
		
	}

	public double getX() { 
		return x;
	}

	public double getY() {
		return y;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public double getSize() {
		return radius*2;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
}