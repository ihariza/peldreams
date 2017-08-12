package com.nha.pelsdreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.nha.pelsdreams.game.AssetsLoading;
import com.nha.pelsdreams.screens.DirectedGame;
import com.nha.pelsdreams.screens.LoadingScreen;
import com.nha.pelsdreams.screens.MenuScreen;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper;

public class PelsDreamsGame extends DirectedGame {

	// private static final String TAG = PelDreamsGame.class.getName();

	@Override
	public void create() {
		// Establece el tipo de log a DEBUG
		Gdx.app.setLogLevel(Constants.LOG_LEVEL);
		// Carga los assets Loading
		AssetsLoading.instance.load(new AssetManager());
		// Inicializa el juego en la pantalla de men� principal
		setScreen(new LoadingScreen(new MenuScreen(this,
				SaveGameHelper.loadGameState())));
	}
}
