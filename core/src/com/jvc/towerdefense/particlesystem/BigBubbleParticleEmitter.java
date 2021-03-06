package com.jvc.towerdefense.particlesystem;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jvc.towerdefense.manager.ResourceManager;


public class BigBubbleParticleEmitter extends ParticleEmitter {

	public BigBubbleParticleEmitter() {
		super();
				
		life = 2.5f;
		damping =1f;
		
		set(ResourceManager.getInstance().bigbubble);
	}

	public void addParticle(Vector2 position) {
		addParticle(position, random.set(MathUtils.random() * 1.2f - 0.05f, 0.02f + MathUtils.random() * 1.2f) ,life, 1);
	}	

}
