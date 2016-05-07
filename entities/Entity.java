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
	protected double x, y;
	public int id;

	protected ArrayList<Entity> cache = null;

	public Entity() {
		COUNT++;
		this.id = COUNT;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
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

	public ArrayList<Individual> getNearbyIndividuals(int radius) {
		if (cache == null)
			getNearbyEntities(radius);

		ArrayList<Individual> individuals = new ArrayList<Individual>();
		for (Entity e : cache) {
			if (e instanceof Individual) {
				individuals.add((Individual) e);
			}
		}

		return individuals;
	}

	private void getNearbyEntities(int radius) {
		ArrayList<Entity> entities = new ArrayList<Entity>();

		for (Entity e : Aggregator.getInstance().getEntities()) {
			int x2 = e.getX();
			int y2 = e.getY();

			if (!e.equals(this) && Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2)) < radius) {
				entities.add(e);
			}
		}

		this.cache = entities;
	}

	public ArrayList<Food> getNearbyFood(int radius) {
		if (cache == null)
			getNearbyEntities(radius);

		ArrayList<Food> food = new ArrayList<Food>();
		for (Entity e : cache) {
			if (e instanceof Food)
				food.add((Food) e);
		}

		return food;
	}

	public ArrayList<MaleIndividual> getNearbyMales(int radius) {
		if (cache == null)
			getNearbyEntities(radius);

		ArrayList<MaleIndividual> males = new ArrayList<MaleIndividual>();

		for (Entity e : cache) {
			if (e instanceof MaleIndividual)
				males.add((MaleIndividual) e);
		}

		return males;
	}

	public ArrayList<FemaleIndividual> getNearbyFemales(int radius) {
		if (cache == null)
			getNearbyEntities(radius);

		ArrayList<FemaleIndividual> females = new ArrayList<FemaleIndividual>();

		for (Entity e : cache) {
			if (e instanceof FemaleIndividual)
				females.add((FemaleIndividual) e);
		}

		return females;
	}

	public abstract void render(Graphics g);

	public void update() {
		this.cache = null;
	}

	public void die() {
		EntityBuilder.getInstance().make(Entity.Type.FOOD, new Point((int) x, (int) y), null);
		Aggregator.getInstance().killNoDie(this);
	}

}
