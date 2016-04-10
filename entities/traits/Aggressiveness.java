package entities.traits;

public class Aggressiveness implements Trait {

	private int value;

	@Override
	public String getName() {
		return "Aggressiveness";
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
