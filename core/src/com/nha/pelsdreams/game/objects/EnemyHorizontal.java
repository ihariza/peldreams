package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.AssetsWorlds;
import com.nha.pelsdreams.utils.AudioManager;
import com.nha.pelsdreams.utils.Constants;

/**
 * Representa un enemigo volador
 * 
 * @author Nacho Herrera
 * 
 */
public class EnemyHorizontal extends AbstractGameObject {

	public static final String TAG = EnemyHorizontal.class.getName();

	private Animation arrows;
	// Enemigo seleccionado aleatoriamente
	private Animation animationSelected;
	// Almacena los enemigos
	private Array<Animation> enemies;
	// Velocidad del enemigo
	private Vector2 velocity;
	private boolean isLaunched;

	public EnemyHorizontal() {
		init();
	}

	public void init() {
		// Tipo de objeto
		userDataType = UserDataType.ENEMY_HORIZONTAL;

		arrows = AssetsWorlds.instance.enemiesWorld001.arrows;
		enemies = new Array<Animation>();
		enemies.add(arrows);
		// Establece una animaci�n aleatoriamente
		animationSelected = enemies.get(MathUtils.random(0, enemies.size - 1));
		setAnimation(animationSelected);
		dimension.set(animationSelected.getKeyFrames()[0].getRegionWidth()
				* Constants.WORLD_TO_BOX,
				animationSelected.getKeyFrames()[0].getRegionHeight()
						* Constants.WORLD_TO_BOX);
		// Centra la imagen en el objeto
		origin.set(0, 0);
		// Establece los l�mites para la detecci�n de colisiones
		bounds.set(0, 0, dimension.x, dimension.y);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		velocity = body.getLinearVelocity();

		if (isLaunched)
			body.setLinearVelocity(Constants.ENEMY_HORIZONTAL_MAX_VELOCITY);

		// Asegura que la velocidad no exceda del m�ximo establecido
		velocity.x = MathUtils.clamp(velocity.x,
				Constants.ENEMY_HORIZONTAL_MAX_VELOCITY.x,
				-Constants.ENEMY_HORIZONTAL_MAX_VELOCITY.x);
	}

	/**
	 * Lanza al enemigo a una velocidad constante
	 */
	public void setLaunch() {
		AudioManager.instance.play(AssetsWorlds.instance.sounds.arrow);
		setAnimationEffect(AssetsWorlds.instance.effects.smokeLaunch);
		isLaunched = true;
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		TextureRegion regEffect = null;

		// Dibuja la imagen
		reg = animation.getKeyFrame(stateTime, true);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);

		// Dibuja el efecto de humo
		if (animationEffect != null) {
			if (!animationEffect.isAnimationFinished(stateTimeEffect)) {
				regEffect = animationEffect.getKeyFrame(stateTimeEffect, true);

				batch.draw(regEffect.getTexture(), effectPosition.x,
						effectPosition.y, origin.x, origin.y,
						regEffect.getRegionWidth() * Constants.WORLD_TO_BOX,
						regEffect.getRegionHeight() * Constants.WORLD_TO_BOX,
						scale.x, 0.6f, rotation, regEffect.getRegionX(),
						regEffect.getRegionY(), regEffect.getRegionWidth(),
						regEffect.getRegionHeight(), false, false);
			}
		}
	}

}