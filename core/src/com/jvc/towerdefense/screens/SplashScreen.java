package com.jvc.towerdefense.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen{
	
	private Game game;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Sprite splash;
	private TweenManager tweenManager;
	
	public SplashScreen(Game g) {
		game = g;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        splash.draw(spriteBatch);
        spriteBatch.end();
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		float screenW = Gdx.graphics.getWidth();
        float screenH = Gdx.graphics.getHeight();
        float w = 1;
        float h = w * screenH / screenW;
        camera = new OrthographicCamera(w, h);
		Gdx.graphics.setVSync(Settings.vSync());
		spriteBatch = new SpriteBatch();
		splash = new Sprite(new Texture("data/splash.png"));
		splash.setSize(w, w * splash.getHeight() / splash.getWidth());
        splash.setPosition(-w/2, -h/2);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}).start(tweenManager);

		tweenManager.update(Float.MIN_VALUE);	
	}

	@Override
	public void hide() {
		dispose();
		
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
		spriteBatch.dispose();
		splash.getTexture().dispose();
		
	}
}
