package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;

public class Tree extends Entity {
	public static float WIDTH = 1f;
	public static float HEIGHT = 2f;
	public static float FRUITPRODUCTION=1f;

    public Tree() {
        System.out.println(" In Tree Constructor");
    }

	public Tree(Vector2 pos) {
		this.position = new Vector2(pos);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public float getFruitProduction() {
		return FRUITPRODUCTION;
	}
}
