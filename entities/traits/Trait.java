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
	public void update();
}
