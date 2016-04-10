package entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import entities.traits.Trait;
import entities.traits.TraitBuilder;

public abstract class Individual extends Entity {

	private DNA dna;
	private ArrayList<Trait> traits = new ArrayList<Trait>();

	public Individual(DNA d) {
		this.dna = d;
		for(Trait.Type t : Trait.Type.values()){
			traits.add(TraitBuilder.getInstance().make(t,d));
		}
	}

	public void render(Graphics2D g) {

	}

	@Override
	public String toString() {
		String x = "ID: " + this.id + "\n";
		for(Trait t : traits){
			x += "* " + t.getName() + ": " + t.getValue() + "\n";
		}
		return x;
	}

}
