package entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import frontend.MainFrame;
import frontend.StatPanel;
import main.Aggregator;

public class EntityBuilder {
	private static EntityBuilder instance = null;

	public static EntityBuilder getInstance() {
		if (instance == null) {
			instance = new EntityBuilder();
		}
		return instance;
	}

	public Entity make(Entity.Type t, Point p) {
		Entity e = null;
		Random r = new Random();
		switch (t) {
		case MALE:
			e = new MaleIndividual(new DNA(DNA.generateStrand()));
			break;
		case FEMALE:
			e = new FemaleIndividual(new DNA(DNA.generateStrand()));
			break;
		case FOOD:
			e = new Food(r.nextInt(Food.MAX_FOOD / 2) + Food.MAX_FOOD / 2);
			break;
		}

		if (p == null) {
			e.setX(r.nextInt(MainFrame.WIDTH - StatPanel.WIDTH));
			e.setY(r.nextInt(MainFrame.HEIGHT));
		}else{
			e.setX((int)p.getX());
			e.setY((int)p.getY());
		}

		Aggregator.getInstance().addEntity(e);
		Aggregator.getInstance().put(new Point(e.getX(), e.getY()), e);

		return e;
	}

	public ArrayList<Entity> populate(int count) {
		Random r = new Random();
		ArrayList<Entity> a = new ArrayList<Entity>();

		for (int i = 0; i < count; i++) {
			double chance = r.nextDouble();
			if (chance > 0.5)
				a.add(make(Entity.Type.MALE, null));
			else
				a.add(make(Entity.Type.FEMALE, null));
		}

		return a;
	}

	public ArrayList<Entity> cookFood(int count) {
		ArrayList<Entity> a = new ArrayList<Entity>();

		for (int i = 0; i < count; i++) {
			a.add(make(Entity.Type.FOOD, null));
		}

		return a;
	}
}
