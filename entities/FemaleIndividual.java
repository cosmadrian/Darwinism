package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Main;

public class FemaleIndividual extends Individual {

	private BufferedImage femaleIcon;
	private int xOffset = 13, yOffset = 13;

	public FemaleIndividual(DNA d) {
		super(d);

		try {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
					ImageIO.read(new File("src/female.png")).getScaledInstance(13, 13, Image.SCALE_DEFAULT),
					Color.green));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		g.drawImage(femaleIcon, x - xOffset, y - yOffset, femaleIcon.getWidth(), femaleIcon.getHeight(), null);
	}

	@Override
	public String toString() {
		String x = "Female Individual\n\n" + super.toString();
		return x;
	}
}
