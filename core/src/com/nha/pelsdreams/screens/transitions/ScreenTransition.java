package com.nha.pelsdreams.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Representa una transición
 * 
 * @author Nacho Herrera
 *
 */
public interface ScreenTransition {
	
	public float getDuration();

	public void render(SpriteBatch batch, Viewport viewport, Texture currScreen,
			Texture nextScreen, float alpha);
}
