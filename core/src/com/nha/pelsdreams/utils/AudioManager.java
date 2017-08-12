package com.nha.pelsdreams.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Manager de música y sonidos
 * 
 * @author Nacho Herrera
 *
 */
public class AudioManager {

	// Patrón Singleton
	public static final AudioManager instance = new AudioManager();

	private Music playingMusic;

	private AudioManager() {
	}

	/**
	 * Reproduce un sonido
	 * @param sound
	 */
	public long play(Sound sound) {
		if (GamePreferences.instance.sound)
			return 0;
		return sound.play(0.5f, 1, 0);
	}
	
	/**
	 * Detiene un sonido
	 * @param sound
	 */
	public void stop(Sound sound) {
		sound.stop();
	}

	/**
	 * Reproduce un archivo de música
	 * @param music
	 */
	public void play(Music music, boolean looping) {
		stopMusic();
		playingMusic = music;
		if (!GamePreferences.instance.music) {
			music.setVolume(0.8f);
			music.setLooping(looping);
			music.play();
		}
	}

	/**
	 * Detiene la música en reproducción
	 */
	public void stopMusic() {
		if (playingMusic != null) {
			playingMusic.stop();
		}		
	}

	/**
	 * Actualiza la reproducción de la música
	 * según se marquen las opciones en menú
	 */
	public void onSettingsUpdated() {
		if (playingMusic == null) {
			return;
		}
		
		playingMusic.setVolume(0.8f);
		if (!GamePreferences.instance.music) {
			if (!playingMusic.isPlaying())
				playingMusic.play();
		} else {
			playingMusic.pause();
		}
	}
	
	
}
