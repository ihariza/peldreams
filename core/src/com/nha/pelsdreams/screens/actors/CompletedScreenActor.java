package com.nha.pelsdreams.screens.actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nha.pelsdreams.enums.ShowButtonsType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.screens.DirectedGame;
import com.nha.pelsdreams.screens.GameScreen;
import com.nha.pelsdreams.screens.LoadingScreen;
import com.nha.pelsdreams.screens.WorldScreen;
import com.nha.pelsdreams.utils.Bundle;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla de nivel completado
 * 
 * @author Ignacio Herrera Ariza
 * 
 */
public class CompletedScreenActor extends ScreenActor {

	// private static final String TAG = CompletedScreenActor.class.getName();

	private Label numberMainItem;
	private Label numberItem;

	public CompletedScreenActor(DirectedGame game, GameState gameState) {
		super(game, gameState);
		// Establece el nombre al actor para localizarlo en el stage
		// a la hora de trabajar con �l (moverlo, eliminarlo, etc)
		this.setName("CompletedScreenActor");
		rebuildStage();
	}

	/**
	 * Construye la pantalla de nivel completado
	 */
	private void rebuildStage() {
		Label title = new Label("NIVEL "
				+ (gameScreen.getGameState().currentWorld.currentLevel.id + 1),
				skin, "big-font");
		// A�ade la tabla con el contenido a la tabla principal
		this.add(buildContentTable(title, buildStatisticsTable(),
				ShowButtonsType.ALL));

	}

	/**
	 * Construye la tabla que contiene las estad�sticas del nivel completado
	 * 
	 * @return La tabla construida
	 */
	private Table buildStatisticsTable() {
		Table container = new Table().center().padBottom(20);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		Stack containerStack = new Stack();

		Table levelLabelTable = new Table().top();
		Label levelLabel = new Label(
				Bundle.instance.i18n.get("levelcompleted"), skin, "yellow-font");
		levelLabel.setFontScale(0.8f);
		levelLabelTable.add(levelLabel);

		// Objetivo main items
		Stack mainItemStack = new Stack();
		Table imageMainItemTable = new Table().left().padLeft(20);
		Image imageMainItem = new Image(
				AssetsWorlds.instance.mainItem.animation.getKeyFrame(0));
		imageMainItem.setScale(0.8f);
		imageMainItemTable.add(imageMainItem);

		Table numberMainItemTable = new Table().left().padLeft(85);
		numberMainItem = new Label("", skin);
		numberMainItem.setFontScale(0.8f);
		numberMainItemTable.add(numberMainItem);

		mainItemStack.add(imageMainItemTable);
		mainItemStack.add(numberMainItemTable);

		// Objetivo items
		Stack itemStack = new Stack();
		Table imageItemTable = new Table().left().padLeft(15).padTop(120);
		Image imageItem = new Image(
				AssetsWorlds.instance.item.animation.getKeyFrame(0));
		imageItem.setScale(0.8f);
		imageItemTable.add(imageItem);

		Table numberItemTable = new Table().left().padLeft(85).padTop(120);
		numberItem = new Label("", skin);
		numberItem.setFontScale(0.8f);
		numberItemTable.add(numberItem);

		itemStack.add(imageItemTable);
		itemStack.add(numberItemTable);

		containerStack.add(levelLabelTable);
		containerStack.add(mainItemStack);
		containerStack.add(itemStack);

		container.add(containerStack);
		return container;
	}

	@Override
	public void update() {
		super.update();
		numberMainItem
				.setText("x " + gameScreen.getWorldController().mainItems);
		numberItem.setText("x " + gameScreen.getWorldController().items);
	}

	@Override
	protected ClickListener continueClickListener() {
		return new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				show(false, false);
				// Si ha pasado el último nivel del mundo, vuelve al menú de
				// mundos
				if (gameState.currentWorld.currentLevel.id == gameState.currentWorld.levels.size - 1) {
					game.setScreen(new LoadingScreen(new WorldScreen(game,
							gameState)));
				} else {
                    // Establece el siguiente nivel como nivel actual
                    gameScreen.getGameState().currentWorld.currentLevel = gameScreen
                            .getGameState().currentWorld.levels.get(gameScreen
                                    .getGameState().currentWorld.currentLevel.id + 1);
                    game.setScreen(new GameScreen(game, gameState));
                }
			}
		};
	}

}
