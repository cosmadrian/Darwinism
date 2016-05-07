package entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import entities.states.CallingState;
import entities.states.EatingState;
import entities.states.FightingState;
import entities.states.FleeingState;
import entities.states.GoalMoveState;
import entities.states.IdleState;
import entities.states.MatingState;
import entities.states.MovingState;
import entities.states.State;
import entities.states.StateType;
import entities.traits.Trait;
import entities.traits.TraitBuilder;
import frontend.MainFrame;
import frontend.StatPanel;
import main.Main;

public abstract class Individual extends Entity {

	public static final int LOS = 70;
	public static final int RANGE = 15;
	public static final int MATE_TIMEOUT = 3000; // 3 seconds

	private State idleState, movingState, eatingState, matingState, callingState, fightingState, fleeingState,
			movingWithGoal;

	private Trait aggressiveness, combat, fertility, hunger, speed, stamina;
	private ArrayList<Trait> traits = new ArrayList<Trait>();
	private Map<Individual, Long> potentialMates = new HashMap<Individual, Long>();

	private DNA dna;
	private State state;
	private StateType stateType;
	private MaleIndividual father = null;
	private FemaleIndividual mother = null;

	protected double vx;
	protected double vy;

	public Individual(DNA d) {

		this.dna = d;
		traits.add((aggressiveness = TraitBuilder.getInstance().make(this, Trait.Type.AGGRESSIVENESS, d)));
		traits.add(combat = TraitBuilder.getInstance().make(this, Trait.Type.COMBAT, d));
		traits.add(fertility = TraitBuilder.getInstance().make(this, Trait.Type.FERTILITY, d));
		traits.add(hunger = TraitBuilder.getInstance().make(this, Trait.Type.HUNGER, d));
		traits.add(speed = TraitBuilder.getInstance().make(this, Trait.Type.SPEED, d));
		traits.add(stamina = TraitBuilder.getInstance().make(this, Trait.Type.STAMINA, d));

		idleState = new IdleState(this);
		movingState = new MovingState(this);
		eatingState = new EatingState(this);
		matingState = new MatingState(this);
		callingState = new CallingState(this);
		fightingState = new FightingState(this);
		fleeingState = new FleeingState(this);
		movingWithGoal = new GoalMoveState(this);

		this.state = idleState;
		this.stateType = StateType.IDLE;
	}

	public void render(Graphics g) {
		final int SIZE = 10;

		if (getState() == StateType.CALLING) {
			g.drawOval((int) (x - LOS - SIZE / 2), (int) (y - LOS - SIZE / 2), 2 * LOS, 2 * LOS);
		}

		if (Main.DEBUG) {
			g.drawOval((int) (x - LOS - SIZE / 2), (int) (y - LOS - SIZE / 2), 2 * LOS, 2 * LOS);
			g.drawOval((int) (x - RANGE - SIZE / 2), (int) (y - RANGE - SIZE / 2), 2 * RANGE, 2 * RANGE);
		}
	}

	public void update() {
		super.update();
		for (Trait t : traits) {
			t.update();
		}

		if (stateType != StateType.MOVING && stateType != StateType.MOVING_WITH_GOAL) {
			vx = 0;
			vy = 0;
		}

		if (x + vx < 10 || x + vx > MainFrame.WIDTH - StatPanel.WIDTH - 10) {
			vx = -vx;
		}
		if (y + vy < 10 || y + vy > MainFrame.HEIGHT - 10) {
			vy = -vy;
		}
		x += vx;
		y += vy;

		updatePotentialMates();
		state.update();

		if (this.hunger.getValue() <= 0)
			this.die();
	}

	private void updatePotentialMates() {
		Iterator<Individual> iter = potentialMates.keySet().iterator();
		while (iter.hasNext()) {
			Individual e = iter.next();
			if (System.currentTimeMillis() - potentialMates.get(e) > MATE_TIMEOUT) {
				iter.remove();
			}
		}
	}

	public void setState(StateType t, Object option) {
		this.stateType = t;

		this.state.clean();

		switch (t) {
		case MOVING:
			this.state = movingState;
			break;
		case EATING:
			this.state = eatingState;
			break;
		case MATING:
			this.state = matingState;
			break;
		case CALLING:
			this.state = callingState;
			break;
		case FIGHTING:
			this.state = fightingState;
			break;
		case IDLE:
			this.state = idleState;
			break;
		case FLEEING:
			this.state = fleeingState;
			break;
		case MOVING_WITH_GOAL:
			this.state = movingWithGoal;
			break;
		}

		state.withOption(option);
	}

	public Trait getTrait(Trait.Type t) {
		switch (t) {
		case AGGRESSIVENESS:
			return aggressiveness;
		case COMBAT:
			return combat;
		case FERTILITY:
			return fertility;
		case HUNGER:
			return hunger;
		case SPEED:
			return speed;
		case STAMINA:
			return stamina;
		}

		return null;
	}

	public StateType getState() {
		return this.stateType;
	}

	@Override
	public String toString() {
		String x = "ID: " + this.id + "\n";
		for (Trait t : traits) {
			x += "* " + t.getName() + ": " + t.getValue() + "\n";
		}
		x += "\nNearby Food: " + this.getNearbyFood().size() + "\n";
		x += "Nearby Females: " + this.getNearbyFemales().size() + "\n";
		x += "Nearby Males: " + this.getNearbyMales().size() + "\n";
		x += "Potential Mates: ";
		for (Individual e : potentialMates.keySet()) {
			x += e.id + ",";
		}

		x = x.substring(0, x.length() - 1);
		x += "\n";
		x += "\nState: " + state.getName() + "\n";

		return x;
	}

	public void setMother(FemaleIndividual f) {
		this.mother = f;
	}

	public void setFather(MaleIndividual m) {
		this.father = m;
	}

	public void die() {
		super.die();
	}

	public void setDirection(double d) {
		double s = (double) speed.getValue() / 30.0;
		vx = ((double) s * Math.cos(d));
		vy = ((double) s * Math.sin(d));

	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getSpeed() {
		return (double) speed.getValue() / 30.0;
	}

	public void signal(Individual e) {
		if (potentialMates.containsKey(e)) {
			potentialMates.remove(e);
		}

		potentialMates.put(e, System.currentTimeMillis());
	}

}
