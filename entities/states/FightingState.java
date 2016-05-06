package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class FightingState extends State implements ActionListener {

	public static final Color MALE = new Color(0x7E14FF);
	public static final Color FEMALE = new Color(0xDE0030);
	
	
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

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State withOption(Object option) {
		return this;
	}

}
