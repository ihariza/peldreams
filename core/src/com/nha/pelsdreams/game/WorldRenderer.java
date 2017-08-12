package com.nha.pelsdreams.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.nha.pelsdreams.utils.Constants;

/**
 * Establece la lógica de renderizado del juego
 * @author Nacho Herrera
 *
 */
public class WorldRenderer implements Disposable {

	//private static final String TAG = WorldRenderer.class.getName();

	public OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	private Box2DDebugRenderer b2debugRenderer;

	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	/**
	 * Inicializa los objetos.
	 */
	public void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		// Debug para la física
		b2debugRenderer = new Box2DDebugRenderer();
	}

	/**
	 * Dibuja el mundo del juego
	 */
	public void render() {
		// Aplica la cameraHelper a la cámara principal
		// del juego.
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		if (Constants.DEBUG_DRAW_BOX2D_WORLD) {
			b2debugRenderer.render(worldController.b2world, camera.combined);
		}
	}
	
	/**
	 * Redimensiona el tamaño de la pantalla manteniendo las proporciones.
	 * 
	 * @param width
	 *            El ancho en pixeles.
	 * @param height
	 *            El alto en pixeles.
	 */
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}

	/**
	 * Libera todos los recursos de este objeto.
	 */
	@Override
	public void dispose() {
		batch.dispose();
		if (b2debugRenderer != null)
			b2debugRenderer.dispose();
	}
}
