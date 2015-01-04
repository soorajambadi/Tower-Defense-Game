package com.jvc.towerdefense.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.controller.TowerMenu;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.ResourceManager;
import com.jvc.towerdefense.models.Barracks;
import com.jvc.towerdefense.models.Bullet;
import com.jvc.towerdefense.models.Earthquake;
import com.jvc.towerdefense.models.Enemy;
import com.jvc.towerdefense.models.House;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Person;
import com.jvc.towerdefense.models.Soldier;
import com.jvc.towerdefense.models.Store;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.TownCentre;
import com.jvc.towerdefense.models.Tree;
import com.jvc.towerdefense.models.World;

public class WorldRenderer {
    
    private static float CAMERA_WIDTH = 21f;
    private static  float CAMERA_HEIGHT = 14f;
    private World world;
    public OrthographicCamera cam;
    private ParticleEffect effect;
    private Color color;
    private static final float RUNNING_FRAME_DURATION = 0.2f;
    private AtlasRegion towerTexture;
    private Texture enemyTexture;
    private AtlasRegion townCenterTexture;
    private Texture bgTexture;
    public Texture bubbleTexture;
    public AtlasRegion selectionTexture;
    private TextureRegion pipeTexture;
    private TextureRegion phoenix1;
    private TextureRegion phoenix2;
    private TextureRegion phoenix3;
    private TextureRegion phoenix4;
    private Texture phoenix;
    private AtlasRegion asteroidShowerTexture;
    private TextureRegion bulletTexture;
    private SpriteBatch spritebatch;
    private int width;
    private int height;
    public float ppuX;
    public float ppuY;
    private Animation flyPhoenix;
    private BitmapFont font;
    private Texture selectedtowerTexture;
    private Texture selectedtownCentreTexture;
    public AtlasRegion menu1Texture;
    public AtlasRegion menu2Texture;
    public AtlasRegion menu3Texture;
    public AtlasRegion menu4Texture;
    public AtlasRegion menu5Texture;
    private FPSLogger fpsLogger;
    private Texture menuTexture;
    private Texture scoreTexture;
    private AtlasRegion defenseTowerTexture;
    private AtlasRegion treeTexture;
    private AtlasRegion houseTexture;
    private AtlasRegion personTexture;
    protected AtlasRegion storeTexture;
    private AtlasRegion rangeTexture;
    public AtlasRegion barracksTexture;
    private static final Vector2 drawMenuPoint = new Vector2(0,0);
    public static Vector2 camToScreen = new Vector2(0,0);
    
    ShaderProgram shader;
	ShaderProgram defaultShader;
	private float					amplitudeWave = 0.4f;
	private float					angleWave = 0.0f;
	private float					angleWaveSpeed = 2.0f;
	public static final float 		PI2 = 3.1415926535897932384626433832795f * 2.0f;
    
    public WorldRenderer(World world) {
		this.world = world;
		cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		//cam.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		//this.cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);
		//cam.position.set(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 0);
		cam.update();
		InstanceManager.getInstance().setCamPosition(cam);
		spritebatch = new SpriteBatch();
		loadTextures();
		font = new BitmapFont();
		fpsLogger = new FPSLogger();
		
		ShaderProgram.pedantic = false;
		final String vertexShader = Gdx.files.internal("data/vertexShader.glsl").readString();
		final String fragmentShader = Gdx.files.internal("data/defaultPixelShader.glsl").readString();
		shader = new ShaderProgram(vertexShader, fragmentShader);
		defaultShader = SpriteBatch.createDefaultShader();
    }
    
    public void setSpriteBatch(SpriteBatch batch) {
    	spritebatch = batch;
    }
    
    public Vector2 getCamToScreen() {
    	return camToScreen;
    }
    
    public OrthographicCamera getCamera() {
    	return cam;
    }
    
    public void setCameraWidthHeight(float width, float height) {
    	CAMERA_HEIGHT = height;
    	CAMERA_WIDTH = width;
    }
    
    public void setSize (int w, int h) {
    	// There is a small glitch regarding map when resizing .
    	// Also zooming increases size of controls also.
    	InstanceManager.getInstance().setGraphics(w, h, CAMERA_WIDTH, CAMERA_HEIGHT);
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
		//cam.viewportHeight = Gdx.graphics.getHeight();
		//cam.viewportWidth = Gdx.graphics.getWidth();
		//cam.viewportHeight = CAMERA_HEIGHT;
		//cam.viewportWidth = CAMERA_WIDTH;
		//cam.position.set(CAMERA_WIDTH/2f, CAMERA_HEIGHT/2f, 0);
		cam.viewportHeight = h;
		cam.viewportWidth = w;
		cam.position.set(w/2, h/2, 0);
		cam.update();
		InstanceManager.getInstance().setCamPosition(cam);
		spritebatch = new SpriteBatch();
    }
    
    public float getCameraWidth() {
    	return CAMERA_WIDTH;
    }
    
    public float getCameraHeigth() {
    	return CAMERA_HEIGHT;
    }
    
    public Texture createBgTexture()
    {
    	Pixmap pixmap = new Pixmap((int)ppuX, (int)ppuY, Format.RGBA4444); // or RGBA8888
        pixmap.setColor(0f, 0f, .2f, .2f);
        pixmap.fill();
        Texture texture = new Texture(pixmap); // must be manually disposed
        pixmap.dispose();
        return texture;
    }
    
    public Texture createMenuTexture() 
    {
    	Pixmap pixmap = new Pixmap((int)ppuX, (int)ppuY, Format.RGBA4444); // or RGBA8888
        pixmap.setColor(1f, 0f, 0f, .5f);
        pixmap.fill();
        Texture texture = new Texture(pixmap); // must be manually disposed
        pixmap.dispose();
        return texture;
    }
    
    public Texture createScoreTexture() 
    {
    	Pixmap pixmap = new Pixmap((int)ppuX, (int)ppuY, Format.RGBA4444); // or RGBA8888
        pixmap.setColor(0f, .5f, 0f, .5f);
        pixmap.fill();
        Texture texture = new Texture(pixmap); // must be manually disposed
        pixmap.dispose();
        return texture;
    }
    
    public String getseasonName() {
    	int season = world.getCurrentSeason();
    	if(season == 1)
    		return "Peaceful Season";
    	else if(season == 2)
    		return "Alien Season";
    	else if(season == 3)
    		return "EarthQuake Season";
    	else 
    		return "Peaceful Season";
    }
    
    public void drawBackground() {
    	spritebatch.draw(bgTexture, 0* ppuX, 0* ppuY, 42 *ppuX, 28 * ppuY);
    }
    
    public void drawSpecificMenus() 
    {
    	//spritebatch.draw(menuTexture, TowerMenu.startPosX * ppuX, TowerMenu.startPosY * ppuY, 4 *ppuX, 4 * ppuY);
    }
    
    public void drawScore() {
		camToScreen.x = cam.position.x - cam.viewportWidth/2;
        camToScreen.y = cam.position.y - cam.viewportHeight/2;
    	//spritebatch.draw(scoreTexture, 9*ppuX, 13*ppuY, 5*ppuX, 1*ppuY);
    	font.draw(spritebatch, getseasonName(), 9*ppuX + camToScreen.x, 14*ppuY + camToScreen.y);
    	font.draw(spritebatch, Float.toString(world.getTownCentre().getTotalSteam()), 9*ppuX + camToScreen.x, 13*ppuY + camToScreen.y);
    	font.draw(spritebatch, Float.toString(InstanceManager.getInstance().getTotalPopulation()), 9*ppuX + camToScreen.x, 12*ppuY + camToScreen.y);
    	font.draw(spritebatch, Float.toString(InstanceManager.getInstance().getTotalFood()), 9*ppuX + camToScreen.x , 11*ppuY + camToScreen.y);
    	//System.out.println("Total Steam " + world.getTownCentre().getTotalSteam());
    }
    
    private void loadTextures() {
		//towerTexture = new Texture("data/tower.png");
    	barracksTexture = ResourceManager.getInstance().barracksTexture;
    	towerTexture = ResourceManager.getInstance().windMill;
    	houseTexture = ResourceManager.getInstance().houseTexture;
    	defenseTowerTexture = ResourceManager.getInstance().tower;
		selectedtowerTexture = new Texture("data/selected-tower.png");
		enemyTexture = new Texture("data/enemy.png");
		pipeTexture = new TextureRegion(new Texture("data/pipe.png"));
		personTexture = ResourceManager.getInstance().personTexture;
		//bulletTexture =new TextureRegion(new Texture("data/bullet.png"));
		phoenix = new Texture("data/phoenix.png");
		//townCenterTexture = new Texture("data/towncenter.png");
		townCenterTexture = ResourceManager.getInstance().townCentre;
		//bgTexture = new Texture("data/stonebg.JPG");
		bgTexture = new Texture("data/drawing.png");
		selectedtownCentreTexture = new Texture("data/selectedtowncenter.png");
		//asteroidShowerTexture = new Texture("data/asteroid.png");
		asteroidShowerTexture = ResourceManager.getInstance().asteroidShower;
		bubbleTexture = new Texture("data/bubble.png");
		bulletTexture = new TextureRegion(bubbleTexture);
		storeTexture = ResourceManager.getInstance().storeTexture;
		treeTexture = ResourceManager.getInstance().treeTexture;
		rangeTexture = ResourceManager.getInstance().rangeTexture;
		menu1Texture = ResourceManager.getInstance().menu1Texture;
		menu2Texture = ResourceManager.getInstance().menu2Texture;
		menu3Texture = ResourceManager.getInstance().menu3Texture;
		menu4Texture = ResourceManager.getInstance().menu4Texture;
		menu5Texture = ResourceManager.getInstance().menu5Texture;
		phoenix1 = new TextureRegion(phoenix, 0, 0, 95, 100);
		phoenix2 = new TextureRegion(phoenix, 95, 0, 95, 100);
		phoenix3 = new TextureRegion(phoenix, 190, 0, 95, 100);
		phoenix4 = new TextureRegion(phoenix, 285, 0, 95, 100);
		TextureRegion[] flyPhoenix = new TextureRegion[4];
		flyPhoenix[0] = phoenix1;
		flyPhoenix[1] = phoenix2;
		flyPhoenix[2] = phoenix3;
		flyPhoenix[3] = phoenix4;
		this.flyPhoenix = new Animation(RUNNING_FRAME_DURATION, flyPhoenix);
		//effect = new ParticleEffect();
		//effect.load(Gdx.files.external("jvc/TowerDefense/TowerDefense-android/assets/data/particle.p"), 
			//Gdx.files.external("jvc/TowerDefense/TowerDefense-android/assets/data/"));
		color = spritebatch.getColor();
		//menuTexture = createMenuTexture();
		//selectionTexture = createMenuTexture();
		selectionTexture = ResourceManager.getInstance().selection;
		//scoreTexture = createScoreTexture();
    }
    

    public void renderEarthQuake(float delta) {
    	if(world.getEarthquake()!=null)
    		spritebatch.draw(pipeTexture, world.getEarthquake().getStartPosition().x*ppuX, 
    			world.getEarthquake().getStartPosition().y*ppuY, 0.0f, 0.0f, 
    			Earthquake.WIDTH*ppuX, Earthquake.HEIGHT*ppuY, 
    			1f, world.getEarthquake().getScaling(), world.getEarthquake().getRotation());

    }
    
    public void renderTrees(float delta) {
    	for(Tree t: InstanceManager.getInstance().getTrees()) {
    		spritebatch.draw(treeTexture, t.getPosition().x*ppuX, t.getPosition().y*ppuY,
    				Tree.WIDTH*ppuX, Tree.HEIGHT*ppuY);
    		}
    }
    
    public void renderPeople(float delta) {
    	for(Person p: InstanceManager.getInstance().getPeople()) {
    		spritebatch.draw(personTexture, p.getPosition().x*ppuX, p.getPosition().y*ppuY, 0f, 0f, 
    				Person.WIDTH*ppuX, Person.HEIGHT*ppuY, 1f, 1f, 0);
    	}
    }
    
    public void renderBarracks(float delta) {
    	for(Barracks b : InstanceManager.getInstance().getBarracks()) {
    		spritebatch.draw(barracksTexture, b.getPosition().x*ppuX, b.getPosition().y*ppuY, 0f, 0f, 
    				Barracks.WIDTH*ppuX, Barracks.HEIGHT*ppuY, 1f, 1f, 0);
    	}
    }
    
    public void renderAsteroidShower(float delta) {
    	if(world.getAsteroidShower()!=null) {
    		if(world.getAsteroidShower().getLeftTime()>0f) {
        		spritebatch.draw(asteroidShowerTexture, world.getAsteroidShower().getPosition().x * ppuX, 
        				world.getAsteroidShower().getPosition().y * ppuY, world.getAsteroidShower().getRadius() *ppuX, 
        				world.getAsteroidShower().getRadius() * ppuY);
        	}
    		world.getAsteroidShower().updateTime(delta);
    	}
    	
    	
    }
    
    public void markActivePortion() {
	spritebatch.draw(selectionTexture, world.activePortion.x*ppuX, world.activePortion.y*ppuY,
		Enemy.WIDTH*ppuX, Enemy.HEIGHT*ppuY);
    }
    
    public void drawTownCenter() {
    	
    	if(world.getTownCentre().isAlive == true)
    	{
	    	if(world.getTownCentre().isSelected) {
	    		camToScreen.x = cam.position.x - cam.viewportWidth/2;
	            camToScreen.y = cam.position.y - cam.viewportHeight/2;
				for(int i=0;i<TowerMenu.numberofOptions;i++) {
					spritebatch.draw(TowerMenu.optTex1, (TowerMenu.startPosX + i) * ppuX + camToScreen.x, 
							TowerMenu.startPosY * ppuY + camToScreen.y, TowerMenu.optWidth *ppuX, TowerMenu.optHeight * ppuY);
				}	
				drawTownCentreProperties();
			}
	    	if(world.getTownCentre().isSelected) {
	    		spritebatch.draw(selectedtownCentreTexture, world.getTownCentre().getPosition().x * ppuX, 
		    			world.getTownCentre().getPosition().y * ppuY, TownCentre.WIDTH *ppuX, TownCentre.HEIGHT * ppuY);
	    		//spritebatch.draw(rangeTexture, (world.getTownCentre().getPosition().x+1-world.getTownCentre().RANGE/2)*ppuX,
	    			//	(world.getTownCentre().getPosition().y+1-world.getTownCentre().RANGE/2)*ppuY, world.getTownCentre().RANGE*ppuX, 
	    				//world.getTownCentre().RANGE*ppuY);
	    		
	    		spritebatch.draw(rangeTexture, (world.getTownCentre().getPosition().x+1-world.getTownCentre().RANGE)*ppuX,
	    				(world.getTownCentre().getPosition().y+1-world.getTownCentre().RANGE)*ppuY, world.getTownCentre().RANGE*2*ppuX, 
	    				world.getTownCentre().RANGE*2*ppuY);
	    	}
	    	else
	    		spritebatch.draw(townCenterTexture, world.getTownCentre().getPosition().x * ppuX, 
	    			world.getTownCentre().getPosition().y * ppuY, TownCentre.WIDTH *ppuX, TownCentre.HEIGHT * ppuY);
	    	if(world.getTownCentre().ifBullets()) {
	  //  	    for(Bullet bullet : world.getTownCentre().getBullets()) {
	   // 	    	spritebatch.draw(bulletTexture, bullet.getPosition().x *ppuX, bullet.getPosition().y *ppuY, 
	   // 	    			0.0f, 0.0f, Bullet.WIDTH *ppuX, Bullet.HEIGHT * ppuY, 1f, 1f, bullet.getRotation());
	   // 	    }
	    	}
    	}
    	else
    		font.draw(spritebatch, "GAME OVER", 5*ppuX, 9*ppuY);
    }
    public void render(float delta)
    {
	Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    cam.update();
    //renderer.setView(cam);
    spritebatch.setProjectionMatrix(cam.combined);
    spritebatch.begin();
    drawBackground();
    //renderer.render();
    //renderer.getSpriteBatch().flush();
    spritebatch.end();
	
	/*
	//feed the shader with the new data
	shader.begin();
	shader.setUniformf("waveData", angleWave, amplitudeWave);
	shader.end();
	
	//render the first layer (the water) using our special vertex shader
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	spritebatch.setProjectionMatrix(cam.combined);
	spritebatch.setShader(shader);
	*/
	//spritebatch.begin();
	
    /*TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
    final int layerWidth = layer.getWidth();
	final int layerHeight = layer.getHeight();
	for(int i=0; i< layerWidth; i++)
		for(int j=0; j< layerHeight; j++) {
			 Cell tt = layer.getCell(i, j);
			int abvd;
			if(tt!=null)
				spritebatch.draw(tt.getTile().getTextureRegion(), i*32, j*32);
				abvd=1;
		}

	//System.out.println("camera position is " + cam.position);
	//
	spritebatch.flush();
	*/
	
//spritebatch.setShader(SpriteBatch.createDefaultShader());
	spritebatch.begin();
	markActivePortion();
	drawLinks(delta);
	drawEnemies();
	drawTownCenter();
	drawTowers();
	renderStores(delta);
	renderSoldiers(delta);
	drawDefenseTowers();
	drawMenu();
	//drawTiles();
	renderPeople(delta);
	renderBarracks(delta);
	renderEarthQuake(delta);
	renderAsteroidShower(delta);
	renderHouses(delta);
	renderTrees(delta);
	drawSpecificMenus();
	drawScore();
	InstanceManager.getInstance().bigBubbleParticles.draw(spritebatch);
	InstanceManager.getInstance().explosionParticles.draw(spritebatch);
	InstanceManager.getInstance().bubbleParticles.draw(spritebatch);
	InstanceManager.getInstance().sparkParticles.draw(spritebatch);
	
	spritebatch.end();
	//fpsLogger.log();
    }
    
    public void renderSoldiers(float delta) {
    	// Change with soldier texture
    	for(Soldier s : InstanceManager.getInstance().getSoldiers()) {
    		spritebatch.draw(storeTexture, s.getPosition().x* ppuX, s.getPosition().y * ppuY, Store.WIDTH *ppuX, Store.HEIGTH * ppuY);
    	}
    }
    public void renderStores(float delta) {
    	for(Store s : InstanceManager.getInstance().getStores()) {
    		spritebatch.draw(storeTexture, s.getPosition().x* ppuX, s.getPosition().y * ppuY, Store.WIDTH *ppuX, Store.HEIGTH * ppuY);
    	}
    }
    
    public void renderHouses(float delta) {
    	for(House h : InstanceManager.getInstance().getHouses()) {
    		spritebatch.draw(houseTexture, h.getPosition().x* ppuX, h.getPosition().y * ppuY, House.WIDTH *ppuX, House.HEIGHT * ppuY);
    	}
    }
    private void drawTownCentreProperties() {
        camToScreen.x = cam.position.x - cam.viewportWidth/2;
        camToScreen.y = cam.position.y - cam.viewportHeight/2;
    	//font.draw(spritebatch, "Total Steam :" + world.getTownCentre().getTotalSteam(), ppuX*TowerMenu.startPosX, ppuY*(TowerMenu.startPosY+2));
    	font.draw(spritebatch, "Enemies Faced :" + world.getTownCentre().getEnemiesFaced(), ppuX*TowerMenu.startPosX + camToScreen.x, ppuY*(TowerMenu.startPosY + 3) + camToScreen.y);
    	font.draw(spritebatch, "POWER :" + world.getTownCentre().POWER, ppuX*TowerMenu.startPosX + camToScreen.x, ppuY*(TowerMenu.startPosY + 4) + camToScreen.y);
    }
    
    private void drawTowerProperties(Tower t) {
        camToScreen.x = cam.position.x - cam.viewportWidth/2;
        camToScreen.y = cam.position.y - cam.viewportHeight/2;
    	font.draw(spritebatch, "Steam :" + t.productionRate, ppuX*TowerMenu.startPosX + camToScreen.x, ppuY*(TowerMenu.startPosY + 2) + camToScreen.y);
    	font.draw(spritebatch, "Strength :" + t.getCurrentStrength(), ppuX*TowerMenu.startPosX + camToScreen.x, ppuY*(TowerMenu.startPosY + 3) + camToScreen.y);
    }
    
    private void drawMenu() {
    camToScreen.x = cam.position.x - cam.viewportWidth/2;
    camToScreen.y = cam.position.y - cam.viewportHeight/2;
	spritebatch.draw(menu5Texture, (drawMenuPoint.x ) * ppuX + camToScreen.x, (drawMenuPoint.y ) * ppuY + camToScreen.y, Enemy.WIDTH *ppuX, Enemy.HEIGHT * ppuY);
	spritebatch.draw(menu4Texture, (drawMenuPoint.x ) * ppuX + camToScreen.x, (drawMenuPoint.y +1) * ppuY + camToScreen.y, Enemy.WIDTH *ppuX, Enemy.HEIGHT * ppuY);
	spritebatch.draw(menu3Texture, (drawMenuPoint.x ) * ppuX + camToScreen.x, (drawMenuPoint.y +2) * ppuY + camToScreen.y, Enemy.WIDTH *ppuX, Enemy.HEIGHT * ppuY);
	spritebatch.draw(menu2Texture, (drawMenuPoint.x ) * ppuX + camToScreen.x, (drawMenuPoint.y +3) * ppuY + camToScreen.y, Enemy.WIDTH *ppuX, Enemy.HEIGHT * ppuY);
	spritebatch.draw(menu1Texture, (drawMenuPoint.x ) * ppuX + camToScreen.x, (drawMenuPoint.y +4) * ppuY + camToScreen.y, Enemy.WIDTH *ppuX, Enemy.HEIGHT * ppuY);
	//spritebatch.draw(enemyTexture, 0 * ppuX, 4 * ppuY, 2*Enemy.WIDTH *ppuX, 2*Enemy.HEIGHT * ppuY);
    }
    
    private void drawTowers() {
	for(Tower tower : world.getTowers()) {
		
		if(tower.isSelected) {
		    camToScreen.x = cam.position.x - cam.viewportWidth/2;
		    camToScreen.y = cam.position.y - cam.viewportHeight/2;
			spritebatch.draw(towerTexture, tower.getPosition().x * ppuX, tower.getPosition().y * ppuY, Tower.WIDTH *ppuX, Tower.HEIGHT * ppuY);
			for(int i=0;i<TowerMenu.numberofOptions;i++) {
				spritebatch.draw(TowerMenu.optTex1, (TowerMenu.startPosX + i) * ppuX + camToScreen.x, 
						TowerMenu.startPosY * ppuY + camToScreen.y, TowerMenu.optWidth *ppuX, TowerMenu.optHeight * ppuY);
			}	
			drawTowerProperties(tower);
			// This code is wrong
    		spritebatch.draw(rangeTexture, (tower.getPosition().x+Tower.WIDTH/2-tower.RANGE)*ppuX,
    				(tower.getPosition().y+Tower.HEIGHT/2-tower.RANGE)*ppuY, tower.RANGE*2*ppuX, 
    				tower.RANGE*2*ppuY);
		}
		else 
			spritebatch.draw(towerTexture, tower.getPosition().x * ppuX, tower.getPosition().y * ppuY, Tower.WIDTH *ppuX, Tower.HEIGHT * ppuY);
	    if(tower.ifBullets()) {
	    for(Bullet bullet : tower.getBullets()) {
	    	spritebatch.draw(bulletTexture, bullet.getTarget().x *ppuX, bullet.getTarget().y *ppuY, 
	    			0.0f, 0.0f, Bullet.WIDTH *ppuX, Bullet.HEIGHT * ppuY, 1f, 1f, bullet.getRotation());
	    }
	    }
	}
    }
    
    private void drawDefenseTowers() {
    	for(Tower tower: world.getDefenseTowers()) {
    		if(tower.isSelected) {
    		    camToScreen.x = cam.position.x - cam.viewportWidth/2;
    		    camToScreen.y = cam.position.y - cam.viewportHeight/2;
    			spritebatch.draw(defenseTowerTexture, tower.getPosition().x * ppuX, tower.getPosition().y * ppuY, Tower.WIDTH *ppuX, Tower.HEIGHT * ppuY);
    			for(int i=0;i<TowerMenu.numberofOptions;i++) {
    				spritebatch.draw(TowerMenu.optTex1, (TowerMenu.startPosX + i) * ppuX + camToScreen.x, 
    						TowerMenu.startPosY * ppuY + camToScreen.y, TowerMenu.optWidth *ppuX, TowerMenu.optHeight * ppuY);
    			}	
    			drawTowerProperties(tower);
    		}
    		else 
    			spritebatch.draw(defenseTowerTexture, tower.getPosition().x * ppuX, tower.getPosition().y * ppuY, Tower.WIDTH *ppuX, Tower.HEIGHT * ppuY);
    		font.draw(spritebatch, "Strength :" + tower.getStrength(), ppuX*tower.getPosition().x, ppuY*tower.getPosition().y);
    	    if(tower.ifBullets()) {
    	    //for(Bullet bullet : tower.getBullets()) {
    	    	//spritebatch.draw(bulletTexture, bullet.getPosition().x *ppuX, bullet.getPosition().y *ppuY, 
    	    	//		0.0f, 0.0f, Bullet.WIDTH *ppuX, Bullet.HEIGHT * ppuY, 1f, 1f, bullet.getRotation());
    	   // }
    	    }
    	}
    }
    
    private void drawEnemies() {
	for( Enemy enemy : world.getEnemies()) {
	    spritebatch.draw(flyPhoenix.getKeyFrame(enemy.getStateTime(), true), enemy.getPosition().x * ppuX, enemy.getPosition().y * ppuY, Enemy.WIDTH *ppuX, Enemy.HEIGHT * ppuY);
	    font.draw(spritebatch, "Strength :" + enemy.getStrength(), ppuX*enemy.getPosition().x, ppuY*enemy.getPosition().y);
	}
    }
    
    private void drawLinks(float delta) {
		for (Link link : world.getLinks()) {
		    /*effect.setPosition(link.getPosition().x*ppuX, link.getPosition().y*ppuY);
		    //System.out.println(link.getPosition().x + " " + link.getPosition().y);
		    effect.update(delta);
		    effect.draw(spritebatch, delta); */
			if(link.isAlive) {
				
				/*Vector2 v1 = link.getStartPosition();
				Vector2 v2 = link.getEndPosition();
				float len = link.getScaling();
				Vector2 direction = v1.sub(v2);
				Vector2 incVector;
				float incSteps;
				if(v1.y> v1.x) {
					incSteps = v1.y/v1.x;
					incVector = new Vector2(.1f, v1.y*.1f);
				}
				else {
					incSteps = v1.x/v1.y;
					incVector = new Vector2(v1.x*.1f, .1f);
				}
				Vector2 runVector = new Vector2(v1);
				while(Math.round(runVector.x) != Math.round(v2.x) && Math.round(runVector.y) != Math.round(v2.y)) {
					spritebatch.draw(bubbleTexture, runVector.x, runVector.y, .1f, .1f);
					runVector.add(incVector);
				}
			}*/
				spritebatch.draw(pipeTexture, (link.getEndPosition().x+.5f) *ppuX, (link.getEndPosition().y+.5f) *ppuY, 
					0.0f, 0.0f, Link.WIDTH*2, Link.HEIGHT, link.getScaling(ppuX, ppuY), ppuY/2, link.getRotation(ppuX, ppuY));
				Vector2 s1;
				s1 = new Vector2(link.getEndPosition());
				Vector2 s2;
				s2 = new Vector2(link.getEndPosition());
				font.draw(spritebatch, "Strength :" + link.getStrength(), 
						ppuX*(s1.add(link.getStartPosition()).scl(.5f).x), ppuY*(s2.add(link.getStartPosition()).scl(.5f).y));
				}
		}
    }
}
