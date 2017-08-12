package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.nha.pelsdreams.game.AssetsWorlds;

/**
 * Representa el efecto de partículas de la meta
 * @author Ignacio Herrera
 *
 */
public class GoalParticles extends AbstractGameObject {

	private ParticleEffect particleEffect;
	
	private Vector2 position;
	
	// Tiempo de espera antes de finalizar el ejecto de partículas
	private float delay = 0.5f;

	public GoalParticles(float positionX, float positionY) {
		this.position = new Vector2(positionX, positionY);
		init();
	}

	public void init() {
		particleEffect = AssetsWorlds.instance.goal.particleEffect;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		particleEffect.update(deltaTime);
	}

	@Override
	public void render(SpriteBatch batch) {
		// Dibuja las partículas
		particleEffect.draw(batch);
	}

	public void start() {
		particleEffect
				.setPosition(position.x + dimension.x / 2, position.y + 2.3f);
		particleEffect.start();
	}
	
	public void stop(float deltaTime) {
		delay -= deltaTime;
		if (delay < 0)
			particleEffect.allowCompletion();
	}

}
