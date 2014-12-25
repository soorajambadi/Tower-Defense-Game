package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.manager.InstanceManager;

public class Soldier extends Enemy {
	
	public int type;
	public float totalTime=0f;
	public float maxTime=RATE*3;
	// This is to rate limit maximum number of bullets it can hold
	// Type 1 is knight
	// Type 2 is spearman
	// Type 3 is archer

	public void attackEveryEnemy(float delta, Array<DefenseTower> dTowers) {
		// Fix directions in moveToPosition
		totalTime+=delta;
		// Limit maximum no of bullets they can carry
		if(totalTime>maxTime)
			totalTime = maxTime;
		if(totalTime>=RATE) {
			DefenseTower nearest = getNearestDefenseTower(dTowers);
			if(nearest!=null) { 
					//&& nearest.getPosition().dst(position) < RANGE) {
				moveToPosition(nearest.getPosition());
				if(nearest.getPosition().dst(position) < RANGE) {
					nearest.takeHit(POWER*RATE);
					// Need to take care of this and see if this is correct.
					if(!nearest.isAlive)
					{
						dTowers.removeValue(nearest, false);
					}
					velocity = new Vector2(0f, 0f);
					totalTime-=RATE;
				}
			}
		}
		
	/*	for(DefenseTower d : InstanceManager.getInstance().getDefenseTowers()) {
			if(totalTime>RATE) {
				if(position.dst(d.getPosition()) < RANGE) {
					d.takeHit(POWER*totalTime);
					totalTime-=RATE;
					//InstanceManager.getInstance().explosionParticles.addSmallExplosion(d.getPosition());
				}
			}
		}
	*/
	}
	
	private DefenseTower getNearestDefenseTower(Array<DefenseTower> dTowers) {
		float lowestDist=100f;
		DefenseTower nearest = null;
		for(DefenseTower d : dTowers) {
			if(position.dst(d.getPosition()) < lowestDist) {
				lowestDist = position.dst(d.getPosition());
				nearest = d;
			}
		}
		return nearest;
	}

	public Soldier(Vector2 pos, int type) {
		super();
		this.position = pos;
		this.currentStrength = STRENGTH;
		this.type = type;
		velocity = new Vector2(.1f, .1f);
	}
	
	public Soldier() {
		
	}
	
	public void update(float delta) {
		super.update(delta);
	}
	
	public void moveToPosition(Vector2 pos) {
		Vector2 postmp = new Vector2(position);
		velocity = postmp.sub(pos).nor().scl(-1f);
	}
	
	public void setSoldierPositionAndType(Vector2 pos, int type) {
		this.position = pos;
		this.type = type;
	}
}
