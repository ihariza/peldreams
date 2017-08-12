package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.Constants;

/**
 * Represeta el suelo
 * @author Nacho Herrera
 *
 */
public class Ground extends AbstractGameObject {

	private TextureRegion ground;

	public Ground() {
		init();
	}

	protected void init() {
		userDataType = UserDataType.GROUND;
		ground = AssetsWorlds.instance.world.ground;
		dimension.set(ground.getRegionWidth() * Constants.WORLD_TO_BOX,
				ground.getRegionHeight() * Constants.WORLD_TO_BOX);
		origin.set(-4, 0);
		position.set(1, 1);
	}

	/**
	 * Dibuja el suelo dos veces, la primera normal, y la segunda rotada
	 * horizontalmente
	 * 
	 * @param batch
	 * @param region
	 *            La región de la textura
	 * @param length
	 *            Número de veces que se repite el suelo
	 * @param offsetX
	 *            Distancia de inicio en el eje x
	 * @param offsetY
	 *            Distancia de inicio en el eje y
	 * @param parallaxSpeedX
	 *            Velocidad de movimiento de la capa
	 */
	private void drawBackground(SpriteBatch batch, TextureRegion region,
			float length, float offsetX, float offsetY, float parallaxSpeedX) {

		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;

		for (int i = 0; i < length; i++) {
			// background parte izquierda
			batch.draw(region.getTexture(), origin.x + xRel + position.x
					* parallaxSpeedX, origin.y + yRel + position.y, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y,
					rotation, region.getRegionX(), region.getRegionY(),
					region.getRegionWidth(), region.getRegionHeight(), false,
					false);
			xRel += dimension.x;

			// background parte derecha
			batch.draw(region.getTexture(), origin.x + xRel + position.x
					* parallaxSpeedX, origin.y + yRel + position.y, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y,
					rotation, region.getRegionX(), region.getRegionY(),
					region.getRegionWidth(), region.getRegionHeight(), true,
					false);
			xRel += dimension.x;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		drawBackground(batch, ground, 8, 0.09f, -0.1f, 0.0f);
	}
}
