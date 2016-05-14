package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entities.states.StateHandler;
import entities.states.StateType;
import entities.traits.Trait;
import entities.traits.TraitHandler;
import main.Aggregator;
import main.Main;
import screen.Screen;

public abstract class Individual extends Entity {

	public static final int LOS = 70;
	public static final int RANGE = 15;
	public static final int MATE_TIMEOUT = 2000; // 2 seconds
	public static final int MATE_CALL_RANGE = 100;

	protected Map<Individual, Long> mates = new HashMap<Individual, Long>();
	protected Map<Individual, Long> enemies = new HashMap<Individual, Long>();

	private TraitHandler traitHandler;
	private StateHandler stateHandler;

	private DNA dna;

	protected double vx;
	protected double vy;

	public Individual(DNA d) {

		this.setDNA(d);
		traitHandler = new TraitHandler(this);
		stateHandler = new StateHandler(this);
	}

	public void render(Graphics g) {
		final int SIZE = 10;

		Screen s = Aggregator.getInstance().getScreen();

		if (getState() == StateType.CALLING && s.contains(new Point((int) (x - SIZE / 2), (int) (y - SIZE / 2)))) {
			g.drawOval((int) (x - MATE_CALL_RANGE - SIZE / 2 - s.getX()),
					(int) (y - MATE_CALL_RANGE - SIZE / 2 - s.getY()), 2 * MATE_CALL_RANGE, 2 * MATE_CALL_RANGE);
		}

		if (Main.DEBUG) {
			g.drawOval((int) (x - LOS - SIZE / 2), (int) (y - LOS - SIZE / 2), 2 * LOS, 2 * LOS);
			g.drawOval((int) (x - RANGE - SIZE / 2), (int) (y - RANGE - SIZE / 2), 2 * RANGE, 2 * RANGE);

			g.drawRect((int) this.getBox().getX(), (int) this.getBox().getY(), (int) this.getBox().getWidth(),
					(int) this.getBox().getHeight());

		}

	}

	public void update() {
		super.update();

		traitHandler.update();
		stateHandler.update();

		if (stateHandler.getStateType() != StateType.MOVING
				&& stateHandler.getStateType() != StateType.MOVING_WITH_GOAL) {
			vx = 0;
			vy = 0;
		}

		checkCollisions();

		setX(x + vx);
		setY(y + vy);

		updateMates();

		if (traitHandler.get(Trait.Type.HUNGER).getValue() <= 0)
			Aggregator.getInstance().kill(this);
	}

	private void checkCollisions() {
		int maxWidth = Aggregator.getInstance().getMap().getWidth();
		int maxHeight = Aggregator.getInstance().getMap().getHeight();

		if (x + vx < Entity.BOX_SIZE || x + vx > maxWidth) {
			vx = -vx;
		}
		if (y + vy < Entity.BOX_SIZE || y + vy > maxHeight) {
			vy = -vy;
		}
	}

	public void setState(StateType t, Object option) {
		stateHandler.setState(t, option);
	}

	public Trait getTrait(Trait.Type t) {
		return traitHandler.get(t);

	}

	public StateType getState() {
		return stateHandler.getStateType();
	}

	@Override
	public String toString() {
		String x = "ID: " + this.id + "\n";
		for (Trait t : traitHandler.getTraits()) {
			x += "* " + t.getName() + ": " + t.getValue() + "\n";
		}
		x += "\nNearby Food: " + this.getNearbyFood(LOS).size() + "\n";
		x += "Nearby Females: " + this.getNearbyFemales(LOS).size() + "\n";
		x += "Nearby Males: " + this.getNearbyMales(LOS).size() + "\n";
		x += "Potential Mates: ";
		for (Individual e : mates.keySet()) {
			x += e.id + ",";
		}
		x = x.substring(0, x.length() - 1);
		x += "\n";
		x += "Potential Enemies: ";
		for (Individual e : enemies.keySet()) {
			x += e.id + ",";
		}
		x = x.substring(0, x.length() - 1);
		x += "\n";
		x += "\nState: " + stateHandler.getState().getName() + "\n";

		return x;
	}

	public void die() {
		super.die();
	}

	public void setDirection(double d) {
		double s = (double) traitHandler.get(Trait.Type.SPEED).getValue() / 50.0;
		vx = ((double) s * Math.cos(d));
		vy = ((double) s * Math.sin(d));

	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	private void updateMates() {
		Iterator<Individual> iter = mates.keySet().iterator();
		while (iter.hasNext()) {
			Individual e = iter.next();
			if (System.currentTimeMillis() - mates.get(e) > MATE_TIMEOUT) {
				iter.remove();
			}
		}
		iter = enemies.keySet().iterator();
		while (iter.hasNext()) {
			Individual e = iter.next();
			if (System.currentTimeMillis() - enemies.get(e) > MATE_TIMEOUT) {
				iter.remove();
			}
		}
	}

	public abstract void signal(MaleIndividual e);

	public abstract void signal(FemaleIndividual e);

	public ArrayList<Individual> getPotentialMates() {
		ArrayList<Individual> x = new ArrayList<Individual>();
		x.addAll(mates.keySet());
		return x;
	}

	public ArrayList<Individual> getPotentialEnemies() {
		ArrayList<Individual> x = new ArrayList<Individual>();
		x.addAll(enemies.keySet());
		return x;
	}

	public DNA getDNA() {
		return dna;
	}

	public void setDNA(DNA dna) {
		this.dna = dna;
	}

}
