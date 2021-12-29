package models;

public class Player {
	
	private String nick;
	private double x;
	private double y;
	private double degrees;
	private boolean inversed;
	
	public Player() {
	}
	
	public Player(String nick) {
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String getNick() {
		return nick;
	}
	
	public double getDegrees() {
		return degrees;
	}
	
	public boolean isInversed() {
		return inversed;
	}
}