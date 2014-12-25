package com.jvc.towerdefense.particlesystem;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.ResourceManager;

public class BubbleParticleEmitter extends ParticleEmitter {

	public BubbleParticleEmitter() {
		super();
				
		life = 2.5f;
		damping =1f;
		
		set(ResourceManager.getInstance().bubble);
	}

	public void addParticle(Vector2 position) {
		addParticle(position, random.set(MathUtils.random() * 0.1f - 0.05f, 0.01f + MathUtils.random() * 0.05f) ,life, 1);
	}
	
	

}
