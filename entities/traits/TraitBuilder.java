package entities.traits;

import entities.DNA;
import entities.Individual;

public class TraitBuilder {
	private static TraitBuilder instance;

	private TraitBuilder() {

	}

	public static TraitBuilder getInstance() {
		if (instance == null) {
			instance = new TraitBuilder();
		}

		return instance;
	}

	public Trait make(Individual source, Trait.Type t, DNA d) {
		Trait trait = null;
		switch (t) {
		case FERTILITY:
			trait = new Fertility(source);
			break;
		case STAMINA:
			trait = new Stamina(source);
			break;
		case HUNGER:
			trait = new Hunger(source);
			break;
		case AGGRESSIVENESS:
			trait = new Aggressiveness(source);
			break;
		case COMBAT:
			trait = new Combat(source);
			break;
		case SPEED:
			trait = new Speed(source);
			break;
		}
		trait.setValue(d.getTraitInfluence(t));
		return trait;
	}

}
