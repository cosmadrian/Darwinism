package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Main;

public class FemaleIndividual extends Individual {

	private BufferedImage femaleIcon = null;
	private int xOffset = 13, yOffset = 13;
	private boolean isPregnant = false;
	private DNA childDNA;
	private ArrayList<Individual> children = new ArrayList<Individual>();

	public FemaleIndividual(DNA d) {
		super(d);
	}

	public void render(Graphics g) {

		if (femaleIcon == null) {
			try {
				femaleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
						ImageIO.read(new File("src/female.png")).getScaledInstance(13, 13, Image.SCALE_DEFAULT),
						Color.green));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		g.drawImage(femaleIcon, (int) (x - xOffset), (int) (y - yOffset), femaleIcon.getWidth(), femaleIcon.getHeight(),
				null);

		if (Main.DEBUG)
			g.drawOval((int) (x - LOS - xOffset / 2), (int) (y - LOS - yOffset / 2), 2 * LOS, 2 * LOS);
	}

	public void update() {
		super.update();
	}

	@Override
	public String toString() {
		String x = "Female Individual\n\n" + super.toString();
		return x;
	}

	public boolean isPregnant() {
		return isPregnant;
	}

	public void impregnate(DNA d) {
		isPregnant = true;
		this.childDNA = d;
	}

	public Individual takeBirth() {
		isPregnant = false;

		Random r = new Random();
		Individual d = null;

		if (childDNA.getGender() == Entity.Type.MALE) {
			d = (Individual) EntityBuilder.getInstance().make(Entity.Type.MALE,
					new Point((int) x + (r.nextInt(20) - 10), (int) y + (r.nextInt(20) - 10)), childDNA);
		} else if (childDNA.getGender() == Entity.Type.FEMALE) {
			d = (Individual) EntityBuilder.getInstance().make(Entity.Type.FEMALE,
					new Point((int) x + (r.nextInt(20) - 10), (int) y + (r.nextInt(20) - 10)), childDNA);
		}

		d.setMother(this);
		children.add(d);

		childDNA = null;

		return d;
	}
}
