package com.jvc.towerdefense.manager;

import java.util.HashMap;

import com.jvc.towerdefense.models.Entity;

public class TileManager {
	//public Array<Tile> tiles = new Array<Tile>();
	// Make a hash table here.
	HashMap<Integer, Entity> map;
	
	
	public TileManager() {
		map = new HashMap<Integer, Entity>(21*14);
	}
	
	public void addTile(int key, Entity value) {
	    map.put(key, value);
	}
	
	public Entity getTile(int key) {
		return map.get(key);
	}
	
}
