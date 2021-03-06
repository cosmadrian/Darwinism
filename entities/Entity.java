package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Aggregator;
import screen.Screen;

public abstract class Entity {
	public static enum Type {
		FOOD, MALE, FEMALE
	};

	public static final int BOX_SIZE = 20;

	protected double x, y;
	public int id;
	protected Rectangle box;

	protected ArrayList<Entity> cache = null;
	private static int count = 0;

	public Entity() {
		this.id = count++;
		this.box = new Rectangle(0, 0, BOX_SIZE, BOX_SIZE);
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

	public void setY(double y) {
		this.y = y;
	}

	public Entity withX(double x) {
		setX(x);
		return this;
	}

	public Entity withY(double y) {
		setY(y);
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

	public void renderIcon(Graphics g, BufferedImage img, int SIZE) {
		Screen s = Aggregator.getInstance().getScreen();

		int xOffset = s.getX();
		int yOffset = s.getY();

		if (s.contains(new Point((int) (x - SIZE / 2), (int) (y - SIZE / 2)))) {
			g.drawImage(img, (int) (x - SIZE / 2 - xOffset), (int) (y - SIZE / 2 - yOffset), img.getWidth(),
					img.getHeight(), null);
		}
	}

	public void update() {
		cache = null;
		box.setBounds((int) (x - BOX_SIZE / 2), (int) (y - BOX_SIZE / 2), BOX_SIZE, BOX_SIZE);
	}

	public void die() {
		Aggregator.getInstance()
				.addEntity(EntityBuilder.getInstance().make(Entity.Type.FOOD, new Point((int) x, (int) y), null));

	}

	public Rectangle getBox() {
		return this.box;
	}

}
