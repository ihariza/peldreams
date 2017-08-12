package com.nha.pelsdreams.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.nha.pelsdreams.game.objects.EnemyHorizontal;
import com.nha.pelsdreams.game.objects.EnemyJumper;
import com.nha.pelsdreams.game.objects.Item;
import com.nha.pelsdreams.game.objects.MainItem;
import com.nha.pelsdreams.game.objects.PowerUp;
import com.nha.pelsdreams.screens.DirectedGame;
import com.nha.pelsdreams.utils.CameraHelper;
import com.nha.pelsdreams.utils.Constants;
import com.nha.pelsdreams.utils.SaveGameHelper.GameState;
import com.nha.pelsdreams.utils.WorldUtils;

/**
 * Contiene toda la l�gica para inicializar y modificar el mundo del juego.
 * 
 * @author Nacho Herrera
 * 
 */
public class WorldController extends InputAdapter implements ContactListener,
		Disposable {

	private static final String TAG = WorldController.class.getName();
	private final float STEP_TIME = 1f/60f;
	private float accumulator = 0;

	// Almacena la referencia del juego
	// para cambiar entre las pantallas
	private DirectedGame game;
	// Estado del mundo y sus niveles
	private GameState gameState;
	// Divide la pantalla en 2 para controlar las pulsaciones
	// en un lado de la pantalla o en otro
	private Rectangle screenLeftSide;
	private Rectangle screenRightSide;
	// Establece el fin de juego
	public boolean isGameOver;
	// Nivel completado
	public boolean isLevelCompleted;
	// Nivel no completado
	public boolean isLevelNotCompleted;

	// F�sica del mundo
	public World b2world;

	public CameraHelper cameraHelper;

	public LevelCreator level;
	public int lives;
	// Items recolectados
	public int mainItems;
	public int items;

	public WorldController(DirectedGame game, GameState gameState) {
		this.game = game;
		this.gameState = gameState;
		cameraHelper = new CameraHelper();
	}

	/**
	 * Inicializa el nivel inicial
	 */
	public void initLevel(LevelCreator levelCreator) {
		items = 0;
		mainItems = 0;
		lives = Constants.LIVES_START;
		// Carga el nivel seg�n el mundo y nivel seleccionado
		this.level = levelCreator;
		isGameOver = false;
		isLevelCompleted = false;
		isLevelNotCompleted = false;
		cameraHelper.setTarget(levelCreator.runner);
		initPhysics();
		setupTouchControlAreas();
	}

	/**
	 * Establece el fin de juego
	 */
	public void setGameOver() {
		level.runner.deadByEnemy();
		cameraHelper.setTarget(null);
		lives -= 1;
	}

	/**
	 * Inicializa la f�sica del mundo del juego
	 */
	private void initPhysics() {
		if (b2world != null)
			b2world.dispose();
		b2world = WorldUtils.createWorld();
		b2world.setContactListener(this);
		WorldUtils.createRunner(b2world, level);
		WorldUtils.createEnemyHorizontal(b2world, level);
		WorldUtils.createEnemyJumpers(b2world, level);
		WorldUtils.createEnemyStatic(b2world, level);
		WorldUtils.createGround(b2world, level);
		WorldUtils.createPlatforms(b2world, level);
		WorldUtils.createMainItems(b2world, level);
		WorldUtils.createItems(b2world, level);
		WorldUtils.createPowerUps(b2world, level);
		WorldUtils.createGoal(b2world, level);
	}
	
	/**
	 * �reas de toque para los controles de saltar y agacharse
	 */
	private void setupTouchControlAreas() {
		screenLeftSide = new Rectangle(0, 0, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight());
		screenRightSide = new Rectangle(Gdx.graphics.getWidth() / 2, 0,
				Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
	}

	/**
	 * Actualiza todos los objetos que lo necesitan en funci�n del tiempo.
	 * 
	 * @param deltaTime
	 *            El tiempo transcurrido desde la �tima actualizaci�n.
	 */
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		// Game over
		if (lives < 0) {
			isGameOver = true;
		} else {
            // Si no es gameover, actualiza el input processor
			handleInputGame();
		}

		if (level.goal.isReached) {
			isGameOver = false;
			// Ha conseguido los objetivos?
			if (mainItems >= level.mainItemsObjetive
					&& items >= level.itemsObjetive) {
				isLevelCompleted = true;
				// Establece el gameState en el gameScreen
				game.getCurrScreen().setGameState(gameState);
			} else {
				isLevelNotCompleted = true;
			}
		}

		level.update(deltaTime);

		doPhysicsStep(deltaTime);
		
		cameraHelper.update(deltaTime);

		// Actualiza la posici�n del background
		level.background.updateScrollPosition(cameraHelper.getPosition());
	}

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= STEP_TIME) {
            b2world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }

	/**
	 * Comprueba si el runner ha salido de la pantalla en el eje x.
	 * 
	 * @return True si ha salido de la pantalla, false en caso contrario.
	 */
	public boolean isPlayerOutOfBounds() {
		return level.runner.position.x > level.goal.position.x
				+ Constants.VIEWPORT_WIDTH;
	}

	/**
	 * Controlador de entrada de la c�mara debugger. S�lo para desktop
	 * 
	 * @param deltaTime
	 *            El tiempo transcurrido desde la �tima actualizaci�n.
	 */
	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		if (!cameraHelper.hasTarget(level.runner)) {
			// Control de movimiento de la c�mara
			float camMoveSpeed = 5 * deltaTime;
			// Factor de aceleraci�n
			// 1 equivale a la velocidad normal
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_ACCELERATION))
				camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_LEFT))
				cameraHelper.moveCamera(-camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_RIGHT))
				cameraHelper.moveCamera(camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_UP))
				cameraHelper.moveCamera(0, camMoveSpeed);
			if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_DOWN))
				cameraHelper.moveCamera(0, -camMoveSpeed);
			// Resetea la posici�n de la c�mara al origen
			if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_RESET))
				cameraHelper.setPosition(0, 0);
		}
		// Control de zoom de la c�mara
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		// Acelera el zoom al pulsarla junto con la
		// tecla de aumento o disminuci�n del zoom
		if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_ACCELERATION))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		// Aumenta el zoom
		if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_ADD_ZOOM))
			cameraHelper.addZoom(camZoomSpeed);
		// Disminuye el zoom
		if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_REDUCE_ZOOM))
			cameraHelper.addZoom(-camZoomSpeed);
		// Resetea el zoom
		if (Gdx.input.isKeyPressed(Constants.CAMERA_KEY_RESET_ZOOM))
			cameraHelper.setZoom(1);
	}

	/**
	 * Handler para controles del personaje
	 */
	private void handleInputGame() {
		// Establece el estado del runner (corriendo, saltando o esquivando)
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.UP)
				|| Gdx.input.isKeyPressed(Keys.DOWN)) {
			if (Gdx.input.isKeyPressed(Keys.UP)
					|| (Gdx.input.isTouched(0) && screenRightSide.contains(
							Gdx.input.getX(0), Gdx.input.getY(0)))
                    || (Gdx.input.isTouched(1) && screenRightSide.contains(
                    Gdx.input.getX(1), Gdx.input.getY(1)))) {
				level.runner.setState(true, false);
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)
					|| (Gdx.input.isTouched(0) && screenLeftSide.contains(
							Gdx.input.getX(), Gdx.input.getY()))) {
				level.runner.setState(false, true);
			}
		} else {
			level.runner.setState(false, false);
		}
	}

    /**
	 * M�todo llamado cuando una tecla es levantada.
	 */
	@Override
	public boolean keyUp(int keycode) {
		// Cambia el target de la c�mara
		if (keycode == Constants.CAMERA_KEY_TOGGLE) {
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null
					: level.runner);
			Gdx.app.debug(TAG, "Seguimiento de la c�mara habilitado: "
					+ cameraHelper.hasTarget());
			return true;
		}
		// Muestra la screen de pausa
		else if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			if (!game.getCurrScreen().isPaused()) {
				game.getCurrScreen().pause();
				return true;
			} else
				game.getCurrScreen().resume();
			return true;
		}
		return false;
	}

    /**
	 * M�todo llamado cuando comienza el contacto de dos cuerpos
	 */
	@Override
	public void beginContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();

		// Runner con enemigo est�tico
		if (WorldUtils.bodyIsRunner(a)
				&& WorldUtils.bodyIsEnemyStatic(b)
				|| (WorldUtils.bodyIsEnemyStatic(a) && WorldUtils
						.bodyIsRunner(b))) {
			if (!Constants.DEBUG_RUNNER_GODMODE) {
                contact.setEnabled(false);
				setGameOver();
			} else {
                level.runner.landed();
            }

			/** Runner con enemigo Horizontal **/
		} else if (WorldUtils.bodyIsRunner(a)
				&& WorldUtils.bodyIsEnemyHorizontal(b)
				|| (WorldUtils.bodyIsEnemyHorizontal(a) && WorldUtils
						.bodyIsRunner(b))) {
			// Si es el sensor del enemigo, lo lanza.
			if (a.getUserData() instanceof EnemyHorizontal
					&& contact.getFixtureA().isSensor())
				((EnemyHorizontal) a.getUserData()).setLaunch();
			else if (b.getUserData() instanceof EnemyHorizontal
					&& contact.getFixtureB().isSensor())
				((EnemyHorizontal) b.getUserData()).setLaunch();
			// Comprueba que no sea el contacto entre la parte de arriba o
			// atr�s del enemigo horizontal y el runner
			else if (contact.getWorldManifold().getNormal().y > -0.99f) {
				// El runner muere, si el enemigo no es el sensor o est� en modo
				// GOD
				if (!Constants.DEBUG_RUNNER_GODMODE) {
					contact.setEnabled(false);
					setGameOver();
				}
			} else {
				level.runner.landed();
			}
//			Gdx.app.debug(TAG,
//					Float.toString(contact.getWorldManifold().getNormal().y));
			/** Runner con enemigo saltador **/
		} else if (WorldUtils.bodyIsRunner(a)
				&& WorldUtils.bodyIsEnemyJumper(b)
				|| (WorldUtils.bodyIsEnemyJumper(a) && WorldUtils
						.bodyIsRunner(b))) {
			// Si es el sensor del enemigo, lo lanza.
			if (a.getUserData() instanceof EnemyJumper
					&& contact.getFixtureA().isSensor())
				((EnemyJumper) a.getUserData()).landed();
			else if (b.getUserData() instanceof EnemyJumper
					&& contact.getFixtureB().isSensor())
				((EnemyJumper) b.getUserData()).landed();
			// El runner muere, si el enemigo no es el sensor o est� en modo GOD
			else if (!Constants.DEBUG_RUNNER_GODMODE) {
				contact.setEnabled(false);
				setGameOver();
			}

			/** Runner con plataforma o suelo **/
		} else if ((WorldUtils.bodyIsRunner(a) && (WorldUtils.bodyIsPlatform(b) || WorldUtils
				.bodyIsGround(b)))
				|| ((WorldUtils.bodyIsPlatform(a) || WorldUtils.bodyIsGround(b)) && WorldUtils
						.bodyIsRunner(b))) {
			// Gdx.app.debug(TAG, contact.getWorldManifold().getNormal()
			// .toString());
			// Si choca por el lado o por debajo, el runner muere
			if ((contact.getWorldManifold().getNormal().x >= 0.98f && contact
					.getWorldManifold().getNormal().x <= 1.0f)
					|| (contact.getWorldManifold().getNormal().y >= 0.98f)
					&& (contact.getWorldManifold().getNormal().y <= 1.0f)) {
				if (!Constants.DEBUG_RUNNER_GODMODE) {
					level.runner.deadByRock();
					lives -= 1;
				}
			} else {
				level.runner.landed();
			}

			/** Runner con main item **/
		} else if ((WorldUtils.bodyIsRunner(a) && WorldUtils.bodyIsMainItem(b))
				|| (WorldUtils.bodyIsMainItem(a) && WorldUtils.bodyIsRunner(b))) {

			MainItem mainItem;
			// Identifica que cuerpo es el item y lo obtiene del cuerpo
			if (WorldUtils.bodyIsMainItem(a))
				mainItem = (MainItem) a.getUserData();
			else
				mainItem = (MainItem) b.getUserData();
			// Si no est� recolectado, se recolecta.
			if (!mainItem.collected) {
				mainItem.hit();
				mainItems += 1;
				Gdx.app.debug(TAG, "Item recolectado.");
			}
			/** Runner con item **/
		} else if ((WorldUtils.bodyIsRunner(a) && WorldUtils.bodyIsItem(b))
				|| (WorldUtils.bodyIsItem(a) && WorldUtils.bodyIsRunner(b))) {

			Item item;
			// Identifica que cuerpo es el item y lo obtiene del cuerpo
			if (WorldUtils.bodyIsItem(a))
				item = (Item) a.getUserData();
			else
				item = (Item) b.getUserData();
			// Si no est� recolectado, se recolecta.
			if (!item.collected) {
				item.hit();
				items += 1;
			}
			/** Runner con powerUp **/
		} else if ((WorldUtils.bodyIsRunner(a) && WorldUtils.bodyIsPowerUp(b))
				|| (WorldUtils.bodyIsPowerUp(a) && WorldUtils.bodyIsRunner(b))) {

			PowerUp powerUp;
			// Identifica que cuerpo es la item y lo obtiene del cuerpo
			if (WorldUtils.bodyIsItem(a))
				powerUp = (PowerUp) a.getUserData();
			else
				powerUp = (PowerUp) b.getUserData();

			if (!powerUp.collected) {
				powerUp.collected = true;
				level.runner.setPowerup(true);
				Gdx.app.debug(TAG, "PowerUp recolectado.");
			}
			/** Runner con meta **/
		} else if ((WorldUtils.bodyIsRunner(a) && WorldUtils.bodyIsGoal(b))
				|| (WorldUtils.bodyIsGoal(a) && WorldUtils.bodyIsRunner(b))) {
			// Al llegar a la meta la c�mara deja de seguir al runner.
			if (!level.goal.isReached) {
				cameraHelper.setTarget(null);
				level.goal.reached();
				level.goalParticles.start();
			}
		}
	}

	/**
	 * M�todo llamado cuando termina el contacto de dos cuerpos
	 */
	@Override
	public void endContact(Contact contact) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();

		// Si no est� tocando suelo, desactiva el efecto de part�culas
		if ((WorldUtils.bodyIsRunner(a) && WorldUtils.bodyIsGround(b))
				|| (WorldUtils.bodyIsGround(a) && WorldUtils.bodyIsRunner(b))) {
			// Gdx.app.debug(TAG, contact.getWorldManifold().getNormal()
			// .toString());

			level.runner.dustParticles.allowCompletion();
		}

		// Desactiva el efecto de part�culas del item recolectado
		if ((WorldUtils.bodyIsRunner(a) && WorldUtils.bodyIsItem(b))
				|| (WorldUtils.bodyIsItem(a) && WorldUtils.bodyIsRunner(b))) {
			// Gdx.app.debug(TAG, contact.getWorldManifold().getNormal()
			// .toString());
			Item item;
			// Identifica que cuerpo es el item y lo obtiene del cuerpo
			if (WorldUtils.bodyIsItem(a))
				item = (Item) a.getUserData();
			else
				item = (Item) b.getUserData();
			// Detiene el efecto de part�culas
			item.particleEffect.allowCompletion();
		}
	}

	/**
	 * M�todo llamado antes del contacto de dos cuerpos
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Body a = contact.getFixtureA().getBody();
		Body b = contact.getFixtureB().getBody();

		// Runner con plataforma
		if (WorldUtils.bodyIsRunner(a) && (WorldUtils.bodyIsPlatform(b))
				|| (WorldUtils.bodyIsPlatform(a) && WorldUtils.bodyIsRunner(b)))
			// Si el runner golpea por debajo de la plataforma, desactiva el
			// contacto para evitar el efecto rebote al no caber el cuerpo del
			// runner entre la plataforma y el suelo
			if (contact.getWorldManifold().getNormal().y >= 0.98f
					&& contact.getWorldManifold().getNormal().y <= 1.0f) {
				contact.setEnabled(false);
			}

		// Permite que el enemigo horizontal est� situado detr�s de la
		// plataforma hasta que es lanzado
		if (WorldUtils.bodyIsEnemyHorizontal(a)
				&& (WorldUtils.bodyIsPlatform(b))
				|| (WorldUtils.bodyIsPlatform(a) && WorldUtils
						.bodyIsEnemyHorizontal(b))) {
			// Gdx.app.debug(TAG, contact.getWorldManifold().getNormal()
			// .toString());
			if (contact.getWorldManifold().getNormal().x == 0f) {
				contact.setEnabled(false);
			}
		}

	}

	/**
	 * M�todo llamado despu�s del contacto de dos cuerpos
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	@Override
	public void dispose() {
		if (b2world != null)
			b2world.dispose();
		level.dispose();
	}
}
