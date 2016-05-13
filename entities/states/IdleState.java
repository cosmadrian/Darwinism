package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;
import entities.traits.Trait;

public class IdleState extends State implements ActionListener {

	public static final Color MALE = new Color(0x4A4AFF);
	public static final Color FEMALE = new Color(0xFF5757);

	public IdleState(Individual e) {
		super(e);
		timer = new Timer(0, this);

	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			double sP = ((double) (150 - source.getTrait(Trait.Type.STAMINA).getValue()) / 100.0);
			timer.setInitialDelay((int) (500 * sP));

			timer.start();
		}

		weightedStates.clear();
		foodDistances.clear();

		Tuple<Double, Object> eating, calling, moving, movingToEat, movingToMate;
		getFoodDistances();
		eating = getEatingTuple();
		moving = getMovingTuple();
		calling = getCallingTuple();
		movingToEat = getMovingToEatTuple();
		movingToMate = getMovingToMateTuple();

		weightedStates.put(new Tuple<StateType, Object>(StateType.EATING, eating.second), eating.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING_WITH_GOAL, movingToEat.second),
				movingToEat.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING_WITH_GOAL, movingToMate.second),
				movingToMate.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING, moving.second), moving.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.CALLING, calling.second), calling.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.IDLE, null), 1 - moving.first);

		nextState = super.getNextState(weightedStates);

	}

	@Override
	public String getName() {
		return "IDLE";
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (nextState.first != StateType.IDLE) {
			source.setState(nextState.first, nextState.second);
			timer.stop();
		} else {
			double sP = ((double) (150 - source.getTrait(Trait.Type.STAMINA).getValue()) / 100.0);
			timer.setDelay((int) (500 * sP));
		}
	}

	@Override
	public void clean() {
		super.clean();
		weightedStates.clear();
	}

	@Override
	public State withOption(Object option) {
		return this;
	}

}
