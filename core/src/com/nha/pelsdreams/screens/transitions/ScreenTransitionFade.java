package com.nha.pelsdreams.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Transición de efecto alpha
 * 
 * @author Nacho Herrera
 * 
 */
public class ScreenTransitionFade implements ScreenTransition {

	private static final ScreenTransitionFade instance = new ScreenTransitionFade();

	private float duration;

	public static ScreenTransitionFade init(float duration) {
		instance.duration = duration;
		return instance;
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public void render(SpriteBatch batch, Viewport viewport,Texture currScreen,
			Texture nextScreen, float alpha) {
		float w = viewport.getCamera().viewportWidth;
		float h = viewport.getCamera().viewportHeight;
		alpha = Interpolation.fade.apply(alpha);

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		batch.setColor(1, 1, 1, 1);
		batch.draw(currScreen, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0,
				currScreen.getWidth(), currScreen.getHeight(), false, true);
		batch.setColor(1, 1, 1, alpha);
		batch.draw(nextScreen, 0, 0, 0, 0, w, h, 1, 1, 0, 0, 0,
				nextScreen.getWidth(), nextScreen.getHeight(), false, true);
		batch.end();
	}

}
