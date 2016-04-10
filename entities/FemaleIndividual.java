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
		int radius = 25;
		int xOffset = radius / 2;
		int yOffset = radius / 2;

		g.fillOval(x - xOffset, y - yOffset, radius / 2, radius / 2);
		g.setColor(temp);
	}

	
	@Override
	public String toString(){
		String x = "Female Individual\n\n" + super.toString();
		return x;
	}
}
