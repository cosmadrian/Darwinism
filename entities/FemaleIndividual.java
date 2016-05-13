package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.states.EatingState;
import entities.states.FightingState;
import entities.states.IdleState;
import entities.states.MovingState;
import entities.states.State;
import entities.states.StateType;
import exceptions.AlreadyPregnantException;
import main.Aggregator;
import main.Main;
import screen.Screen;

public class FemaleIndividual extends Individual {

	private static final int SIZE = 11;
	private static final String FEMALE_ICON = "src/female.png";

	private Color currentColor = Color.RED;
	private static BufferedImage femaleIcon = null;
	private boolean isPregnant = false;
	private GestationHandler gestationHandler;

	static {
		try {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
					ImageIO.read(new File(FEMALE_ICON)).getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT),
					Color.green));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FemaleIndividual(DNA d) {
		super(d);
		gestationHandler = new GestationHandler(this);

	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		super.render(g);
		g.setColor(Color.black);

		Screen s = Aggregator.getInstance().getScreen();

		int xOffset = s.getX();
		int yOffset = s.getY();

		if (s.contains(new Point((int) (x - SIZE / 2), (int) (y - SIZE / 2)))) {
			g.drawImage(femaleIcon, (int) (x - SIZE / 2 - xOffset), (int) (y - SIZE / 2 - yOffset),
					femaleIcon.getWidth(), femaleIcon.getHeight(), null);
		}

	}

	public void update() {
		super.update();

		if (femaleIcon == null) {
			return;
		}

		Image i = femaleIcon.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);

		if (isPregnant) {
			femaleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, Color.CYAN));
			currentColor = Color.CYAN;

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

		} else if (getState() == StateType.MOVING || getState() == StateType.MOVING_WITH_GOAL) {
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

	public void cancelPregnancy() {
		this.isPregnant = false;
	}

	public void impregnate(DNA d) throws AlreadyPregnantException {
		if (isPregnant) {
			throw new AlreadyPregnantException("This female is already in pain!");
		}
		isPregnant = true;
		gestationHandler.impregnate(d);
	}

	@Override
	public void signal(MaleIndividual e) {
		if (super.mates.containsKey(e)) {
			mates.remove(e);
		}

		mates.put(e, System.currentTimeMillis());
	}

	@Override
	public void signal(FemaleIndividual e) {
		if (super.enemies.containsKey(e)) {
			enemies.remove(e);
		}

		enemies.put(e, System.currentTimeMillis());
	}

	public void die() {
		super.die();
		gestationHandler.stop();
	}

}
