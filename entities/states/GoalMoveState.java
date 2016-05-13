package entities.states;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Entity;
import entities.Individual;

public class GoalMoveState extends State implements ActionListener {

	private double direction;
	private Entity target;
	private static final int TIMEOUT = 3000;
	private long startTime;

	public GoalMoveState(Individual e) {
		super(e);
		timer = new Timer(100, this);

		nextState = new Tuple<StateType, Object>(StateType.IDLE, null);
	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			timer.start();
			startTime = System.currentTimeMillis();
			if (target != null)
				direction = super.getAngleBetween(new Point(source.getX(), source.getY()),
						new Point(target.getX(), target.getY()));
		}

		source.setDirection(direction);

	}

	@Override
	public void clean() {
		super.clean();
		direction = 0.0;
		target = null;
		startTime = 0;
	}

	@Override
	public State withOption(Object option) {
		this.target = (Entity) option;
		return this;
	}

	@Override
	public String getName() {
		return "MOVING WITH GOAL";
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		double distanceToTarget = Math
				.sqrt(Math.pow(source.getX() - target.getX(), 2) + Math.pow(source.getY() - target.getY(), 2));

		long interval = System.currentTimeMillis() - startTime;

		if (distanceToTarget > Individual.MATE_CALL_RANGE || distanceToTarget <= Individual.RANGE
				|| interval > TIMEOUT) {
			timer.stop();
			source.setState(nextState.first, nextState.second);
			return;
		}

		direction = super.getAngleBetween(new Point(source.getX(), source.getY()),
				new Point(target.getX(), target.getY()));

	}

}
