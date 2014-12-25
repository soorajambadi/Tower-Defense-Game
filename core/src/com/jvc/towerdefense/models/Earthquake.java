package com.jvc.towerdefense.models;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Earthquake {
	
	private Vector2 startPosition;
	private Vector2 endPosition;
	public static float WIDTH=.2f;
	public static float HEIGHT=.2f;
	private Vector2 vel;
	private float rotation;
	private float scaling;
	private float timetoDisplay=1f;
	
	public Earthquake() {
		Random random = new Random();
		int x1 = random.nextInt(21);
		int x2 = random.nextInt(21);
		startPosition = new Vector2(x1,0);
		endPosition = new Vector2(x2, 14);
		vel = new Vector2(startPosition).sub(endPosition);
		rotation = vel.angle();
		/* We manipulate scale and rotation for rendering purposes */
		scaling = vel.len2();
	}
	
	public Vector2 getStartPosition() {
		return startPosition;
	}
	
	public Vector2 getEndPosition() {
		return endPosition;
	}
	
    public float getRotation() {
    	return rotation;
    }
    
    public float getScaling() {
    	return scaling;
    }

}
