package entities;

public class Entity {
	public static enum Type{
		FOOD, MALE, FEMALE
	};
	
	private int x, y;
	protected static int COUNT = 0;
	public int id;
	
	public Entity(){
		this.COUNT ++;
		this.id = COUNT;
	}
	
	public String toString(){
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
}
