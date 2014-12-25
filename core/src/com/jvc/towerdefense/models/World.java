package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.manager.GraphManager;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.misc.MapLoading;
import com.jvc.towerdefense.view.WorldRenderer;

public class World implements MapLoading{
    
    public Vector2 activePortion = new Vector2(-1,1);
    Array<Tower> towers;
    Array<Enemy> enemies = new Array<Enemy>();
    Array<Link> links = new Array<Link>();
    Array<DefenseTower> defenseTowers;
    Array<Store> stores;
    Array<House> houses;
    Array<Barracks> barracks;
    Array<Tree> trees;
    Menu menu = new Menu(this);
    TownCentre tCentre = new TownCentre(new Vector2(10, 7));
    Earthquake eQuake;
    AsteroidShower aShower;
    public float currentSeasonTime=0.0f;
    public float seasonMaxTime=30f;
    public int currentSeason=1;
    /* currentSeason
     *  1 -> Normal
     * 	2 -> Alien
     *  3 -> Earthquake
     *  4 -> reset to 1				
     */
    
    public Array<Tower> getTowers() {
    	return towers;
    }
    
    public void instantiateObject(char c, int i) {
    	if (c == 'O')
    		createTower( i%21, i/21);
    	if (c == 'H')
    		createHouse(i%21, i/21);
    	if (c == 'D')
    		createDefenseTower(i%21, i/21);
    	if (c == 'T')
    		createTree(i%21, i/21);
    	if (c == 'S')
    		createStore(i%21, i/21);
    	}
    
    public Array<DefenseTower> getDefenseTowers() {
    	return defenseTowers;
    }
    public boolean loadTowerFromFile(Array<Tower> towers) {
    	this.towers = towers;
    	return true;
    }
    
    public boolean loadLinksFromFile(Array<Link> links) {
    	this.links = links;
    	return true;
    }
    
    public int getCurrentSeason() {
    	return currentSeason;
    }
    
    public void update(float delta) {
    	currentSeasonTime+=delta;
    }
    
    public void setCurrentSeason(int season) {
    	currentSeason = season;
    }
    
    public float getMaxSeasonTime() {
    	return seasonMaxTime;
    }
    
    public float getcurrentSeasonTime() {
    	return currentSeasonTime;
    }
    
    public void setcurrentSeasonTime(float time) {
    	currentSeasonTime = time;
    }
    
    public Menu getMenu() {
    	return menu;
    }
    
    public Earthquake getEarthquake() {
    	return eQuake;
    }
    
    public TownCentre getTownCentre() {
    	return tCentre;
    }
    
    public void setActivePortion(Vector2 activePortion) {
    	this.activePortion = activePortion;
    }
    
    public Vector2 getActivePortion() {
    	return activePortion;
    }
    
    public Array<Enemy> getEnemies() {
    	return enemies;
    }
    
    public Array<Link> getLinks() {
    	return links;
    }
    
    public World() {
    	towers = InstanceManager.getInstance().getTowers();
    	defenseTowers = InstanceManager.getInstance().getDefenseTowers();
    	enemies = InstanceManager.getInstance().getEnemies();
    	links = InstanceManager.getInstance().getLinks();
    	stores = InstanceManager.getInstance().getStores();
    	houses = InstanceManager.getInstance().getHouses();
    	trees = InstanceManager.getInstance().getTrees();
    	barracks = InstanceManager.getInstance().getBarracks();
    	//createDemoWorld();
    }
    
	public void destroyedByEarthquake() {
		eQuake = new Earthquake();
		Vector2 intersection = new Vector2(-1,-1);
		for(Link l : links) {
			if(Intersector.intersectLines(l.getStartPosition(), l.getEndPosition(), 
					eQuake.getStartPosition(), eQuake.getEndPosition(), intersection)) {
				links.removeValue(l, false);
				l.t1.removeLink(l);
				l.t2.removeLink(l);
			}
		}
		for(Tower t: towers) {
			if(Intersector.distanceLinePoint(eQuake.getStartPosition().x, eQuake.getStartPosition().y, eQuake.getEndPosition().x,
                    eQuake.getEndPosition().y, t.getPosition().x, t.getPosition().y )<.1f) {
				//if(t.isSelected) Handle this
				for(Link l: t.getTowerLinks()) {
					l.t1.removeLink(l);
					l.t2.removeLink(l);
				}
				towers.removeValue(t, false);
				System.out.println("Earth quake removed me");
			}
		}
	}
	
    public AsteroidShower getAsteroidShower() {
    	return  aShower;
    }
	
	public void destroyedByAsteroidShower() {
		aShower = new AsteroidShower();
		//Vector2 intersection = new Vector2(-1, -1);
		for(Link l : links) {
			if(Intersector.distanceLinePoint(l.getEndPosition().x, l.getEndPosition().y, l.getStartPosition().x,
                    l.getStartPosition().y, aShower.getPosition().x, aShower.getPosition().y) < aShower.getRadius()) {
				links.removeValue(l, false);
				l.t1.removeLink(l);
				l.t2.removeLink(l);
			}
		}
		for(Tower t: towers) {
			if(t.getPosition().dst(aShower.getPosition())<aShower.getRadius()) {
				//if(t.isSelected) Handle this
				for(Link l: t.getTowerLinks()) {
					l.t1.removeLink(l);
					l.t2.removeLink(l);
				}
				towers.removeValue(t, false);
				System.out.println("Asteroid Shower removed me");
			}
		}
	}
    
    private void createDemoWorld() {
		for(int i=0; i<3; i++) {
		    towers.add(new Tower(new Vector2(i+7, i+7)));
		    InstanceManager.getInstance().getTrees().add(new Tree(new Vector2(i+2, i+2)));
		    InstanceManager.getInstance().getPeople().add(new Person(new Vector2(i+4f, i+4f)));
		    createSoldier(i, i+1, 1);
	}
	
	for(int i=0; i<2; i++) {
	    enemies.add(new Enemy(new Vector2(i, 5), new Vector2(-1,-1)));
	}
	Link nLink = new Link(towers.get(0), tCentre);
	links.add(nLink);
	nLink.t1.addLink(nLink);
	nLink.t2.addLink(nLink);
    }
    
    public void createTree(float x, float y) {
    	Tree t = new Tree(new Vector2(x, y));
    	InstanceManager.getInstance().getTrees().add(t);
    	int x1 = MathUtils.floor(x);
		int y1 = MathUtils.floor(y);
		InstanceManager.getInstance().getTileManager().addTile(x1*21+y1, t);
    }
    
    public void createStore(float x, float y) {
    	Store s = new Store(new Vector2(x, y));
		InstanceManager.getInstance().getStores().add(s);
		int x1 = MathUtils.floor(x);
		int y1 = MathUtils.floor(y);
		InstanceManager.getInstance().getTileManager().addTile(x1*21+y1, s);
    }
    
    public void createTower(float x, float y) {
    	Tower t = new Tower(new Vector2(x, y));
		InstanceManager.getInstance().getTowers().add(t);
		int x1 = MathUtils.floor(x);
		int y1 = MathUtils.floor(y);
		InstanceManager.getInstance().getTileManager().addTile(x1*21+y1, t);
		//System.out.println("Tower" + x + " " + y);
    }
    
    public void createHouse(float x, float y) {
    	House h = new House(new Vector2(x, y));
    	InstanceManager.getInstance().getHouses().add(h);
    	int x1 = MathUtils.floor(x);
		int y1 = MathUtils.floor(y);
		InstanceManager.getInstance().getTileManager().addTile(x1*21+y1, h);
    }
    
    public void createDefenseTower(float x, float y) {
    	DefenseTower df = new DefenseTower(new Vector2(x,y));
    	defenseTowers.add(df);
    	int x1 = MathUtils.floor(x);
		int y1 = MathUtils.floor(y);
		InstanceManager.getInstance().getTileManager().addTile(x1*21+y1, df);
    }
    
    public void createBarracks(float x, float y) {
    	Barracks b = new Barracks(new Vector2(x,y));
    	InstanceManager.getInstance().getBarracks().add(b);
    	int x1 = MathUtils.floor(x);
		int y1 = MathUtils.floor(y);
		InstanceManager.getInstance().getTileManager().addTile(x1*21+y1, b);
    	System.out.println("in create barraks");
    }
    
    public void createEnemy(float x, float y) {
    	enemies.add(new Enemy(new Vector2(x, y), new Vector2(1,1)));
    }
    
    public void createSoldier(float x, float y, int type) {
    	InstanceManager.getInstance().getSoldiers().add(new Soldier(new Vector2(x,y), type));
    }
    
    /* This function is not used anywhere */
    public void createLink(Tower t1, Tower t2) {
    	Link nLink = new Link(t1, t2);
    	links.add(nLink);
    	t1.addLink(nLink);
    	t2.addLink(nLink);
    }
    
    public void createLink2(int t1, int t2, float cost) {
    	if(t1 == -1 && t2 == -1)
    		return;
    	else if(t1 == -1) {
    		Link nLink = new Link(tCentre, towers.get(t2));
    		links.add(nLink);
    		nLink.t2.addLink(nLink);
    		nLink.t1.addLink(nLink);
    		
    	}
    	else if(t2 == -1) {
    		Link nLink = new Link(tCentre, towers.get(t1));
    		links.add(nLink);
    		nLink.t2.addLink(nLink);
    		nLink.t1.addLink(nLink);
    	}
    	else {
    		Link nLink = new Link(towers.get(t1), towers.get(t2));
    		links.add(nLink);
    		nLink.t1.addLink(nLink);
    		nLink.t2.addLink(nLink);
    	}
    	getTownCentre().consumeSteam(cost);
    }
    
    public int getActiveTower(Vector2 pos) {
    	pos.x = MathUtils.floor(pos.x);
    	pos.y = MathUtils.floor(pos.y);
    	//System.out.println("position is " + pos);
    	if(pos.x == tCentre.getPosition().x && pos.y == tCentre.getPosition().y)
    		return -1; // this is the UID of TownCentre
    	for(Tower tower: towers) {
    		//System.out.println("tower is " + tower.getPosition());
    		if(tower.Covers(pos)) {
    			System.out.println("tower found is " + tower.getPosition());
    			return towers.indexOf(tower, true);
    			// Here we return UID of tower rather than index
    		}
    	}
    	return -1;
    }
    
    public Array<Tower> getTowersToBeAttacked(int number, Array<Tower> towers) {
    	Array<Tower> output = new Array<Tower>();
    	if(towers.size < number)
    		number = towers.size;
    	try {
    	for(int i=0;i<number;i++) {
    		Tower temp;
    		temp = towers.get(i);
    		for(Tower t : towers) {
    			if(output.contains(t, false))
    				continue;
    			if(t.currentStrength > temp.currentStrength)
    				temp = t;
    		}
    		output.add(temp);
    	}
    	return output;
    }
    	catch(Exception e) {
    		return null;
    	}
    }
    
    public Array<Link> getLinksToBeAttacked(int number, Array<Link> links) {
    	Array<Link> output = new Array<Link>();
    	if(links.size < number)
    		number = links.size;
    	try {
    	for(int i=0;i<number;i++) {
    		Link temp;
    		temp = links.get(i);
    		for(Link l : links) {
    			if(output.contains(l, false))
    				continue;
    			if(l.currentStrength > temp.currentStrength)
    				temp = l;
    		}
    		output.add(temp);
    	}
    	return output;
    }
    	catch(Exception e) {
    		return null;
    	}
    }
    
    public boolean isInSameSquare(Vector2 pos, float x, float y) {
		if(MathUtils.floor(pos.x) == MathUtils.floor(x) && MathUtils.floor(pos.y) == MathUtils.floor(y)) {
		    return true;
		}
		return false;
    }
    
    public boolean isTowerCentreSelected() {
    	//if(Math.floor(activePortion.x) == getTownCentre().getPosition().x && Math.floor(activePortion.y) == getTownCentre().getPosition().y)
    		//return true;
    	
    	int activeX = (int)Math.floor(activePortion.x);
    	int activeY = (int)Math.floor(activePortion.y);
    	int townX = (int)getTownCentre().getPosition().x;
    	int townY = (int)getTownCentre().getPosition().y;
    	
    	if(townX<=activeX && townY <=activeY && townX+TownCentre.WIDTH >= activeX && townY+TownCentre.HEIGHT >= activeY)
    		return true;
    	return false;
    }
}

