package com.jvc.towerdefense.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.InstanceManager;
import com.jvc.towerdefense.misc.GameInputHandler;

public class Entity //implements GameInputHandler 
	{
	public boolean isSelected;
	Vector2 position;
	
	public Entity() {
		isSelected = false;
	}
	
	/*public boolean handleInput(float x, float y) {
		int X = MathUtils.floor(x);
		int Y = MathUtils.floor(y);
		
		Entity clickedObject = InstanceManager.getInstance().getEntityByPosition(X, Y);
		boolean handled;
		if(clickedObject!=null)
			if(InstanceManager.getInstance().activeEntity!=null) {
				handled = InstanceManager.getInstance().activeEntity.handleInput(X, Y, clickedObject);
				if(!handled) {
					InstanceManager.getInstance().activeEntity = clickedObject;
					clickedObject.setSelection(true);
				}
			}
			else {
			// Handle Menu related things here.
			// Or will come to know more about it later
				// change clicked Object also
				InstanceManager.getInstance().activeEntity = clickedObject;
				clickedObject.setSelection(true);
			}
		return false;
	}*/
	
	private boolean handleInput(int x, int y, Entity clickedObject) {
		if(isSelected) {
			// Do something here 
		}
		return false;	
	}

	public void setSelection(boolean value) {
		isSelected = value;
	}
	
	public boolean getSelection() {
		return isSelected;
	}
}
