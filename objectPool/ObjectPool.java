package objectPool;

import java.util.Stack;

import entities.Entity;

public class ObjectPool {
	
	private static ObjectPool instance = null;
	private Stack<Entity> graveyard;
	
	private ObjectPool(){
		graveyard = new Stack<Entity>();
	}
	
	public static ObjectPool getInstance(){
		if(instance  == null){
			instance = new ObjectPool();
		}
		return instance;
	}
	
	public void bury(Entity e){
		e.reset();
		graveyard.push(e);
	}
	
	/*TODO: add type of entity + builders if null */
	public Entity resurrect(Entity e){
		if(graveyard.isEmpty()){
			return null;
		}
		
		return graveyard.pop();
	}

}
