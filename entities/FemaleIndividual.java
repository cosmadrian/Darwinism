package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class FemaleIndividual extends Individual {

	private Color c = Color.RED;

	public FemaleIndividual(DNA d) {
		super(d);
	}

	public void render(Graphics2D g) {
		Color temp = g.getColor();
		g.setColor(c);
		super.render(g);
		g.setColor(temp);
	}

}
