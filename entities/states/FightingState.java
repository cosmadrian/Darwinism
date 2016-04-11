package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class FightingState extends State implements ActionListener {

	public FightingState(Individual individual) {
		super(individual);
	}

	@Override
	public void update() {

	}

	@Override
	public String getName() {
		return "FIGHTING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
