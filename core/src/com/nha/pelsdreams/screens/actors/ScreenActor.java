package com.nha.pelsdreams.screens.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nha.pelsdreams.enums.ShowButtonsType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.screens.DirectedGame;
import com.nha.pelsdreams.screens.GameScreen;
import com.nha.pelsdreams.screens.LoadingScreen;
import com.nha.pelsdreams.screens.MenuScreen;
import com.nha.pelsdreams.utils.Bundle;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla principal de opciones del juego Contiene el fondo de
 * la tabla, el t�tulo, y los botones de regresar al men�, reiniciar el nivel y
 * continuar.
 * 
 * El listener del bot�n continuar necesita sobreescribirse puesto ya que
 * difiere seg�n sea la pantalla de nivel completado (pasa al siguiente nivel) o
 * a la pantalla de pausa (contin�a el juego).
 * 
 * @author Nacho Herrera
 * 
 */
public abstract class ScreenActor extends Table {

	// private static final String TAG = ScreenActor.class.getName();

	protected DirectedGame game;
	protected GameScreen gameScreen;
	protected GameState gameState;
	protected Skin skin;
	private Table buttonsTable;
	public Button continueButton;
	private ConfirmType confirmType;
	private Stack stack;
	// Controla si la pantalla est� visible
	public boolean isVisible;
	// Controla si s�lo se muestra el bot�n de continuar
	private ShowButtonsType showButtonsType;
	// Texto de confirmaci�n (Ej.: Regresar� al men� principal)
	private Label confirmLabel2;

	// Identifica que tipo de confirmaci�n mostrar
	protected enum ConfirmType {
		RESTART, MENU
	}

	public ScreenActor(DirectedGame game, GameState gameState) {
		this.game = game;
		// Establece el gameScreen
		// Comprueba primero si la siguiente pantalla es el nuevo GameScreen
		// (Para el caso en que se reinicie el nivel, pues en DirectedScreen,
		// tanto current como next ser�n del tipo GameScreen)
		if (game.getNextScreen() instanceof GameScreen) {
			this.gameScreen = (GameScreen) game.getNextScreen();
		} else if (game.getCurrScreen() instanceof GameScreen) {
			this.gameScreen = (GameScreen) game.getCurrScreen();
		}

		this.gameState = gameState;
		skin = AssetsWorlds.instance.inGameUI.skin;
		setFillParent(true);
		setPosition(getOriginX(), Gdx.graphics.getHeight());
		if (Constants.DEBUG_DRAW_UI)
			this.debug();
	}

	/**
	 * Construye la tabla de contenido
	 * 
	 * @param topTitle
	 *            T�tulo de la pantalla
	 * @param extraTable
	 *            Tabla situada entre la tabla de t�tulo y la de botones. Puede
	 *            ser nula.
	 * @param showButtonsType
	 *            Botones a mostrar
	 * @return La tabla con el contenido.
	 */
	protected Stack buildContentTable(Label topTitle, Table extraTable,
			ShowButtonsType showButtonsType) {

		this.showButtonsType = showButtonsType;

		clear();
		// Tabla que contiene el men� completo
		Stack contentTable = new Stack();
		// contentTable.center();
		contentTable.setName("contentTable");
		// Tabla superior (Contiene el t�tulo)
		contentTable.add(buildTopTable(topTitle));

		// A�ade la tabla extra
		if (extraTable != null) {
			extraTable.center();
			contentTable.add(extraTable);
		}

		// A�ade la tabla de botones
		Table buttonsTable = buildButtonsTable();
		buttonsTable.center().bottom().padBottom(30);
		contentTable.add(buttonsTable);

		// A�ade la tabla de confimaci�n
		Table confirmTable = buildConfirmTable();
		confirmTable.setVisible(false);

		// Stack que contiene la tabla con el fondo y el men� completo
		stack = new Stack();
		stack.add(buildBackgroundTable());
		stack.add(confirmTable);
		stack.add(contentTable);

		return stack;
	}

	/**
	 * Construye la tabla superior de la pantalla que contiene el t�tulo
	 * 
	 * @return La tabla construida
	 */
	private Table buildTopTable(Label title) {
		Table topTable = new Table().center().top();
		if (Constants.DEBUG_DRAW_UI)
			topTable.debug();
		topTable.add(title);
		return topTable;
	}

	/**
	 * Construye el fondo de pantalla
	 * 
	 * @return Table Tabla con el fondo.
	 */
	protected Table buildBackgroundTable() {
		Table table = new Table();
		// A�ade el Background
		Image imgBackground = new Image(skin.getDrawable("background"));
		table.add(imgBackground);
		return table;
	}

	/**
	 * Construye la tabla que contiene los botones para regresar al men�,
	 * continuar al siguiente nivel y reiniciar.
	 * 
	 * @return La tabla construida
	 */
	protected Table buildButtonsTable() {
		Table topTable = new Table();
		if (Constants.DEBUG_DRAW_UI)
			topTable.debug();

		// A�ade los botones de menu, reinicio y continuar
		buttonsTable = new Table();
		continueButton = new Button(skin, "continue");
		continueButton.addListener(continueClickListener());
		Button restartbtn = new Button(skin, "restart");
		restartbtn.addListener(restartClickListener);
		Button menubtn = new Button(skin, "menu");
		menubtn.addListener(menuClickListener);

		switch (showButtonsType) {
		case ALL:
			buttonsTable.add(menubtn);
			buttonsTable.add(restartbtn).padLeft(40).padRight(40).padTop(30);
			buttonsTable.add(continueButton);
			break;
		case MENU_AND_RESTART:
			buttonsTable.add(menubtn).padLeft(190).padBottom(20);
			buttonsTable.add(restartbtn).padLeft(150).padRight(190).padBottom(20);
			break;
		case CONTINUE:
			buttonsTable.add().padRight(220).padTop(90);
			buttonsTable.add(continueButton);
			break;
		default:
			break;
		}

		// A�ade todos los botones a la tabla principal
		topTable.add(buttonsTable).padTop(20);
		return topTable;
	}

	/**
	 * Contruye la tabla de confirmaci�n
	 * 
	 * @return La tabla de confirmaci�n
	 */
	protected Table buildConfirmTable() {
		Table confirmTable = new Table();
		confirmTable.setName("confirmTable");
		if (Constants.DEBUG_DRAW_UI)
			confirmTable.debug();
		// A�ade las opciones de confirmaci�n
		Label confirmLabel1 = new Label(Bundle.instance.i18n.get("areyousure"),
				skin, "yellow-font");

		confirmLabel2 = new Label("", skin);
		confirmLabel2.setAlignment(Align.center);
		confirmLabel2.setFontScale(0.8f);

		Table buttonsTable = new Table();
		Button yesbtn = new Button(skin, "yes");
		yesbtn.addListener(yesClickListener);
		Button nobtn = new Button(skin, "no");
		nobtn.addListener(noClickListener);
		buttonsTable.add(yesbtn).padRight(150).padTop(20);
		buttonsTable.add(nobtn).padTop(20);

		confirmTable.add(confirmLabel1);
		confirmTable.row();
		confirmTable.add(confirmLabel2).padTop(30).padBottom(50);
		confirmTable.row();
		confirmTable.add(buttonsTable);

		return confirmTable;
	}

	/**
	 * Oculta o muestra la opnci�n de confirmaci�n
	 * 
	 * @param visible
	 *            True si se muestra, false en caso contrario
	 * @param type
	 *            Tipo de confirmaci�n
	 */
	protected void showConfirmOption(Boolean visible, ConfirmType type) {
		this.confirmType = type;
		if (visible) {
			// A�ade la tabla de confirmaci�n
			stack.findActor("confirmTable").setVisible(true);
			// A�ade las etiquetas de confirmaci�n
			switch (confirmType) {
			case MENU:
				confirmLabel2.setText(Bundle.instance.i18n
						.get("returnmainmenu"));
				break;
			case RESTART:
				confirmLabel2.setText(Bundle.instance.i18n.get("restartlevel"));
				break;
			}

			stack.findActor("contentTable").setVisible(false);
		} else {
			// A�ade las tabla anterior
			stack.findActor("contentTable").setVisible(true);
			stack.findActor("confirmTable").setVisible(false);
		}
	}

	/**
	 * Muestra la pantalla de nivel completado
	 * 
	 * @param visible
	 *            True para hacer visible, false en caso contrario
	 * @param hasAnimation
	 *            True aplica una animaci�n, false en caso contrario
	 */
	public void show(boolean visible, boolean hasAnimation) {
		float moveX = 0;
		float moveY = Gdx.graphics.getHeight() * (visible ? -1 : 1);

		MoveByAction moveBy = new MoveByAction();
		if (hasAnimation)
			moveBy.setDuration(1.0f);
		else
			moveBy.setDuration(0);
		moveBy.setInterpolation(Interpolation.exp10Out);
		moveBy.setAmount(moveX, moveY);
		this.addAction(moveBy);
		isVisible = visible;
	}

	/**
	 * Actualiza el texto de las tablas
	 */
	public void update() {
	}

	/**
	 * Evento Click del bot�n yes de restart
	 */
	protected ClickListener yesClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			// Vuelve al men� principal
			if (confirmType == ConfirmType.MENU) {
				game.setScreen(new LoadingScreen(
						new MenuScreen(game, gameState)));
			}
			// Reinicia el nivel actual
			if (confirmType == ConfirmType.RESTART) {
				stack.findActor("contentTable").setVisible(true);
				stack.findActor("confirmTable").setVisible(false);
				game.setScreen(new GameScreen(game, gameState));
			}
		}
	};

	/**
	 * Evento Click del bot�n no
	 */
	protected ClickListener noClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			showConfirmOption(false, null);
		}
	};

	/**
	 * Evento Click que reinicia el nivel
	 */
	protected ClickListener restartClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			showConfirmOption(true, ConfirmType.RESTART);
		}
	};

	/**
	 * Evento Click que vuelve al men�u principal
	 */
	protected ClickListener menuClickListener = new ClickListener() {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			showConfirmOption(true, ConfirmType.MENU);
		}
	};

	/**
	 * Evento Click del bot�n Continuar
	 */
	protected abstract ClickListener continueClickListener();
}
