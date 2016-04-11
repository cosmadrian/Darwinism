package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Main;

public class MaleIndividual extends Individual {

	private BufferedImage maleIcon;
	private int xOffset = 15, yOffset = 15;

	public MaleIndividual(DNA d) {
		super(d);
		try {
			maleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
					ImageIO.read(new File("src/male.png")).getScaledInstance(15, 15, Image.SCALE_DEFAULT),
					Color.green));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		g.drawImage(maleIcon, x - xOffset, y - yOffset, maleIcon.getWidth(), maleIcon.getHeight(), null);
	}

	public void update() {
		super.update();
	}

	@Override
	public String toString() {
		String x = "Male Individual\n\n" + super.toString();
		return x;
	}

}
