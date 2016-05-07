package entities.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entities.Individual;
import entities.traits.Trait;

public class CallingState extends State implements ActionListener {

	public CallingState(Individual individual) {
		super(individual);
		timer = new Timer(0, this);
	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			double fP = ((double) (source.getTrait(Trait.Type.FERTILITY).getValue()) / 100.0);
			double sP = ((double) (source.getTrait(Trait.Type.STAMINA).getValue()) / 100.0);

			timer.setInitialDelay((int) (1000 * (1 + sP) * (1 + fP)));
			timer.start();
		}
		
		for(Individual ind : source.getNearbyIndividuals()){
			ind.signal(source);
		}
		
	}

	@Override
	public String getName() {
		return "CALLING";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.stop();
		source.setState(StateType.IDLE, null);
	}

	@Override
	public void clean() {
	}

	@Override
	public State withOption(Object option) {
		return this;
	}

}
