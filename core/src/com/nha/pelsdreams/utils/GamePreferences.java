package com.nha.pelsdreams.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Almacena las preferencias del juego
 * 
 * @author Nacho Herrera
 *
 */
public class GamePreferences {

	public static final String TAG = GamePreferences.class.getName();

	public static final GamePreferences instance = new GamePreferences();

	public boolean sound;
	public boolean music;
	private Preferences prefs;

	// singleton: prevent instantiation from other classes
	private GamePreferences() {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}

	/**
	 * Carga las preferencias
	 */
	public void load() {
		sound = prefs.getBoolean("sound", true);
		music = prefs.getBoolean("music", true);

	}

	/**
	 * Guarda las preferencias
	 */
	public void save() {
		prefs.putBoolean("sound", sound);
		prefs.putBoolean("music", music);
		prefs.flush();
	}
}
