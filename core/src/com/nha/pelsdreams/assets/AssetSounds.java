package com.nha.pelsdreams.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa los sonidos
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetSounds {

	public final Sound jump;
	public final Sound dodging;
	public final Sound liveLostWall;
	public final Sound arrow;
	public final Sound snake_dead;
	public final Sound totem;
	public final Sound dead;
	public final Sound item;
	public final Sound button;
	

	public AssetSounds(AssetManager manager, String assetType) {
		if (assetType.equals("UI")) {
			jump = null;
			dodging = null;
			liveLostWall = null;
			arrow = null;
			snake_dead = null;
			totem = null;
			dead = null;
			item = null;
			button = manager.get(Constants.SOUND_BUTTON, Sound.class);
		} else {
			jump = manager.get(Constants.SOUND_JUMP, Sound.class);
			dodging = manager.get(Constants.SOUND_DODGING, Sound.class);
			liveLostWall = manager.get(Constants.SOUND_WALL, Sound.class);
			arrow = manager.get(Constants.SOUND_ARROWS, Sound.class);
			snake_dead = manager.get(Constants.SOUND_SNAKE_DEAD, Sound.class);
			totem = manager.get(Constants.SOUND_TOTEM, Sound.class);
			dead = manager.get(Constants.SOUND_DEAD, Sound.class);
			item = manager.get(Constants.SOUND_ITEM, Sound.class);
			button = null;
		}	
	}
}
