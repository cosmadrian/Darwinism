package entities.traits;

import entities.Individual;

public class Speed extends Trait {

	private int baseValue;
	private boolean changed = false;

	public Speed(Individual source) {
		super(source);
	}

	@Override
	public String getName() {
		return "Speed";
	}

	@Override
	public void update() {
		if (!changed) {
			changed = true;
			baseValue = this.value;
		}

		double hP = (double) (source.getTrait(Trait.Type.HUNGER).getValue()) / 100.0;
		double sP = (double) (source.getTrait(Trait.Type.STAMINA).getValue() - 10) / 100.0;

		if (sP > 0)
			sP = 0;

		this.value = (int) ((1 + hP) * (1 + sP) * baseValue);
	}

}
