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
import com.nha.pelsdreams.utils.Bundle;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;

/**
 * Representa la pantalla de inicio de cada nivel
 * 
 * @author Nacho Herrera
 * 
 */
public class InitScreenActor extends ScreenActor {

	// private static final String TAG = InitScreenActor.class.getName();

	public InitScreenActor(DirectedGame game, GameState gameState) {
		super(game, gameState);
		// Establece el nombre al actor para localizarlo en el stage
		// a la hora de trabajar con él (moverlo, eliminarlo, etc)
		this.setName("InitScreenActor");
		rebuildStage();
	}

	/**
	 * Construye la pantalla de inicio del juego
	 */
	private void rebuildStage() {
		// Añade la tabla con el contenido a la tabla principal
		Label title = new Label(Bundle.instance.i18n.get("level") + " "
				+ (gameScreen.getGameState().currentWorld.currentLevel.id + 1),
				skin, "big-font");
		add(buildContentTable(title, buildObjetivesTable(),
				ShowButtonsType.CONTINUE));
	}

	/**
	 * Construye la tabla que contiene los objetivos del nivel
	 * 
	 * @return La tabla construida
	 */
	private Table buildObjetivesTable() {
		Table container = new Table().center().padBottom(20);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		Stack containerStack = new Stack();

		Table levelLabelTable = new Table().center().top();
		Label levelLabel = new Label(Bundle.instance.i18n.get("goals"), skin,
				"yellow-font");
		levelLabel.setFontScale(0.8f);
		levelLabelTable.add(levelLabel);

		// Objetivo main items
		Stack mainItemStack = new Stack();
		Table imageMainItemTable = new Table().center().left().padLeft(5);
		Image imageMainItem = new Image(
				AssetsWorlds.instance.mainItem.animation.getKeyFrame(0));
		imageMainItem.setScale(0.8f);
		imageMainItemTable.add(imageMainItem);

		Table numberMainItemTable = new Table().center().left().padLeft(70);
		Label numberMainItem = new Label("x "
				+ gameScreen.getWorldController().level.mainItemsObjetive, skin);
		numberMainItem.setFontScale(0.8f);
		numberMainItemTable.add(numberMainItem);

		mainItemStack.add(imageMainItemTable);
		mainItemStack.add(numberMainItemTable);

		// Objetivo items
		Stack itemStack = new Stack();
		Table imageItemTable = new Table().center().left().padTop(120);
		Image imageItem = new Image(
				AssetsWorlds.instance.item.animation.getKeyFrame(0));
		imageItem.setScale(0.8f);
		imageItemTable.add(imageItem);

		Table numberItemTable = new Table().center().left().padLeft(70)
				.padTop(120);
		Label numberItem = new Label("x "
				+ gameScreen.getWorldController().level.itemsObjetive, skin);
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
	protected ClickListener continueClickListener() {
		return new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gameState.currentWorld.currentLevel.id == 0) {
					gameScreen.showHelpScreen();
				} else {
					gameScreen.resume();
				}	
			}
		};
	}
}
