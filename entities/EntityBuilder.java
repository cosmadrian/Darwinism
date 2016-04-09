package entities;

import java.util.ArrayList;
import java.util.Random;

import frontend.MainFrame;

public class EntityBuilder {
	private static EntityBuilder instance = null;

	public static EntityBuilder getInstance() {
		if (instance == null) {
			instance = new EntityBuilder();
		}
		return instance;
	}

	/* TODO: make clusters for food */
	public Entity make(Entity.Type t) {
		Entity e = null;
		Random r = new Random();
		switch (t) {
		case MALE:
			e = new MaleIndividual(new DNA(DNA.generateStrand())).withX(r.nextInt(MainFrame.WIDTH))
					.withY(r.nextInt(MainFrame.HEIGHT));
			break;
		case FEMALE:
			e = new FemaleIndividual(new DNA(DNA.generateStrand())).withX(r.nextInt(MainFrame.WIDTH))
					.withY(r.nextInt(MainFrame.HEIGHT));
			break;
		case FOOD:
			e = new Food(new Random().nextInt(Food.MAX_FOOD / 2) + Food.MAX_FOOD / 2).withX(r.nextInt(MainFrame.WIDTH))
					.withY(r.nextInt(MainFrame.HEIGHT));
			break;
		}
		return e;
	}

	public ArrayList<Entity> populate(int count) {
		Random r = new Random();
		ArrayList<Entity> a = new ArrayList<Entity>();

		for (int i = 0; i < count; i++) {
			double chance = r.nextDouble();
			if (chance > 0.5)
				a.add(make(Entity.Type.MALE));
			else
				a.add(make(Entity.Type.FEMALE));
		}

		return a;
	}

	public ArrayList<Entity> cookFood(int count) {
		ArrayList<Entity> a = new ArrayList<Entity>();

		for (int i = 0; i < count; i++) {
			a.add(make(Entity.Type.FOOD));
		}

		return a;
	}
}
