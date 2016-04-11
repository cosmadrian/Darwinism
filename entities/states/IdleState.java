package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;

public class IdleState extends State implements ActionListener {

	public IdleState(Individual e) {
		super(e);
		timer = new Timer(1000, this);
		nextState = States.MOVING;
	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			timer.start();
		}
	}

	@Override
	public String getName() {
		return "IDLE";
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		source.setState(nextState);
		timer.stop();
	}

}
