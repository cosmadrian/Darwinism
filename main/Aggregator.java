package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entities.Entity;
import map.Map;
import map.MapGenerator;

public class Aggregator {

	private ArrayList<Entity> entities;
	private Map map;

	private static Aggregator instance = null;

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

}
