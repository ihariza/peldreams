package com.nha.pelsdreams.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nha.pelsdreams.game.AssetsUI;
import com.nha.pelsdreams.screens.transitions.PagedScrollPane;
import com.nha.pelsdreams.screens.transitions.ScreenTransition;
import com.nha.pelsdreams.screens.transitions.ScreenTransitionSlide;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla de selecci�n de sue�os
 * 
 * @author Nacho Herrera
 *
 */
public class WorldScreen extends AbstractGameScreen {

	private static final String TAG = WorldScreen.class.getName();

	private Skin skin;
	private Music music;

	public WorldScreen(DirectedGame game, GameState gameState) {
		super(game, gameState);
	}

	/**
	 * Construye la pantalla principal del juego
	 */
	private void rebuildStage() {
		skin = AssetsUI.instance.skinWorldLevels;
		// Crea las capas en tablas
		Table backgroundTable = buildBackgroundTable();
		Table topTable = buildTopTable();
		// ScrollPane
		PagedScrollPane scroll = buildPagedScrollPane();
		// Monta el stage para la pantalla del men�
		// Stack (una tabla encima de la otra)
		stage.clear();
		Stack stack = new Stack();

		stack.setFillParent(true);
		stack.add(backgroundTable);

		stack.add(scroll);
		stack.add(topTable);
		stage.addActor(stack);
	}

	/**
	 * Construye el fondo de pantalla
	 * 
	 * @return Table Tabla con el fondo.
	 */
	private Table buildBackgroundTable() {
		Table table = new Table();
		// A�ade el Background
		Image imgBackground = new Image(skin, "worlds_bg");
		table.add(imgBackground).expand().fill();
		return table;
	}

	/**
	 * Construye la tabla superior de la pantalla que contiene un bot�n para
	 * regresar al men� anterior.
	 * 
	 * @return La tabla construida
	 */
	private Table buildTopTable() {
		Table topTable = new Table();
		topTable.top().right().padRight(30).padTop(25);
		if (Constants.DEBUG_DRAW_UI)
			topTable.debug();
		Button backbtn = new Button(skin, "backbutton");
		backbtn.addListener(backClickListener);
		topTable.add(backbtn);
		return topTable;
	}

	/**
	 * Construye un ScrollPane que contiene todos los botones correspondientes a
	 * los mundos.
	 * 
	 * @return El scrollpane construido
	 */
	private PagedScrollPane buildPagedScrollPane() {
		PagedScrollPane scroll = new PagedScrollPane(null, skin);
		scroll.setScrollingDisabled(false, true);
		scroll.setFlingTime(0.1f);
		scroll.setPageSpacing(0);

		// Un mundo por p�gina
		for (int i = 0; i <= gameState.worlds.size; i++) {
			Table worlds = new Table();
			if (Constants.DEBUG_DRAW_UI)
				worlds.debug();
			
			// Ajusta el escalado al redimensionar la pantalla
			float scale = 0;
			switch (Gdx.graphics.getWidth()) {
			case 960: // resoluci�n 960 x 540
				scale = 1.05f;
				break;
			case 1280: // resoluci�n 1280 x 720
				scale = 1.15f;
				break;
			default:
				scale = 1;
				break;
			}
//			float scale = Gdx.graphics.getWidth() == Constants.VIEWPORT_GUI_WIDTH ? 1
//					: 1.15f;
			// Aplica padding para centrar el mundo s�lo la primera p�gina
			if (i == 0)
				worlds.defaults().pad(0, 260 * scale, 20, 40);
			else
				worlds.defaults().pad(0, 0, 20, 240 * scale);

			worlds.add(getWorldButton(i));
			scroll.addPage(worlds);
		}
		return scroll;
	}

	/**
	 * Crea los botones que representan los mundos
	 * 
	 * @param worldId
	 *            N�mero identificativo del mundo
	 * @return El bot�n correspondiente al mundo
	 */
	public Button getWorldButton(int worldId) {
		Button button = null;
		// Mundo 1
		if (worldId == 0) {
			button = new Button(skin, "world001button");
			// Asigna el n�mero de mundo al nombre de bot�n para identificar
			// que mundo se ha pulsado
			button.setName(Integer.toString(gameState.worlds.get(worldId).id));
			button.addListener(worldClickListener);
		}

		// Mundo coming soon
		if (worldId == gameState.worlds.size) {
			button = new Button(skin, "world_comingsoon");
			button.setDisabled(true);
		}

		return button;
	}

	/**
	 * Vuelve al men� anterior
	 */
	public ClickListener backClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			ScreenTransition transition = ScreenTransitionSlide.init(0.25f,
					ScreenTransitionSlide.RIGHT, false, Interpolation.pow3Out);
			game.setScreen(new MenuScreen(game, gameState), transition);
		}
	};

	/**
	 * Carga el mundo seleccionado
	 */
	public ClickListener worldClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			Gdx.app.debug(TAG, "Click: " + event.getListenerActor().getName());
			ScreenTransition transition = ScreenTransitionSlide.init(0.25f,
					ScreenTransitionSlide.LEFT, false, Interpolation.pow3Out);
			// Establece el mundo seleccionado
			gameState.currentWorld = gameState.worlds.get(Integer
					.parseInt(event.getListenerActor().getName()));
			game.setScreen(new LevelScreen(game, gameState), transition);
		}
	};

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(deltaTime);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		rebuildStage();
	}

	@Override
	public void show() {
		super.show();
		rebuildStage();
		// Reproduce la música
		music = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSIC_DREAMS));
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

	@Override
	public InputProcessor getInputProcessor() {
		return stage;
	}

}
