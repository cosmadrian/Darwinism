package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import entities.Entity;
import entities.EntityBuilder;
import entities.Food;
import entities.Individual;
import map.GaussianHeightGenerator;
import map.Map;
import map.MapGenerator;
import screen.Screen;

public class Aggregator {

	private static Aggregator instance = null;
	private ArrayList<Entity> entities;
	private Map map;
	private Screen screen;

	public Entity selectedEntity = null;

	private Aggregator() {
		entities = new ArrayList<Entity>();
		map = new MapGenerator(new GaussianHeightGenerator()).generate(entities);
		screen = new Screen(map.getWidth(), map.getHeight());

		map.setScreen(screen);
	}

	public static Aggregator getInstance() {
		if (instance == null) {
			instance = new Aggregator();
		}
		return instance;
	}

	public void renderEntities(Graphics g) {
		for (Entity e : entities) {
			if (e.equals(selectedEntity)) {
				g.setColor(Color.BLACK);
				g.drawRect(e.getX() - 7 - screen.getX(), e.getY() - 7 - screen.getY(), 15, 15);
			}
			e.render(g);
		}
	}

	public void renderMap(Graphics g) {
		map.render(g);
	}

	public void updateEntities() {

		Object temp[] = entities.toArray();
		int n = entities.size();

		for (int i = 0; i < n; i++) {
			Entity workingEntity = (Entity) temp[i];

			workingEntity.update();

		}
	}

	public void updateMap() {
		map.update();
	}

	public void addAllEntity(ArrayList<Entity> e) {
		this.entities.addAll(e);
	}

	public void addEntity(Entity e) {
		this.entities.add(e);
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public Entity getEntityByPosition(Point p) {

		int xOffset = 20, yOffset = 20;
		Rectangle r = new Rectangle((int) (p.getX() - xOffset / 2), (int) (p.getY() - yOffset / 2), xOffset, yOffset);
		for (Entity e : entities) {
			if (r.contains(e.getX() - screen.getX(), e.getY() - screen.getY()))
				return e;
		}
		return null;
	}

	public void kill(Entity e) {
		e.die();
		if (e instanceof Food) {
			EntityBuilder.getInstance().foodCount--;
		} else if (e instanceof Individual) {
			EntityBuilder.getInstance().individualCount--;
		}

		entities.remove(e);

		if (selectedEntity != null && selectedEntity.equals(e))
			selectedEntity = null;
		e = null;
	}

	public Screen getScreen() {
		return screen;
	}

	public Map getMap() {
		return map;
	}

}
