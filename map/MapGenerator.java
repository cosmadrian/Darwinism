package map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import entities.Entity;
import entities.EntityBuilder;

public class MapGenerator {

	/* TODO: make enum */
	private static final int STEP_DEFAULT = 75;
	private int step;
	private HeightGenerator generator;

	public MapGenerator(HeightGenerator g) {
		this.generator = g;
		this.step = STEP_DEFAULT;
	}

	public Map generate(ArrayList<Entity> entities) {
		Random r = new Random();
		Map m = new Map();

		for (int i = 0; i < m.getHeight(); i += step) {
			for (int j = 0; j < m.getWidth(); j += step) {
				double val = generator.getHeight(j, i);
				double chance = r.nextDouble();
				if (chance < val) {
					int x = j, y = i;
					int rX = x + r.nextInt(step);
					int rY = y + r.nextInt(step);

					entities.add(
							EntityBuilder.getInstance().make(Entity.Type.FOOD, new Point((int) rX, (int) rY), null));
				}
			}
		}

		return m;
	}
	
	public void setStep(int step){
		this.step = step;
	}

}
