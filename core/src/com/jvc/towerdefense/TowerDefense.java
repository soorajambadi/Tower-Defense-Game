package com.jvc.towerdefense;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.screens.GameScreen;
import com.jvc.towerdefense.screens.MainMenu;
import com.jvc.towerdefense.screens.SplashScreen;

public class TowerDefense extends Game {
	
	public static Screen currentScreen;
	public static String TITLE = "Tower Defense JVC";
	
	@Override
	public void create() {	
		//currentScreen = new MainMenu();
		currentScreen = InstanceManager.getInstance().getGameScreen();
		//currentScreen = new MainMenu();
		//currentScreen = new SplashScreen(this);
	    setScreen(currentScreen);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
	    super.render();
	}

	@Override
	public void resize(int width, int height) {
		currentScreen.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
