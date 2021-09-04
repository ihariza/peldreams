package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.nha.pelsdreams.enums.StateType;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa el personaje controlado por el jugador
 * 
 * @author Nacho Herrera
 * 
 */
public class Runner extends AbstractGameObject {

	public static final String TAG = Runner.class.getName();

	private final float JUMP_TIME_MAX = 0.35f;
	private final float JUMP_TIME_MIN = 0.15f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.015f;

	private Animation<TextureRegion> animRunning;
	private Animation<TextureRegion> animJump;
	private Animation<TextureRegion> animJumpRising;
	private Animation<TextureRegion> animJumpFalling;
	private Animation<TextureRegion> animDeadRock;
	private Animation<TextureRegion> animDeadEnemy;
	private Animation<TextureRegion> animDeadEnemyEnd;
	private Animation<TextureRegion> animDodgingReversed;
	private Animation<TextureRegion> animDodging;
	private Animation<TextureRegion> animDodged;
	private Vector2 velocity;
	private Vector2 originDodge;
	public StateType state;
	private float timeJumping;
	private boolean hasPowerup;
	public float timeLeftPowerup;
	public Boolean isDeadRock;
	public Boolean isDeadEnemy;
	public ParticleEffect dustParticles;
	public ParticleEffect fallParticles;
	// Tiempo para el control de la animaci�n de muerte por enemigo
	private float deadEnemyTimeAnimation;

	public Runner() {
		init();
	}

	private void init() {
		// Tipo de objeto
		userDataType = UserDataType.RUNNER;
		animRunning = AssetsWorlds.instance.runner.anim_run;
		animJump = AssetsWorlds.instance.runner.anim_jump;
		animJumpRising = AssetsWorlds.instance.runner.anim_jump_rising;
		animJumpFalling = AssetsWorlds.instance.runner.anim_jump_falling;
		animDeadRock = AssetsWorlds.instance.runner.anim_dead_rock;
		animDeadEnemy = AssetsWorlds.instance.runner.anim_dead_enemy;
		animDeadEnemyEnd = AssetsWorlds.instance.runner.anim_dead_enemy_end;
		animDeadEnemy = AssetsWorlds.instance.runner.anim_jump_falling;
		animDodging = AssetsWorlds.instance.runner.anim_dodging;
		animDodgingReversed = AssetsWorlds.instance.runner.anim_dodgingReversed;
		animDodged = AssetsWorlds.instance.runner.anim_dodged;
		setAnimation(animRunning);
		dimension.set(animRunning.getKeyFrame(0).getRegionWidth()
				* Constants.WORLD_TO_BOX,
				animRunning.getKeyFrame(0).getRegionHeight()
						* Constants.WORLD_TO_BOX);
		// Establece el origen en el centro del personaje
		origin.set(dimension.x / 2, dimension.y / 2);
		// Establece el origen abajo cuando el personaje est� esquivando
		originDodge = new Vector2(dimension.x / 2, dimension.y / 4);
		// Establece los l�mites para la detecci�n de colisiones
		bounds.set(0, 0, dimension.x, dimension.y);
		// Estado de salto
		state = StateType.GROUNDED;
		timeJumping = 0;
		// Power-ups
		hasPowerup = false;
		timeLeftPowerup = 0;
		// Part�culas
		dustParticles = AssetsWorlds.instance.runner.dustParticles;
		fallParticles = AssetsWorlds.instance.runner.fallParticles;
		isDeadRock = false;
		isDeadEnemy = false;
		deadEnemyTimeAnimation = 0;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		velocity = body.getLinearVelocity();

		// Establece una velocidad fija en el eje x
		body.setLinearVelocity(Constants.RUNNER_MAX_VELOCITY.x, velocity.y);

		// Actualiza el movimiento en el eje y si no ha muerto
		if (!isDeadEnemy && !isDeadRock)
			updateMotionY(deltaTime);

		// Actualiza el estado de las animaciones si ha muerto
		updateDeadState(deltaTime);

		// Si tiene power-ups habilitado, va restando el tiempo hasta que llegue
		// a cero.
		if (timeLeftPowerup > 0) {
			timeLeftPowerup -= deltaTime;
			if (timeLeftPowerup < 0) {
				// Deshabilita el power-up
				timeLeftPowerup = 0;
				setPowerup(false);
			}
		}

		dustParticles.update(deltaTime);
		fallParticles.update(deltaTime);

		// Establece la dimension del frame correspondiente
		dimension.set(animation.getKeyFrame(stateTime).getRegionWidth()
				* Constants.WORLD_TO_BOX, animation.getKeyFrame(stateTime)
				.getRegionHeight() * Constants.WORLD_TO_BOX);
	}

	/**
	 * Actualiza el movimiento sobre el eje y
	 * 
	 * @param deltaTime
	 */
	private void updateMotionY(float deltaTime) {
		switch (state) {
		case GROUNDED:
			if (animation == animDodged) {
				setAnimation(animDodgingReversed);
			}
			// Si toca el suelo, establece la animaci�n de correr
			if (animation != animRunning
					&& animation.isAnimationFinished(stateTime)) {
				setAnimation(animRunning);
			}
			break;
		case JUMP_RISING:
			// Establece la animaci�n salto inicial si est� tocando el suelo
			if (timeJumping == 0) {
				setAnimation(animJump);
			}

			timeJumping += deltaTime;
			// Queda tiempo de salto?
			if (timeJumping <= JUMP_TIME_MAX) {
				// Sigue saltando
				body.setLinearVelocity(velocity.x,
						Constants.RUNNER_MAX_VELOCITY.y);
			}

			break;
		case JUMP_FALLING:
			timeJumping += deltaTime;
			// Si se hizo un toque corto en la pantalla, salta a la m�nima
			// altura
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
				// sigue saltando
				body.setLinearVelocity(velocity.x,
						Constants.RUNNER_MAX_VELOCITY.y);
			}
			// Si ha llegado a la m�xima altura de salto,
			// establece la animaci�n
			// de ca�da del salto
			if (timeJumping >= JUMP_TIME_MAX)
				setAnimation(animJumpFalling);
			break;
		case DODGED:
			if (animation != animDodging && animation != animDodged) {
				setAnimation(animDodging);
			}
			if (animation != animDodged && animation == animDodging
					&& animation.isAnimationFinished(stateTime)) {
				setAnimation(animDodged);
			}
			if (velocity.x != 0) {
				dustParticles.setPosition(position.x + dimension.x / 2 + 0.1f,
						position.y + 0.1f);
				dustParticles.start();
			}
			break;
		}

		if (state != StateType.GROUNDED) {
			velocity = body.getLinearVelocity();
			// Asegura que la velocidad no exceda del m�ximo establecido
			velocity.y = MathUtils.clamp(velocity.y,
					-Constants.RUNNER_MAX_VELOCITY.y,
					Constants.RUNNER_MAX_VELOCITY.y);
		}
		// Si ha terminado la animaci�n de salto, establece la de subida en
		// salto
		if (animation == animJump) {
			if (animation.isAnimationFinished(stateTime)) {
				setAnimation(animJumpRising);
			}
		}

		// Establece la animaci�n de ca�da de salto en cuanto llega al tiempo
		// m�ximo de salto. Si se establece dentro del case JUMP_FALLING, se
		// establece
		// dicha animaci�n siempre que no se pulse la tecla de salto.
		// if (timeJumping >= JUMP_TIME_MAX && animation != animJumpFalling
		// && jumpState != JUMP_STATE.GROUNDED)
		// setAnimation(animJumpFalling);
	}

	/**
	 * Establece el estado del salto
	 * 
	 * @param jumpKeyPressed
	 *            True si la tecla est� pulsada, false en caso contrario.
	 */
	public void setState(boolean jumpKeyPressed, boolean dodgeKeyPressed) {
		switch (state) {
		case GROUNDED: // Tocando el suelo
			AudioManager.instance.stop(AssetsWorlds.instance.sounds.dodging);
			if (jumpKeyPressed) {
				AudioManager.instance.play(AssetsWorlds.instance.sounds.jump);
				// Comienza a contar el tiempo de salto
				timeJumping = 0;
				state = StateType.JUMP_RISING;

			} else if (dodgeKeyPressed) {
				state = StateType.DODGED;
				AudioManager.instance.play(AssetsWorlds.instance.sounds.dodging);
				// Establece la forma del cuerpo para el modo esquivar
				((PolygonShape) (body.getFixtureList().first()).getShape())
						.setAsBox(dimension.x / 2 - 0.2f, dimension.y / 4,
								originDodge, 0);
			}
			break;
		case JUMP_RISING: // Subiendo en el salto
			if (!jumpKeyPressed) {
				state = StateType.JUMP_FALLING;
			}
			break;
		case JUMP_FALLING: // Cayendo del salto
			if (jumpKeyPressed && hasPowerup) {
				// AudioManager.instance.play(Assets.instance.sounds.jumpWithFeather,
				// 1, MathUtils.random(1.0f, 1.1f));
				timeJumping = JUMP_TIME_OFFSET_FLYING;
				state = StateType.JUMP_RISING;
			}
			break;
		case DODGED:
			if (!dodgeKeyPressed) {
				// Establece la forma del cuerpo para el modo normal
				((PolygonShape) (body.getFixtureList().first()).getShape())
						.setAsBox(dimension.x / 2 - 0.28f,
								dimension.y / 2 + 0.08f, origin, 0);

				state = StateType.GROUNDED;
			}
			break;
		}
	}

	/**
	 * Actualiza el estado de las animaciones para las muertes
	 */
	private void updateDeadState(float deltaTime) {
		// Si muere por roca
		if (isDeadRock && !isDeadEnemy) {
			// Establece la animaci�n de muerte por roca
			if (animation != animDeadRock) {
				setAnimation(animDeadRock);
			}
			body.setLinearVelocity(0, 0);
			body.setGravityScale(3);
		}

		// Si muere por un enemigo
		if (isDeadEnemy && !isDeadRock) {
			// Establece la animaci�n de muerte por enemigo
			if (animation != animDeadEnemy && animation != animDeadEnemyEnd) {
				setAnimation(animDeadEnemy);
			}
			// Establece la animaci�n de muerte final si ha terminado la
			// anterior
			if (animation == animDeadEnemy
					&& animation.isAnimationFinished(stateTime)) {
				animation = animDeadEnemyEnd;
			}
			setGameOverEnemyAnimation(deltaTime);
		}
	}

	public void deadByRock() {
		AudioManager.instance.play(AssetsWorlds.instance.sounds.liveLostWall);
		dustParticles.allowCompletion();
		isDeadRock = true;
	}

	public void deadByEnemy() {
		AudioManager.instance.play(AssetsWorlds.instance.sounds.snake_dead);
		dustParticles.allowCompletion();
		isDeadEnemy = true;
	}

	/**
	 * El runner ha tocado el suelo
	 */
	public void landed() {
		fallParticles.setPosition(position.x + dimension.x / 2 + 0.2f,
				position.y);
		fallParticles.start();
		state = StateType.GROUNDED;
	}

	/**
	 * Establece la animaci�n de movimiento al runner en la animaci�n final
	 * cuando muere por un enemigo. (Sale disparado por el aire)
	 */
	private void setGameOverEnemyAnimation(float deltaTime) {
		deadEnemyTimeAnimation += deltaTime;
		body.setFixedRotation(false);
		body.setLinearVelocity(0, 2.2f);
		body.getFixtureList().first().setSensor(true);
		if (deadEnemyTimeAnimation >= 0.2f && deadEnemyTimeAnimation < 1.2f)
			if (body.getLinearVelocity().x <= 2.5)
				body.applyForce(0.2f, 0f, 0, 0, true);
		if (deadEnemyTimeAnimation >= 1.2f && deadEnemyTimeAnimation < 1.4f) {
			body.setFixedRotation(true);
			if (body.getLinearVelocity().x <= 9.5)
				body.applyLinearImpulse(8.5f, 2.5f, 0, 0, true);
		}
		if (deadEnemyTimeAnimation >= 1.4f) {
			body.setFixedRotation(true);
			if (body.getLinearVelocity().x <= 12.5)
				body.applyLinearImpulse(12.5f, 5f, 0, 0, true);
		}
	}

	/**
	 * Establece el estado del power-up
	 * 
	 * @param pickedUp
	 *            True para habilitarlo, false para deshabilitarlo
	 */
	public void setPowerup(boolean pickedUp) {
		hasPowerup = pickedUp;
		if (pickedUp) {
			timeLeftPowerup = Constants.ITEM_POWERUP_DURATION;
		}
	}

	/**
	 * Comprueba si el runner tiene un power-up
	 * 
	 * @return True si tiene un power-up, false en caso contrario.
	 */
	public boolean hasPowerup() {
		return hasPowerup && timeLeftPowerup > 0;
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		// Dibuja las part�culas
		dustParticles.draw(batch);

		// Establece un color especial cuando el runner tiene un power-up
		if (hasPowerup) {
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}

		// Dibuja la imagen
		if (animation != null) {
			reg = animation.getKeyFrame(stateTime, true);
			batch.draw(reg.getTexture(), position.x, position.y, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y,
					rotation, reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		}

		fallParticles.draw(batch);
		if (fallParticles.isComplete())
			fallParticles.allowCompletion();
	}
}