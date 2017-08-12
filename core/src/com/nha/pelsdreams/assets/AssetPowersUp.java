package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/**
 * Representa la imagen del Power-up
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetPowersUp {

	public final AtlasRegion powerUpRed;
	public final AtlasRegion powerUpBlue;
	public final AtlasRegion powerUpGreen;
	public final AtlasRegion powerUpRedGUI;
	public final AtlasRegion powerUpBlueGUI;
	public final AtlasRegion powerUpGreenGUI;

	public AssetPowersUp(TextureAtlas atlas) {
		powerUpRed = atlas.findRegion("power_red");
		powerUpBlue = atlas.findRegion("power_blue");
		powerUpGreen = atlas.findRegion("power_green");
		powerUpRedGUI = new AtlasRegion(atlas.findRegion("power_red"));
		powerUpBlueGUI = new AtlasRegion(atlas.findRegion("power_blue"));
		powerUpGreenGUI = new AtlasRegion(atlas.findRegion("power_green"));
		
		// Rota la imagen para que se vea correctamente
		// en el GUI
		powerUpRedGUI.flip(false, true);
		powerUpBlueGUI.flip(false, true);
		powerUpGreenGUI.flip(false, true);
	}
}