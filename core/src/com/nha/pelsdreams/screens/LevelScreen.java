package com.nha.pelsdreams.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nha.pelsdreams.game.AssetsUI;
import com.nha.pelsdreams.screens.transitions.PagedScrollPane;
import com.nha.pelsdreams.screens.transitions.ScreenTransition;
import com.nha.pelsdreams.screens.transitions.ScreenTransitionSlide;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;
import com.nha.pelsdreams.utils.SaveGameHelper.LevelState;

/**
 * Representa la pantalla de niveles
 * 
 * @author Nacho Herrera
 *
 */
public class LevelScreen extends AbstractGameScreen {

	private static final String TAG = LevelScreen.class.getName();

	private Skin skin;
	private Music music;
	
	public LevelScreen(DirectedGame game, GameState gameState) {
		super(game, gameState);
	}

	private void rebuildStage() {
		skin = AssetsUI.instance.skinWorldLevels;
		// Crea las capas en tablas
		Table backgroundTable = buildBackgroundTable();
		Table topTable = buildTopTable();
		// ScrollPane
		Table scroll = buildPagedScrollPane();
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
		Image imgBackground = new Image(skin.getDrawable("levelbg00"
				+ (gameState.currentWorld.id + 1)));
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
	 * los niveles
	 * 
	 * @return El scrollpane construido
	 */
	private Table buildPagedScrollPane() {
		Table container = new Table();
		float scaleY = Gdx.graphics.getHeight() == Constants.VIEWPORT_GUI_HEIGHT ? 1
				: 2f;
		container.bottom().padBottom(10 * scaleY);
		PagedScrollPane scroll = new PagedScrollPane(null, skin);
		scroll.setScrollingDisabled(false, true);
		scroll.setFlingTime(0.1f);
		scroll.setPageSpacing(0);

		int pageNumber = 2;
		int columnsNumber = 3;
		int rowsNumber = 2;
		// N�mero de level (0 a 11)
		int levelNumber = 0;
		// Ajusta el escalado al redimensionar la pantalla
		float scaleX = Gdx.graphics.getWidth() == Constants.VIEWPORT_GUI_WIDTH ? 1
				: 1.2f;
		// N�mero de p�ginas que contienen los niveles
		for (int i = 0; i < pageNumber; i++) {
			Table levels = new Table();
			if (Constants.DEBUG_DRAW_UI)
				levels.debug();
			levels.defaults().pad(15, 15, 15, 15);
			// N�mero de filas
			for (int j = 0; j < rowsNumber; j++) {
				levels.row();
				// N�mero de columnas
				for (int x = 0; x < columnsNumber; x++) {
					// Aplica espaciado en el lado izquierdo al primer bot�n de
					// cada fila
					// para centrar la primera p�gina
					if ((j == 0 && x == 0) || (j == 1 && x == 0))
						levels.add(
								getLevelButton(gameState.currentWorld.levels
										.get(levelNumber))).padLeft(
								110 * scaleX);
					// Aplica espaciado en el lado izquierdo al �ltimo bot�n de
					// cada fila
					// para centrar la �ltima p�gina
					else if ((j == 0 && x == 2) || (j == 1 && x == 2))
						levels.add(
								getLevelButton(gameState.currentWorld.levels
										.get(levelNumber))).padRight(
								100 * scaleX);
					else
						levels.add(getLevelButton(gameState.currentWorld.levels
								.get(levelNumber)));

					levelNumber++;
				}
			}
			scroll.addPage(levels);
		}
		container.add(scroll);
		return container;
	}

	/**
	 * Crea los botones que representan los niveles
	 * 
	 * @param level
	 *            El n�mero de nivel
	 * @return El bot�n del nivel
	 */
	public Button getLevelButton(LevelState level) {
		Button button = new Button(skin, "levelbutton");
		// Establece el bot�n habilitado o deshabilitado seg�n el nivel est�
		// desbloqueado o bloqueado
		button.setDisabled(!level.state);
		// Crea una etiqueta con el n�mero de nivel
		Label label = new Label(Integer.toString(level.id + 1), skin);
		label.setAlignment(Align.center);

		// Coloca el n�mero de nivel encima del bot�n
		button.stack(label).padBottom(15);

		// Crea los mainItems
		Table mainItemTable = new Table();
		if (Constants.DEBUG_DRAW_UI)
			mainItemTable.debug();
		mainItemTable.defaults().padLeft(5).padRight(5).padBottom(18);
		for (int mainItem = 0; mainItem < 3; mainItem++) {
			if (level.mainItems > mainItem) {
				mainItemTable
						.add(new Image(skin.getDrawable("main_item_full")));
			} else {
				mainItemTable
						.add(new Image(skin.getDrawable("main_item_empty")));
			}
		}

		button.row();
		button.add(mainItemTable);

		button.setName(Integer.toString(level.id));
		button.addListener(levelClickListener);
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
			game.setScreen(new WorldScreen(game, gameState), transition);
		}
	};

	/**
	 * Carga el nivel seleccionado
	 */
	public ClickListener levelClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// Si el bot�n no est� deshabilitado, empieza el nivel
			if (!((Button) event.getListenerActor()).isDisabled()) {

				Gdx.app.debug(TAG, "Click: "
						+ event.getListenerActor().getName());

				// Establece el level pulsado como actual para tener una
				// referencia de que nivel se est� jugando.
				gameState.currentWorld.currentLevel = gameState.currentWorld.levels
						.get(Integer.parseInt(event.getListenerActor()
								.getName()));

				game.setScreen(new LoadingScreen(
						new GameScreen(game, gameState)));
			}
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
		music = Gdx.audio.newMusic(Gdx.files.internal(Constants.MUSIC_DESERT));
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
