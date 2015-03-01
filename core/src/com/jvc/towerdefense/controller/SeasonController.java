package com.jvc.towerdefense.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.models.Enemy;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.World;

public class SeasonController {

	// Season 1 -> Peaceful Season
	// Season 2 -> Alien Season
	// Season 3 -> Earthquake
	// Season 4 -> Spring Season
	public World world;
    float cTime1=0.0f;
    float cTime2=0.0f;
    Array<Tower> toBeAttackedTower;
	Array<Link> toBeAttackedLink;
	public SeasonController(World world) {
		this.world = world;
		cTime1=0.0f;
		cTime2=0.0f;
		toBeAttackedTower = new Array<Tower>();
		toBeAttackedLink = new Array<Link>();
	}
	
	public void update() {
		cTime1 = world.getcurrentSeasonTime();
		//System.out.println("cTime is " + world.getcurrentSeasonTime());
		if(cTime1 >= world.getMaxSeasonTime()) {
			world.setcurrentSeasonTime(0.0f);
			world.setCurrentSeason(world.getCurrentSeason()+1);
			cTime2=0.0f;
			cTime1 = 0.0f;
			// Here we are resetting cTime1 with setcurrentSeasonTime, so resetting cTime2 also.
		}
		if(world.getCurrentSeason() == 4)
			world.setCurrentSeason(1);
            //System.out.println("In Spring season");
		else if(world.getCurrentSeason() == 2) {
			toBeAttackedTower = world.getTowersToBeAttacked(3, world.getTowers());
			toBeAttackedLink = world.getLinksToBeAttacked(3, world.getLinks());
			//System.out.println("time1 to time2 ");
			//System.out.println(cTime1 + " " + cTime2);
			if(cTime1 - cTime2 >= world.getMaxSeasonTime()/5) {
				if(toBeAttackedTower!=null) {
					for(Tower t : toBeAttackedTower) {
						Vector2 velocity = new Vector2(5, 2).sub(t.getPosition()).scl(-1).scl(.5f);
						// Changed from mul to scl
						Enemy newEnemy = new Enemy(new Vector2(5, 2), velocity);
						world.getEnemies().add(newEnemy);
						if(newEnemy.inRange(t))
							t.takeHit(newEnemy.POWER);
						world.getTownCentre().addEnemiesFaced(1);
						/* if(t.isAlive == false) {
							for(Link l : t.getTowerLinks())
							{
								l.t1.removeLink(l);
								l.t2.removeLink(l);
								world.getLinks().removeValue(l, false);
							}
							world.getTowers().removeValue(t, false);
						}
						*/
					}
				}
				
				if(toBeAttackedLink!=null) {
					for(Link l : toBeAttackedLink) {
						Vector2 velocity = new Vector2(5, 2).sub(l.getStartPosition()).scl(-1).scl(.5f);
						Enemy newEnemy = new Enemy(new Vector2(5, 2), velocity);
						world.getEnemies().add(newEnemy);
					}
				}
				cTime2 = cTime1;
			}
		}
		else if(world.getCurrentSeason() == 3) {
			if(cTime1 - cTime2 >= world.getMaxSeasonTime()/3) {
				//world.destroyedByEarthquake();
				world.destroyedByAsteroidShower();
				cTime2 = cTime1;
			}
		}
	}
}
