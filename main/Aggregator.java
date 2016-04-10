package main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Entity;
import map.Map;
import map.MapGenerator;

public class Aggregator {

	private ArrayList<Entity> entities;
	private HashMap<Rectangle, Entity> positions;
	private Map map;
	
	public Entity selectedEntity = null;

	private static Aggregator instance = null;

	private Aggregator() {
		entities = new ArrayList<Entity>();
		positions = new HashMap<Rectangle, Entity>();
		map = MapGenerator.getInstance().generate();
	}

	public static Aggregator getInstance() {
		if (instance == null) {
			instance = new Aggregator();
		}
		return instance;
	}

	public void renderEntities(Graphics2D g) {
		for (Entity e : entities) {
			e.render(g);
		}
	}

	public void renderMap(Graphics2D g) {
		map.render(g);
	}

	public void updateEntities() {
		for (Entity e : entities) {
			e.update();
		}
	}

	public void setEntities(ArrayList<Entity> e) {
		this.entities = e;
	}

	public void addEntities(ArrayList<Entity> e) {
		this.entities.addAll(e);
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public Entity getEntityByPosition(Point p) {
		for (Rectangle r : positions.keySet()) {
			if (r.contains(p)) {
				return positions.get(r);
			}
		}
		return null;
	}

	/* TODO: improve performance */
	public void put(Point p, Entity e) {
		int xOffset = 15;
		int yOffset = 15;

		Rectangle r = new Rectangle((int) (p.getX() - xOffset), (int) (p.getY() - yOffset), 2 * xOffset, 2 * yOffset);

		positions.put(r, e);
	}

}
