package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;

public class MovingState extends State implements ActionListener {

	public static final Color MALE = new Color(0x0000BD);
	public static final Color FEMALE = new Color(0xBD0000);

	private double direction = 0.0;
	private boolean set = false;

	public MovingState(Individual e) {
		super(e);
		/* TODO: rate depends on stamina */
		timer = new Timer(1000, this);
		timer.start();
	}

	@Override
	public void update() {

		if (!set) {
			source.setDirection(direction);
			set = true;
		}

		weightedStates.clear();
		weightedStates.put(new Tuple<StateType, Object>(StateType.IDLE, null), 0.0);
		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING, null), 0.0);
		weightedStates.put(new Tuple<StateType, Object>(StateType.EATING, null), 0.0);

		nextState = super.getNextState(weightedStates);
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

		timer.stop();
		source.setState(nextState.first, nextState.second);
	}

	@Override
	public void clean() {
		direction = 0.0;

	}

	@Override
	public State withOption(Object option) {
		this.direction = (Double) option;
		return this;
	}

}
