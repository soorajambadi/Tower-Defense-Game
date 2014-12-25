package com.jvc.towerdefense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.controller.MapAttackMenu;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.MapLoader;
import com.jvc.towerdefense.manager.ResourceManager;
import com.jvc.towerdefense.misc.MapLoading;
import com.jvc.towerdefense.models.BlockPiece;
import com.jvc.towerdefense.models.DialogBox;
import com.jvc.towerdefense.models.TargetWorld;
import com.jvc.towerdefense.view.GenericRenderer;
import com.jvc.towerdefense.view.WorldRenderer;

public class TargetWorldScreen implements Screen, InputProcessor{

	public TargetWorld tWorld;
	public MapLoader loader;
	//public WorldRenderer renderer;
	public SpriteBatch batch;
	GenericRenderer renderer;
	MapAttackMenu menu;
	DialogBox dbox;
	Array<BlockPiece> blocks;
	
	// for calculating camera movement when dragged.
    final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	final Vector3 newPos = new Vector3();
	
	public TargetWorldScreen() {
	
		//InstanceManager.getInstance().getTrees().clear();
		tWorld = new TargetWorld();
		loader = new MapLoader("maps/level1", tWorld);
		loader.loadMapToGame();
		//renderer = new WorldRenderer(tWorld);
		renderer = new GenericRenderer(tWorld);
		batch = new SpriteBatch();
		menu = new MapAttackMenu(tWorld);
		dbox = new DialogBox(null);
		createDemoBoxes();
	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.B) {
			// It should land in main map screen
			((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().savedScreen);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int x = screenX;
		int y = screenY;
		x = MathUtils.floor((x + renderer.getCamera().position.x - renderer.getCamera().viewportWidth/2f)/(Gdx.graphics.getWidth()/21));
		y = MathUtils.floor((Gdx.graphics.getHeight()-y + renderer.getCamera().position.y - renderer.getCamera().viewportHeight/2f)/(Gdx.graphics.getHeight()/14));
		if(!dbox.processInput(x, y))
			menu.processInput(x, y);
		//controller.touchDown(x*renderer.getCameraWidth()/width, (height - y)*renderer.getCameraHeigth()/height, pointer, button);
		last.set(x, y, 0);
		System.out.println("touched down in target world screen");
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		OrthographicCamera camera = renderer.getCamera();
		//camera.unproject(curr.set(x, y, 0));
		curr.set(screenX, screenY, 0);
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			//camera.unproject(delta.set(last.x, last.y, 0));
			delta.set(last.x, last.y, 0);
			delta.sub(curr);
			delta.y = -delta.y;
			//camera.translate(delta.x, delta.y, 0);
			newPos.set(camera.position);
			newPos.add(delta.x, delta.y, 0);
			if(!InstanceManager.getInstance().outsideLimits(newPos)) {
				camera.position.set(newPos);
				//renderer.getCamToScreen().set(new Vector2(newPos).sub(renderer.))
			}
		}
		last.set(screenX, screenY, 0);
    	return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(float delta) {
		// Here we couple input with rendering. Is it a good idea ?
		renderer.render(delta);
		batch.begin();
		dbox.render();
		//renderer.render(delta);	
		//batch.begin();
		//batch.draw(ResourceManager.getInstance().barracksTexture, 0,0, 100, 100);
		//batch.end();
		batch.end();
		menu.update(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void createDemoBoxes() {
		blocks = new Array<BlockPiece>();
		BlockPiece b = new BlockPiece(ResourceManager.getInstance().houseTexture, 
				5, 4, batch, menu, new Vector2(0, 0), 1);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().tower, 
				5, 4, batch, menu, new Vector2(5, 0), 2);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().windMill, 
				5, 4, batch, menu, new Vector2(10, 0), 3);
		blocks.add(b);

		for(BlockPiece bp : blocks) {
			dbox.addBlockPiece(bp);
		}
	}

}
