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

	private BufferedImage foodIcon;
	private int xOffset = 11, yOffset = 11;
	private int quantity;

	public Food(int i) {
		this.quantity = i;

		try {
			foodIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
					ImageIO.read(new File("src/food.png")).getScaledInstance(11, 11, Image.SCALE_DEFAULT),
					Color.green));
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		g.drawImage(foodIcon, (int) (x - xOffset), (int) (y - yOffset), foodIcon.getWidth(), foodIcon.getHeight(),
				null);
	}

	@Override
	public String toString() {
		return "Food\n\nID: " + this.id + "\n* Quantity: " + quantity;
	}

	@Override
	public void update() {
		if (quantity <= 0)
			Aggregator.getInstance().kill(this);
	}

	public void die() {
	}
}
