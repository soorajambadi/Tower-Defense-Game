package com.jvc.towerdefense.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class DialogBox {
	
	public TextureRegion background;
	public float width;
	public float height;
	private SpriteBatch spritebatch;
	public Array<BlockPiece> blocks = new Array<BlockPiece>();
	
	public DialogBox(Array<BlockPiece> blocks) {
		this.blocks = blocks;
	}
	
	public void addBlockPiece(BlockPiece piece) {
		if(blocks == null)
			blocks = new Array<BlockPiece>();
		blocks.add(piece);
	}
	
	public void render() {
		if(blocks!=null) {
			for(BlockPiece block : blocks) {
				block.render();
			}
		}
	}
	
	public boolean processInput(int x, int y) {
		System.out.println("x :"+ x + "y :" + y);
		if(blocks!=null) {
			for(BlockPiece block : blocks) {
				if(block.processInput(x, y))
					return true;
			}
		}
		return false;
	}

}
