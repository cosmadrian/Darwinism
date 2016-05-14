package entities;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import main.Aggregator;

public class GestationHandler implements ActionListener {

	private static final int GESTATION_PERIOD = 5000;

	private FemaleIndividual female;
	private DNA childDNA;
	private Timer timer;

	public GestationHandler(FemaleIndividual e) {
		this.female = e;
		timer = new Timer(GESTATION_PERIOD, this);
	}

	public void impregnate(DNA d) {
		this.setDNA(d);
		this.startGestation();
	}

	public void takeBirth() {

		Random r = new Random();
		Individual d = null;

		if (childDNA.getGender() == Entity.Type.MALE) {
			Aggregator.getInstance().addEntity(EntityBuilder.getInstance().make(Entity.Type.MALE,
					new Point((int) female.getX() + (r.nextInt(20) - 10), (int) female.getY() + (r.nextInt(20) - 10)),
					childDNA));
		} else if (childDNA.getGender() == Entity.Type.FEMALE) {
			Aggregator.getInstance().addEntity(EntityBuilder.getInstance().make(Entity.Type.FEMALE,
					new Point((int) female.getX() + (r.nextInt(20) - 10), (int) female.getY() + (r.nextInt(20) - 10)),
					childDNA));
		}

		childDNA = null;
	}

	public void setDNA(DNA d) {
		DNA maleDNA = new DNA(d.mutate());
		DNA femaleDNA = new DNA(female.getDNA().mutate());
		DNA combined = DNA.combine(maleDNA, femaleDNA);

		this.childDNA = combined;
	}

	public void startGestation() {
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.stop();
		this.takeBirth();
		female.cancelPregnancy();
	}

	public void stop() {
		this.timer.stop();
	}

}
