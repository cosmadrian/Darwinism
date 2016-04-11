package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class FleeingState extends State implements ActionListener {

	public FleeingState(Individual e) {
		super(e);
	}

	@Override
	public void update() {

	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

}
