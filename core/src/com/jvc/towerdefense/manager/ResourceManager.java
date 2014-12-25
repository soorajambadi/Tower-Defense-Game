package com.jvc.towerdefense.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class ResourceManager {
	
	public static ResourceManager instance;
	public TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("texture/dfa.pack"));
	public TextureAtlas atlas2 = new TextureAtlas(Gdx.files.internal("texture/packhigh"));
	public AtlasRegion townCentre = atlas.findRegion("XMLID_42_");
	public AtlasRegion tower = atlas.findRegion("XMLID_2_");
	public AtlasRegion selection = atlas.findRegion("use42234");
	public AtlasRegion windMill = atlas.findRegion("use42193");
	public AtlasRegion asteroidShower = atlas.findRegion("use42224");
	public AtlasRegion treeTexture = atlas.findRegion("use42294");
	public AtlasRegion houseTexture = atlas.findRegion("use42355");
	public AtlasRegion personTexture = atlas.findRegion("use42236");
	public AtlasRegion storeTexture = atlas.findRegion("use42195");
	public AtlasRegion rangeTexture = atlas.findRegion("use42330");
	public AtlasRegion barracksTexture = atlas.findRegion("use42207");
	public AtlasRegion menu1Texture = atlas.findRegion("use42346");
	public AtlasRegion menu2Texture = atlas.findRegion("use42216");
	public AtlasRegion menu3Texture = atlas.findRegion("use42226");
	public AtlasRegion menu4Texture = atlas.findRegion("XMLID_44_");
	public AtlasRegion menu5Texture = atlas.findRegion("use42182");
	
	public Sprite spark = atlas2.createSprite("spark");
	public Sprite bubble = atlas2.createSprite("bubble");
	public Sprite bigbubble = atlas2.createSprite("bigbubble");
	public Sprite explosion = atlas2.createSprite("explosion");
	
	//public Texture t = test.getTexture();
	
	public static ResourceManager getInstance() {	
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	public ResourceManager() {

		/*atlas = new TextureAtlas(Gdx.files.internal("texture/dfa.pack"));
		atlas2 = new TextureAtlas(Gdx.files.internal("texture/packhigh"));
		townCentre = atlas.findRegion("XMLID_34_");
		tower = atlas.findRegion("XMLID_2_");
		selection = atlas.findRegion("use42234");
		windMill = atlas.findRegion("use42193");
		asteroidShower = atlas.findRegion("use42224");
		treeTexture = atlas.findRegion("use42294");
		houseTexture = atlas.findRegion("use42355");
		personTexture = atlas.findRegion("use42236");
		
		spark = atlas2.createSprite("spark");
		bubble = atlas2.createSprite("bubble");
		bigbubble = atlas2.createSprite("bigbubble");
		explosion = atlas2.createSprite("explosion");
		//t = test.getTexture();
		 */
	}
}
