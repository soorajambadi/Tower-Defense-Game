package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.controller.BarracksMenu;
import com.jvc.towerdefense.manager.InstanceManager;

public class Barracks extends House {
	
	public static float TIME = 1f;
	public Array<Soldier> queue = new Array<Soldier>();
	BarracksMenu bMenu;

	public Barracks(Vector2 pos) {
		super(pos);
		bMenu = new BarracksMenu();
	}

    public Barracks() {
        System.out.println("In Barracks Constructor");
    }
	
	public void createKnight(Vector2 pos) {
		Soldier knight = new Soldier(pos, 0);
		InstanceManager.getInstance().getSoldiers().add(knight);
	}
	
	public void createSpearMan(Vector2 pos) {
		Soldier spearman = new Soldier(pos, 1);
		InstanceManager.getInstance().getSoldiers().add(spearman);
	}
	
	public void createArcher(Vector2 pos) {
		Soldier archer = new Soldier(pos, 2);
		InstanceManager.getInstance().getSoldiers().add(archer);
	}

}
