package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Representa las imagenes de los enemigos
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetEnemyWorld001 {

	public final Animation<TextureRegion> snake_first, snake_second;
	public final Animation<TextureRegion> arrows;
	public final Animation<TextureRegion> worm;

	public AssetEnemyWorld001(TextureAtlas atlas) {

		// Animacion de la serpiente
		Array<AtlasRegion> regSnake_first = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regSnake_1 = atlas.findRegion("snake", 1);
		AtlasRegion regSnake_2 = atlas.findRegion("snake", 2);
		regSnake_first.add(regSnake_1);
		regSnake_first.add(regSnake_2);
		Array<AtlasRegion> regSnake_second = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regSnake_3 = atlas.findRegion("snake", 3);
		AtlasRegion regSnake_4 = atlas.findRegion("snake", 4);
		AtlasRegion regSnake_5 = atlas.findRegion("snake", 5);
		regSnake_second.add(regSnake_3);
		regSnake_second.add(regSnake_4);
		regSnake_second.add(regSnake_5);

		snake_first = new Animation<>(1.0f / 15.0f, regSnake_first,
				Animation.PlayMode.LOOP_PINGPONG);
		snake_second = new Animation<>(1.0f / 15.0f, regSnake_second,
				Animation.PlayMode.LOOP_PINGPONG);

		// Animacion de la flecha
		Array<AtlasRegion> regArrows = null;
		regArrows = atlas.findRegions("arrows");

		arrows = new Animation<>(1.0f / 15.0f, regArrows,
				Animation.PlayMode.LOOP_PINGPONG);

		// Animacion del worm
		Array<AtlasRegion> regWorm = null;
		regWorm = atlas.findRegions("worm");

		worm = new Animation<>(1.0f / 15.0f, regWorm,
				Animation.PlayMode.LOOP_PINGPONG);
	}
}
