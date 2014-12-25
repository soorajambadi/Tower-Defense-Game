package com.jvc.towerdefense.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.ResourceManager;
import com.jvc.towerdefense.models.Barracks;
import com.jvc.towerdefense.models.DefenseTower;
import com.jvc.towerdefense.models.House;
import com.jvc.towerdefense.models.Soldier;
import com.jvc.towerdefense.models.Store;
import com.jvc.towerdefense.models.TargetWorld;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.Tree;

public class GenericRenderer extends WorldRenderer {
	//OrthographicCamera cam;
	SpriteBatch batch;
	TargetWorld world;
	Texture bgTexture;
	
	public GenericRenderer(TargetWorld world) {
		super(world);
		//setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//cam.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0f);
		//cam.update();
		this.world = world;
		bgTexture = new Texture("data/drawing.png");
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		batch.begin();
		drawBackground();
		drawTowers();
		renderStores(delta);
		drawDefenseTowers();
		renderBarracks(delta);
		renderHouses(delta);
		renderTrees(delta);
		renderSoldiers(delta);
		batch.end();
	}
	
	public void renderSoldiers(float delta) {
		for(Soldier s : world.soldiers) {
    		batch.draw(storeTexture, s.getPosition().x* ppuX, s.getPosition().y * ppuY, Store.WIDTH *ppuX, Store.HEIGTH * ppuY);
    	}
		
	}

	public void drawBackground() {
		batch.draw(bgTexture, 0* ppuX, 0* ppuY, 42 *ppuX, 28 * ppuY);
	}
	
	public void renderTrees(float delta) {
		for(Tree t: world.trees) {
	    	batch.draw(ResourceManager.getInstance().treeTexture, t.getPosition().x*ppuX, t.getPosition().y*ppuY,
	    			Tree.WIDTH*ppuX, Tree.HEIGHT*ppuY);
	    }
	}
	public void drawTowers() {
		for(Tower t : world.towers) {
			batch.draw(ResourceManager.getInstance().windMill, t.getPosition().x * ppuX, t.getPosition().y * ppuY, Tower.WIDTH *ppuX, Tower.HEIGHT * ppuY);
		}
	}
	public void renderStores(float delta) {
    	for(Store s : world.stores) {
    		batch.draw(ResourceManager.getInstance().storeTexture, s.getPosition().x* ppuX, s.getPosition().y * ppuY, Store.WIDTH *ppuX, Store.HEIGTH * ppuY);
    	}
    }
	
	private void drawDefenseTowers() {
		for(DefenseTower dT : world.defenseTowers) {
			batch.draw(ResourceManager.getInstance().tower, dT.getPosition().x* ppuX, dT.getPosition().y * ppuY, Store.WIDTH *ppuX, Store.HEIGTH * ppuY);
		}
	}
	
	public void renderHouses(float delta) {
    	for(House h : world.houses) {
    		batch.draw(ResourceManager.getInstance().houseTexture, h.getPosition().x* ppuX, h.getPosition().y * ppuY, House.WIDTH *ppuX, House.HEIGHT * ppuY);
    	}
    }
	
	public void renderBarracks(float delta) {
    	for(Barracks b : world.barracks) {
    		batch.draw(barracksTexture, b.getPosition().x*ppuX, b.getPosition().y*ppuY, 0f, 0f, 
    				Barracks.WIDTH*ppuX, Barracks.HEIGHT*ppuY, 1f, 1f, 0);
    	}
    }

}
