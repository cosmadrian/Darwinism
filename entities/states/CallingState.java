package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class CallingState extends State implements ActionListener{

	public CallingState(Individual individual) {
		super(individual);
	}

	@Override
	public void update() {

	}

	@Override
	public String getName() {
		return "CALLING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State withOption(Object option) {
		return this;
	}

}
