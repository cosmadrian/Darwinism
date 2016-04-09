package map;

public class MapGenerator {

	private static MapGenerator instance;

	private MapGenerator() {

	}

	public static MapGenerator getInstance() {
		if (instance == null) {
			instance = new MapGenerator();
		}

		return instance;
	}
	
	public Map generate(){
		return new Map();
	}

}
