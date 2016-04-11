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

	private BufferedImage maleIcon = null;
	private int xOffset = 15, yOffset = 15;

	public MaleIndividual(DNA d) {
		super(d);
		
	}

	public void render(Graphics g) {
		if(maleIcon == null){
			try {
				maleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
						ImageIO.read(new File("src/male.png")).getScaledInstance(15, 15, Image.SCALE_DEFAULT),
						Color.green));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g.drawImage(maleIcon, x - xOffset, y - yOffset, maleIcon.getWidth(), maleIcon.getHeight(), null);
		
		if (Main.DEBUG)
			g.drawOval(x - LOS - xOffset / 2, y - LOS - yOffset / 2, 2 * LOS, 2 * LOS);
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
