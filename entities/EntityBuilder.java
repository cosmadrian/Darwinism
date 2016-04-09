package entities;

public class EntityBuilder {
	private EntityBuilder instance = null;
	
	public EntityBuilder getInstance(){
		if(instance == null){
			instance = new EntityBuilder();
		}
		return instance;
	}
	
	public Entity make(Entity.Type t){
		return null;
	}
}
