package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Food;
import entities.Individual;
import entities.traits.Trait;

public class EatingState extends State implements ActionListener {

	public static final Color MALE = new Color(0x828282); // 
	public static final Color FEMALE = new Color(0x828282); // gray
	private static final int EATING_RATE = 8;

	private Food foodSource;

	public EatingState(Individual individual) {
		super(individual);

		timer = new Timer(1000, this);
	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			timer.start();
			return;
		}
		weightedStates.clear();
		foodDistances.clear();

		double c = hungerProbability(source.getTrait(Trait.Type.HUNGER).getValue());
		weightedStates.put(new Tuple<StateType, Object>(StateType.EATING, null), c);
		weightedStates.put(new Tuple<StateType, Object>(StateType.IDLE, null), 1 - c);

		nextState = State.getNextState(weightedStates);

	}

	@Override
	public String getName() {
		return "EATING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (foodSource == null) {
			timer.stop();
			source.setState(StateType.IDLE, null);
			return;
		}

		int val = source.getTrait(Trait.Type.HUNGER).getValue();
		source.getTrait(Trait.Type.HUNGER).setValue(val + EATING_RATE);
		foodSource.consume(EATING_RATE);
		if (foodSource.getQuantity() <= 0) {
			foodSource = null;
		}

		if (nextState != null && nextState.first != StateType.EATING) {
			timer.stop();
			source.setState(nextState.first, nextState.second);
		}
	}

	public void setFoodSource(Food e) {
		this.foodSource = e;
	}

	private double hungerProbability(double x) {
		return Math.pow(2, -(x / 40));
	}

	@Override
	public void clean() {
		super.clean();
		this.foodSource = null;
	}

	@Override
	public State withOption(Object option) {
		this.foodSource = (Food) option;
		return this;
	}
}
