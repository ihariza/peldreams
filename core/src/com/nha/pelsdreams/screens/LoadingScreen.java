package com.nha.pelsdreams.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.nha.pelsdreams.game.AssetsLoading;
import com.nha.pelsdreams.game.AssetsUI;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.screens.actors.LoadingBarActor;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa la pantalla de carga
 * 
 * @author Nacho Herrera
 * 
 */
public class LoadingScreen extends AbstractGameScreen {


    // private static final String TAG = LoadingScreen.class.getName();

    private AbstractGameScreen nextScreen;
    //private LoadingBarActor loadingBarActor;

    /**
     * Constructor
     *
     * @param nextScreen
     *            Pantalla siguiente a mostrar.
     */
    public LoadingScreen(AbstractGameScreen nextScreen) {
        super(nextScreen.game, nextScreen.gameState);
        this.nextScreen = nextScreen;
        //loadingBarActor = new LoadingBarActor();
    }

    /**
     * Construye la pantalla principal del juego
     */
    private void rebuildStage() {
        stage.clear();

        Stack stack = new Stack();
        stack.setFillParent(true);

        // A�ade la imagen
        Table imageTable = new Table();
        if (Constants.DEBUG_DRAW_UI)
            imageTable.debug();
        Image image = new Image(AssetsLoading.instance.loadingRegion);
        imageTable.add(image);
        stack.add(imageTable);

        // A�ade la animaci�n de bolas
        //stack.add(loadingBarActor);

        stage.addActor(stack);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

        // Comprueba si ha cargado los assets correspondientes
        if (nextScreen instanceof MenuScreen
                || nextScreen instanceof WorldScreen
                || nextScreen instanceof LevelScreen) {
            if (AssetsUI.instance.getAssetManager().update()) {
                // Obtiene los assets
                AssetsUI.instance.getLoaded();
                game.setScreen(nextScreen);
            }
        } else {
            if (AssetsWorlds.instance.getAssetManager().update()) {
                // Obtiene los assets
                AssetsWorlds.instance.getLoaded();
                game.setScreen(nextScreen);
            }
        }
    }

    @Override
    public void show() {
        super.show();
        rebuildStage();
        // Descarga los assets
        if (AssetsUI.instance.getAssetManager() != null)
            AssetsUI.instance.dispose();
        if (AssetsWorlds.instance.getAssetManager() != null)
            AssetsWorlds.instance.dispose();
        // Carga los assets
        if (nextScreen instanceof MenuScreen
                || nextScreen instanceof WorldScreen
                || nextScreen instanceof LevelScreen) {
            // Carga los assets de la UI
            AssetsUI.instance.load(new AssetManager());
        } else {
            // Carga los assets del world
            AssetsWorlds.instance.load(new AssetManager(),
                    (gameState.currentWorld.id + 1));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

}
