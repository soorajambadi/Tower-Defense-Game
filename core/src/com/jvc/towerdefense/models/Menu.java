package com.jvc.towerdefense.models;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.TowerDefense;
import com.jvc.towerdefense.controller.BarracksMenu;
import com.jvc.towerdefense.controller.WorldController;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.MapManager;
import com.jvc.towerdefense.screens.BarracksScreen;
import com.jvc.towerdefense.screens.JunkScreen;

public class Menu {
    
    private World world;
    
    public Menu(World world) {
    	this.world = world;
    }
    
    public Menu() {
    	System.out.println("Menu constructor");
    }

    public boolean handleInput(float x, float y, WorldController controller) {
		System.out.println("x" + x + " " + "y" + " " + y);
		//x = MathUtils.floor(x);
		//y = MathUtils.floor(y);
		x = x - (InstanceManager.getInstance().camPosition.x/InstanceManager.getInstance().ppuX
    			- InstanceManager.getInstance().WIDTH/2);
		y = y - (InstanceManager.getInstance().camPosition.y/InstanceManager.getInstance().ppuY
    			- InstanceManager.getInstance().HEIGHT/2);
		x = MathUtils.floor(x);
		y = MathUtils.floor(y);
		System.out.println("x" + x + " " + "y" + " " + y);
		if(x<1 && y<1 &&  y>=0 && x>=0) {
		    //System.out.println("Inside build enemy");
		    controller.setActiveAction(1); /*build enemy action active */
		    return true;
		}
		else if(x<1 && y<2 && y>=1 && x>=0) {
		    //System.out.println("Inside build Wind mill");
		    //controller.setActiveAction(2); /*build Tower action Active*/
			MapManager.getInstance().handleMapAndWar();
		    return true;
		}
		else if(x<1 && y<3 && y>=2 && x>=0) {
		    //System.out.println("Inside buildLink");
		    controller.setActiveAction(3); /*build Link action Active*/
		    return true;
		}
		else if(x<1 && y<4 && y>=3 && x>=0) {
		    //System.out.println("Inside buildTower");
		    //controller.setActiveAction(6); /*build Tower action Active*/
			
			JunkScreen newScreen = new JunkScreen(controller);
		    ((Game) Gdx.app.getApplicationListener()).setScreen(newScreen);
		    //TowerDefense.currentScreen = newScreen;
		    //For this first we need to implement resize in JunkScreen
		    //((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().getGameScreen());
		    return true;
		}
		else if(x<1 && y<5 && y>=4 && x>=0) {
		    //System.out.println("Inside buildTower");
			BarracksMenu bMenu = new BarracksMenu();
			BarracksScreen bScreen = new BarracksScreen(bMenu);
			((Game) Gdx.app.getApplicationListener()).setScreen(bScreen);
		    //controller.setActiveAction(7); /*build Tower action Active*/
		    return true;
		}
		else if(controller.getActiveAction() == -1 || controller.getActiveAction()==5 || controller.getActiveAction()==4) {
			if(world.getActiveTower(new Vector2(x, y))!=-1) {
				controller.setActiveAction(4);
				// returns non negative if we selected a tower.
			}
			else if(world.getTownCentre().getPosition().x == Math.floor(x) && 
					world.getTownCentre().getPosition().y == Math.floor(y)) {
				controller.setActiveAction(5);
			}
			return false;
			// only if we return false other input handler in WorldController will work.
			//controller.setActiveTower(world.getActive(controller.getActivePortion()));	
		}
	    return false;
    }
}
