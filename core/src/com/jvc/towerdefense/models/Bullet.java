package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.ResourceManager;

public class Bullet implements Poolable{
    
    private Vector2 velocity = new Vector2();
    private Vector2 position = new Vector2();
    private Vector2 target = new Vector2();
    public final static float SPEED = 100f;
    public final static float HEIGHT = .2f;
    public final static float WIDTH = .2f;
    private Rectangle bounds;
    public float rotation;
    public boolean alive=false;
    
    public Bullet() {
    	position = new Vector2();
    	target = new Vector2();
    	alive=false;
    	bounds = new Rectangle();
    }
    
    public void init(Vector2 pos, Vector2 target) {
        position.set(pos);
        target.set(target);
    	this.velocity = new Vector2(target).sub(pos);
    	this.velocity = this.velocity.nor().scl(Bullet.SPEED);
    	rotation = velocity.angle();
    	alive = true;
        bounds.set(target.x, target.y, .1f, .1f);
    }
    
    public Bullet(Vector2 pos, Vector2 target) {
		this.position = pos;
		this.target = new Vector2(target);
		this.velocity = new Vector2(target).sub(pos);
		this.velocity = this.velocity.nor().scl(Bullet.SPEED);
		rotation = velocity.angle();
		alive = true;
    }
    
    public void reset() {
        position.set(0,0);
        alive = false;
        velocity.set(0,0);
        rotation = 0f;
        target.set(0,0);
        bounds.set(0,0,0,0);
    }
    
    public void update(float delta) {
	    position.add(velocity.x * delta, velocity.y*delta);
	    //if(bounds.contains(position))
	    	//alive=false;
	    if (position.x > 21 || position.x < 0 || position.y > 14 || position.y < 0)
	    	alive=false;
	    //if(Math.round(position.x) == Math.round(target.x) && Math.round(position.y) == Math.round(target.y))
	    	//alive=false;
	    //if(position == target)
	    	//alive=false;
    }
    
    public Vector2 getPosition() {
	return this.position;
    }
    
    public Vector2 getTarget() {
    	return target;
    }
    
    public float getRotation() {
	return rotation;
	
    }

}
