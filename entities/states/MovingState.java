package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import entities.Individual;

public class MovingState extends State implements ActionListener {

	private boolean directionSet = false;

	public MovingState(Individual e) {
		super(e);
		Random r = new Random();
		timer = new Timer(r.nextInt(2000) + 500, this);

		nextState = States.IDLE;
	}

	@Override
	public void update() {
		Random r = new Random();

		if (!timer.isRunning()) {
			timer.setInitialDelay(r.nextInt(1500) + 200);
			timer.start();
			directionSet = false;
		} else {
			if (directionSet == false) {

				int x = source.getX() + r.nextInt(20) - 10;
				int y = source.getY() + r.nextInt(20) - 10;
				double theta = r.nextDouble() * 5 - 4;

				source.setDirection(theta);
				directionSet = true;
			}

		}
	}

	@Override
	public String getName() {
		return "MOVING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		source.setState(nextState);
		timer.stop();
	}

}
