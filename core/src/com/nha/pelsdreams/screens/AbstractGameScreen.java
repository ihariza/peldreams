package com.nha.pelsdreams.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa una pantalla
 * @author Nacho Herrera
 *
 */
public abstract class AbstractGameScreen implements Screen {

	protected DirectedGame game;
	protected Stage stage;
	// Referencia del estado del juego
	protected GameState gameState;
	protected boolean paused;

	public AbstractGameScreen(DirectedGame game, GameState gameState) {
		this.game = game;
		this.gameState = gameState;
		stage = new Stage(new ExtendViewport(Constants.VIEWPORT_GUI_WIDTH,
				Constants.VIEWPORT_GUI_HEIGHT));
	}

	/**
	 * Obtiene el estado del juego
	 * @return El estado del juego
	 */
	public GameState getGameState() {
		return gameState;
	}
	
	/**
	 * Establece el estado del juego
	 * @param gameState El estado del juego
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	/**
	 * Obtiene el estado del juego
	 * @return True si está pausado, false en caso contrario.
	 */
	public boolean isPaused() {
		return paused;
	}

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);
	
	public void show() {
		// Establece si el botón BACK en Android debe ser capturado.
		// Esto nos permite controlar el botón BACK para hacer una pausa en el
		// juego o volver al menú principal
		Gdx.input.setCatchBackKey(true);
	}

	public void hide() {
		if (stage != null)
			stage.dispose();
	}
	
	/**
	 * Pausa el juego
	 */
	public abstract void pause();
	
	/**
	 * Inicializa el juego
	 */
	public void initGame() {
	}

	/**
	 * Resume el juego
	 */
	public void resume() {
	}
	
	public abstract InputProcessor getInputProcessor();

	public void dispose() {
	}

}
