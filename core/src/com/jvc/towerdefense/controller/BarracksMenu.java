package com.jvc.towerdefense.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.misc.EventHandler;
import com.jvc.towerdefense.models.Soldier;
import com.jvc.towerdefense.models.Tower;

public class BarracksMenu implements EventHandler {

	public static int numberofOptions=3;
	public static int optHeight=1;
	public static int optWidth=1;
	public static int menuHeight=4;
	public static int menuWidth=4;
	public static int startPosX=17;
	public static int startPosY=5;
	
	public static Texture optTex1;
	public static Texture optTex2;
	public static Texture optTex3;
	public int selectedType=0;
	
	public BarracksMenu() 
	{
		optTex1 = optTex2 = optTex3 = new Texture("data/tower.png");
	}
	
	public boolean handleInput(float x, float y, WorldController controller, Tower activeTower) {
		x = x - (InstanceManager.getInstance().camPosition.x/InstanceManager.getInstance().ppuX
    			- InstanceManager.getInstance().WIDTH/2);
		y = y - (InstanceManager.getInstance().camPosition.y/InstanceManager.getInstance().ppuY
    			- InstanceManager.getInstance().HEIGHT/2);
		x = MathUtils.floor(x);
		y = MathUtils.floor(y);
		if(x == startPosX && y == startPosY) {
		    //System.out.println("Inside update menu");
		    //activeTower.STRENGTH += 1000;
		    activeTower.upgradeLevel(controller.getWorld());
		    return true;
		}
		else if(x == startPosX+1 && y == startPosY) {
		    //ystem.out.println("Inside 2nd menu");
		    //activeTower.RANGE+= 1000;
		    return true;
		}
		else if(x == startPosX+2 && y == startPosY) {
		    //System.out.println("Inside 3rd menu");
		    return true;
		}
		return false;
	}

	@Override
	public void HandleEvent(int action) {
		InstanceManager.getInstance().getSoldiers().add(new Soldier(new Vector2(5f, 5f), action));	
	}
}
