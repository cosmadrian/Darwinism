package entities;

import java.util.Random;

import entities.traits.Trait;

public class DNA {
	public static final int TRAIT_COUNT = Trait.Type.values().length;
	public static final int BYTES_PER_TRAIT = 1;
	
	private String strand;
	
	public DNA(String s){
		this.strand = s;
	}

	public static String generateStrand() {
		char[] text = new char[TRAIT_COUNT * BYTES_PER_TRAIT];
		Random r = new Random();

		for (int i = 0; i < TRAIT_COUNT * BYTES_PER_TRAIT; i++) {
			text[i] = (char)r.nextInt(255);
		}

		return new String(text);
	}
	
	public int getTraitInfluence(Trait.Type t){
		return 0;
	}
	
	/* TODO */
	public String mutate(){
		return strand;
	}
	
	/* TODO */
	public DNA combine(DNA male, DNA female){
		return new DNA(DNA.generateStrand());
	}
}
