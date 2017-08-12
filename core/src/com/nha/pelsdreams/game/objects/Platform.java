package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa una plataforma.
 * 
 * @author Nacho Herrera
 * 
 */
public class Platform extends AbstractGameObject {
	
	public static final String TAG = Platform.class.getName();
	
	private TextureRegion regPlatform;
	private int unitsAmount;

	/**
	 * Constructor
	 * @param unitsAmount Cantidad de unidades que tiene la plataforma
	 */
	public Platform(int unitsAmount) {
		this.unitsAmount = unitsAmount;
		init();
	}

	protected void init() {
		userDataType = UserDataType.PLATFORM;
		// Añade las plataformas para posteriormente
		// seleccionar una de forma aletoria
		Array<TextureRegion> regPlatforms = new Array<TextureRegion>();
		regPlatforms.add(AssetsWorlds.instance.world.platform);
		regPlatform = regPlatforms.get(MathUtils.random(0, regPlatforms.size - 1));
		
		// Dimensión
		dimension.set(regPlatform.getRegionWidth() * Constants.WORLD_TO_BOX,
				regPlatform.getRegionHeight() * Constants.WORLD_TO_BOX);
		origin.set(0, 0);
		// Ajusta los límites para la detección de colisiones
		bounds.set(origin.x, origin.y, dimension.x * unitsAmount, dimension.y);

	}

	@Override
	public void render(SpriteBatch batch) {
		float posX = position.x;
		for (int i = 0; i < unitsAmount; i++) {
			batch.draw(regPlatform.getTexture(), posX, position.y, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
					regPlatform.getRegionX(), regPlatform.getRegionY(),
					regPlatform.getRegionWidth(), regPlatform.getRegionHeight(), false,
					false);
			posX += Constants.PIXEL_TO_WORLD;
		}
	}

}
