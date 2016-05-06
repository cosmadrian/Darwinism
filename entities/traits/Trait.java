package entities.traits;

import entities.Individual;

public abstract class Trait {
	public static enum Type {
		AGGRESSIVENESS, FERTILITY, STAMINA, COMBAT, HUNGER, SPEED
	}
	
	protected int value = 0;
	protected Individual source;
	
	public Trait(Individual source){
		this.source = source;
	}
	
	public Trait(){}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public abstract void update();

	public abstract String getName();
}
