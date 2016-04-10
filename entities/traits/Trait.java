package entities.traits;

public interface Trait {
	public static enum Type{
		AGGRESSIVENESS,
		FERTILITY,
		STAMINA,
		COMBAT,
		HUNGER,
	}
	
	public String getName();
	public int getValue();
	public void setValue(int value);
	public void update();
}
