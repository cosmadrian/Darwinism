package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entities.Individual;

public class MovingState extends State implements ActionListener {

	public static final Color MALE = new Color(0x0000BD);
	public static final Color FEMALE = new Color(0xBD0000);

	private double direction = 0.0;

	public MovingState(Individual e) {
		super(e);

	}

	@Override
	public void update() {
		
	}

	@Override
	public String getName() {
		return "MOVING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void setDirection(double direction) {
		this.direction = direction;
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
