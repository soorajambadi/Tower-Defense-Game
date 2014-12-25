package com.jvc.towerdefense.manager;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
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
import com.jvc.towerdefense.models.Tree;
import com.jvc.towerdefense.particlesystem.BigBubbleParticleEmitter;
import com.jvc.towerdefense.particlesystem.BubbleParticleEmitter;
import com.jvc.towerdefense.particlesystem.ExplosionParticleEmitter;
import com.jvc.towerdefense.particlesystem.SparkParticleEmitter;
import com.jvc.towerdefense.screens.GameScreen;
import com.jvc.towerdefense.screens.MainMenu;
import com.jvc.towerdefense.screens.MapSelectorScreen;
import com.jvc.towerdefense.screens.TargetWorldScreen;

public class InstanceManager {
	
	public static InstanceManager instance;
	public Vector3 camPosBeforeBuildScreen = new Vector3(0,0,0);
	public Entity activeEntity = null;
	int mapSizeX = 42;
	GameScreen gScreen = null;
	Screen mScreen = null;
	int mapSizeY = 28;
	public OrthographicCamera cam;
	public Vector3 camPosition;
	public Vector2 startMapLocation = new Vector2(0f,0f);
	Array<Tree> trees = new Array<Tree>();
	Array<Link> links = new Array<Link>();
	Array<House> houses = new Array<House>();
	Array<Enemy> enemies = new Array<Enemy>();
	Array<Person> persons = new Array<Person>();
	Array<Tower> towers = new Array<Tower>();
	Array<Store> stores = new Array<Store>();
	Array<Soldier> soldiers = new Array<Soldier>();
	Array<DefenseTower> defenseTowers = new Array<DefenseTower>();
	Array<Barracks> barracks = new Array<Barracks>();
	public BubbleParticleEmitter bubbleParticles = new BubbleParticleEmitter();
	public BigBubbleParticleEmitter bigBubbleParticles = new BigBubbleParticleEmitter();
	public SparkParticleEmitter sparkParticles = new SparkParticleEmitter();
	public ExplosionParticleEmitter explosionParticles = new ExplosionParticleEmitter();
	private float totalFood;
	private int totalPopulation;
	public Random random;
	public int gWidth=0;
	public int gHeight=0;
	public float ppuX = 0f;
	public float ppuY = 0f;
	public float WIDTH;
	public float HEIGHT;
	public TileManager tManager;
	public Entity currentSelection;
	public static GameScreen savedScreen;
	
	public void setGraphics(int width, int height, float WIDTH, float HEIGHT) {
		gHeight = height;
		gWidth = width;
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		ppuX = width/WIDTH;
		ppuY = height/HEIGHT;
	}
	
	public void setCamPosBeforeBuildScreen(Vector3 vec) {
		camPosBeforeBuildScreen.set(vec);
	}
	
	public void setCamPosition(OrthographicCamera cam) {
		camPosition = cam.position;
	}
	
	public OrthographicCamera getActiveCamera() {
		return cam;
	}
	
	public Screen getGameScreen() {
		if(gScreen == null)
			gScreen = new GameScreen();
		return gScreen;
	}
	
	public Screen getMainMenuScreen() {
		if(mScreen == null)
			mScreen = new MainMenu();
		return mScreen;
	}
	
	public Array<TileManager> getTiles(float range) {
		return null;
	}
	
	public Vector3 getCamPosition() {
		return camPosition;
	}
	
	public InstanceManager() {
		trees = new Array<Tree>();
		houses = new Array<House>();
		persons = new Array<Person>();
		enemies = new Array<Enemy>();
		stores = new Array<Store>();
		barracks = new Array<Barracks>();
		soldiers = new Array<Soldier>();
		links = new Array<Link>();
		bubbleParticles = new BubbleParticleEmitter();
		bigBubbleParticles = new BigBubbleParticleEmitter();
		sparkParticles = new SparkParticleEmitter();
		explosionParticles = new ExplosionParticleEmitter();
		totalFood = 0.0f;
		totalPopulation = 0;
		random = new Random();
		startMapLocation = new Vector2(0,0);
		tManager =  new TileManager();
		currentSelection = null;
		savedScreen = null;
	}
	
	public TileManager getTileManager() {
		return tManager;
	}
	public static InstanceManager getInstance() {	
		if (instance == null) {
			instance = new InstanceManager();
		}
		return instance;
	}
	
	public float getTotalFood() {
		return totalFood;
	}
	
	
	public Array<Tower> getTowers() {
		return towers;
	}
	
	public Array<Store> getStores() {
		return stores;
	}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public Array<Link> getLinks() {
		return links;
	}
	
	public Array<DefenseTower> getDefenseTowers() {
		return defenseTowers;
	}
	
	public Array<Tree> getTrees() {
		return trees;
	}
	
	public Array<House> getHouses() {
		return houses;
	}
	
	public Array<Soldier> getSoldiers() {
		return soldiers;
	}
	
	public int getTotalPopulation() {
		return houses.size * House.CAPACITY;
	}
	
	public Array<Person> getPeople() {
		return persons;
	}
	
	public Array<Barracks> getBarracks() {
		return barracks;
	}
	
	// I don't know if this is possible
	/*public Entity getEntityByPosition(int x, int y) {
		for(Store s : stores) {
			if(s.getPosition().x == x && s.getPosition().y == y)
				return (Entity) s;
		}
		for(DefenseTower d : defenseTowers) {
			if(d.getPosition().x == x && d.getPosition().y == y)
				return (Entity) d;
		}
		for(Tower t : towers) {
			if(t.getPosition().x == x && t.getPosition().y == y)
				return (Entity) t;
		}
	}
	*/
	public void handleInput(float x, float y) {
		int X = MathUtils.floor(x);
		int Y = MathUtils.floor(y);
		
		//Entity clickedObject = getEntityByPosition(X, Y);
		//clickedObject.handleInput(X, Y);
	}
	
	public float getFoodProductionRate() {
		return trees.size * Tree.FRUITPRODUCTION; 
	}
	
	public void convertToMap(float x, float y, Array<Float> cI) {
		if(x+startMapLocation.x <= mapSizeX)
			cI.set(0, x+startMapLocation.x);
		else
			cI.set(0, mapSizeX-WIDTH);
		if(startMapLocation.y-y >= mapSizeY)
			cI.set(1, startMapLocation.y-y);
		else
			cI.set(1, HEIGHT);
	}
	
	public float getFoodConsumptionRate() {
		return persons.size * Person.FOODCONSUMPTION;
	}
	
	public void update(float delta) {
		// Food updation technique needs to be refined. 
		// Now tree has no limit.
		totalFood -= getFoodConsumptionRate()*delta;
		for(Store store : stores) {
			totalFood+= store.getFoodProductionRate()*delta;
		}
		for(Store store: stores)
			store.setCurrentStorage(totalFood/stores.size);
	}
	
	public float getSteamConsumptionRate() {
		return defenseTowers.size * Tower.STEAMCONSUMPTION;
	}
	
	public boolean outsideLimits(Vector3 position) {
		float x_left_limit = WIDTH*ppuX/2;
        float x_right_limit = (mapSizeX - WIDTH/2)*ppuX;
        float y_bottom_limit = HEIGHT*ppuY / 2;
        float y_top_limit = (mapSizeY - HEIGHT/2)*ppuY;

        if( position.x < x_left_limit || position.x > x_right_limit )
            return true;
        else if( position.y < y_bottom_limit || position.y > y_top_limit )
            return true;
        else
          return false;	
	}

	public void handleMapAndWar() {
		//TargetWorldScreen wScreen = new TargetWorldScreen();
		 //((Game) Gdx.app.getApplicationListener()).setScreen(wScreen);
		MapSelectorScreen mScreen = new MapSelectorScreen();
		savedScreen = (GameScreen) ((Game) Gdx.app.getApplicationListener()).getScreen();
		((Game) Gdx.app.getApplicationListener()).setScreen(mScreen);
		//((Game) Gdx.app.getApplicationListener()).setScreen(savedScreen);
		
	}
}
