package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import main.Aggregator;

public abstract class Entity {
	public static enum Type {
		FOOD, MALE, FEMALE
	};

	protected static int COUNT = 0;
	protected int x, y;
	public int id;

	protected int LOS = 70;

	public Entity() {
		COUNT++;
		this.id = COUNT;
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

	public Entity withX(int x) {
		this.x = x;
		return this;
	}

	public Entity withY(int y) {
		this.y = y;
		return this;
	}

	/* super inefficient retrieval */
	private ArrayList<Entity> getNearbyEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();

		for (Entity e : Aggregator.getInstance().getEntities()) {
			int x2 = e.getX();
			int y2 = e.getY();

			if (!e.equals(this) && Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2)) < LOS) {
				entities.add(e);
			}
		}

		return entities;
	}

	public ArrayList<Food> getNearbyFood() {
		ArrayList<Food> food = new ArrayList<Food>();
		for (Entity e : getNearbyEntities()) {
			if (e instanceof Food)
				food.add((Food) e);
		}

		return food;
	}

	public ArrayList<MaleIndividual> getNearbyMales() {
		ArrayList<MaleIndividual> males = new ArrayList<MaleIndividual>();

		for (Entity e : getNearbyEntities()) {
			if (e instanceof MaleIndividual)
				males.add((MaleIndividual) e);
		}

		return males;
	}

	public ArrayList<FemaleIndividual> getNearbyFemales() {
		ArrayList<FemaleIndividual> females = new ArrayList<FemaleIndividual>();

		for (Entity e : getNearbyEntities()) {
			if (e instanceof FemaleIndividual)
				females.add((FemaleIndividual) e);
		}

		return females;
	}

	public abstract void render(Graphics g);

	public abstract void update();

	public void die() {
		Aggregator.getInstance().kill(this);

		Aggregator.getInstance().addEntity(EntityBuilder.getInstance().make(Entity.Type.FOOD, new Point(x, y)));
	}

	/* resets all the stats for this entity */
	public void reset() {

	}
}
