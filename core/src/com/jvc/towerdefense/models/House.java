package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;

public class House extends Entity {

	public Vector2 position;
	public static float WIDTH = 1f;
	public static float HEIGHT = 1f;
	public static int CAPACITY = 10;
	public static float STRENGTH = 20f;
	public static float cost=10f;
	
	public House(Vector2 pos) {
		super();
		this.position = pos;
		//cost = 20f;
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
	
	public void updateHouse() {
		CAPACITY = CAPACITY + 10;
		STRENGTH = STRENGTH + 10;
	}
	
	public void showGUI() {
		
	}
	
	/*public boolean handleInput(float x, float y, Entity entity) {
		super.handleInput(x, y);
			if(isSelected) {
				showGUI();
			}
		return false;
	}*/
}
