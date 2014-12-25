package com.jvc.towerdefense.models;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class AsteroidShower {
	
	private Vector2 position;
	private float radius;
	private float power=2f;
	private float MAXTIME=3f;
	private float leftTime=3f;
	
	public AsteroidShower() {
		Random random = new Random();
		int x = random.nextInt(21);
		int y = random.nextInt(14);
		position = new Vector2(x, y);
		radius = 2;
	}
	
	public AsteroidShower(Vector2 pos, int radius) {
		position = pos;
		this.radius = radius;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getPower() {
		return power;
	}
	
	public void updateTime(float delta) {
		leftTime = leftTime - delta;
	}
	
	public float getLeftTime() {
		return leftTime;
	}
}
