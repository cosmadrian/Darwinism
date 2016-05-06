package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;

public class GoalMoveState extends State implements ActionListener {

	private double direction;
	private int time;

	private boolean set = false; 
	
	public GoalMoveState(Individual e) {
		super(e);
		timer = new Timer(0, this);

		nextState = new Tuple<StateType, Object>(StateType.IDLE, null);
	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			timer.setInitialDelay(time);
			timer.start();
		}
		
		if(!set){
			source.setDirection(direction);
			set = true;
		}

	}

	@Override
	public void clean() {
		set = false;
		direction = 0.0;
		time = 0;
	}

	@Override
	public State withOption(Object option) {
		Tuple<Double, Integer> o = (Tuple<Double, Integer>) option;
		direction = o.first;
		time = o.second;
		return this;
	}

	@Override
	public String getName() {
		return "MOVING WITH GOAL";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (nextState == null) {
			return;
		}
		timer.stop();
		source.setState(nextState.first, nextState.second);
	}

}
