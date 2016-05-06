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

import entities.states.EatingState;
import entities.states.FightingState;
import entities.states.IdleState;
import entities.states.MovingState;
import entities.states.State;
import entities.states.StateType;
import main.Main;

public class FemaleIndividual extends Individual {

	private Color currentColor = Color.RED;
	private BufferedImage femaleIcon = null;
	private boolean isPregnant = false;
	private DNA childDNA;
	private ArrayList<Individual> children = new ArrayList<Individual>();
	private static final int SIZE = 11;
	
	
	public FemaleIndividual(DNA d) {
		super(d);
	}

	public void render(Graphics g) {

		if (femaleIcon == null) {
			try {
				femaleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
						ImageIO.read(new File("src/female.png")).getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT),
						Color.green));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		g.drawImage(femaleIcon, (int) (x - SIZE), (int) (y - SIZE), femaleIcon.getWidth(), femaleIcon.getHeight(),
				null);

		if (Main.DEBUG)
			g.drawOval((int) (x - LOS - SIZE / 2), (int) (y - LOS - SIZE / 2), 2 * LOS, 2 * LOS);
	}

	public void update() {
		super.update();

		if (femaleIcon == null) {
			return;
		}

		Image i = femaleIcon.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);

		if (isPregnant) {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, Color.PINK));
			currentColor = Color.PINK;

			return;
		}

		if (getState() == StateType.EATING) {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, EatingState.FEMALE));
			currentColor = EatingState.FEMALE;

		} else if (getState() == StateType.FIGHTING) {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, FightingState.FEMALE));
			currentColor = FightingState.FEMALE;

		} else if (getState() == StateType.IDLE) {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, IdleState.FEMALE));
			currentColor = IdleState.FEMALE;

		} else if (getState() == StateType.MOVING) {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, MovingState.FEMALE));
			currentColor = MovingState.FEMALE;

		} else {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, State.FEMALE));
			currentColor = State.FEMALE;
		}

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

		// d.setMother(this);
		// children.add(d);

		childDNA = null;

		return d;
	}
}
