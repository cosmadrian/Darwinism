package entities;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.states.CallingState;
import entities.states.EatingState;
import entities.states.FightingState;
import entities.states.FleeingState;
import entities.states.IdleState;
import entities.states.MatingState;
import entities.states.MovingState;
import entities.states.State;
import entities.states.StateType;
import entities.traits.Trait;
import entities.traits.TraitBuilder;
import frontend.MainFrame;
import frontend.StatPanel;

public abstract class Individual extends Entity {

	private State idleState, movingState, eatingState, matingState, callingState, fightingState, fleeingState;

	private Trait aggressiveness, combat, fertility, hunger, speed, stamina;
	private ArrayList<Trait> traits = new ArrayList<Trait>();

	private DNA dna;
	private State state;
	private StateType stateType;
	private MaleIndividual father = null;
	private FemaleIndividual mother = null;
	public static final int RANGE = 18;

	private double vx, vy;

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

		this.state = idleState;
		this.stateType = StateType.IDLE;
	}

	public void render(Graphics g) {

	}

	public void update() {
		super.update();
		for (Trait t : traits) {
			t.update();
		}
		if (x + vx < 0 || x + vx > MainFrame.WIDTH - StatPanel.WIDTH)
			vx = -vx;
		if (y + vy < 0 || y + vy > MainFrame.WIDTH - StatPanel.WIDTH)
			vy = -vy;
		x += vx;
		y += vy;

		state.update();

		if (this.hunger.getValue() <= 0)
			this.die();
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
	
	public StateType getState(){
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
		int s = speed.getValue() / 30;
		vx = ((double) s * Math.cos(d));
		vy = ((double) s * Math.sin(d));
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}
	
	public double getSpeed(){
		return Math.sqrt(vx*vx + vy*vy);
	}
}
