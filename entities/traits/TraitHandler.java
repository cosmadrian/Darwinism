package entities.traits;

import java.util.ArrayList;

import entities.DNA;
import entities.Individual;

public class TraitHandler {

	private Trait aggressiveness, combat, fertility, hunger, speed, stamina;
	private Individual source;
	private ArrayList<Trait> traits = new ArrayList<Trait>();

	public TraitHandler(Individual e) {
		this.source = e;
		initTraits(e.getDNA());
	}

	public void update() {
		for (Trait t : traits) {
			t.update();
		}
	}

	private void initTraits(DNA d) {
		traits.add((aggressiveness = TraitBuilder.getInstance().make(source, Trait.Type.AGGRESSIVENESS, d)));
		traits.add(combat = TraitBuilder.getInstance().make(source, Trait.Type.COMBAT, d));
		traits.add(fertility = TraitBuilder.getInstance().make(source, Trait.Type.FERTILITY, d));
		traits.add(hunger = TraitBuilder.getInstance().make(source, Trait.Type.HUNGER, d));
		traits.add(speed = TraitBuilder.getInstance().make(source, Trait.Type.SPEED, d));
		traits.add(stamina = TraitBuilder.getInstance().make(source, Trait.Type.STAMINA, d));
	}

	public ArrayList<Trait> getTraits() {
		return traits;
	}

	public Trait get(Trait.Type t) {
		switch (t) {
		case AGGRESSIVENESS:
			return aggressiveness;
		case COMBAT:
			return combat;
		case FERTILITY:
			return fertility;
		case HUNGER:
			return hunger;
		case SPEED:
			return speed;
		case STAMINA:
			return stamina;
		}

		return null;
	}

}
