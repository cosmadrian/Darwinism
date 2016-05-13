package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;
import entities.traits.Trait;

public class MovingState extends State implements ActionListener {

	public static final Color MALE = new Color(0x0000BD);
	public static final Color FEMALE = new Color(0xBD0000);

	private double direction = 0.0;
	private boolean set = false;

	public MovingState(Individual e) {
		super(e);

		timer = new Timer(0, this);
	}

	@Override
	public void update() {

		if (!timer.isRunning()) {
			timer.start();
			
			double sP = ((double) (source.getTrait(Trait.Type.STAMINA).getValue() + 20) / 100.0);
			timer.setInitialDelay((int) (500 * sP));
		}

		if (!set) {
			source.setDirection(direction);
			set = true;
		}
		weightedStates.clear();
		foodDistances.clear();

		Tuple<Double, Object> eating, moving, movingToMate;
		getFoodDistances();
		eating = getEatingTuple();
		moving = getMovingTuple();
		movingToMate = getMovingToMateTuple();

		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING, moving.second), moving.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.EATING, eating.second), eating.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING_WITH_GOAL, movingToMate.second), movingToMate.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.IDLE, null), 1 - moving.first);

		nextState = super.getNextState(weightedStates);
		
		if(nextState.first == StateType.MOVING_WITH_GOAL){
			actionPerformed(null);
		}

	}

	@Override
	public String getName() {
		return "MOVING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (nextState == null) {
			return;
		}

		if (nextState.first != StateType.MOVING) {
			source.setState(nextState.first, nextState.second);
			timer.stop();

		} else {
			double sP = ((double) (source.getTrait(Trait.Type.STAMINA).getValue() + 20) / 100.0);
			timer.setDelay((int) (500 * sP));
		}
	}

	@Override
	public void clean() {
		super.clean();
		direction = 0.0;
		set = false;
	}

	@Override
	public State withOption(Object option) {
		this.direction = (Double) option;
		return this;
	}

}
