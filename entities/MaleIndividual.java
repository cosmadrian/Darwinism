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

		int radius = 30;
		int xOffset = radius / 2;
		int yOffset = radius / 2;

		g.fillOval(x - xOffset, y - yOffset, radius / 2, radius / 2);

		g.setColor(temp);
	}

	public void update() {

	}

	@Override
	public String toString() {
		String x = "Male Individual\n\n" + super.toString();
		return x;
	}

}
