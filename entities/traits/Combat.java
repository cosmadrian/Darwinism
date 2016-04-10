package entities.traits;

public class Combat implements Trait {

	private int value;

	@Override
	public String getName() {
		return "Combat";
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public void update() {

	}

}
