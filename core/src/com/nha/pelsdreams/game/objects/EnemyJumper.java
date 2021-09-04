package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa un enemigo volador
 * 
 * @author Nacho Herrera
 * 
 */
public class EnemyJumper extends AbstractGameObject {

	public static final String TAG = EnemyJumper.class.getName();
	
	private final float JUMP_TIME_MAX = 0.32f;
	
	private enum JUMP_STATE {
		GROUNDED, JUMP_RISING, JUMP_FALLING
	}

	// Almacena las animaciones de la serpiente
	private Array<Animation<TextureRegion>> worm;
	// Enemigo seleccionado aleatoriamente
	private Array<Animation<TextureRegion>> animationSelected;
	// Almacena los enemigos
	private Array<Array<Animation<TextureRegion>>> enemies;
	// Tiempo trancurrido para cambiar de animación
	private static float DELAY_TIME = 0.2f;
	private float delay;

	private Vector2 velocity;
	private JUMP_STATE jumpState;
	private float timeJumping;
	public float timeLeftPowerup;
	
	public EnemyJumper() {
		init();
	}

	public void init() {
		// Tipo de objeto
		userDataType = UserDataType.ENEMY_JUMPER;
		// Centra la imagen en el objeto
		origin.set(0, 0);
		worm = new Array<>();
		worm.add(AssetsWorlds.instance.enemiesWorld001.worm);
		worm.add(AssetsWorlds.instance.enemiesWorld001.worm);
		enemies = new Array<>();
		enemies.add(worm);
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
		// Estado de salto
		jumpState = JUMP_STATE.GROUNDED;
		timeJumping = 0;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		updateMotionY(deltaTime);
	}
	
	/**
	 * Actualiza el movimiento sobre el eje y
	 * 
	 * @param deltaTime
	 */
	protected void updateMotionY(float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			// Decrementa el tiempo de espera para saltar
			delay -= deltaTime;
			if (delay <= 0) {
				setAnimation(animationSelected.get(0));
				delay = DELAY_TIME;
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		case JUMP_RISING:
			// Establece la animación de salto inicial si está tocando el suelo
			if (timeJumping == 0)
				setAnimation(animationSelected.get(0));

			timeJumping += deltaTime;
			// Queda tiempo de salto?
			if (timeJumping <= JUMP_TIME_MAX) {
				// Sigue saltando
				body.setLinearVelocity(velocity.x,
						Constants.ENEMY_JUMPER_MAX_VELOCITY.y);
			}
			else {
				jumpState = JUMP_STATE.JUMP_FALLING;
			}

			break;
		case JUMP_FALLING:
			timeJumping += deltaTime;
			// Si ha llegado a la máxima altura de salto,
			// establece la animación
			// de caída del salto
			if (timeJumping >= JUMP_TIME_MAX)
				setAnimation(animationSelected.get(0));
		}
		
		if (jumpState != JUMP_STATE.GROUNDED) {
			velocity = body.getLinearVelocity();
			// Asegura que la velocidad no exceda del máximo establecido
			velocity.y = MathUtils.clamp(velocity.y,
					-Constants.ENEMY_JUMPER_MAX_VELOCITY.y,
					Constants.ENEMY_JUMPER_MAX_VELOCITY.y);
		}
	}
	
	/**
	 * El enemigo ha tocado el suelo
	 */
	public void landed() {
		jumpState = JUMP_STATE.GROUNDED;
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