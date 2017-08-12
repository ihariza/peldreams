package com.nha.pelsdreams.screens.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nha.pelsdreams.game.AssetsLoading;
import com.nha.pelsdreams.utils.Constants;

/**
 * Repreenta la animación de la pantalla de carga
 * 
 * @author Nacho Herrera Ariza
 * 
 */
public class LoadingBarActor extends Table {

	// private static final String TAG = LoadingBarActor.class.getName();
	
	private SequenceAction sequence;
	
	public LoadingBarActor() {
		buildLoadingBar();
	}
	
	/**
	 * Construye la barra de progreso
	 */
	private void buildLoadingBar() {
		Table root = new Table();
		if (Constants.DEBUG_DRAW_UI)
			root.debug();

		// Crea 10 bolas
		float counterDelay = 0;
		for (int i = 0; i <= 10; i++) {
			root.add(buildBall(counterDelay)).center().padRight(10);
			counterDelay += 0.2f;
		}

		add(root).center().padTop(90);
	}

	/**
	 * Construye una bola con su animación
	 * 
	 * @param delay
	 *            Tiempo de espera para iniciar la animación
	 * @return La imagen de la bola
	 */
	private Image buildBall(float delay) {

		Image ball = new Image(AssetsLoading.instance.ballRegion);
		ball.getColor().a = 0.0f;
		ball.setScale(0.5f);
		ball.setOrigin(ball.getWidth() / 2, ball.getHeight() / 2);
		setAnimation(ball, delay);
		return ball;
	}
	

	/**
	 * Aplica la animación a un actor
	 * 
	 * @param actor
	 *            el actor al cual aplicar la animación
	 */
	private void setAnimation(Actor actor, float delay) {
		sequence = new SequenceAction();
		ParallelAction parallel = new ParallelAction();

		DelayAction delayAction = new DelayAction(delay);

		ScaleByAction scaleUp = new ScaleByAction();
		scaleUp.setAmount(1f);
		scaleUp.setDuration(0.1f);

		AlphaAction alphaUp = new AlphaAction();
		alphaUp.setAlpha(1f);
		alphaUp.setDuration(0.1f);

		AlphaAction alphaDown = new AlphaAction();
		alphaDown.setAlpha(0f);
		alphaDown.setDuration(0.2f);

		sequence.addAction(delayAction);
		parallel.addAction(alphaUp);
		parallel.addAction(scaleUp);
		sequence.addAction(parallel);
		sequence.addAction(alphaDown);

		actor.addAction(sequence);
	}

}
