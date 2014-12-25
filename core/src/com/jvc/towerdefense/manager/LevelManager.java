package com.jvc.towerdefense.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class LevelManager {
	
	public static int level=1;
	private FileHandle levelFile;
	private Json json;
	
	public LevelManager() {
		level = 1;
		json = new Json();
		levelFile = Gdx.files.local("data/levelFile");
	}
	
	public int getLevel() {
		return level;
	}
	
	public void loadLevelFile() {
		
	}
	
	public void getLevelDetails(int level) {
		
	}
	
	public void saveLevelFile() {
		json.writeValue("level", 1);
		json.writeValue("EnemyPower", 3);
		json.writeValue("EnemyRange", 7);
		//levelFile.writeString(json., append)
	}
	
	
}


