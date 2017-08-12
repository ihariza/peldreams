package com.nha.pelsdreams.screens.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.nha.pelsdreams.enums.ShowButtonsType;
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
public class GameOverScreenActor extends ScreenActor {

	// private static final String TAG = GameOverScreenActor.class.getName();

	private Label labelYellow;

	public GameOverScreenActor(DirectedGame game, GameState gameState) {
		super(game, gameState);
		// Establece el nombre al actor para localizarlo en el stage
		// a la hora de trabajar con �l (moverlo, eliminarlo, etc)
		this.setName("GameOverScreenActor");
		rebuildStage();
	}

	/**
	 * Construye la pantalla de pausa del juego
	 */
	private void rebuildStage() {
		// A�ade la tabla con el contenido a la tabla principal
		Label title = new Label("UPSSSS...", skin, "big-font");
		add(buildContentTable(title, buildObjetivesTable(),
				ShowButtonsType.MENU_AND_RESTART));
	}

	/**
	 * Construye la tabla que contiene los objetivos del nivel
	 * 
	 * @return La tabla construida
	 */
	private Table buildObjetivesTable() {
		// A�ade los botones de m�sica y sonido
		Table container = new Table().center().padBottom(30);
		if (Constants.DEBUG_DRAW_UI)
			container.debug();
		labelYellow = new Label("", skin, "yellow-font");
		labelYellow.setFontScale(0.7f);
		labelYellow.setAlignment(Align.center);

		Label label2 = new Label(Bundle.instance.i18n.get("gameover"), skin);
		label2.setFontScale(0.7f);
		label2.setAlignment(Align.center);

		container.add(labelYellow);
		container.row();
		container.add(label2);
		return container;
	}

	@Override
	public void update() {
		super.update();
		if (gameScreen.getWorldController().level.runner.isDeadRock) {
			labelYellow.setText(Bundle.instance.i18n.get("gameoverplatform"));
		} else {
			labelYellow.setText(Bundle.instance.i18n.get("gameoverenemy"));
		}
	}

	@Override
	protected ClickListener continueClickListener() {
		return null;
	}

}
