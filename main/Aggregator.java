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
import map.Map;
import map.MapGenerator;

public class Aggregator {

	private static Aggregator instance = null;
	private ArrayList<Entity> entities;
	private Map map;

	public Entity selectedEntity = null;

	private Aggregator() {
		entities = new ArrayList<Entity>();
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
			if (e.equals(selectedEntity)) {
				g.setColor(Color.BLACK);
				g.drawRect(e.getX() - 15, e.getY() - 15, 15, 15);
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
			((Entity) temp[i]).update();
		}

	}

	public void updateMap() {
		map.update();
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
		int xOffset = 20, yOffset = 20;
		Rectangle r = new Rectangle((int) p.getX(), (int) p.getY(), xOffset, yOffset);
		for (Entity e : entities) {
			if (r.contains(e.getX(), e.getY()))
				return e;
		}
		return null;
	}

	public void kill(Entity e) {
		e.die();
		killNoDie(e);
	}

	public void killNoDie(Entity e) {

		if (e instanceof Food) {
			EntityBuilder.getInstance().foodCount--;
		} else if (e instanceof Individual) {
			EntityBuilder.getInstance().individualCount--;
		}

		entities.remove(e);

		if (selectedEntity != null && selectedEntity.equals(e))
			selectedEntity = null;
	}

}
