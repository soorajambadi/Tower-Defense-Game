package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.jvc.towerdefense.controller.TowerMenu;
import com.jvc.towerdefense.manager.InstanceManager;

public class Tower extends Entity {

    public String name;
    public static final float HEIGHT = 1f;
    public static final float WIDTH = 1f;
    public float RANGE = 3f;
    public float STRENGTH = 50f;
    public float RATE = .2f;
    public float currentStrength;
    public static float STEAMCONSUMPTION=.2f;
    public float lastFireTime=0;
    public float currentTime=0.0f;
    public float maximumTime = RATE*3f;
    //private Array<Bullet> bullets = new Array<Bullet>();
    private Array<Link> tLinks = new Array<Link>();
    private final Array<Bullet> activeBullets = new Array<Bullet>();
    
    private final transient Pool<Bullet> bulletPool = new Pool<Bullet>() {
    @Override
    
    protected Bullet newObject() {
        return new Bullet();
    }
    
    };
    
    public Tower() {
    	System.out.println("Tower Constructor");
    }
    
    public int bulletCount = 0;
    public boolean isSelected=false;
    public static int lastUID=0;
    public int UID;
    public int level=1;
	public float POWER=.5f;
	public float levelUpgradeCost =50f;
	public float towerCost=200f;
	public float productionRate = 1f;
    
    //private Vector2 position = new Vector2();
    private Rectangle bounds = new Rectangle();
    public TowerMenu towerMenu;
    public boolean isAlive;
    public static float cost=50f;
    
    public Tower(Vector2 pos) {
        this.position = new Vector2();
		this.position.x = MathUtils.floor(pos.x);
		this.position.y = MathUtils.floor(pos.y);
		currentStrength = STRENGTH;
		this.bounds.width = WIDTH;
		this.bounds.height = HEIGHT;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.isAlive = true;
		this.UID = lastUID + 1;
		lastUID = lastUID + 1;
		towerMenu = new TowerMenu();
		currentTime=0.0f;
        name = InstanceManager.getInstance().getNewTowerName();
    }
    
    public Vector2 getPosition() {
    	return this.position;
    }
    
	public float getLevelUpgradeCost() {
		return levelUpgradeCost;
	}

    public Rectangle getBounds() {
    	return bounds;
    }
    
    public Array<Link> getTowerLinks() {
    	return tLinks;
    }
    /* pass whole array of links and it returns links with this tower */
    public Array<Link> getLinks( Array<Link> links) {
    	Array<Link> rlinks = new Array<Link>();
    	for(Link link : links) {
    		if(link.getTower1() == this || link.getTower2() == this) {
    			if(link.isAlive)
    				rlinks.add(link);
    		}
    	}
		return rlinks;
    	
    }
    
    public void addLink(Link link) {
    	tLinks.add(link);
    }
    
    public void removeLink(Link link) {
    	tLinks.removeValue(link, false);
    }
    
    public boolean Covers(Vector2 pos) {
	/*if((getPosition().x == pos.x) && (getPosition().y == pos.y) || (getPosition().x + 1 == pos.x) && (getPosition().y == pos.y)
			|| (getPosition().x -1 == pos.x) && (getPosition().y  == pos.y) || (getPosition().x == pos.x) && (getPosition().y + 1 == pos.y)
			|| (getPosition().x -1  == pos.x) && (getPosition().y + 1 == pos.y) || (getPosition().x +1  == pos.x) && (getPosition().y + 1 == pos.y)
			|| (getPosition().x -1 == pos.x) && (getPosition().y - 1 == pos.y) || (getPosition().x + 1 == pos.x) && (getPosition().y - 1 == pos.y) || 
			(getPosition().x == pos.x) && (getPosition().y - 1 == pos.y)) */
    	if((getPosition().x == pos.x) && (getPosition().y == pos.y))
    		return true;
    	return false;
    }
  
    public void update(float delta) {
    	updateBullets(delta);
    }
    
    public float getCurrentStrength() {
    	return currentStrength;
    }
    
    public void updateBullets(float delta) {
    	Bullet bullet;
		if(ifBullets()) {
			int len = activeBullets.size;
			for (int i = len; --i >= 0;) {
				bullet = activeBullets.get(i);
			    bullet.update(delta);
			    if(bullet.alive == false) {
			    	activeBullets.removeIndex(i);
			    	bulletPool.free(bullet);
			    }
			}
		}
		
    }
    
    public boolean inRange(Enemy enemy) {
    	if(position.dst(enemy.getPosition()) <= this.RANGE) {
    		return true;
    	}
    	return false;
    }
    
    public float getMaxStrength() {
    	return this.STRENGTH;
    }
    public float getStrength() {
    	return currentStrength;
    }
    
    public void fireBullets(Enemy enemy, float delta) {
    	lastFireTime+=delta;
    	Vector2 target = enemy.getPosition();
    	if(lastFireTime>RATE) {
	    	enemy.takeHit(POWER);
	    	//Bullet nBullet = bulletPool.obtain();
	    	//nBullet.init(getPosition(), target);
	    	//activeBullets.add(nBullet);
	    	lastFireTime-=RATE;
	    	InstanceManager.getInstance().explosionParticles.addSmallExplosion(
    			new Vector2(target.x*InstanceManager.getInstance().ppuX, target.y*InstanceManager.getInstance().ppuY));
    	//isAlive = false;
    	}
		//bullets.add(new Bullet(new Vector2(this.getPosition()), target));
		//bulletCount=1;
    }
    
    public Array<Bullet> getBullets() {
    	return activeBullets;
    }
    
    public boolean ifBullets() {
		if(activeBullets.size>0)
		    return true;
		return false;
    }
    
    public void takeHit(float damage) {
    	this.currentStrength -= damage;
    	if(this.currentStrength <= 0) {
    	    this.isAlive = false;
    	}
    }
    
    public boolean upgradeLevel(World world) {
    	float neededResources = getLevelUpgradeCost();
    	if(world.getTownCentre().getTotalSteam() >= neededResources) {
    		world.getTownCentre().consumeSteam(neededResources);
    		level=level+1;
    		RANGE+=1;
    		STRENGTH+=10;
    		POWER+=1;
    		currentStrength = STRENGTH;
    		productionRate+=1;
    		return true;
    	}
    	return false;
    }
    
    public float getRepairCost() {
    	return (getMaxStrength()-getStrength())*towerCost/getMaxStrength();
    }
    
}
