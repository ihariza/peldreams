package com.nha.pelsdreams.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nha.pelsdreams.game.AssetsUI;
import com.nha.pelsdreams.screens.transitions.ScreenTransition;
import com.nha.pelsdreams.screens.transitions.ScreenTransitionSlide;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.GamePreferences;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla de men� principal
 * 
 * @author Nacho Herrera
 *
 */
public class MenuScreen extends AbstractGameScreen {

	// private static final String TAG = MenuScreen.class.getName();

	private Skin skin;

	// men�
	private Button btnMenuPlay;
	private Button btnMenuSound;
	private Button btnMenuMusic;
	
	private Music music;

	public MenuScreen(DirectedGame game, GameState gameState) {
		super(game, gameState);
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		rebuildStage(width, height);
	}

	@Override
	public void show() {
		super.show();
		// Construye la pantalla de men� inicial
		rebuildStage(stage.getViewport().getWorldWidth(), stage
				.getViewport().getWorldHeight());
		// Carga las preferencias desde el archivo de preferencias
		GamePreferences.instance.load();
		// Reproduce la música de la intro
		music = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSIC_INTRO));
		AudioManager.instance.play(music, true);
	}

	@Override
	public void hide() {
		super.hide();
		music.dispose();
	}

	@Override
	public void pause() {
	}

	/**
	 * Construye la pantalla principal del juego
	 */
	private void rebuildStage(float width, float height) {
		skin = AssetsUI.instance.skinMainMenu;
		// Crea las capas en tablas
		Table layerBackground = buildBackgroundLayer();
		layerBackground.setSize(width, height);
		Table layerControls = buildControlsLayer();
		// Monta el stage para la pantalla del men�
		// Stack (una tabla encima de la otra)
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setFillParent(true);
		stack.add(layerBackground);
		stack.add(layerControls);
	}

	/**
	 * Construye el fondo de pantalla
	 * 
	 * @return Table Tabla con el fondo.
	 */
	private Table buildBackgroundLayer() {
		Table table = new Table();
		// A�ade el Background
		Image imgBackground = new Image(skin, "background");
		table.add(imgBackground).expand().fill();
		return table;
	}

	/**
	 * Construye la tabla que contiene los botones
	 * 
	 * @return Table Tabla con los controles.
	 */
	private Table buildControlsLayer() {
		Table table = new Table();
		table.top().right().padRight(25).padTop(15);

		// A�ade el bot�n Play
		btnMenuPlay = new Button(skin, "play");
		table.add(btnMenuPlay);
		// Define el listener del bot�n
		btnMenuPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onPlayClicked();
			}
		});

		// A�ade el bot�n M�sica
		btnMenuMusic = new Button(skin, "music");
		btnMenuMusic.setChecked(GamePreferences.instance.music);
		table.add(btnMenuMusic).padTop(75);
		// Define el listener del bot�n
		btnMenuMusic.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSoundClicked();
			}
		});
		table.row();
		// A�ade el bot�n Sonido
		btnMenuSound = new Button(skin, "sound");
		btnMenuSound.setChecked(GamePreferences.instance.sound);
		table.add(btnMenuSound).top().padTop(10).padLeft(110);
		// Define el listener del bot�n
		btnMenuSound.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSoundClicked();
			}
		});
		if (Constants.DEBUG_DRAW_UI)
			table.debug();
		return table;
	}

	/**
	 * Evento del bot�n Play
	 */
	private void onPlayClicked() {
		AudioManager.instance.play(AssetsUI.instance.sounds.button);
		ScreenTransition transition = ScreenTransitionSlide.init(0.25f,
				ScreenTransitionSlide.LEFT, false, Interpolation.pow3Out);
		game.setScreen(new WorldScreen(game, gameState), transition);
	}

	/**
	 * Evento del bot�n Sound
	 */
	private void onSoundClicked() {
		saveSettings();
		// Actualiza la música y sonidos
		AudioManager.instance.onSettingsUpdated();
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

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}

}
