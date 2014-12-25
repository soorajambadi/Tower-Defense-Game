package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;

public class Person {

	public static float HEIGHT = .5f;
	public static float WIDTH = .25f;
	private static float STRENGTH = 10f;
	private static float POWER = 1f;
	private static float SPEED = 3f;
	public static float FOODCONSUMPTION = .2f;
	private Vector2 position;
	public Vector2 velocity;
	private float rotation;
	
	public Person(Vector2 pos) {
		this.position = pos;
		this.velocity = new Vector2(InstanceManager.getInstance().random.nextInt(21), 
				InstanceManager.getInstance().random.nextInt(14)).nor().scl(SPEED);
		//velocity = new Vector2(2f, 2f);
		rotation = velocity.angle();
	}
	
	public void update(float delta) {
		position.add(velocity.x*delta, velocity.y*delta);
		//InstanceManager.getInstance().bigBubbleParticles.addParticle(new Vector2(position.x*20, position.y*20));
		//InstanceManager.getInstance().sparkParticles.addLaserExplosion(new Vector2(position.x*20, position.y*20), 
				//new Vector2(velocity.x*20, velocity.y*20));
		//InstanceManager.getInstance().bubbleParticles.addParticle(new Vector2(position.x*20, position.y*20));
		if (position.x > 21 || position.x < 0 || position.y > 14 || position.y < 0) {
			 	int r1 = InstanceManager.getInstance().random.nextInt(10);
			 	int r2 = InstanceManager.getInstance().random.nextInt(10);
			 	r1=r2=1;
			 	if(r1 > 5)
			 		r1 = -1;
			 	if (r2 > 5)
			 		r2 = -1;
		    	//velocity.x = InstanceManager.getInstance().random.nextInt(21)*r1*1.0f;
		    	//velocity.y = InstanceManager.getInstance().random.nextInt(14)*r2*1.0f;
			 	velocity.scl(-1f);
		    	velocity.nor().scl(SPEED);
		    	rotation = velocity.angle();
		 }
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public float getRotation() {
		return rotation;
	}
}
