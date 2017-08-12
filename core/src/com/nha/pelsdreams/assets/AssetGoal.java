package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Representa la imagen de la meta
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetGoal {

	public final TextureRegion goal;
	public final ParticleEffect particleEffect;

	public AssetGoal(TextureAtlas atlas, ParticleEffect effect) {
		// Textura del goal
		goal = atlas.findRegion("goal");
		particleEffect = effect;
	}
}
