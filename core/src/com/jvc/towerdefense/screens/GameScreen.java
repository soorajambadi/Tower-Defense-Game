package com.jvc.towerdefense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.jvc.towerdefense.manager.GameSaver;
import com.jvc.towerdefense.controller.WorldController;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.models.World;
import com.jvc.towerdefense.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor{
    
    private World world;
    public static WorldRenderer renderer;
    private WorldController controller;
    private int width, height;
    // for calculating camera movement when dragged.
    final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	final Vector3 newPos = new Vector3();
    
    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(1f, 0f, 0f, .1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;
		System.out.println("width is " + width + "height is " + height);
	
    }

    @Override
    public void show() {
    	if(world == null)
    		world = new World();
    	if(renderer == null)
    		renderer = new WorldRenderer(world);
    	if(controller == null)
    		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
	
    }

    public void restartWithWorld(World w) {
        //this.world = world;
        this.world = new World();
        renderer = new WorldRenderer(world);
        controller = new WorldController(world);
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
    	Gdx.input.setInputProcessor(null);
	
    }

    @Override
    public boolean keyDown(int keycode) {
    	GameSaver g = new GameSaver(new World());
    	if(keycode == Input.Keys.B) {
    		((Game) Gdx.app.getApplicationListener()).setScreen(InstanceManager.getInstance().getMainMenuScreen());
    	}
    	if(keycode == Input.Keys.S) {
    		g.saveGame(world);

    	}
    	else if(keycode == Input.Keys.L) {
            World w = g.loadGame();
            restartWithWorld(w);
        }
    	
    	//Implementation is wrong here. 
    	int w = Gdx.graphics.getWidth();
    	int h = Gdx.graphics.getHeight();
    	if(keycode == Input.Keys.I) {
    		Vector3 camPos = new Vector3(renderer.getCamera().position);
    		renderer.setCameraWidthHeight(renderer.getCameraWidth()*2, renderer.getCameraHeigth()*2);
    		renderer.setSize(w, h);
    		renderer.getCamera().position.set(camPos);
    		renderer.getCamera().update();
    	}
    	else if(keycode == Input.Keys.O) {
    		Vector3 camPos = new Vector3(renderer.getCamera().position);
    		camPos.scl(1/renderer.ppuX, 1/renderer.ppuY, 1);
    		renderer.setCameraWidthHeight(renderer.getCameraWidth()/2, renderer.getCameraHeigth()/2);
    		renderer.setSize(w, h);
    		camPos.scl(renderer.ppuX, renderer.ppuY, 1);
    		renderer.getCamera().position.set(camPos);
    		renderer.getCamera().update();
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
    public boolean touchDown(int x, int y, int pointer, int button) {
	//System.out.println(x + " " + y);
	    System.out.println("x :" + x + "y :" + y+ "width :" + width + "height :" + height);
		controller.touchDown(x*renderer.getCameraWidth()/width, (height - y)*renderer.getCameraHeigth()/height, pointer, button);
		last.set(x, y, 0);
		return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
	// TODO Auto-generated method stub
    	return false;
    }

    /* Design is broken here you will have to sync camera with map 
     * also touch dragged is called with each little movement after clicking
     * without taking off finger */
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
		OrthographicCamera camera = renderer.getCamera();
		//camera.unproject(curr.set(x, y, 0));
		curr.set(x, y, 0);
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
		last.set(x, y, 0);
    	return false;
    }

    public boolean touchMoved(int x, int y) {
	// TODO Auto-generated method stub
    	return false;
    }

    @Override
    public boolean scrolled(int amount) {
	// TODO Auto-generated method stub
    	return false;
    }

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

}
