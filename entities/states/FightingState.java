package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class FightingState extends State implements ActionListener {

	public static final Color MALE = new Color(0x002696); // albastru inchis
	public static final Color FEMALE = new Color(0x8F001F); // rosu inchis

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
		super.clean();

	}

	@Override
	public State withOption(Object option) {
		return this;
	}

}
