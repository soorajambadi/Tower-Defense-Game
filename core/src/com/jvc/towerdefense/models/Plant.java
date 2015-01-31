package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.Vector2;

public class Plant extends Entity {
    public static float WIDTH = 2f;
    public static float HEIGHT = 2f;
    private float stateTime = 0f;

    public Plant() {
        System.out.print("In Plant Constructor");
    }

    public float getStateTime() {
        return stateTime;
    }

    public void incrementStateTime(float delta) {
        stateTime += delta;
    }

    public Plant(Vector2 pos) {
        this.position = new Vector2(pos);
    }

    public Vector2 getPosition() {
        return position;
    }
}
