package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa el item principal a recolectar
 * 
 * @author Nacho Herrera
 * 
 */
public class MainItem extends AbstractGameObject {

	private Animation animation;
	public ParticleEffect particleEffect = new ParticleEffect();
	// Estado actual de visibilidad
	public boolean collected;
	// Tiempo trancurrido para cambiar de animaci�n
	private static float DELAY_TIME = 0.5f;
	private float delay;

	public MainItem() {
		init();
	}

	private void init() {
		userDataType = UserDataType.MAIN_ITEM;
		animation = AssetsWorlds.instance.mainItem.animationIdle;
		setAnimation(animation);
		dimension.set(animation.getKeyFrames()[0].getRegionWidth()
				* Constants.WORLD_TO_BOX,
				animation.getKeyFrames()[0].getRegionHeight()
						* Constants.WORLD_TO_BOX);
		// Establece los l�mites para la detecci�n de colisiones
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
		delay = DELAY_TIME;
		// Part�culas
		particleEffect.load(Gdx.files.internal("particles/item.pfx"),
				Gdx.files.internal("particles"));
	}

	@Override
	public void update(float deltaTime) {
		particleEffect.update(deltaTime);
		super.update(deltaTime);

		// Decrementa el tiempo de espera para cambiar de animaci�n al enemigo
		// seleccionado
		delay -= deltaTime;

		// Establece 0.5 unidades de tiempo para el idle y 1 unidad para la
		// animaci�n en movimiento
		if (delay < -1)
			delay = DELAY_TIME;
		// Dibuja la imagen
		if (delay < 0) {
			animation = AssetsWorlds.instance.mainItem.animation;
		} else {
			animation = AssetsWorlds.instance.mainItem.animationIdle;
		}

	}

	@Override
	public void render(SpriteBatch batch) {

		particleEffect.draw(batch);

		// Si no ha sido recogida, se dibuja
		if (!collected) {
			TextureRegion reg = null;

			reg = animation.getKeyFrame(stateTime, true);
			batch.draw(reg.getTexture(), position.x, position.y, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y,
					rotation, reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		}
	}

	public void hit() {
		AudioManager.instance.play(AssetsWorlds.instance.sounds.item);
		particleEffect.setPosition(position.x + dimension.x / 2, position.y
				+ dimension.y / 2);
		particleEffect.start();
		collected = true;
	}

	@Override
	public void dispose() {
		super.dispose();
		particleEffect.dispose();
	}

}
