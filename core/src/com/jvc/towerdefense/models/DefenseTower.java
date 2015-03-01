package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.controller.TowerMenu;

public class DefenseTower extends Tower{
	boolean isPowered = true;
	
	public DefenseTower(Vector2 pos) {
		super(pos);
        super.STRENGTH = 30f;
        super.currentStrength = 30f;
        super.POWER = 10f;
	}

    public DefenseTower()
    {
        System.out.println("In defense tower constructor ");
    }

	public boolean isPowered() {
		return isPowered;
	}
	
	public void setPower(boolean switcher) {
		isPowered = switcher;
	}
}
