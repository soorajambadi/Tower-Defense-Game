package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;

public class Tree extends Entity {
	public static float WIDTH = 1f;
	public static float HEIGHT = 2f;
	public static float FRUITPRODUCTION=1f;
	private Vector2 pos;
	
	public Tree(Vector2 pos) {
		this.pos = pos;
	}
	
	public Vector2 getPosition() {
		return pos;
	}
	
	public float getFruitProduction() {
		return FRUITPRODUCTION;
	}
}
