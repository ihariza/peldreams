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
 * Representa la pantalla de ayuda de control del juego.
 * 
 * @author Ignacio Herrera Ariza
 * 
 */
public class HelpScreenActor extends ScreenActor {

	// private static final String TAG = HelpScreenActor.class.getName();

	public HelpScreenActor(DirectedGame game, GameState gameState) {
		super(game, gameState);
		// Establece el nombre al actor para localizarlo en el stage
		// a la hora de trabajar con él (moverlo, eliminarlo, etc)
		this.setName("HelpScreenActor");
		rebuildStage();
	}

	/**
	 * Construye la pantalla de ayuda del juego
	 */
	private void rebuildStage() {
		// Añade la tabla con el contenido a la tabla principal
		Label title = new Label(Bundle.instance.i18n.format("helpTitle"), skin,
				"big-font");
		add(buildContentTable(title, buildContentTable(),
				ShowButtonsType.CONTINUE));
	}

	/**
	 * Construye la tabla que contiene la ayuda con control
	 * 
	 * @return La tabla construida
	 */
	private Table buildContentTable() {
		Table container = new Table().center().padBottom(30);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		Stack containerStack = new Stack();

		Table jumpLabelTable = new Table().center().top();
		if (Constants.DEBUG_DRAW_UI)
			jumpLabelTable.debug();
		Image buttonJump = new Image(
				AssetsWorlds.instance.inGameUI.upControlButtonStyle.up);
		buttonJump.setScale(0.7f);
		Label jumpLabel = new Label(Bundle.instance.i18n.get("helpJump"), skin,
				"yellow-font");
		jumpLabel.setFontScale(0.7f);
		Image buttonDodge = new Image(
				AssetsWorlds.instance.inGameUI.downControlButtonStyle.up);
		buttonDodge.setScale(0.7f);
		Label dodgedLabel = new Label(Bundle.instance.i18n.get("helpDodge"), skin,
				"yellow-font");
		dodgedLabel.setFontScale(0.7f);

		jumpLabelTable.add(buttonJump);
		jumpLabelTable.add(jumpLabel);
		jumpLabelTable.row();
		jumpLabelTable.add(buttonDodge);
		jumpLabelTable.add(dodgedLabel);

		containerStack.add(jumpLabelTable);
		
		container.add(containerStack).padTop(15);
		return container;
	}

	@Override
	protected ClickListener continueClickListener() {
		return new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScreen.resume();
			}
		};
	}

}
