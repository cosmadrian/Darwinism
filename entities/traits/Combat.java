package entities.traits;

import entities.Individual;

public class Combat extends Trait {

	private int baseValue;
	private boolean changed = false;

	public Combat(Individual source) {
		super(source);
	}

	@Override
	public String getName() {
		return "Combat";
	}

	@Override
	public void update() {
		if (!changed) {
			baseValue = this.value;
			changed = true;
		}

		double sP = (double) (source.getTrait(Trait.Type.STAMINA).getValue() - 50) / 100.0;
		double aP = (double) (source.getTrait(Trait.Type.AGGRESSIVENESS).getValue() - 75) / 100.0;
		double hP = (double) (source.getTrait(Trait.Type.HUNGER).getValue() - 25) / 100.0;

		if (aP < 0.0)
			aP = 0.0;
		if (hP > 0)
			hP = 0.0;

		this.value = (int) (baseValue * (1 + sP) * (1 + aP) * (1 + hP));
	}

}
