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
 * Representa el item a recolectar
 * 
 * @author Nacho Herrera
 * 
 */
public class Item extends AbstractGameObject {

	private Animation animItem;
	public ParticleEffect particleEffect = new ParticleEffect();
	// Estado actual de visibilidad
	public boolean collected;

	public Item() {
		init();
	}

	private void init() {
		userDataType = UserDataType.ITEM;
		animItem = AssetsWorlds.instance.item.animation;
		setAnimation(animItem);
		// Redimensionado a un 90%
		dimension.set(animItem.getKeyFrames()[0].getRegionWidth()
				* Constants.WORLD_TO_BOX * 0.9f,
				animItem.getKeyFrames()[0].getRegionHeight()
						* Constants.WORLD_TO_BOX * 0.9f);
		// Establece los l�mites para la detecci�n de colisiones
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
		// Part�culas
		particleEffect.load(Gdx.files.internal("particles/item.pfx"),
				Gdx.files.internal("particles"));

	}

	@Override
	public void update(float deltaTime) {
		particleEffect.update(deltaTime);
		super.update(deltaTime);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		
		particleEffect.draw(batch);
		if (particleEffect.isComplete())
			particleEffect.allowCompletion();
		
		// Si no ha sido recogida, se dibuja
		if (!collected) {
			TextureRegion reg = null;

			reg = animItem.getKeyFrame(stateTime, true);
			batch.draw(reg.getTexture(), position.x, position.y, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
					reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
		}		
	}
	
	public void hit() {
		AudioManager.instance.play(AssetsWorlds.instance.sounds.item);
		particleEffect.setPosition(position.x + dimension.x / 2,
				position.y + dimension.y / 2);
		particleEffect.start();
		collected = true;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		particleEffect.dispose();
	}

}
