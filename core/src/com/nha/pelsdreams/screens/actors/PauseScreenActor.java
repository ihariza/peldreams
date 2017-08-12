package com.nha.pelsdreams.screens.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nha.pelsdreams.enums.ShowButtonsType;
import com.nha.pelsdreams.screens.DirectedGame;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Bundle;
import com.nha.pelsdreams.utils.GamePreferences;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla de pausa del juego
 * 
 * @author Nacho Herrera
 * 
 */
public class PauseScreenActor extends ScreenActor {

	// private static final String TAG = PauseScreenActor.class.getName();
	private Button btnMenuMusic;
	private Button btnMenuSound;

	public PauseScreenActor(DirectedGame game, GameState gameState) {
		super(game, gameState);
		// Establece el nombre al actor para localizarlo en el stage
		// a la hora de trabajar con �l (moverlo, eliminarlo, etc)
		this.setName("PauseScreenActor");
		// Carga las preferencias
		GamePreferences.instance.load();
		rebuildStage();
	}

	/**
	 * Construye la pantalla de pausa del juego
	 */
	private void rebuildStage() {
		Label title = new Label(Bundle.instance.i18n.get("pause"), skin,
				"big-font");
		// A�ade la tabla con el contenido a la tabla principal
		add(buildContentTable(title, buildSoundTable(), ShowButtonsType.ALL));
	}

	/**
	 * Construye la tabla que contiene los botones de sonido y m�sica.
	 * 
	 * @return La tabla construida
	 */
	private Table buildSoundTable() {
		// A�ade los botones de m�sica y sonido
		Table soundTable = new Table();
		btnMenuMusic = new Button(skin, "music");
		btnMenuMusic.setChecked(GamePreferences.instance.music);
		// Define el listener del bot�n
		btnMenuMusic.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSoundClicked();
			}
		});
		btnMenuSound = new Button(skin, "sound");
		btnMenuSound.setChecked(GamePreferences.instance.sound);
		// Define el listener del bot�n
		btnMenuSound.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSoundClicked();
			}
		});
		soundTable.add(btnMenuMusic).padRight(40).padBottom(10);
		soundTable.add(btnMenuSound);
		return soundTable;
	}

	@Override
	protected ClickListener continueClickListener() {
		return new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScreen.resume();
			}
		};
	}

	/**
	 * Guarda las preferencias en el archivo de preferencias
	 */
	private void saveSettings() {
		GamePreferences prefs = GamePreferences.instance;
		prefs.sound = btnMenuSound.isChecked();
		prefs.music = btnMenuMusic.isChecked();
		prefs.save();
	}

	/**
	 * Evento del bot�n Sound
	 */
	private void onSoundClicked() {
		saveSettings();
		// Actualiza la música y sonidos
		AudioManager.instance.onSettingsUpdated();
	}

}
