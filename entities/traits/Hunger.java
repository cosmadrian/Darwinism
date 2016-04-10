package entities.traits;

public class Hunger implements Trait {

	int value;

	@Override
	public String getName() {
		return "Hunger";
	}

	@Override
	public void update() {
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
	}

}
