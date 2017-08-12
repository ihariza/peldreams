package com.nha.pelsdreams.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nha.pelsdreams.game.objects.AbstractGameObject;

/**
 * Almacena el valor actual de posición y zoom para la cámara. Además, puede
 * seguir a un objeto al mismo tiempo cuando se establece como objetivo llamando
 * a setTarget().
 * 
 * @author Nacho Herrera
 * 
 */
public class CameraHelper {

//	private static final String TAG = CameraHelper.class.getName();

	private Vector2 position;
	private float zoom;
	private AbstractGameObject target;

	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}

	/**
	 * Actualiza la posición de la cámara.
	 * 
	 * @param deltaTime
	 *            El tiempo transcurrido desde la útima actualización.
	 */
	public void update(float deltaTime) {
		if (!hasTarget())
			return;
		
		// Posición de la cámara del target en el suelo verticalmente
		// y a la izquierda horizontalmente
		position.x = (target.position.x + target.origin.x + Constants.VIEWPORT_WIDTH / 2 + 0.5f);
		//position.y = target.position.y + target.origin.y + Constants.VIEWPORT_HEIGHT / 2 - Constants.PIXEL_TO_WORLD_DIMENSION.y;
		// Evita que la cámara siga al runner cuando ha caído más de la
		// distancia establecida.
		//position.y = Math.max(-Constants.CAMERA_DISTANCE_FOLLOW, position.y);
		// La cámara no sigue al runner en el eje y (cuando salta)
		position.y = Constants.VIEWPORT_HEIGHT / 2 + 1;
	}

	/**
	 * establece la posición de la cámara
	 * 
	 * @param x
	 *            la posición en el eje x
	 * @param y
	 *            la posición en el eje y
	 */
	public void setPosition(float x, float y) {
		this.position.set(x , y);
	}

	/**
	 * Obtiene la posición de la cámara
	 * 
	 * @return La posición en el eje x e y
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Mueve la cámara
	 * 
	 * @param x
	 *            La posición en el eje x
	 * @param y
	 *            La posición en el eje y
	 */
	public void moveCamera(float x, float y) {
		x += position.x;
		y += position.y;
		setPosition(x, y);
	}

	/**
	 * Añade zoom a la cámara
	 * 
	 * @param amount
	 *            Cantidad de zoom a agregar.
	 */
	public void addZoom(float amount) {
		setZoom(zoom + amount);
	}

	/**
	 * Establece el zoom de la cámara dentro de un mínimo y máximo establecidos.
	 * 
	 * @param zoom
	 *            El zoom a establecer.
	 */
	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, Constants.MAX_ZOOM_IN,
				Constants.MAX_ZOOM_OUT);
	}

	/**
	 * Obtiene el zoom de la cámara
	 * 
	 * @return El zoom establecido.
	 */
	public float getZoom() {
		return zoom;
	}

	/**
	 * Establece el objetivo.
	 * 
	 * @param target
	 *            El objetivo a seguir. null para dejar de seguir al objetivo.
	 */
	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	/**
	 * Obtiene el último objetivo establecido.
	 * 
	 * @return El objetivo.
	 */
	public AbstractGameObject getTarget() {
		return target;
	}

	/**
	 * Comprueba si existe un objetivo establecido.
	 * 
	 * @return true si existe un objetivo establecido, false en caso contrario.
	 */
	public boolean hasTarget() {
		return target != null;
	}

	/**
	 * Comprueba si existe un objetivo concreto establecido.
	 * 
	 * @param target
	 *            El objetivo a comprobar.
	 * @return true si el objetivo está establecido, false en caso contrario
	 */
	public boolean hasTarget(AbstractGameObject target) {
		return hasTarget() && this.target.equals(target);
	}

	/**
	 * Actualiza los atributos de la cámara. Debe ser llamado al comienzo del
	 * renderizado de cada frame.
	 * 
	 * @param camera
	 *            La cámara a actualizar.
	 */
	public void applyTo(OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}
