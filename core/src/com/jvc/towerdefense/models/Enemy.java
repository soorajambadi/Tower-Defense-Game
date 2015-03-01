package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;

public class Enemy {
    
    public static final float HEIGHT = 1f;
    public static final float WIDTH = 1f;
    public float STRENGTH = 20f;
    public float RANGE = 2f;
    public float RATE = .5f;
    public float currentStrength;
    public float POWER = 1f;
    public float stateTime = 0f;
    private boolean isAlive = true;
    public int level=1;
    public float SPEED = 1f;
    public float lastFireTime=0f;
    
    Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();
    Vector2 velocity = new Vector2();
    
    public Enemy(Vector2 pos, Vector2 vel) {
		this.position.x = MathUtils.floor(pos.x);
		this.position.y = MathUtils.floor(pos.y);
		this.bounds.x = pos.x;
		this.bounds.y = pos.y;
		this.bounds.height = HEIGHT;
		this.bounds.width = WIDTH;
		currentStrength = STRENGTH;
		this.velocity = vel;
		velocity.nor().scl(SPEED);
    }
    
    public Vector2 getVelocity() {
    	return velocity;
    }
    public Enemy() {
    	System.out.println("Enemy Constructor");
    }
    public Vector2 getPosition() {
    	return position;
    }
    public Rectangle getBounds() {
    	return bounds;
    }

    public void update(float delta) {
		position.add(velocity.x*delta, velocity.y*delta);
		stateTime = stateTime + delta;
		//System.out.println(position.x + " " + position.y + " " + velocity.x + " " + velocity.y);
    }
    
    public float getStateTime() {
    	return this.stateTime;
    }
    
    public void takeHit(float damage) {
		this.currentStrength = this.currentStrength - damage;
		if(this.currentStrength <= 0) {
		    this.isAlive = false;
		}
		//System.out.println(STRENGTH);
    }
    
    public void fireLink(Link link, float delta) {
    	lastFireTime+=delta;
    	if(lastFireTime>RATE) {
    		link.takeHit(POWER);
    		lastFireTime-=RATE;
    		
    	}	
    }
    
    public boolean isAlive() {
    	return isAlive;
    }
    
    public float getStrength() {
    	return currentStrength;
    }
    public boolean inRange(Tower tower) {
    	if(position.dst(tower.getPosition()) <= this.RANGE) {
    	    return true;
    	}
    	return false;
    }
    
    public boolean inRange(Link link) { 	
    	if(Intersector.distanceLinePoint(link.getStartPosition().x, link.getStartPosition().y, link.getEndPosition().x,
                link.getEndPosition().y, position.x, position.y) <= this.RANGE) {
    	    return true;
    	}
    	return false;
    }
    
    public void fireTower(Tower tower, float delta) {
    	lastFireTime+=delta;
    	if(lastFireTime>RATE) {
    		tower.takeHit(this.POWER);
    		lastFireTime-=RATE;
    	}
    }
    
    public int getLevel() {
    	return level;
    }
    
    public boolean upgradeLevel() {
    	level+=1;
    	STRENGTH+=10;
    	POWER+=2;
    	return true;
    }

}
