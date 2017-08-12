package com.nha.pelsdreams.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.game.LevelCreator;
import com.nha.pelsdreams.game.WorldController;
import com.nha.pelsdreams.game.WorldRenderer;
import com.nha.pelsdreams.screens.actors.CompletedScreenActor;
import com.nha.pelsdreams.screens.actors.GameGuiActor;
import com.nha.pelsdreams.screens.actors.GameOverScreenActor;
import com.nha.pelsdreams.screens.actors.HelpScreenActor;
import com.nha.pelsdreams.screens.actors.InitScreenActor;
import com.nha.pelsdreams.screens.actors.NotCompletedScreenActor;
import com.nha.pelsdreams.screens.actors.PauseScreenActor;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla de juego
 * 
 * @author Ignacio Herrera Ariza
 * 
 */
public class GameScreen extends AbstractGameScreen {

	private static final String TAG = GameScreen.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private InputMultiplexer multiplexer;
	private PauseScreenActor pauseScreenActor;
	private CompletedScreenActor completedScreenActor;
	private NotCompletedScreenActor notCompletedScreenActor;
	private InitScreenActor initScreenActor;
	public HelpScreenActor helpScreenActor;
	private GameOverScreenActor gameOverScreenActor;
	public GameGuiActor gameGuiActor;
	// Controla si es el comienzo de un nivel
	public boolean isInitLevel;
	// Tiempo de espera antes de que se muestre la pantalla de game over
	private float timeLeftGameOverDelay;
	// Tiempo de espera antes de que se muestre la pantalla de nivel completado
	private float timeLeftGameFinishedDelay;

	// Controla si hay una pantalla mostr�ndose (pausa, gameover, inicio de
	// nivel, fin de nivel...)
	private boolean isShowingScreenActor;
	private Music music;

	public GameScreen(DirectedGame game, GameState gameState) {
		super(game, gameState);
	}

	public WorldController getWorldController() {
		return worldController;
	}

	/**
	 * Inicia el nivel
	 */
	@Override
	public void initGame() {
		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
		timeLeftGameFinishedDelay = Constants.TIME_DELAY_GAME_FINISHED;
		// Crea la l�gica del juego
		worldController = new WorldController(game, gameState);
		// Carga el nivel correspondiente
		LevelCreator level = new LevelCreator(Constants.WORLDS_PATH
				+ (gameState.currentWorld.id + 1) + "/"
				+ Constants.LEVELFILE_NAME,
				gameState.currentWorld.currentLevel.id);
		worldController.initLevel(level);
		// Crea el renderizado
		worldRenderer = new WorldRenderer(worldController);
		// Crea el stage del GUI
		gameGuiActor = new GameGuiActor(this);
		stage.addActor(gameGuiActor);
		// A�ade al stage la pantalla de pausa
		pauseScreenActor = new PauseScreenActor(game, gameState);
		stage.addActor(pauseScreenActor);
		// A�ade la pantalla de informaci�n inicio de nivel
		initScreenActor = new InitScreenActor(game, gameState);
		stage.addActor(initScreenActor);
		// Crea el input processor para el stage que contiene el bot�n de
		// pausa en la pantalla de juego
		// y para el control del juego
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(worldController);

		worldController.update(0);
		// Muestra la pantalla de inicio de nivel
		initScreenActor.show(true, true);
		// Crea la pantalla de ayuda de juego en el nivel 1
		if (gameState.currentWorld.currentLevel.id == 0) {
			helpScreenActor = new HelpScreenActor(game, gameState);
			stage.addActor(helpScreenActor);
		}
		// Reproduce la música
		music = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSIC_DESERT));
		AudioManager.instance.play(music, true);
	}

	@Override
	public void render(float deltaTime) {
		// El mundo no se actualiza si est� pausado o es game over.
		if (!paused) {
			// Actualiza el mundo en funci�n del tiempo que ha pasado
			// desde la �ltima vez que se renderiz�.
			worldController.update(deltaTime);
		}
		// Muestra la pantalla de game over
		if (worldController.isGameOver) {
			// Deshabilita el bot�n de pausa y el input processor del
			// worldcontroller
			setInputProcessor(false);
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) {
				gameOver();
				worldController.isGameOver = false;
			}
		}

		// Muestra la pantalla de nivel completado
		if (worldController.isLevelCompleted) {
			setInputProcessor(false);
			timeLeftGameFinishedDelay -= deltaTime;
			if (timeLeftGameFinishedDelay < 0) {
				// Muestra la pantalla de nivel completado
				levelCompleted();
				worldController.isLevelCompleted = false;
			}
		}

		// Muestra la pantalla de nivel no completado
		if (worldController.isLevelNotCompleted) {
			setInputProcessor(false);
			timeLeftGameFinishedDelay -= deltaTime;
			if (timeLeftGameFinishedDelay < 0) {
				levelNotCompleted();
				worldController.isLevelNotCompleted = false;
			}
		}

		// Renderiza el mundo
		worldRenderer.render();
		// Renderiza el GUI
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		worldRenderer.resize(width, height);
		Gdx.app.debug(TAG, "Pantalla redimensionada");
	}

	@Override
	public void show() {
		super.show();
		initGame();
	}

	@Override
	public void hide() {
		music.dispose();
		worldController.dispose();
		worldRenderer.dispose();
		stage.dispose();
	}

	@Override
	public void pause() {
		paused = true;
		// Muestra la pantalla de pausa
		if (!isShowingScreenActor && !worldController.isGameOver) {
			pauseScreenActor.show(true, true);
			isShowingScreenActor = true;
		}
	}

	@Override
	public void resume() {
		// Oculta la pantalla de inicio
		if (initScreenActor.isVisible) {
			initScreenActor.show(false, true);
		}

		// Oculta la pantalla de ayuda
		if (helpScreenActor != null && helpScreenActor.isVisible) {
			helpScreenActor.show(false, true);
			stage.getRoot().removeActor(helpScreenActor);
			helpScreenActor = null;
		}

		// Oculta la pantalla de pausa
		if (pauseScreenActor.isVisible) {
			pauseScreenActor.show(false, true);
		}

		paused = false;
		isShowingScreenActor = false;
	}

	/**
	 * Muestra la pantalla de ayuda
	 */
	public void showHelpScreen() {
        pauseScreenActor.show(false, false);
        initScreenActor.show(false, false);
		helpScreenActor.show(true, true);
		isShowingScreenActor = true;
	}

	/**
	 * Muestra la pantalla de nivel completado
	 */
	public void levelCompleted() {
		// Detiene la música
		AudioManager.instance.stopMusic();
		// Pausa el juego
		paused = true;
		// Almacena en disco el estado del juego
		saveGameState(true);
		// A�ade al stage la pantalla de nivel completado
		if (completedScreenActor == null) {
			completedScreenActor = new CompletedScreenActor(game, gameState);
			stage.addActor(completedScreenActor);
		}
		completedScreenActor.update();
		// Muestra la pantalla de nivel completado
		completedScreenActor.show(true, true);
		// Reproduce la música de la intro
		music = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSIC_COMPLETED));
		AudioManager.instance.play(music, false);
		isShowingScreenActor = true;
	}

	/**
	 * Muestra la pantalla de nivel no completado
	 */
	public void levelNotCompleted() {
		// Detiene la música
		AudioManager.instance.stopMusic();
		// Pausa el juego
		paused = true;
		// Almacena en disco el estado del juego
		saveGameState(false);
		// A�ade al stage la pantalla de nivel no completado
		if (notCompletedScreenActor == null) {
			notCompletedScreenActor = new NotCompletedScreenActor(game,
					gameState);
			stage.addActor(notCompletedScreenActor);
		}
		notCompletedScreenActor.update();
		// Muestra la pantalla de nivel completado
		notCompletedScreenActor.show(true, true);
		AudioManager.instance.play(AssetsWorlds.instance.sounds.dead);
		isShowingScreenActor = true;
	}

	/**
	 * Muestra la pantalla de game over
	 */
	public void gameOver() {
		// Detiene la música
		AudioManager.instance.stopMusic();
		Gdx.app.debug(TAG, "Game Over");
		paused = true;
		// A�ade al stage la pantalla de game over
		if (gameOverScreenActor == null) {
			gameOverScreenActor = new GameOverScreenActor(game, gameState);
			stage.addActor(gameOverScreenActor);
		}
		gameOverScreenActor.update();
		gameOverScreenActor.show(true, true);
		AudioManager.instance.play(AssetsWorlds.instance.sounds.dead);
		isShowingScreenActor = true;
	}

	/**
	 * Almacena el estado del juego
	 * 
	 * @param isCompleted
	 *            True si el nivel ha sido completado, false en caso contrario
	 */
	public void saveGameState(boolean isCompleted) {
		// Actualiza los items recogidos en el nivel realizado
		// Si se han recolectado menos que la vez anterior, no se actualiza
		if (gameState.currentWorld.currentLevel.mainItems < worldController.mainItems)
			gameState.currentWorld.currentLevel.mainItems = worldController.mainItems;
		if (isCompleted) {
			// Si es el �ltimo nivel del mundo actual, habilita el siguiente
			// mundo en caso de existir.
			if (gameState.currentWorld.currentLevel.id == gameState.currentWorld.levels.size - 1) {
				if (gameState.currentWorld.id < gameState.worlds.size - 1) {
					gameState.worlds.get(gameState.currentWorld.id + 1).state = true;
				}
			}
			// Habilita el siguiente nivel
			else {
				gameState.currentWorld.levels
						.get(gameState.currentWorld.currentLevel.id + 1).state = true;
			}
		}
		// Se guarda el estado en fichero
		SaveGameHelper.saveGameState(gameState);
	}

	/**
	 * Habilita o deshabilita el bot�n de pausa y el inputProcessor del world
	 * controller
	 * 
	 * @param enabled
	 *            True habilitado, false en caso contrario
	 */
	public void setInputProcessor(boolean enabled) {
		// Habilita o deshabilita el bot�n de pausa del GUI y el inputproccesor
		// del worldcontroller seg�n se muestre u
		// oculte la pantalla de opciones
		if (enabled != gameGuiActor.findActor("PauseButton").isTouchable()) {
			gameGuiActor.findActor("PauseButton").setTouchable(
					enabled ? Touchable.enabled : Touchable.disabled);
			if (enabled) {
				multiplexer.addProcessor(worldController);
			} else {
				multiplexer.removeProcessor(worldController);
			}
		}
	}

	@Override
	public InputProcessor getInputProcessor() {
		return multiplexer;
	}

}
