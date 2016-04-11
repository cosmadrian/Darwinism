package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;

public class MovingState extends State implements ActionListener {

	public MovingState(Individual e) {
		super(e);
		timer = new Timer(1000,this);
		nextState = States.IDLE;
	}

	@Override
	public void update() {
		if(!timer.isRunning()){
			timer.start();
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
