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

public abstract class Individual extends Entity  {

	public static final int LOS = 70;
	public static final int RANGE = 15;
	public static final int MATE_TIMEOUT = 2000; // 2 seconds
	public static final int MATE_CALL_RANGE = 100;

	protected Map<Individual, Long> mates = new HashMap<Individual, Long>();
	protected Map<Individual, Long> enemies = new HashMap<Individual, Long>();

	private State idleState, movingState, eatingState, matingState, callingState, fightingState, fleeingState,
			movingWithGoal;

	private Trait aggressiveness, combat, fertility, hunger, speed, stamina;
	private ArrayList<Trait> traits = new ArrayList<Trait>();

	private DNA dna;
	private State state;
	private StateType stateType;

	protected double vx;
	protected double vy;

	public Individual(DNA d) {

		this.setDNA(d);
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
			g.drawOval((int) (x - MATE_CALL_RANGE - SIZE / 2), (int) (y - MATE_CALL_RANGE - SIZE / 2),
					2 * MATE_CALL_RANGE, 2 * MATE_CALL_RANGE);
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
		
		setX(x+vx);
		setY(y+vy);

		updateMates();
		state.update();

		if (this.hunger.getValue() <= 0)
			this.die();
	}

	public void setState(StateType t, Object option) {
		if(this instanceof FemaleIndividual){
			if(((FemaleIndividual)this).isPregnant()){
				if(t == StateType.CALLING || t == StateType.MATING){
					this.setState(StateType.IDLE, null);
					return;
				}
			}
		}
		
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
		x += "\nState: " + state.getName() + "\n";

		return x;
	}

	public void die() {
		super.die();
	}

	public void setDirection(double d) {
		double s = (double) speed.getValue() / 50.0;
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
