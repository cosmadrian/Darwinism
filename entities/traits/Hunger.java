package entities.traits;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;

public class Hunger extends Trait implements ActionListener {

	private Timer timer;
	private int rate = 1000; // ms
	private int STARVATION_RATE = 2;
	private double K = 0.0;

	public Hunger(Individual source) {
		super(source);
		timer = new Timer(rate, this);
		timer.start();
	}

	@Override
	public String getName() {
		return "Hunger";
	}

	@Override
	public void update() {
		if (value == 0)
			timer.stop();

		K = (double) (50 - source.getTrait(Trait.Type.STAMINA).getValue()) / 100.0;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.value -= STARVATION_RATE * (1 + K);
	}
}
