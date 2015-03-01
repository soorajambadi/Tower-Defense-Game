package com.jvc.towerdefense.attackai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.models.DefenseTower;
import com.jvc.towerdefense.models.Enemy;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.World;

public class SteamAttackers {

	World world;
	Array<Link> toBeAttackedLink;
	Array<Tower> toBeAttackedTower;
	public SteamAttackers(World world) {
		this.world = world;
		toBeAttackedLink = new Array<Link>();
		toBeAttackedTower = new Array<Tower>();
		
	}
	
	public void AttackTowersAndLinks(float delta) {
		// Need to control the amount of hits by the enemy
				// Establish a bound for firing rate in enemies
			    // Enemy attacks goes here
				for (Enemy enemy: world.getEnemies()) {
					for(Link l : toBeAttackedLink) {
						if(enemy.inRange(l))
							enemy.fireLink(l, delta);
							//l.takeHit(enemy.POWER);
						if(l.isAlive() == false) {
							world.getLinks().removeValue(l, false);
							toBeAttackedLink.removeValue(l, false);
							l.getTower1().removeLink(l);
							l.getTower2().removeLink(l);
						}
					}
					
					for(DefenseTower t : InstanceManager.getInstance().getDefenseTowers()) {
						if(enemy.inRange(t))
							enemy.fireTower(t, delta);
							//t.takeHit(enemy.POWER);
						if(t.isAlive == false) {
							InstanceManager.getInstance().getTileManager().
							addTile(MathUtils.floor(t.getPosition().x)*21 + MathUtils.floor(t.getPosition().y), null);
							InstanceManager.getInstance().getDefenseTowers().removeValue(t, false);
							toBeAttackedTower.removeValue(t, false);
						}
						// Defense Tower doesn't have any links
					}

                    for(Tower t : InstanceManager.getInstance().getTowers()) {
                        if(enemy.inRange(t))
                            enemy.fireTower(t, delta);
                        //t.takeHit(enemy.POWER);
                        if(t.isAlive == false) {
                            InstanceManager.getInstance().getTileManager().
                                    addTile(MathUtils.floor(t.getPosition().x)*21 + MathUtils.floor(t.getPosition().y), null);
                            InstanceManager.getInstance().getTowers().removeValue(t, false);
                            toBeAttackedTower.removeValue(t, false);
                        }
                        // Defense Tower doesn't have any links
                    }
				}
	}
}
