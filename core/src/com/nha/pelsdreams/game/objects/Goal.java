package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa la meta
 * 
 * @author Ignacio Herrera
 * 
 */
public class Goal extends AbstractGameObject {

	private TextureRegion goal;
	// Controla si se ha llegado a la meta para comenzar la animaci�n.
	public boolean isReached;
	// Controla cuando la animaci�n ha terminado 
	public boolean animFinished;
	// Controla el movimiento de temblor
	private static float shakeTime = 0.04f;
	private float shakeTimeLeft;
	
	// Tiempo para comenzar la animaci�n hacia arriba
	private float animUpDelay= 1.5f;

	public Goal() {
		init();
	}

	public void init() {
		userDataType = UserDataType.GOAL;
		goal = AssetsWorlds.instance.goal.goal;
		// Dimensi�n
		dimension.set(goal.getRegionWidth() * Constants.WORLD_TO_BOX,
				goal.getRegionHeight() * Constants.WORLD_TO_BOX);
		origin.set(-4, 1);
		// Ajusta los l�mites para la detecti�n de colisiones en las esquinas
		bounds.set(origin.x, origin.y, dimension.x, dimension.y);
		shakeTimeLeft = shakeTime;
	}

	@Override
	public void update(float deltaTime) {
		// Si el runner ha llegado a la meta, activa la animaci�n
		if (isReached) {
			shakeTimeLeft -= 0.01f;
			animUpDelay -= deltaTime;
			// Mueve hacia arriba el goal hasta que llegue a la altura
			// determinada
			if (position.y < 2.0f) {
				if (animUpDelay < 0)
					position.y += deltaTime;
				// Mueve el goal hacia la derecha o izquierda en funci�n del
				// tiempo
				if (shakeTimeLeft > 0) {
					position.x += 0.01f;
				} if (shakeTimeLeft < 0) {
					position.x -= 0.01f;
				}
				// Si se ha terminado el tiempo estimado,
				// resetea el tiempo, para cambiar de direcci�n de movimiento.
				if (shakeTimeLeft <= -0.03) {
					shakeTimeLeft = shakeTime;
				}

			}
			if (position.y > 1.65f)
				animFinished = true;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// Dibuja el goal
		batch.draw(goal.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				goal.getRegionX(), goal.getRegionY(), goal.getRegionWidth(),
				goal.getRegionHeight(), false, false);
	}

	/**
	 * Indica que el runner ha llegado a la meta
	 */
	public void reached() {
		isReached = true;
		AudioManager.instance.play(AssetsWorlds.instance.sounds.totem);
	}

}
