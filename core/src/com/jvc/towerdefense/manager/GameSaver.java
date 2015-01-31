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
        InstanceManager manager = InstanceManager.getInstance();
        System.out.println(json.prettyPrint(manager));
        saveDataValue("instance", InstanceManager.getInstance());

		return true;
	}
	
	public World loadGame() {
        save = getSave();
        InstanceManager m = loadDataValue("instance", InstanceManager.class);
        InstanceManager.setcurrentInstance(m);
        return world;
	}
}
