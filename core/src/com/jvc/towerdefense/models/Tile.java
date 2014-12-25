package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Tile {
	
	public Vector2 position;
	public int id=0;
	public Array<Tree> trees = null;
	public Array<House> houses = null;
	public Array<DefenseTower> dTowers = null;
	public Array<Tower> towers = null;
	public Array<Person> persons = null;
	
	public Tile(Vector2 pos) {
		position = pos;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId(int id) {
		return id;
	}
	
	public void addTree(Tree tree) {
		if(trees == null) 
			trees = new Array<Tree>();
		trees.add(tree);
	}
	
	public void addHouse(House house) {
		if(houses == null) 
			houses = new Array<House>();
		houses.add(house);
	}
	
	public void addPerson(Person person) {
		if(persons == null) 
			persons = new Array<Person>();
		persons.add(person);
	}
	
	public void addDefenseTower(DefenseTower tower) {
		if(dTowers == null) 
			dTowers = new Array<DefenseTower>();
		dTowers.add(tower);
	}
	
	public void addTree(Tower tower) {
		if(towers == null) 
			towers = new Array<Tower>();
		towers.add(tower);
	}
}
