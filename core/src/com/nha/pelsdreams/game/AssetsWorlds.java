package com.nha.pelsdreams.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.nha.pelsdreams.assets.AssetEffects;
import com.nha.pelsdreams.assets.AssetEnemyWorld001;
import com.nha.pelsdreams.assets.AssetFonts;
import com.nha.pelsdreams.assets.AssetGoal;
import com.nha.pelsdreams.assets.AssetInGameUI;
import com.nha.pelsdreams.assets.AssetItem;
import com.nha.pelsdreams.assets.AssetMainItem;
import com.nha.pelsdreams.assets.AssetPowersUp;
import com.nha.pelsdreams.assets.AssetRunner;
import com.nha.pelsdreams.assets.AssetSounds;
import com.nha.pelsdreams.assets.AssetWorld;
import com.nha.pelsdreams.utils.Constants;

/**
 * Organiza y estructura los Assets del Mundo
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetsWorlds implements Disposable, AssetErrorListener {

	public static final String TAG = AssetsWorlds.class.getName();

	public static final AssetsWorlds instance = new AssetsWorlds();
	private AssetManager assetManager;

	public AssetFonts fonts;
	public AssetRunner runner;
	public AssetEnemyWorld001 enemiesWorld001;
	public AssetMainItem mainItem;
	public AssetItem item;
	public AssetGoal goal;
	public AssetPowersUp powerUp;
	public AssetWorld world;
	public AssetEffects effects;
	public AssetInGameUI inGameUI;
	public int worldnumber;
	public AssetSounds sounds;

	// Patr�n singleton: evita la instanciaci�n desde otras clases
	private AssetsWorlds() {
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
	public void load(AssetManager assetManager, int worldnumber) {
		this.assetManager = assetManager;
		Texture.setAssetManager(assetManager);
		// Establece el listener para los errores
		assetManager.setErrorListener(this);
		Texture.setAssetManager(assetManager);
		// Asigna el n�mero de mundo
		this.worldnumber = worldnumber;
		// Carga texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_EFFECTS, TextureAtlas.class);
		assetManager.load(Constants.TEXTURE_ATLAS_RUNNER, TextureAtlas.class);
		assetManager
				.load(Constants.TEXTURE_ATLAS_INGAME_UI, TextureAtlas.class);
		assetManager.load(Constants.PARTICLE_DUST, ParticleEffect.class);
		assetManager.load(Constants.PARTICLE_FALL, ParticleEffect.class);
		assetManager.load(Constants.PARTICLE_GOAL, ParticleEffect.class);
		assetManager.load(Constants.PARTICLE_ITEM, ParticleEffect.class);
		assetManager.load(Constants.SOUND_ARROWS, Sound.class);
		assetManager.load(Constants.SOUND_WALL, Sound.class);
		assetManager.load(Constants.SOUND_DODGING, Sound.class);
		assetManager.load(Constants.SOUND_JUMP, Sound.class);
		assetManager.load(Constants.SOUND_SNAKE_DEAD, Sound.class);
		assetManager.load(Constants.SOUND_TOTEM, Sound.class);
		assetManager.load(Constants.SOUND_DEAD, Sound.class);
		assetManager.load(Constants.SOUND_ITEM, Sound.class);

		switch (worldnumber) {
		case 1:
			assetManager.load(Constants.TEXTURE_ATLAS_WORLD001_BACKGROUND,
					TextureAtlas.class);
			assetManager.load(Constants.TEXTURE_ATLAS_WORLD001_ENEMIES,
					TextureAtlas.class);
			assetManager.load(Constants.MUSIC_DESERT, Music.class);
			break;
		default:
			break;
		}
	}

	/**
	 * Inicializa los assets seg�n el mundo
	 */
	public void getLoaded() {
		Gdx.app.debug(TAG, "# Assets cargados: "
				+ assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "## Asset: " + a);

		TextureAtlas atlas_objects = assetManager
				.get(Constants.TEXTURE_ATLAS_OBJECTS);
		TextureAtlas atlas_effects = assetManager
				.get(Constants.TEXTURE_ATLAS_EFFECTS);
		TextureAtlas atlas_runner = assetManager
				.get(Constants.TEXTURE_ATLAS_RUNNER);
		TextureAtlas atlas_ingame_ui = assetManager
				.get(Constants.TEXTURE_ATLAS_INGAME_UI);
		ParticleEffect dustParticles = assetManager.get(Constants.PARTICLE_DUST);
		ParticleEffect fallParticles = assetManager.get(Constants.PARTICLE_FALL);
		ParticleEffect goalParticles = assetManager.get(Constants.PARTICLE_GOAL);
		

		// establece el filtro de suavizado de p�xeles
		// para cada textura cuando se escala en aumento o decremento
		for (Texture t : atlas_objects.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		for (Texture t : atlas_effects.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		for (Texture t : atlas_runner.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// crea los recursos
		fonts = new AssetFonts();
		runner = new AssetRunner(atlas_runner, dustParticles, fallParticles);
		effects = new AssetEffects(atlas_effects);
		goal = new AssetGoal(atlas_objects, goalParticles);
		mainItem = new AssetMainItem(atlas_objects);
		inGameUI = new AssetInGameUI(atlas_ingame_ui);
		sounds = new AssetSounds(assetManager, "WORLD");

		switch (worldnumber) {
		case 1:
			TextureAtlas atlas_world001_background = assetManager
					.get(Constants.TEXTURE_ATLAS_WORLD001_BACKGROUND);
			TextureAtlas atlas_world001_enemies = assetManager
					.get(Constants.TEXTURE_ATLAS_WORLD001_ENEMIES);

			for (Texture t : atlas_world001_background.getTextures())
				t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			for (Texture t : atlas_world001_enemies.getTextures())
				t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

			item = new AssetItem(atlas_objects);
			powerUp = new AssetPowersUp(atlas_objects);
			world = new AssetWorld(atlas_world001_background);
			enemiesWorld001 = new AssetEnemyWorld001(atlas_world001_enemies);
			break;
		default:
			break;
		}
	}

	/**
	 * Libera todos los recursos de este objeto.
	 */
	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.yellowBig.dispose();
		fonts.yellowBigFlipped.dispose();
		fonts.yellowNormalFlipped.dispose();
		fonts.generalBig.dispose();
		fonts.generalBigFlipped.dispose();
		fonts.generalNormalFlipped.dispose();
		fonts.generalNormal.dispose();
	}

	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "No se pudo cargar el asset '" + asset.fileName
				+ "'", (Exception) throwable);
	}

}
