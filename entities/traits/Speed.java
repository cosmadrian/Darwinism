package entities.traits;

public class Speed implements Trait {

	private int value;
	
	@Override
	public String getName() {
		return "Speed";
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
