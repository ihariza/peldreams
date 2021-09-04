package com.nha.pelsdreams.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nha.pelsdreams.enums.UserDataType;

/**
 * Representa un objeto en el mundo del juego.
 * 
 * @author Nacho Herrera
 * 
 */
public abstract class AbstractGameObject {

	// Cuerpo box2d del objeto
	public Body body;
	// Tipo de objeto
	// Usado identificar comparar qué cuerpos son los que colisionan
	public UserDataType userDataType;
	// Posición actual del objeto
	public Vector2 position;
	// Dimensión del objeto
	public Vector2 dimension;
	// Posición de origen del objeto
	public Vector2 origin;
	// Escala del objeto
	public Vector2 scale;
	// Rotación del objeto
	public float rotation;
	// Rectángulo que delimita el objeto, describiendo el cuerpo físico que será
	// utilizado para la detección de colisiones con otros objetos.
	// Los límites del rectángulo se pueden establecer a cualquier tamaño y es
	// completamente independiente de la dimensión real del objeto en el mundo
	// del juego
	public Rectangle bounds;
	public float stateTime;
	public float stateTimeEffect;
	public Animation<TextureRegion> animation;
	public Animation<TextureRegion> animationEffect;
	// Posición donde se produce el efecto
	public Vector2 effectPosition;

	public AbstractGameObject() {
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		bounds = new Rectangle();
	}

	/**
	 * Actualiza la posición del objeto en el eje x e y
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		stateTime += deltaTime;
		stateTimeEffect += deltaTime;

		// Actualiza la nueva posición
		if (body != null) {
			position.x = body.getPosition().x;
			position.y = body.getPosition().y;
		}
	}

	/**
	 * Renderiza el objeto
	 * 
	 * @param batch
	 */
	public abstract void render(SpriteBatch batch);

	/**
	 * Establece la animación del objeto
	 * 
	 * @param animation
	 */
	public void setAnimation(Animation<TextureRegion> animation) {
		this.animation = animation;
		stateTime = 0;
	}
	
	/**
	 * Establece la animación effecto del objeto
	 * 
	 * @param animationEffect
	 */
	public void setAnimationEffect(Animation<TextureRegion> animationEffect) {
		this.animationEffect = animationEffect;
		effectPosition = new Vector2(position.x, position.y);
		stateTimeEffect = 0;
	}
	
	public void dispose() {
	}
	
}
