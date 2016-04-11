package main;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Entity;
import map.Map;
import map.MapGenerator;
import objectPool.ObjectPool;

public class Aggregator {

	private static Aggregator instance = null;
	private ArrayList<Entity> entities;
	private HashMap<Rectangle, Entity> positions;
	private Map map;

	public Entity selectedEntity = null;

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

	public void renderEntities(Graphics g) {
		for (Entity e : entities) {
			e.render(g);
		}
	}

	public void renderMap(Graphics g) {
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

	public void addEntity(Entity e) {
		this.entities.add(e);
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
	
	public HashMap<Rectangle,Entity> getPositions(){
		return this.positions;
	}

	public void put(Point p, Entity e) {
		int xOffset = 15;
		int yOffset = 15;

		Rectangle r = new Rectangle((int) (p.getX() - xOffset), (int) (p.getY() - yOffset), xOffset, yOffset);

		positions.put(r, e);
	}

	public void kill(Entity e) {
		entities.remove(e);
		Point p = new Point(e.getX(), e.getY());
		for (Rectangle r : positions.keySet()) {
			if (r.contains(p) && positions.get(r).equals(e)) {
				positions.remove(r);
				break;
			}
		}
		if (selectedEntity.equals(e))
			selectedEntity = null;

		ObjectPool.getInstance().bury(e);
	}

}
