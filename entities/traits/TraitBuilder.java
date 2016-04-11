package entities.traits;

import entities.DNA;

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

	public Trait make(Trait.Type t, DNA d) {
		Trait trait = null;
		switch (t) {
		case FERTILITY:
			trait = new Fertility();
			break;
		case STAMINA:
			trait = new Stamina();
			break;
		case HUNGER:
			trait = new Hunger();
			break;
		case AGGRESSIVENESS:
			trait = new Aggressiveness();
			break;
		case COMBAT:
			trait = new Combat();
			break;
		case SPEED:
			trait = new Speed();
			break;
		}
		trait.setValue(d.getTraitInfluence(t));
		return trait;
	}

}
