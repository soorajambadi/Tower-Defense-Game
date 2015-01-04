package com.jvc.towerdefense.controller;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.attackai.SteamAttackers;
import com.jvc.towerdefense.manager.GraphManager;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.MapLoader;
import com.jvc.towerdefense.misc.EventHandler;
import com.jvc.towerdefense.models.Barracks;
import com.jvc.towerdefense.models.DefenseTower;
import com.jvc.towerdefense.models.Enemy;
import com.jvc.towerdefense.models.Entity;
import com.jvc.towerdefense.models.House;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Person;
import com.jvc.towerdefense.models.Soldier;
import com.jvc.towerdefense.models.Store;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.TownCentre;
import com.jvc.towerdefense.models.World;

/** most important todo move every index to UID rather than array index **/
public class WorldController implements EventHandler {
    /* I think the problem is with the round off */
	
    private World world;
    public GraphManager gManager;
	Array<Float> cI = new Array<Float>(2);
    public Vector2 touch1 = new Vector2(-1f, -1f);
    //public Vector2 touch2 = new Vector2(-1f, -1f);
    public int touchFlag = 0;
    public int buildPipe=0;
    public int buildTower=0;
    public static int timeTrack=0;
    public Vector2 activePortion = new Vector2(1,1);
    /* There are two activePortion the main one is in world, this one is temporary. */
    public int activeAction=-1;
    Random random;
    /* -1 -> no action */
    /* 1 -> create enemy */
    /* 2 -> create tower */
    /* 3 -> create link between 2 towers */
    /* 4 -> tower is selected */
    /* 6 -> defense Tower */
    /* 7 -> House */
    /* 8 -> Store */
    /* 9 -> Hospital */
    /* 10 -> Barracks */
    public int activeTower=-1; // Shows the index of currently active tower.
    Array<Tower> toBeAttackedTower = new Array<Tower>();
	Array<Link> toBeAttackedLink = new Array<Link>();
	MapLoader loader;
	SeasonController sController;
	SteamAttackers sAttackers;
        
    public WorldController(World world) {
		this.world = world;
	    gManager= new GraphManager(world);
	    random = new Random();
	    cI = new Array<Float>();
	    cI.add(0f);
	    cI.add(0f);
	    loader = new MapLoader("maps/level1", world);
	    loader.loadMapToGame();
	    sController = new SeasonController(world);
	    sAttackers = new SteamAttackers(world);
    }

    public WorldController() {
        System.out.println(" In World Controller Constructor ");
    }
    
    public void setActiveTower(int tower) {
    	activeTower = tower;
    }
    
    public World getWorld() {
    	return world;
    }
    
    public void touchDown(float x, float y, int pointer, int button) {
    	InstanceManager.getInstance().convertToMap(x, y, cI);
	    //processInput(cI.get(0), cI.get(1));
    	float x1;
    	float y1;
    	x1 = x + (InstanceManager.getInstance().camPosition.x/InstanceManager.getInstance().ppuX
    			- InstanceManager.getInstance().WIDTH/2);
    	y1 = y + (InstanceManager.getInstance().camPosition.y/InstanceManager.getInstance().ppuY
    			- InstanceManager.getInstance().HEIGHT/2);
    	processInput(x1, y1);
    	//InstanceManager.getInstance().handleInput(x1, y1);
    }
    
    public int getActiveAction() {
    	return activeAction;
    }
    
    public void setActiveAction(int val) {
    	activeAction = val;
    }
    
    public int getActiveTower() {
    	return activeTower;
    }
    
    public Vector2 getActivePortion() {
    	return world.getActivePortion();
    }
    
    public void setActivePortion(Vector2 vec) {
    	world.setActivePortion(vec);
    }
    
    void processInput(float x, float y) {
    	
    	if(InstanceManager.getInstance().currentSelection == null) {
	    	int x1 = MathUtils.floor(x);
	    	int y1 = MathUtils.floor(y);
	    	Entity ret = InstanceManager.getInstance().getTileManager().getTile(x1*21+y1);
	    	if (ret instanceof Store) {
	    		System.out.println("Food production is " + ((Store) ret).foodProductionRate);
	    	}
	    	if (ret instanceof Tower) {
	    		System.out.println("Steam production is " + ((Tower) ret).productionRate);
	    	}
	    	if (ret instanceof DefenseTower) {
	    		System.out.println("Range of defense tower is " + ((DefenseTower) ret).RANGE);
	    	}
	    	InstanceManager.getInstance().currentSelection = ret;
	    	System.out.println("current selection is " + ret);
	    	return;
    	}
    	
    	// Here check if input is handled by selected Entity else select the entity with the click
    	else {
    		Entity selection = InstanceManager.getInstance().currentSelection;
    		
    		if(selection instanceof DefenseTower) {
    			((DefenseTower) selection).towerMenu.handleInput(x, y, this, (DefenseTower) selection);
    			System.out.println("New input handler for defense Tower");
    		}
    		
    		else if(selection instanceof Tower) {
    			((Tower) selection).towerMenu.handleInput(x, y, this, (Tower) selection);
    			System.out.println("New input handler");
    		}
    	}
    	
    	
	    boolean towerMenuAction = false;
	    if(world.isInSameSquare(getActivePortion(), x, y)){
		    //if(activeAction == -1) {
		    setActivePortion(new Vector2(x, y));
		    setActiveAction(-1);
		    return;
		    //}
		}
	    if(world.isTowerCentreSelected()) {
	    	towerMenuAction = world.getTownCentre().towerMenu.handleInput(x, y, this, world.getTownCentre());
	    	if(!towerMenuAction)
	    		activeAction = -1;
	    }
	    else
	    	world.getTownCentre().isSelected=false;
	    if(activeTower!=-1) {
	    	try {
	    		towerMenuAction = world.getTowers().get(activeTower).towerMenu.handleInput(x, y, this, world.getTowers().get(activeTower));
	   		}
	    	catch(IndexOutOfBoundsException e) {
	    		activeTower=-1;
	    	}
	    }
	    /*else if(world.getActiveTower(activePortion)!=-1) {
	    	if (activeTower!=-1)
	    		world.getTowers().get(activeTower).isSelected=false;
	    	setActiveTower(world.getActiveTower(activePortion));	
	    	world.getTowers().get(activeTower).isSelected=true;
		    if(world.getTowers().get(activeTower).isSelected == true) {
		    	towerMenuAction = world.getTowers().get(activeTower).towerMenu.handleInput(x, y, 
		    			this, world.getTowers().get(activeTower));
		    }
	    }*/

	    //setActivePortion(new Vector2(x,y));
	     
		if(!towerMenuAction && !world.getMenu().handleInput(x, y, this)) {
			
		    if(activeAction == -1) {
		    	activePortion = new Vector2(x,y);
		    	world.getTownCentre().isSelected=false;
		    	if(activeTower!=-1)
		    		world.getTowers().get(activeTower).isSelected=false;
		    }
		    
		    if(activeAction == 1) {
				activePortion = new Vector2(x, y);
				world.createEnemy(x, y);
				activeAction=-1;
		    }
		    
		    if(activeAction == 2) {
				activePortion = new Vector2(x, y);
				if(Tower.cost<world.getTownCentre().getTotalSteam()) {
					world.getTownCentre().consumeSteam(Tower.cost);
					world.createTower(x, y);
				}
				activeAction=-1;
		    }
		    
		    if(activeAction == 6) {
		    	activePortion = new Vector2(x, y);
				if(DefenseTower.cost<world.getTownCentre().getTotalSteam()) {
					world.getTownCentre().consumeSteam(Tower.cost);
					world.createDefenseTower(x, y);
				}
				activeAction=-1;
		    }
		    if(activeAction == 8) {
		    	activePortion = new Vector2(x, y);
		    	if(Store.cost<world.getTownCentre().getTotalSteam()) {
		    		world.getTownCentre().consumeSteam(Store.cost);
		    		world.createStore(x, y);
		    	}
		    	activeAction=-1;
		    }
		    
		    if(activeAction == 10) {
		    	activePortion = new Vector2(x, y);
		    	if(Barracks.cost < world.getTownCentre().getTotalSteam()) {
		    		world.getTownCentre().consumeSteam(Barracks.cost);
		    		world.createBarracks(x, y);
		    	}
		    	activeAction=-1;
		    }
		    
		    if(activeAction == 7) {
		    	activePortion = new Vector2(x, y);
				if(House.cost<world.getTownCentre().getTotalSteam()) {
					world.getTownCentre().consumeSteam(Tower.cost);
					world.createHouse(x, y);
				}
				activeAction=-1;
		    }
		    
		    if(activeAction == 3) {
		    	// Change using this function
				int t1=-2, t2=-2; // We don't put -1 here as it represents town center
				t1 = world.getActiveTower(world.getActivePortion());
				//System.out.println("t1 is" + t1);
				t2 = world.getActiveTower(new Vector2(x, y));
				//System.out.println("t2 is" + t2);
				if(t1!=-2 && t2!=-2) {
					if(gManager.satisfyCosts(t1, t2))
						world.createLink2(t1, t2, gManager.getCosts(t1, t2));
				}
				activeAction = -1;
				activePortion = new Vector2(x,y);
				if (activeTower!=-1)
		    		world.getTowers().get(activeTower).isSelected=false;
		    	setActiveTower(world.getActiveTower(activePortion));
		    	if(world.getActiveTower(activePortion)!=-1) {
		    		world.getTowers().get(activeTower).isSelected=true;
		    	}
		    }
		    
		    if(activeAction == 4) {
		    	activePortion = new Vector2(x,y);
		    	if(world.getTownCentre().isSelected == true)
		    		world.getTownCentre().isSelected = false;
		    	if (activeTower!=-1)
		    		world.getTowers().get(activeTower).isSelected=false;
		    	setActiveTower(world.getActiveTower(activePortion));
		    	if(world.getActiveTower(activePortion)!=-1) {
		    		world.getTowers().get(activeTower).isSelected=true;
		    	}
		    }
		    
		    if(activeAction == 5) {
		    	activePortion = new Vector2(x,y);
		    	if (activeTower!=-1)
		    		world.getTowers().get(activeTower).isSelected=false;
		    	setActiveTower(-1);
		    	world.setActivePortion(activePortion);
		    	if(world.isTowerCentreSelected()) {
		    		world.getTownCentre().isSelected = true;
		    		//System.out.println("town centre selected");
		    		//System.out.println("X :" + world.getTownCentre().getPosition().x + "Y :" + world.getTownCentre().getPosition().y);
		    	}
		    }
	    }
		if(activeAction!=3)
	    	world.setActivePortion(activePortion);
    }
    
    public void update(float delta) {
	
    	// Defense Tower actions goes here. It fires at enemies
		for(DefenseTower tower : world.getDefenseTowers()) {
		    for(Enemy enemy : world.getEnemies()) {
			    if(tower.isPowered() == true && tower.inRange(enemy)) {
					tower.fireBullets(enemy, delta);
					//enemy.takeHit(tower.POWER*delta);
					if(!enemy.isAlive()) {
					    world.getEnemies().removeValue(enemy, false);
					}
			    } 
			}
		}
	
		// Towncentre actions goes here. It shoots enemies here.
		TownCentre tCentre = world.getTownCentre();
	    for(Enemy enemy : world.getEnemies()) {
		    if(tCentre.inRange(enemy)) {
				tCentre.fireBullets(enemy, delta);
				//enemy.takeHit(tCentre.POWER*delta);
				if(!enemy.isAlive()) {
				    world.getEnemies().removeValue(enemy, false);
				}
		    } 
		}
	    
	    // Soldiers attack enemies here.
	    for(Soldier s: InstanceManager.getInstance().getSoldiers()) {
	    	//s.attackEveryEnemy(delta, InstanceManager.getInstance().getDefenseTowers());
	    }
	
	    /*for(Enemy enemy : world.getEnemies()) {
			for(Tower tower : world.getTowers()) {
			   if(enemy.inRange(tower)) {
				   tower.takeHit(enemy.STRENGTH*delta);
				   //System.out.println("tower " + tower.UID + "strength is " + tower.STRENGTH);
				   if(!tower.isAlive) {
					   	if(world.getTowers().indexOf(tower, false) !=-1)
					   		activeTower=-1;
					    world.getTowers().removeValue(tower, false);
					}
			   }
			}
			// This makes townCentre take the hit.
			if(enemy.inRange(tCentre) && tCentre.isAlive) {
				tCentre.takeHit(enemy.STRENGTH*delta);
				   //System.out.println("TownCenter " + tCentre.UID + "strength is " + tCentre.STRENGTH);
			}
	    }*/
		
	    // Steams attackers attack towers and links here
	    sAttackers.AttackTowersAndLinks(delta);
		
		for(Tower tower : world.getTowers()) {
		    tower.update(delta);
		}
		
		for(Enemy enemy : world.getEnemies()) {
		    enemy.update(delta);
		    if(enemy.getPosition().x > 21 && enemy.getPosition().y > 14)
		    	world.getEnemies().removeValue(enemy, false);
		}
		
		for(Person p: InstanceManager.getInstance().getPeople()) {
			p.update(delta);
		}
		
		for(Soldier s : InstanceManager.getInstance().getSoldiers()) {
			s.update(delta);
		}
		
		// A lot happens here.
		InstanceManager.getInstance().update(delta);
		
		for(DefenseTower tower : world.getDefenseTowers()) {
			tower.update(delta);
		}
		// If game slows down you can limit updation timing here.
		tCentre.update(delta);
		gManager.update(delta);
		world.update(delta);
		sController.update();
		
    }

	@Override
	public void HandleEvent(int action) {
		// This will make sure which button in Texture was clicked.
		setActiveAction(action);
		System.out.println("action is :" + action);
		
	}

}
