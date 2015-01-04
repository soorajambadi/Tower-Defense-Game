package com.jvc.towerdefense.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Menu;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.World;

public class GameSaver {
	
	private World world;
	private Json json;
	private FileHandle saveFile;
    private Save save = new Save();

    public static class Save {
        public ObjectMap<String, Object> data = new ObjectMap<String, Object>();
    }

	public GameSaver(World world) {
		this.world = world;
		json = new Json();
		saveFile = Gdx.files.local("data/saveFile");
	}

    public Save getSave() {
        Save save = new Save();
        save = json.fromJson(Save.class, saveFile.readString());
        return save;
    }

    public void saveToJson(){
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        saveFile.writeString(json.prettyPrint(save), false);
        System.out.println(json.prettyPrint(save));
    }

    public <T> T loadDataValue(String key, Class type){
        if(save.data.containsKey(key))
            return (T) save.data.get(key);
        else
            return null;
    }
    public void saveDataValue(String key, Object object){
        save.data.put(key, object);
        saveToJson();
    }


    public boolean saveGame(World world) {
        //saveDataValue("towers", world.getTowers());
        //saveDataValue("links", world.getLinks());
        InstanceManager manager = InstanceManager.getInstance();
        System.out.println(json.prettyPrint(manager));
        saveDataValue("instance", InstanceManager.getInstance());
		//Seems like the pool object is causing some problems
		/**
        saveFile.writeString(json.toJson(world.getTowers(), Array.class, Tower.class), false);
		System.out.println(json.toJson(world.getTowers()));
		saveFile.writeString(json.toJson(world.getLinks(), Array.class, Link.class), true);
		System.out.println(json.toJson(world.getLinks(), Array.class, Link.class));
        **/
		//saveFile.writeString(json.toJson(world, World.class), false);
		//System.out.println(json.toJson(world, World.class));

		return true;
	}
	
	public World loadGame() {
        save = getSave();
        //Array<Tower> towers = loadDataValue("towers", Array.class);
        //Array<Link> links = loadDataValue("links", Array.class);
		//Array<Tower> towers = json.fromJson(Array.class, Tower.class, saveFile);
		//world.loadTowerFromFile(towers);
		//Array<Link> links = json.fromJson(Array.class, Link.class, saveFile);
        //System.out.println(links);
        //System.out.println(json.fromJson(World.class, saveFile));
		////World world2 = json.fromJson(World.class, saveFile);
        //world.menu = new Menu(world);
        //World li = json.fromJson(World.class, saveFile);
		//world.loadLinksFromFile(world2.getLinks());
		//return world2;
        //InstanceManager.getInstance().setTowers(towers);
        //InstanceManager.getInstance().setLinks(links);
        //world.loadTowerFromFile(towers);
        //world.loadLinksFromFile(links);
        InstanceManager m = loadDataValue("instance", InstanceManager.class);
        InstanceManager.setcurrentInstance(m);
        return world;

        //InstanceManager manager = json.fromJson(InstanceManager.class, text);
	}
}
