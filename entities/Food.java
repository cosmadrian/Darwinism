package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Aggregator;
import main.Main;

public class Food extends Entity {

	public static final int MAX_FOOD = 100;
	private static final String FOOD_ICON = "src/food.png";

	private static BufferedImage foodIcon;
	private static final int SIZE = 9;
	private int quantity;

	static {
		try {
			foodIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
					ImageIO.read(new File(FOOD_ICON)).getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT), Color.green));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Food(int i) {
		this.quantity = i;

	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void consume(int amount) {
		this.quantity -= amount;
	}

	public void render(Graphics g) {

		this.renderIcon(g, foodIcon, SIZE);

	}

	@Override
	public String toString() {
		return "Food\n\nID: " + this.id + "\n* Quantity: " + quantity;
	}

	@Override
	public void update() {
		super.update();
		if (quantity <= 0)
			Aggregator.getInstance().kill(this);
	}

	public void die() {
	}
}
