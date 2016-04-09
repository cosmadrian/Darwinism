package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class MaleIndividual extends Individual {
	
	private Color c = Color.blue;

	public MaleIndividual(DNA d) {
		super(d);
	}

	public void render(Graphics2D g) {
		Color temp = g.getColor();
		g.setColor(c);
		super.render(g);
		g.setColor(temp);
	}

	public void update() {

	}

}
