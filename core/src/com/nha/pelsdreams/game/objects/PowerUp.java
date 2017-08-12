package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;

/**
 * Representa un power-up que dará la capacidad de volar al personaje del
 * jugador al recogerlo.
 * 
 * @author Nacho Herrera
 * 
 */
public class PowerUp extends AbstractGameObject {

	private TextureRegion regPowerUp;
	// Estado actual de visibilidad
	public boolean collected;

	public PowerUp() {
		init();
	}

	private void init() {
		userDataType = UserDataType.POWER_UP;
		dimension.set(0.5f, 0.5f);
		regPowerUp = AssetsWorlds.instance.powerUp.powerUpBlue;
		// Establece los l�mites para la detecci�n de colisiones
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}

	public void render(SpriteBatch batch) {
		// Si ha sido recogida, no se dibuja
		if (collected)
			return;

		TextureRegion reg = null;
		reg = regPowerUp;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

}
