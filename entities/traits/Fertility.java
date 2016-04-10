package entities.traits;

public class Fertility implements Trait {

	private int value;

	@Override
	public String getName() {
		return "Fertility";
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
