package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Representa la imagen del item principal (reloj)
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetMainItem {

	public final Animation<TextureRegion> animation;
	public final Animation<TextureRegion> animationIdle;

	public AssetMainItem(TextureAtlas atlas) {
		
		// Animacion del item
		//Array<AtlasRegion> regMainItem = atlas.findRegions("main_item");
		Array<AtlasRegion> regMainItem = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regMainItem_1 = atlas.findRegion("main_item", 1);
		AtlasRegion regMainItem_2 = atlas.findRegion("main_item", 2);
		AtlasRegion regMainItem_3 = atlas.findRegion("main_item", 3);

		regMainItem.add(regMainItem_1);
		regMainItem.add(regMainItem_2);
		regMainItem.add(regMainItem_3);
		regMainItem.add(regMainItem_2);
		regMainItem.add(regMainItem_3);
		regMainItem.add(regMainItem_2);
		regMainItem.add(regMainItem_3);
		
		animation = new Animation<>(1.0f / 20.0f, regMainItem,
				Animation.PlayMode.LOOP_PINGPONG);
		
		Array<AtlasRegion> regMainItemIdle = new Array<TextureAtlas.AtlasRegion>();
		regMainItemIdle.add(regMainItem_1);
	
		animationIdle = new Animation<>(1.0f / 20.0f, regMainItemIdle,
				Animation.PlayMode.LOOP_PINGPONG);
	}
}
