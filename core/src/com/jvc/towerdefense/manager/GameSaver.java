package com.jvc.towerdefense.manager;

import com.badlogic.gdx.utils.Json;
import com.jvc.towerdefense.models.World;

public class GameSaver {

	public void save(World world) 
	{
		Json json = new Json();
		json.toJson(world);
	}
}
