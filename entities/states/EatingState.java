package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class EatingState extends State implements ActionListener{

	public EatingState(Individual individual) {
		super(individual);
	}

	@Override
	public void update() {

	}

	@Override
	public String getName() {
		return "EATING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
