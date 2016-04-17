package entities.traits;

public abstract class Trait {
	public static enum Type {
		AGGRESSIVENESS, FERTILITY, STAMINA, COMBAT, HUNGER, SPEED
	}

	protected int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public abstract void update();

	public abstract String getName();
}
