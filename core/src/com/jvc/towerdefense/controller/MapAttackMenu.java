package com.jvc.towerdefense.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.misc.EventHandler;
import com.jvc.towerdefense.models.DefenseTower;
import com.jvc.towerdefense.models.Soldier;
import com.jvc.towerdefense.models.TargetWorld;

public class MapAttackMenu implements EventHandler{
	
	int currentAction=-1;
	TargetWorld world;
	float rate;
	// You need to handle rate within each tower
	public MapAttackMenu(TargetWorld world) {
		this.world = world;
	}
	
	public boolean processInput(int x, int y) {
		if(currentAction==-1)
			return false;
		if(InstanceManager.getInstance().getSoldiers().size > 0) {
			world.soldiers.add(new Soldier(new Vector2(x, y), currentAction));
			InstanceManager.getInstance().getSoldiers().removeIndex(0);
		}
		else 
			System.out.println("All soldiers are abroad ");
		return false;
	}

	@Override
	public void HandleEvent(int action) {
		currentAction=action;
		
	}

	public void update(float delta) {
		for(Soldier s : world.soldiers) {
			s.attackEveryEnemy(delta, world.defenseTowers);
			s.update(delta);
			// Insert code to let towers attack soldiers		
		}
		
		// Fix this or this wont work fine.
		for(DefenseTower d: world.defenseTowers) {
			rate = d.RATE;
			d.currentTime+=delta;
			if(d.currentTime>d.maximumTime)
				d.currentTime=d.maximumTime;
			// This is actually wrong
			for(Soldier s : world.soldiers) {
				// If you rearrange these you can optimize the game
				if(d.getPosition().dst(s.getPosition()) < d.RANGE) {
					if(d.currentTime >= d.RATE) {
						s.takeHit(d.RATE*d.POWER);
						if(s.isAlive() == false)
							world.soldiers.removeValue(s, false);
						d.currentTime-=d.RATE;
					}
				}
			}
		}
		if(world.defenseTowers.size == 0) {
			System.out.println("You won");
			((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().savedScreen);
		}
		
	}

}
