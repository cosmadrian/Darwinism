package entities.states;

import java.awt.Color;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;

import entities.Individual;

public abstract class State {

	public static final Color MALE = Color.BLUE;
	public static final Color FEMALE = Color.RED;

	protected Individual source;
	protected Tuple<StateType,Object> nextState;
	protected Timer timer;
	protected Object option;

	protected HashMap<Tuple<StateType, Object>, Double> weightedStates;

	public State(Individual e) {
		this.source = e;
		weightedStates = new HashMap<Tuple<StateType, Object>, Double>();
	}

	public abstract void update();

	public abstract void clean();

	public abstract State withOption(Object option);

	public abstract String getName();

	public static Tuple<StateType, Object> getNextState(HashMap<Tuple<StateType, Object>, Double> weightedStates) {
		Random r = new Random();
		double chance;
		double max = 0;

		for (Double c : weightedStates.values()) {
			max += c;
		}
		chance = r.nextDouble() * max;

		for (Tuple<StateType, Object> t : weightedStates.keySet()) {
			if (chance < weightedStates.get(t)) {
				return t;
			}
			chance -= weightedStates.get(t);
		}

		System.out.println("YOU CANT REACH ME");
		return null;
	}

}
