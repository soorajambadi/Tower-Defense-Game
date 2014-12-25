package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Link {
    
    public Tower t1;
    public Tower t2;
    public Vector2 t1pos;
    public Vector2 t2pos;
    private Vector2 vel;
    private float rotation;
    public static final float WIDTH = .5f;
    public static final float HEIGHT = .25f;
    public float STRENGTH=20f;
    public float currentStrength;
    private float scaling;
    public int UID;
    public static int lastUID=0;
    public int upgradeCostUnit = 2;
    public float length;
    public boolean isAlive;
    public float linkCost=100f;
    
    public Link() {
    	System.out.println("Link Constructor");
    }
    public Link(Tower t1, Tower t2) {
		this.t1 = t1;
		this.t2 = t2;
		t1pos = t1.getPosition();
		t2pos = t2.getPosition();
		vel = new Vector2(t1pos).sub(t2pos);
		length = vel.len();
		rotation = vel.angle();
		/* We manipulate scale and rotation for rendering purposes */
		scaling = vel.len();
		this.UID = lastUID + 1;
		lastUID = lastUID + 1;
		this.isAlive = true;
		currentStrength = STRENGTH;
    }
    
    public float getUpgradeCost() {
    	return length*upgradeCostUnit;
    }
    
    public float getRepairCost() {
    	return (STRENGTH-currentStrength)*linkCost*length/STRENGTH;
    }
    
    public float getMaxStrength() {
    	return STRENGTH;
    }
    
    public float getStrength() {
    	return currentStrength;
    }
    
    public Vector2 getStartPosition() {
    	return t1pos;
    }
    
    public Vector2 getEndPosition() {
    	return t2pos;
    }
    
    public float getRotation(float ppuX, float ppuY) {
    	float angle = (float)Math.atan2(vel.y*ppuY, vel.x*ppuX) * MathUtils.radiansToDegrees;
		if (angle < 0) angle += 360;
			return angle;
    }
    
    public float getScaling(float ppuX, float ppuY) {
    	return (float)Math.sqrt((vel.x*ppuX * ppuX *vel.x) + (vel.y *ppuY * ppuY *vel.y)); 
    }
    
    public void takeHit(float damage) {
    	currentStrength-=damage;
    	if(currentStrength<=0) {
    		isAlive = false;
    	}
    	//System.out.println("Current Strenght is "+ currentStrength);
    }
    
    public boolean isAlive() {
    	return isAlive;
    }
}
