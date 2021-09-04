package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa un enemigo estático
 * 
 * @author Nacho Herrera
 * 
 */
public class EnemyStatic extends AbstractGameObject {

	public static final String TAG = EnemyStatic.class.getName();

	// Almacena las animaciones de la serpiente
	private Array<Animation<TextureRegion>> snake;
	// Enemigo seleccionado aleatoriamente
	private Array<Animation<TextureRegion>> animationSelected;
	// Almacena los enemigos
	private Array<Array<Animation<TextureRegion>>> enemies;
	// Tiempo trancurrido para cambiar de animación
	private static float DELAY_TIME = 2;
	private float delay;

	public EnemyStatic() {
		init();
	}

	public void init() {
		// Tipo de objeto
		userDataType = UserDataType.ENEMY_STATIC;
		// Centra la imagen en el objeto
		origin.set(0, 0);
		snake = new Array<>();
		snake.add(AssetsWorlds.instance.enemiesWorld001.snake_first);
		snake.add(AssetsWorlds.instance.enemiesWorld001.snake_second);
		enemies = new Array<>();
		enemies.add(snake);
		// Establece una animación aleatoriamente
		animationSelected = enemies.get(MathUtils.random(0, enemies.size - 1));
		setAnimation(animationSelected.first());
		dimension.set(
				animationSelected.first().getKeyFrame(0).getRegionWidth()
						* Constants.WORLD_TO_BOX, animationSelected.first()
						.getKeyFrame(0).getRegionHeight()
						* Constants.WORLD_TO_BOX);
		// Establece los límites para la detección de colisiones
		bounds.set(0, 0, dimension.x, dimension.y);
		delay = DELAY_TIME;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		// Decrementa el tiempo de espera para cambiar de animación al enemigo
		// seleccionado
		delay -= deltaTime;
		if (delay < -1)
			delay = DELAY_TIME;
		// Establece la animación según el tiempo de retardo
		if (delay < 0) {
			animation = animationSelected.get(1);
		} else {
			animation = animationSelected.get(0);
		}
	}

	public void hit() {

	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;

		// Dibuja la imagen
		reg = animation.getKeyFrame(stateTime, true);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

}