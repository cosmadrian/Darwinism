package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
import main.Main;

public class MaleIndividual extends Individual {

	private BufferedImage maleIcon = null;
	private Color currentColor = Color.BLUE;
	private static final int SIZE = 13;

	public MaleIndividual(DNA d) {
		super(d);

	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		super.render(g);
		g.setColor(Color.black);

		if (maleIcon == null) {
			try {
				maleIcon = Main.toBufferedImage(Main.TransformColorToTransparency(
						ImageIO.read(new File("src/male.png")).getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT),
						Color.green));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		g.drawImage(maleIcon, (int) (x - SIZE), (int) (y - SIZE), maleIcon.getWidth(), maleIcon.getHeight(), null);
	}

	public void update() {
		super.update();

		if (maleIcon == null) {
			return;
		}

		Image i = maleIcon.getScaledInstance(SIZE, SIZE, Image.SCALE_DEFAULT);

		if (getState() == StateType.EATING) {
			maleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, EatingState.MALE));
			currentColor = EatingState.MALE;

		} else if (getState() == StateType.FIGHTING) {
			maleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, FightingState.MALE));
			currentColor = FightingState.MALE;

		} else if (getState() == StateType.IDLE) {
			maleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, IdleState.MALE));
			currentColor = IdleState.MALE;

		} else if (getState() == StateType.MOVING || getState() == StateType.MOVING_WITH_GOAL) {
			maleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, MovingState.MALE));
			currentColor = MovingState.MALE;

		} else {
			maleIcon = Main.toBufferedImage(Main.TransformColorToColor(i, currentColor, State.MALE));
			currentColor = State.MALE;
		}

	}

	@Override
	public String toString() {
		String x = "Male Individual\n\n" + super.toString();
		return x;
	}

	@Override
	public void signal(MaleIndividual e) {
		if (super.enemies.containsKey(e)) {
			enemies.remove(e);
		}

		enemies.put(e, System.currentTimeMillis());

	}

	@Override
	public void signal(FemaleIndividual e) {
		if (super.mates.containsKey(e)) {
			mates.remove(e);
		}

		mates.put(e, System.currentTimeMillis());
	}
}
