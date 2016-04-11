package entities.states;

import javax.swing.Timer;

import entities.Individual;

public abstract class State {
	
	protected Individual source;
	protected States nextState;
	protected Timer timer;
	
	public State(Individual e){
		this.source = e;
	}
	
	public abstract void update();

	public abstract String getName();
}
