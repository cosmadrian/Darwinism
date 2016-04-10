package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Food extends Entity {

	public static final int MAX_FOOD = 100;

	private Color color = new Color(77, 193, 85);
	private int quantity;

	public Food(int i) {
		this.quantity = i;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void render(Graphics2D g) {
		Color c = g.getColor();
		g.setColor(color);

		int[] xCoords = { x, x - 7, x + 7 };
		int[] yCoords = { y - 10, y + 2, y + 2 };

		g.fillPolygon(xCoords, yCoords, 3);

		g.setColor(c);
	}

	@Override
	public String toString() {
		return "Food\n\nID: " + this.id + "\n* Quantity: " + quantity;
	}
}
