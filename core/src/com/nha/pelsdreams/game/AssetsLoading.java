package com.nha.pelsdreams.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.nha.pelsdreams.utils.Constants;

/**
 * Organiza y estructura los Assets
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetsLoading implements Disposable, AssetErrorListener {

	public static final String TAG = AssetsLoading.class.getName();
	public static final AssetsLoading instance = new AssetsLoading();
	private AssetManager assetManager;

	public TextureRegion loadingRegion;
	public TextureRegion ballRegion;

	// patrón singleton: evita la instanciación desde otras clases
	private AssetsLoading() {
	}
	
	/**
	 * Obtiene el asset manager
	 * 
	 * @return El asset manager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Carga todos los assets
	 * 
	 * @param assetManager
	 *            El assetManager
	 */
	public void load(AssetManager assetManager) {
		this.assetManager = assetManager;
		// establece el listener para los errores
		assetManager.setErrorListener(this);
		// carga texture atlas
		assetManager.load(Constants.TEXTURE_LOADING, TextureAtlas.class);

		assetManager.finishLoading();

		Gdx.app.debug(TAG, "# Assets cargados: "
				+ assetManager.getAssetNames().size);

		TextureAtlas atlas_loading = assetManager
				.get(Constants.TEXTURE_LOADING);
		// establece el filtro de suavizado de píxeles
		// para cada textura cuando se escala en aumento o decremento
		for (Texture t : atlas_loading.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		loadingRegion = new TextureRegion(atlas_loading.findRegion("loading"));
		
		ballRegion = new TextureRegion(atlas_loading.findRegion("ball"));

		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "## Asset: " + a);

	}

	/**
	 * Libera todos los recursos de este objeto.
	 */
	@Override
	public void dispose() {
		assetManager.dispose();
	}

	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "No se pudo cargar el asset '" + asset.fileName
				+ "'", (Exception) throwable);
	}

}
