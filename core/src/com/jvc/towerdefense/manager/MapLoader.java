package com.jvc.towerdefense.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.misc.MapLoading;
import com.jvc.towerdefense.models.World;

public class MapLoader {

	public FileHandle handle;
	public Array<Integer> dimensions = new Array<Integer>();
	public Array<Character> map = new Array<Character>();
	public MapLoading world;
	
	public MapLoader(String path, MapLoading world) {
		handle = Gdx.files.internal(path);
		this.world  = world;
		String lines[] = handle.readString().split("\\r?\\n");
		dimensions.add(21);
		dimensions.add(14);
		for(int i=1; i<lines.length; i++) {
			processLine(lines[i], i-1);
		}
	}
	
	public void processLine(String line, int i) {
		for(int j=0; j< line.length(); j++) {
			map.add(line.charAt(j));
		}
	}
	
	public Array<Integer> getDimensions() {
		return dimensions;
	}
	
	public void loadMapToGame() {
		for(int i=0; i< map.size; i++) {
			world.instantiateObject(map.get(i).charValue(), i);
		}
	}
}
