package entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import frontend.MainFrame;
import frontend.StatPanel;
import main.Aggregator;

public class EntityBuilder {

	private static EntityBuilder instance = null;
	public int foodCount = 0;
	public int individualCount = 0;

	public static EntityBuilder getInstance() {
		if (instance == null) {
			instance = new EntityBuilder();
		}
		return instance;
	}

	public Entity make(Entity.Type t, Point p, DNA d) {
		Entity e = null;
		Random r = new Random();

		if (d == null) {
			d = new DNA(DNA.generateStrand(t));
		}

		switch (t) {
		case MALE:
			e = new MaleIndividual(d);
			this.individualCount++;
			break;
		case FEMALE:
			e = new FemaleIndividual(d);
			this.individualCount++;
			break;
		case FOOD:
			e = new Food(r.nextInt(Food.MAX_FOOD / 2) + Food.MAX_FOOD / 2);
			this.foodCount++;
			break;
		}

		if (p == null) {
			e.setX(r.nextInt(MainFrame.WIDTH - StatPanel.WIDTH));
			e.setY(r.nextInt(MainFrame.HEIGHT));

		} else {
			e.setY((int) p.getY());
			e.setX((int) p.getX());
		}

		Aggregator.getInstance().addEntity(e);

		return e;
	}

	public ArrayList<Entity> populate(int count) {
		Random r = new Random();
		ArrayList<Entity> a = new ArrayList<Entity>();

		for (int i = 0; i < count; i++) {
			double chance = r.nextDouble();
			if (chance > 0.5)
				a.add(make(Entity.Type.MALE, null, null));
			else
				a.add(make(Entity.Type.FEMALE, null, null));
		}

		return a;
	}

	public ArrayList<Entity> cookFood(int count) {
		ArrayList<Entity> a = new ArrayList<Entity>();

		for (int i = 0; i < count; i++) {
			a.add(make(Entity.Type.FOOD, null, null));
		}

		return a;
	}
}
