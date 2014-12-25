package com.jvc.towerdefense.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.World;

public class GameSaver {
	
	private World world;
	private Json json;
	FileHandle saveFile;

	public GameSaver(World world) {
		this.world = world;
		json = new Json();	
		saveFile = Gdx.files.local("data/saveFile");
	}
	
	public boolean saveGame() {
		//Seems like the pool object is causing some problems
		//saveFile.writeString(json.toJson(world.getTowers(), Array.class, Tower.class), false);
		//System.out.println(json.toJson(world.getTowers()));
		//saveFile.writeString(json.toJson(world.getLinks(), Array.class, Link.class), true);
		//System.out.println(json.toJson(world.getLinks(), Array.class, Link.class));
		//saveFile.writeString(json.toJson(world, World.class), true);
		//System.out.println(json.toJson(world, World.class));
		return true;
	}
	
	public boolean loadGame() {
		Array<Tower> towers = json.fromJson(Array.class, Tower.class, saveFile);
		world.loadTowerFromFile(towers);
		Array<Link> links = json.fromJson(Array.class, Link.class, saveFile);
		//World world = json.fromJson(World.class, saveFile);
		
		world.loadLinksFromFile(links);
		return true;
	}
}
