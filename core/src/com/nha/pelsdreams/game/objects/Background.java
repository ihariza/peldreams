package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa el fondo de pantalla del juego en varias capas, moviéndose a
 * diferente velocidad para simular una ilusión óptica de profundidad.
 * 
 * @author Nacho Herrera
 * 
 */
public class Background extends AbstractGameObject {
	private TextureRegion blueMountains;
	private TextureRegion sun;
	private TextureRegion cloud1;
	private TextureRegion cloud2;
	private TextureRegion cloud3;
	private TextureRegion background_layer2_1;
	private TextureRegion background_layer2_2;
	private TextureRegion background_layer2_2v1;
	private TextureRegion background_layer2_2v2;
	private TextureRegion background_layer2_2v4;

	public Background() {
		init();
	}

	private void init() {
		blueMountains = AssetsWorlds.instance.world.blueMountains;
		sun = AssetsWorlds.instance.world.sun;
		cloud1 = AssetsWorlds.instance.world.cloud1;
		cloud2 = AssetsWorlds.instance.world.cloud2;
		cloud3 = AssetsWorlds.instance.world.cloud3;
		background_layer2_1 = AssetsWorlds.instance.world.background_layer2_1;
		background_layer2_2 = AssetsWorlds.instance.world.background_layer2_2;
		background_layer2_2v1 = AssetsWorlds.instance.world.background_layer2_2v1;
		background_layer2_2v2 = AssetsWorlds.instance.world.background_layer2_2v2;
		background_layer2_2v4 = AssetsWorlds.instance.world.background_layer2_2v4;

		// Origen de x
		origin.x = -4;
		// Origen de y
		origin.y = 1;

	}

	/**
	 * Dibuja una capa del fondo de pantalla en espejo, la primera normal, y la
	 * segunda rotada horizontalmente
	 * 
	 * @param batch
	 * @param region
	 *            La región de la textura
	 * @param length
	 *            Número de veces que se repite el fondo
	 * @param offsetX
	 *            Distancia de inicio en el eje x
	 * @param offsetY
	 *            Distancia de inicio en el eje y
	 * @param parallaxSpeedX
	 *            Velocidad de movimiento de la capa
	 */
	private void drawBackgroundMirror(SpriteBatch batch, TextureRegion region,
			float length, float offsetX, float offsetY, float parallaxSpeedX) {

		dimension.set(region.getRegionWidth() * Constants.WORLD_TO_BOX,
				region.getRegionHeight() * Constants.WORLD_TO_BOX);

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

	/**
	 * Dibuja una capa del fondo de pantalla
	 * 
	 * @param batch
	 * @param region
	 *            La región de la textura
	 * @param paddingX
	 *            Espaciado entre objetos
	 * @param length
	 *            Número de veces que se repite
	 * @param offsetX
	 *            Distancia de inicio en el eje x
	 * @param offsetY
	 *            Distancia de inicio en el eje y
	 * @param parallaxSpeedX
	 *            Velocidad de movimiento de la capa
	 */
	private void drawBackground(SpriteBatch batch, TextureRegion region,
			float paddingX, float length, float offsetX, float offsetY,
			float parallaxSpeedX) {
		float xRel = offsetX;
		float yRel = offsetY;

		for (int i = 0; i < length; i++) {
			batch.draw(region, origin.x + xRel + position.x * parallaxSpeedX,
					origin.y + yRel + position.y, region.getRegionWidth()
							* Constants.WORLD_TO_BOX, region.getRegionHeight()
							* Constants.WORLD_TO_BOX);
			// Separa los objetos según el vector paddingX
			xRel += dimension.x + paddingX;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		// Establece el color de la pantalla de fondo en azul cielo
		Gdx.gl.glClearColor(0x30 / 255.0f, 0x80 / 255.0f, 0xcf / 255.0f,
				0xff / 255.0f);
		// Limpia la pantalla
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawBackground(batch, sun, 0, 1, 1, 1.8f, 0.9f);
		drawBackgroundMirror(batch, blueMountains, 2, -0.1f, 0.5f, 0.8f);
		drawBackground(batch, cloud3, 14, 5, 13, 2.8f, 0.3f);
		drawBackground(batch, background_layer2_2, 5, 3.5f, 0, 0.6f, 0.7f);
		drawBackgroundMirror(batch, background_layer2_1, 4, 0, 0.6f, 0.6f);
		drawBackground(batch, background_layer2_2v1, 15, 4, -15, 1.5f, 0.6f);
		drawBackground(batch, background_layer2_2v4, 15.5f, 4, -15, 1.2f, 0.6f);
		drawBackground(batch, background_layer2_2v2, 7.5f, 5, -15, 0.2f, 0.6f);
		drawBackground(batch, cloud1, 14, 6, 10, 3.2f, 0.4f);
		drawBackground(batch, cloud2, 14, 6, 7, 3.6f, 0.2f);
	}

	/**
	 * Actualiza la posición de la cámara
	 * @param camPosition
	 */
	public void updateScrollPosition(Vector2 camPosition) {
		position.set(camPosition.x, position.y);
	}

}
