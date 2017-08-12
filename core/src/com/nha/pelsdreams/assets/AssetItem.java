package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Representa la imagen del item a recolectar
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetItem {

	public final Animation animation;

	public AssetItem(TextureAtlas atlas) {
		
		// Animacion del item
		Array<AtlasRegion> regItem = atlas.findRegions("item");

		animation = new Animation(1.0f / 12.0f, regItem,
				Animation.PlayMode.LOOP_PINGPONG);
	}
}
