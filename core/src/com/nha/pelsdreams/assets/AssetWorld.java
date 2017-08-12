package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/**
 * Representa las imágenes del decorado
 * @author Nacho Herrera
 *
 */
public class AssetWorld {
	
	public final AtlasRegion blueMountains;
	public final AtlasRegion sun;
	public final AtlasRegion ground;
	public final AtlasRegion cloud1;
	public final AtlasRegion cloud2;
	public final AtlasRegion cloud3;
	public final AtlasRegion background_layer2_1;
	public final AtlasRegion background_layer2_2;
	public final AtlasRegion background_layer2_2v1;
	public final AtlasRegion background_layer2_2v2;
	public final AtlasRegion background_layer2_2v4;
	public final AtlasRegion platform;

	public AssetWorld(TextureAtlas atlas) {
		blueMountains = atlas.findRegion("world_001_blueMontains");
		sun = atlas.findRegion("world_001_sun");
		ground = atlas.findRegion("world_001_ground");
		cloud1 = atlas.findRegion("cloud1");
		cloud2 = atlas.findRegion("cloud2");
		cloud3 = atlas.findRegion("cloud3");
		background_layer2_1 = atlas.findRegion("world_001_background21");
		background_layer2_2 = atlas.findRegion("world_001_background22");
		background_layer2_2v1 = atlas.findRegion("world_001_background22v1");
		background_layer2_2v2 = atlas.findRegion("world_001_background22v2");
		background_layer2_2v4 = atlas.findRegion("world_001_background22v4");
		platform = atlas.findRegion("platform_brown");
	}
}
