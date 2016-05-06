package entities.traits;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;
import entities.states.StateType;

public class Stamina extends Trait implements ActionListener {

	private static final int EXHAUSTION_RATE = 3;
	private static final int RECOVERY_RATE = 1;
	private Timer timer;
	private int rate = 1000; // ms

	public Stamina(Individual source) {
		super(source);
		timer = new Timer(rate, this);
		timer.start();
	}

	@Override
	public String getName() {
		return "Stamina";
	}

	@Override
	public void update() {
		if(this.value > 100){
			this.value = 100;
		}
		
		if(this.value < 0){
			this.value = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(source.getState() == StateType.MOVING){
			this.value -= EXHAUSTION_RATE * (source.getTrait(Trait.Type.SPEED).getValue() / 100.0);
		}else {
			this.value += RECOVERY_RATE;
		}
	}

}
