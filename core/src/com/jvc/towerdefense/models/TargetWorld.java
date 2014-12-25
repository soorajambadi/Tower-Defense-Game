package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.misc.MapLoading;

public class TargetWorld extends World implements MapLoading {
	
	public Array<Tower> towers;
	public Array<DefenseTower> defenseTowers;
	public Array<Tree> trees;
	public Array<House> houses;
	public Array<Store> stores;
	public Array<Soldier> soldiers;
	public Array<Barracks> barracks;
	
	public TargetWorld() {
		towers = new Array<Tower>();
		defenseTowers = new Array<DefenseTower>();
		trees = new Array<Tree>();
		houses = new Array<House>();
		stores = new Array<Store>();
		soldiers = new Array<Soldier>();
		// These are our soldiers
		barracks = new Array<Barracks>();
	}

	@Override
	public void instantiateObject(char c, int i) {
		if (c == 'O')
    		createTower( i%21, i/21);
    	if (c == 'H')
    		createHouse(i%21, i/21);
    	if (c == 'D')
    		createDefenseTower(i%21, i/21);
    	if (c == 'T')
    		createTree(i%21, i/21);
    	if (c == 'S')
    		createStore(i%21, i/21);
	}

	private void createStore(int i, int j) {
		stores.add(new Store(new Vector2(i, j)));
		
	}

	private void createTree(int i, int j) {
		trees.add(new Tree(new Vector2(i, j)));
		
	}

	private void createDefenseTower(int i, int j) {
		defenseTowers.add(new DefenseTower(new Vector2(i, j)));
		
	}

	private void createHouse(int i, int j) {
		houses.add(new House(new Vector2(i, j)));
		
	}

	private void createTower(int i, int j) {
		towers.add(new Tower(new Vector2(i, j)));
		
	}
	
}
