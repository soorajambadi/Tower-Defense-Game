package com.jvc.towerdefense.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.misc.EventHandler;

public class BlockPiece {

	public TextureRegion texture;
	public float width;
	public float height;
	private SpriteBatch spritebatch;
	public Vector2 position;
	EventHandler eH;
	int onClickAction;
	// Position will be a point
	
	public BlockPiece(TextureRegion texture, float width, float height, SpriteBatch batch, EventHandler event, Vector2 position, int action) {
		eH = event;
		this.onClickAction = action;
		this.texture = texture;
		this.width = width;
		this.height = height;
		this.spritebatch = batch;
		this.position = position;
	}
	
	public boolean processInput(int x, int y) {
		System.out.println("x : " +x);
		System.out.println("y " + y); 
		if(x >= position.x && y >=position.y && x<= position.x + width && y <=position.y + height) {
			eH.HandleEvent(onClickAction);
			System.out.println("OnclickAction" + onClickAction);
			return true;
		}
		return false;
	}
	
	public void render() {
		float ppuX = Gdx.graphics.getWidth()/21;
		float ppuY = Gdx.graphics.getHeight()/14;
		spritebatch.draw(texture, position.x*InstanceManager.getInstance().ppuX, position.y*InstanceManager.getInstance().ppuY,
				width*InstanceManager.getInstance().ppuX, 
				InstanceManager.getInstance().ppuY*height);
	}
}
