package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Representa los efectos especiales
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetEffects {

	public final Animation<TextureRegion> smokeLaunch;

	public AssetEffects(TextureAtlas atlas) {
		// Animacion efecto humo lanzar
		Array<AtlasRegion> regLaunch = new Array<TextureAtlas.AtlasRegion>();
		for (int i = 1; i < 11 ; i++) {
			regLaunch.add(new AtlasRegion(atlas.findRegion("launch", i)));
		}

		smokeLaunch = new Animation<>(1.0f / 30.0f, regLaunch, Animation.PlayMode.NORMAL);
	}
}
