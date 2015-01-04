package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;

public class Store extends Entity {
	public static float WIDTH = 1f;
	public static float HEIGTH = 1f;
	public Vector2 position = new Vector2();
	public static int CAPACITY = 100;
	public static float STRENGTH = 20f;
	public static float cost=10f;
	public static float RANGE=3f;
	public float currentStorage=0;
	public float foodProductionRate=0;

    public Store() {
        System.out.println(" In Store Constructor");
    }

	public Store(Vector2 pos) {
		position = pos;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public float getCost() {
		return cost;
	}
	
	public float getCapacity() {
		return CAPACITY;
	}
	
	public void addToCurrentStorage(float stock) {
		currentStorage+=stock;
	}
	
	public void setCurrentStorage(float newStock) {
		currentStorage = newStock;
	}
	
	public float getCurrentStorage() {
		return currentStorage;
	}
	
	public float getFoodProductionRate() {
		foodProductionRate=0;
		for(Tree tree : InstanceManager.getInstance().getTrees()) {
			if(inRange(tree))
				foodProductionRate+=Tree.FRUITPRODUCTION;
		}
		return foodProductionRate;
	}
	
	public boolean inRange(Tree tree) {
		if(position.dst(tree.getPosition()) < RANGE) 
			return true;
		return false;
	}
	
	public void updateHouse() {
		CAPACITY = CAPACITY + 10;
		STRENGTH = STRENGTH + 10;
	}
}
