package entities.traits;

public class Stamina implements Trait {

	private int value;
	
	@Override
	public String getName() {
		return "Stamina";
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
