package com.nha.pelsdreams.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.nha.pelsdreams.assets.AssetSounds;
import com.nha.pelsdreams.utils.Constants;

/**
 * Organiza y estructura los Assets de la UI
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetsUI implements Disposable, AssetErrorListener {

	public static final String TAG = AssetsUI.class.getName();
	public static final AssetsUI instance = new AssetsUI();
	private AssetManager assetManager;

	public Skin skinMainMenu;
	public Skin skinWorldLevels;
	public AssetSounds sounds;

	// patr�n singleton: evita la instanciaci�n desde otras clases
	private AssetsUI() {
	}
	
	/**
	 * Obtiene el asset manager
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
		Texture.setAssetManager(assetManager);
		// carga texture atlas
		assetManager.load(Constants.TEXTURE_MAIN_MENU_ATLAS_UI,
				TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_WORLD_LEVELS_ATLAS_UI,
				TextureAtlas.class);
		assetManager.load(Constants.SOUND_BUTTON, Sound.class);
	}
	
	/**
	 * Obtiene los assets cargados
	 */
	public boolean getLoaded() {
		Gdx.app.debug(TAG, "# Assets cargados: "
				+ assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "## Asset: " + a);

		TextureAtlas atlas_skinMainMenu = assetManager
				.get(Constants.TEXTURE_MAIN_MENU_ATLAS_UI);
		TextureAtlas atlas_skinWorldLevels = assetManager
				.get(Constants.TEXTURE_WORLD_LEVELS_ATLAS_UI);
		// establece el filtro de suavizado de p�xeles
		// para cada textura cuando se escala en aumento o decremento
		for (Texture t : atlas_skinMainMenu.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		for (Texture t : atlas_skinWorldLevels.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// crea los recursos
		skinMainMenu = new Skin(
				Gdx.files.internal(Constants.SKIN_MAIN_MENU_UI),
				atlas_skinMainMenu);
		skinWorldLevels = new Skin(
				Gdx.files.internal(Constants.SKIN_WORLD_LEVELS_UI),
				atlas_skinWorldLevels);
		// Filtro suavizado para las texturas
		for (Texture t : skinMainMenu.getAtlas().getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		for (Texture t : skinWorldLevels.getAtlas().getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skinWorldLevels.getFont("default-font").getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sounds = new AssetSounds(assetManager, "UI");
		return true;
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
