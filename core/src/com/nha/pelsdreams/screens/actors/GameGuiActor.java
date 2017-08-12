package com.nha.pelsdreams.screens.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.game.WorldController;
import com.nha.pelsdreams.screens.GameScreen;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa la pantalla del GUI durante una partida
 * 
 * @author Nacho Herrera
 *
 */
public class GameGuiActor extends Stack {

	private static final String TAG = GameGuiActor.class.getName();

	private GameScreen gameScreen;
	private WorldController worldController;
	private Array<Image> mainItems;
	private int mainItemPadRight;
	private int itemsVisual;
	private Label fpsLabel;
	private Label itemCounterLabel;
	private Label screenSizeLabel;

	public GameGuiActor(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.worldController = gameScreen.getWorldController();
		buildGUI();
	}

	private void buildGUI() {

		// Añade los Mainitems
		this.add(buildGuiMainItem());

		// Añade los items
		this.add(buildGuiItem());

		// Añade el botón de pausa
		this.add(buildPauseButton());

		// Añade los botones de control
		this.add(buildUpControlButtons());
		this.add(buildDownControlButtons());

		// Añade el fps counter
		this.add(buildFpsCounter());

		// Añade el screen size info
		this.add(buildScreenSize());

		itemsVisual = 0;
		itemCounterLabel.setText("0");
		this.setFillParent(true);
		this.setName("GameGuiActor");
	}

	/**
	 * Devuelve los main items que recolectados.
	 * 
	 * @return La colección de items.
	 */
	private Table buildGuiMainItem() {
		Table container = new Table();
		// Añade 3 items al array
		mainItems = new Array<Image>();
		for (int i = 0; i < 3; i++) {
			Image mainItem = new Image(
					AssetsWorlds.instance.mainItem.animation.getKeyFrame(0));
			// Establece el alpha a la mitad, para indicar que aún no se ha
			// recolectado
			mainItem.getColor().a = 0.5f;
			mainItem.setScale(0.9f);
			// Origen de rotación se establece en el centro del actor
			mainItem.setOrigin(mainItem.getWidth() / 2,
					mainItem.getHeight() / 2);
			mainItems.add(mainItem);
		}
		mainItemPadRight = 10;
		container.add(mainItems.get(0)).left().padRight(mainItemPadRight);
		container.add(mainItems.get(1)).left().padRight(mainItemPadRight);
		container.add(mainItems.get(2)).left().padRight(mainItemPadRight);
		return container.left().top().padTop(25).padLeft(25);
	}

	/**
	 * Devuelve los items que recolectados.
	 * 
	 * @return El item y el contador.
	 */
	private Table buildGuiItem() {
		Table container = new Table();
		Image item = new Image(
				AssetsWorlds.instance.item.animation.getKeyFrame(0));
		item.setScale(0.8f);
		BitmapFont fontBitmap = AssetsWorlds.instance.fonts.generalBig;
		fontBitmap.getData().setScale(0.6f);
		Label label = new Label("x", new Label.LabelStyle(fontBitmap,
				Color.WHITE));
		itemCounterLabel = new Label(Integer.toString(worldController.items),
				new Label.LabelStyle(fontBitmap, Color.WHITE));
		container.add(item).left();
		container.add(label).left().padRight(10).padBottom(11);
		container.add(itemCounterLabel).left().padBottom(9);
		// Ajusta el padding a continuación del main item
		return container
				.left()
				.top()
				.padTop(7)
				.padLeft(
						(mainItems.get(0).getWidth() + mainItemPadRight)
								* mainItems.size + 100);
	}

	/**
	 * Devuelve el botón de pausa.
	 * 
	 * @return El botón de pausa.
	 */
	private Table buildPauseButton() {
		Table container = new Table();
		container.setFillParent(true);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		// Crea el botón
		Button pausebtn = new Button(
				AssetsWorlds.instance.inGameUI.menuButtonStyle);
		pausebtn.setName("PauseButton");
		// Añade el click listener al botón de pausa
		pausebtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!gameScreen.isPaused()) {
					gameScreen.pause();
					Gdx.app.debug(TAG, "Botón pausa pulsado");
				} else {
					gameScreen.resume();
				}
			}
		});
		container.add(pausebtn);
		return container.right().top().padTop(25).padRight(25);
	}

	/**
	 * Devuelve el botón de control arriba.
	 * 
	 * @return El botón de control arriba.
	 */
	private Table buildUpControlButtons() {
		Table container = new Table();
		container.setFillParent(true);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		// Crea los botones de salto y esquivar
		Button upbtn = new Button(
				AssetsWorlds.instance.inGameUI.upControlButtonStyle);
		upbtn.setName("UpButton");
		container.add(upbtn);
		return container.right().bottom().padBottom(6).padRight(25);
	}

	/**
	 * Devuelve el botón de control abajo.
	 * 
	 * @return El botón de control abajo.
	 */
	private Table buildDownControlButtons() {
		Table container = new Table();
		container.setFillParent(true);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		// Crea los botones de salto y esquivar
		Button downbtn = new Button(
				AssetsWorlds.instance.inGameUI.downControlButtonStyle);
		downbtn.setName("DownButton");
		container.add(downbtn);
		return container.left().bottom().padBottom(6).padLeft(25);
	}

	/**
	 * Dibuja el FPS del GUI del juego
	 */
	private Table buildFpsCounter() {
		Table container = new Table();
		BitmapFont fpsFontBitmap = AssetsWorlds.instance.fonts.generalNormal;
		fpsFontBitmap.getData().setScale(0.4f);
		fpsLabel = new Label("", new Label.LabelStyle(fpsFontBitmap,
				Color.WHITE));
		container.add(fpsLabel);
		return container.bottom().left().padBottom(25).padLeft(25);
	}

	/**
	 * Dibuja el tamaño de pantalla juego
	 * 
	 */
	private Table buildScreenSize() {
		Table container = new Table();
		BitmapFont bitmap = AssetsWorlds.instance.fonts.generalNormal;
		bitmap.getData().setScale(0.4f);
		screenSizeLabel = new Label("", new Label.LabelStyle(bitmap,
				Color.WHITE));
		container.add(screenSizeLabel);
		return container.bottom().right().padBottom(25).padLeft(25);
	}

	/**
	 * Dibuja el tiempo restante al powerUp recolectado.
	 * 
	 * @param batch
	 */
	private void renderGuiPowerup(Batch batch) {
		float x = 180;
		float y = 20;

		float timeLeftPowerup = worldController.level.runner.timeLeftPowerup;
		if (timeLeftPowerup > 0) {
			// Comienza a parpadear si el tiempo restante del powerup es menor
			// de 4 segundos.
			// El intervalo de parpadeo es establecido a 5 por segundo.
			if (timeLeftPowerup < 4) {
				if (((int) (timeLeftPowerup * 5) % 2) != 0) {
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(AssetsWorlds.instance.powerUp.powerUpBlueGUI, x, y);
			batch.setColor(1, 1, 1, 1);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (!gameScreen.isPaused()) {
			// Aplica la animación al recolectar un item
			if (itemsVisual < worldController.mainItems) {
				int i = worldController.mainItems - 1;
				setAnimation(mainItems.get(i));
				itemsVisual += 1;
			}
			// Actualiza el contador de items
			if (Integer.parseInt(itemCounterLabel.getText().toString()) < worldController.items) {
				itemCounterLabel.setText(Integer
						.toString(worldController.items));
			}
		}

		// Dibuja el fps counter
		if (Constants.DEBUG_MODE) {
			int fps = Gdx.graphics.getFramesPerSecond();
			fpsLabel.setText("FPS: " + fps);
			if (fps >= 45) {
				// 45 o más FPS se muestra en verde
				fpsLabel.setColor(0, 1, 0, 1);
			} else if (fps >= 30) {
				// 30 o más FPS se muestra en amarillo
				fpsLabel.setColor(1, 1, 0, 1);
			} else {
				// menos de 30 FPS se muestra en rojo
				fpsLabel.setColor(1, 0, 0, 1);
			}
			// Dibuja el tamaño de la pantalla
			screenSizeLabel.setText(Gdx.graphics.getWidth() + " x "
					+ Gdx.graphics.getHeight());
		}
	}

	/**
	 * Aplica la animación a un actor
	 * 
	 * @param actor
	 *            el actor al cual aplicar la animación
	 */
	private void setAnimation(Actor actor) {
		SequenceAction sequence = new SequenceAction();
		ParallelAction parallel = new ParallelAction();

		ScaleByAction scaleUp = new ScaleByAction();
		scaleUp.setAmount(0.60f);
		scaleUp.setDuration(0.25f);

		ScaleByAction scaleDown = new ScaleByAction();
		scaleDown.setAmount(-0.60f);
		scaleDown.setDuration(0.1f);

		RotateToAction rotate = new RotateToAction();
		rotate.setRotation(360);
		rotate.setDuration(0.2f);

		AlphaAction alpha = new AlphaAction();
		alpha.setAlpha(1f);
		alpha.setDuration(0.3f);

		parallel.addAction(scaleDown);
		parallel.addAction(rotate);
		parallel.addAction(alpha);
		sequence.addAction(scaleUp);
		sequence.addAction(parallel);

		actor.addAction(sequence);
	}

	public void restart() {
		this.clear();
		buildGUI();
	}

}
