package entities.traits;

import entities.Individual;

public class Fertility extends Trait {

	private int baseValue;
	private boolean changed = false;

	public Fertility(Individual source) {
		super(source);
	}

	@Override
	public String getName() {
		return "Fertility";
	}

	@Override
	public void update() {
		if (!changed) {
			changed = true;
			baseValue = this.value;
		}

		double hP = (double) (source.getTrait(Trait.Type.HUNGER).getValue() - 50) / 100.0;
		double sP = (double) (source.getTrait(Trait.Type.STAMINA).getValue() - 25) / 100.0;

		if (hP > 0)
			hP = 0;
		if (sP > 0)
			sP = 0;

		this.value = (int) ((1 + hP) * (1 + sP) * baseValue);

	}

}
