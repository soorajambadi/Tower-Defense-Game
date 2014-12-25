package com.jvc.towerdefense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.controller.BarracksMenu;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.ResourceManager;
import com.jvc.towerdefense.models.BlockPiece;
import com.jvc.towerdefense.models.DialogBox;

public class BarracksScreen implements Screen, InputProcessor {
	
	BarracksMenu controller;
	DialogBox dBox;
	SpriteBatch batch = new SpriteBatch();
	Array<BlockPiece> blocks;

	public  BarracksScreen(BarracksMenu controller) {
		this.controller = controller;
		dBox = new DialogBox(null);
		createDemoBoxes();
	}
	
	public void setDialogBoxBlockPieces(Array<BlockPiece> blocks) {
		for(BlockPiece block : blocks) {
			dBox.addBlockPiece(block);
		}
	}
	
	public void createDemoBoxes() {
		blocks = new Array<BlockPiece>();
		BlockPiece b = new BlockPiece(ResourceManager.getInstance().houseTexture, 
				5, 4, batch, controller, new Vector2(0, 0), 1);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().tower, 
				5, 4, batch, controller, new Vector2(5, 0), 2);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().windMill, 
				5, 4, batch, controller, new Vector2(10, 0), 3);
		blocks.add(b);
		setDialogBoxBlockPieces(blocks);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
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
		float screenx;
		float screeny;
		screenx = screenX / InstanceManager.getInstance().ppuX;
		screeny = (InstanceManager.getInstance().gHeight- screenY)/ InstanceManager.getInstance().ppuY ;
		dBox.processInput(MathUtils.floor(screenx), MathUtils.floor(screeny));
		//processInput(MathUtils.floor(screenx), MathUtils.floor(screeny));
		((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().getGameScreen());
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
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
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		dBox.render();
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
}
