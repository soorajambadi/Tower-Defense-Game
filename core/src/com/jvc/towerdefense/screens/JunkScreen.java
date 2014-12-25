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
import com.jvc.towerdefense.controller.WorldController;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.manager.ResourceManager;
import com.jvc.towerdefense.models.BlockPiece;
import com.jvc.towerdefense.models.DialogBox;

public class JunkScreen implements Screen, InputProcessor{

	float oneWidth = 21/2;
	float oneHeigth = 14/3;
	float currentX = 0;
	float currentY = 0;
	WorldController controller;
	Screen toBeShownScreen = null;
	SpriteBatch batch = new SpriteBatch();
	DialogBox dBox;
	Array<BlockPiece> blocks;
	
	public  JunkScreen(WorldController controller) {
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
				5, 4, batch, controller, new Vector2(0, 0), 7);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().tower, 
				5, 4, batch, controller, new Vector2(5, 0), 6);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().windMill, 
				5, 4, batch, controller, new Vector2(10, 0), 2);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().storeTexture, 
				5, 4, batch, controller, new Vector2(15, 0), 8);
		blocks.add(b);
		
		b = new BlockPiece(ResourceManager.getInstance().barracksTexture, 
				5, 4, batch, controller, new Vector2(0, 5), 10);
		blocks.add(b);
		/*for(int i=0;i<3;i++) {
			BlockPiece b = new BlockPiece(ResourceManager.getInstance().houseTexture, 
				5, 2, batch, controller, new Vector2(i*7, i*6), i+1);
			blocks.add(b);
		}
		*/
		setDialogBoxBlockPieces(blocks);
	}
	
	public void processInput(int x, int y) {
		dBox.processInput(x, y);	
		if(x>=0 && x < 10.5 && y>28/3 && y<=14) {
			System.out.println("Inside farm");
			//toBeShownScreen = InstanceManager.getInstance().getFarmScreen();
		}
		else if(x>=0 && x < 10.5 && y>14/3 && y<=28/3) {
			System.out.println("Inside Link");
			//toBeShownScreen = InstanceManager.getInstance().getFarmScreen();
		}
		else if(x>=0 && x < 10.5 && y>0 && y<=14/3) {
			System.out.println("Inside Fuck");
			//toBeShownScreen = InstanceManager.getInstance().getFarmScreen();
		}
		else if(x>=10.5 && x <= 21 && y>0 && y<=14/3) {
			System.out.println("Inside goback");
			//toBeShownScreen = InstanceManager.getInstance().getFarmScreen();
		}
		else if(x>=10.5 && x <= 21 && y>14/3 && y<=28/3) {
			System.out.println("Inside Buildings");
			//toBeShownScreen = InstanceManager.getInstance().getFarmScreen();
		}
		else if(x>=10.5 && x <= 21 && y>28/3 && y<=14) {
			System.out.println("Inside Soldiers");
			//toBeShownScreen = InstanceManager.getInstance().getFarmScreen();
		}
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
		//controller.setActiveAction(6);
		float screenx;
		float screeny;
		screenx = screenX / InstanceManager.getInstance().ppuX;
		screeny = (InstanceManager.getInstance().gHeight- screenY)/ InstanceManager.getInstance().ppuY ;
		InstanceManager.getInstance().setCamPosBeforeBuildScreen(GameScreen.renderer.getCamera().position);
		processInput(MathUtils.floor(screenx), MathUtils.floor(screeny));
		((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().getGameScreen());
		GameScreen.renderer.getCamera().position.set(InstanceManager.getInstance().camPosBeforeBuildScreen);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().getGameScreen());
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		controller.setActiveAction(6);
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
		 /*
		  * batch.draw(ResourceManager.getInstance().houseTexture, 0, 0);
		 batch.draw(ResourceManager.getInstance().houseTexture, 50, 50);
		 batch.draw(ResourceManager.getInstance().houseTexture, 100, 100);
		 batch.draw(ResourceManager.getInstance().houseTexture, 150, 150);
		 batch.draw(ResourceManager.getInstance().houseTexture, 200, 200);
		 */
		 dBox.render();
		 batch.end();
		 
		
	}

	@Override
	public void resize(int width, int height) {
		InstanceManager.getInstance().setGraphics(width, height, InstanceManager.getInstance().WIDTH, 
				InstanceManager.getInstance().HEIGHT);
		InstanceManager.getInstance().ppuX = width/InstanceManager.getInstance().WIDTH;
		InstanceManager.getInstance().ppuY = height/InstanceManager.getInstance().HEIGHT;
		
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
