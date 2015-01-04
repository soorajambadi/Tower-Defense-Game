package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;

public class TownCentre extends Tower {
	// Steam is stored in townCentre 
	// Food is stored in Food store

	 public static final float HEIGHT = 2f;
	 public static final float WIDTH = 2f;
	 public float RANGE = 3f;
	 public float STRENGTH = 7f;
	 public int level=1;
	 public float POWER=2f;
	 public float levelUpgradeCost = 250f;
	 public int UID=-1;
	 private float totalSteam;
	 private int enemiesFaced;
	 
	 public int getEnemiesFaced() {
		 return enemiesFaced;
	 }
	 
	 public float getLevelUpgradeCost() {
		 return levelUpgradeCost;
	 }
	 
	 public float getSteamConsumedforLevel() {
		 return level*1;
	 }
	 
	 public void addProducedSteam(float steam) {
		 totalSteam+=steam;
	 }
	 
	 public void consumeSteam(float consumedSteam) {
		 totalSteam-=consumedSteam;
		 if(totalSteam < 0.0f)
			 isAlive=false;
	 }

	 public TownCentre(Vector2 pos) {
		super(pos);
		totalSteam = 1000f;
		enemiesFaced = 0;
         name = "tower0";
	 }
	 
	 public void addEnemiesFaced(int delta) {
		 enemiesFaced = enemiesFaced + delta;
	 }
	 
	 public TownCentre() {
		 System.out.println("Towncentre constructor");
	 }

	 public float getTotalSteam() {
		 return totalSteam;
	 }
	 
	 public void takeHit(float damage) {
	    	return;
	    	// Currently townCentre doesn't take any hit.
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
	    
	    public void update(float delta) {
	    	super.update(delta);
	    	consumeSteam(InstanceManager.getInstance().getSteamConsumptionRate()*delta);
	    }

}
