package entities;

import java.awt.Graphics2D;

public class Entity {
	public static enum Type {
		FOOD, MALE, FEMALE
	};

	protected static int COUNT = 0;
	protected int x, y;
	public int id;

	public Entity() {
		COUNT++;
		this.id = COUNT;
	}

	public String toString() {
		return "Entity";
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Entity withX(int x){
		this.x = x;
		return this;
	}
	
	public Entity withY(int y){
		this.y = y;
		return this;
	}

	public void render(Graphics2D g) {
	}

	public void update() {
	}
}
