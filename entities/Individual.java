package entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entities.states.State;

public abstract class Individual extends Entity {

	private DNA dna;
	private ArrayList<State> states = new ArrayList<State>();

	public Individual(DNA d) {
		this.dna = d;
	}


	public void render(Graphics2D g) {
		int radius = 30;
		int xOffset = radius / 2;
		int yOffset = radius / 2;

		g.fillOval(x - xOffset, y - yOffset, radius / 2, radius / 2);
	}

}
