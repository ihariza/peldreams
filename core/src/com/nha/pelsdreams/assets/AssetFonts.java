package com.nha.pelsdreams.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa las fuentes del juego
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetFonts {
	public final BitmapFont generalNormal;
	public final BitmapFont generalNormalFlipped;
	public final BitmapFont generalBig;
	public final BitmapFont generalBigFlipped;
	public final BitmapFont yellowNormalFlipped;
	public final BitmapFont yellowBig;
	public final BitmapFont yellowBigFlipped;

	public AssetFonts() {
		// Crea tres fuentes de bitmap de Libgdx's de 15px
		generalNormal = new BitmapFont(
				Gdx.files.internal(Constants.GENERAL_NORMAL_FONT));
		generalNormalFlipped = new BitmapFont(
				Gdx.files.internal(Constants.GENERAL_NORMAL_FONT), true);
		generalBig = new BitmapFont(
				Gdx.files.internal(Constants.GENERAL_BIG_FONT));
		generalBigFlipped = new BitmapFont(
				Gdx.files.internal(Constants.GENERAL_BIG_FONT), true);
		generalBigFlipped.getData().setScale(0.65f);
		yellowNormalFlipped = new BitmapFont(
				Gdx.files.internal(Constants.YELLOW_NORMAL_FONT), true);
		yellowBig = new BitmapFont(
				Gdx.files.internal(Constants.YELLOW_BIG_FONT));
		yellowBigFlipped = new BitmapFont(
				Gdx.files.internal(Constants.YELLOW_BIG_FONT), true);
		yellowBigFlipped.getData().setScale(0.65f);

		// Establecemos el filtro de suavizado para las fuentes
		generalNormal.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generalNormalFlipped.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generalBig.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		generalBigFlipped.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		yellowBig.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		yellowNormalFlipped.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		yellowBigFlipped.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
}
