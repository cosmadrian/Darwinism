package entities.traits;

import entities.Individual;

public class Aggressiveness extends Trait {

	private int baseValue;
	private boolean changed = false;

	public Aggressiveness(Individual source) {
		super(source);
	}

	@Override
	public String getName() {
		return "Aggressiveness";
	}

	@Override
	public void update() {
		if (!changed) {
			baseValue = this.value;
			changed = true;
		}

		double hP = (double) (70 - source.getTrait(Trait.Type.HUNGER).getValue()) / 100.0;
		double fP = (double) (source.getTrait(Trait.Type.FERTILITY).getValue()) / 150.0;
		if (hP < 0)
			hP = 0.0;

		this.value = (int) ((1 + hP) * baseValue * (1 + fP));
		if (this.value > 100)
			this.value = 100;
	}
}
