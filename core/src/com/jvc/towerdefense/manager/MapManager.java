package com.jvc.towerdefense.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.jvc.towerdefense.screens.TargetWorldScreen;

public class MapManager extends InstanceManager {
	
	public void handleMapAndWar() {
		TargetWorldScreen wScreen = new TargetWorldScreen();
		 ((Game) Gdx.app.getApplicationListener()).setScreen(wScreen);
	}
}
