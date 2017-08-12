package com.nha.pelsdreams.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nha.pelsdreams.screens.transitions.ScreenTransition;

/**
 * Controla el paso de una pantalla a otra
 * pudiendo a�adir un efecto de transici�n
 * 
 * @author Nacho Herrera
 *
 */
public abstract class DirectedGame implements ApplicationListener {
	private static final String TAG = DirectedGame.class.getName();

	private boolean init;
	// Pantalla actual
	protected AbstractGameScreen currScreen;
	// Pantalla siguiente
	private AbstractGameScreen nextScreen;
	private FrameBuffer currFbo;
	private FrameBuffer nextFbo;
	private SpriteBatch batch;
	private Viewport viewport;
	// Tiempo de transici�n restante
	private float trackElapsedTime;
	// Instancia del efecto
	private ScreenTransition screenTransition;

	public void setScreen(AbstractGameScreen screen) {
		setScreen(screen, null);
	}

	public void setScreen(AbstractGameScreen screen,
			ScreenTransition screenTransition) {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		if (!init) {
			currFbo = new FrameBuffer(Format.RGB888, w, h, false);
			nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
			batch = new SpriteBatch();
			viewport = screen.stage.getViewport();
			init = true;
		}
		// Comienza una nueva transici�n
		nextScreen = screen;
		// Activa la siguiente pantalla
		nextScreen.show();
		nextScreen.resize(w, h);
		// Permite que se actualice la pantalla del juego, para ajustar la
		// c�mara
		if (nextScreen instanceof GameScreen)
			((GameScreen) nextScreen).getWorldController().update(0);
		if (currScreen != null)
			currScreen.pause();
		nextScreen.pause();
		// Deshabilita el input processor para evitar alguna interacci�n
		// del usuario durante la transici�n
		Gdx.input.setInputProcessor(null);
		this.screenTransition = screenTransition;
		// Resetea el tiempo restante
		trackElapsedTime = 0;
	}

	public AbstractGameScreen getCurrScreen() {
		return currScreen;
	}

	public AbstractGameScreen getNextScreen() {
		return nextScreen;
	}

	@Override
	public void create() {
	}

	@Override
	public void resize(int width, int height) {
		if (currScreen != null)
			currScreen.resize(width, height);
		if (nextScreen != null)
			nextScreen.resize(width, height);
	}

	@Override
	public void render() {
		// Obtiene el deltatime y fija el l�mite m�ximo en 1/60 seg.
		float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 1.0f / 60.0f);
		if (nextScreen == null) {
			// No hay transici�n en curso
			if (currScreen != null)
				currScreen.render(deltaTime);
		} else {
			// Transici�n en curso
			float duration = 0;
			if (screenTransition != null)
				duration = screenTransition.getDuration();
			// Actualiza el progreso de transici�n en curso
			trackElapsedTime = Math.min(trackElapsedTime + deltaTime, duration);
			if (screenTransition == null || trackElapsedTime >= duration) {
				// No aplica ning�n efecto de transici�n o la transici�n acaba
				// de terminar
				if (currScreen != null)
					currScreen.hide();
				// Habilita input proccesor a la siguiente screen
				Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
				// Intercambia las screens
				currScreen = nextScreen;
				nextScreen = null;
				screenTransition = null;
			} else {
				// Renderiza las screens a los FBOs
				currFbo.begin();
				if (currScreen != null)
					currScreen.render(deltaTime);
				currFbo.end();
				nextFbo.begin();
				nextScreen.render(deltaTime);
				nextFbo.end();
				// Aplica el efecto de transici�n a la screen
				float alpha = trackElapsedTime / duration;
				screenTransition.render(batch, viewport,
						currFbo.getColorBufferTexture(),
						nextFbo.getColorBufferTexture(), alpha);
			}
		}
	}

	@Override
	public void pause() {
		Gdx.app.debug(TAG, "pause");
		if (currScreen != null) 
			currScreen.pause();	
	}

	@Override
	public void resume() {
		Gdx.app.debug(TAG, "resume Android");
		// Carga los assets Loading
//		if (currScreen != null) {
//			if (AssetsUI.instance.getAssetManager() != null) {
//				if (AssetsUI.instance.getAssetManager().update()) {
//					AssetsLoading.instance.load(new AssetManager());
//					setScreen(new LoadingScreen(currScreen));
//				}
//			}
//			if (AssetsWorlds.instance.getAssetManager() != null) {
//				if (AssetsWorlds.instance.getAssetManager().update()) {
//					AssetsLoading.instance.load(new AssetManager());
//					setScreen(new LoadingScreen(currScreen));
//				}
//			}
			
			currScreen.pause();
//		}
	}

	@Override
	public void dispose() {
		Gdx.app.debug(TAG, "dispose");
		if (currScreen != null)
			currScreen.hide();
		if (nextScreen != null)
			nextScreen.hide();
		if (init) {
			currFbo.dispose();
			currScreen = null;
			nextFbo.dispose();
			nextScreen = null;
			batch.dispose();
			init = false;
		}
	}

}