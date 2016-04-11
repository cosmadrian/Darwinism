package entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entities.states.CallingState;
import entities.states.EatingState;
import entities.states.FightingState;
import entities.states.IdleState;
import entities.states.MatingState;
import entities.states.MovingState;
import entities.states.State;
import entities.states.States;
import entities.traits.Trait;
import entities.traits.TraitBuilder;

public abstract class Individual extends Entity {

	private State idleState;
	private State movingState;
	private State eatingState;
	private State matingState;
	private State callingState;
	private State fightingState;

	private ArrayList<Trait> traits = new ArrayList<Trait>();
	private DNA dna;
	private State state;
	private MaleIndividual father = null;
	private FemaleIndividual mother = null;

	public Individual(DNA d) {
		this.dna = d;
		for (Trait.Type t : Trait.Type.values()) {
			traits.add(TraitBuilder.getInstance().make(t, d));
		}

		idleState = new IdleState(this);
		movingState = new MovingState(this);
		eatingState = new EatingState(this);
		matingState = new MatingState(this);
		callingState = new CallingState(this);
		fightingState = new FightingState(this);

		this.state = idleState;
	}

	public void render(Graphics2D g) {

	}

	public void update() {
		for (Trait t : traits) {
			t.update();
		}

		state.update();

	}

	public void setState(States t) {
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
		}
	}

	@Override
	public String toString() {
		String x = "ID: " + this.id + "\n";
		for (Trait t : traits) {
			x += "* " + t.getName() + ": " + t.getValue() + "\n";
		}
		x += "\nState: " + state.getName() + "\n";
		return x;
	}

	public void setMother(FemaleIndividual f) {
		this.mother = f;
	}

	public void setFather(MaleIndividual m) {
		this.father = m;
	}

}
