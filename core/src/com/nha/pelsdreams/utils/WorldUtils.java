package com.nha.pelsdreams.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nha.pelsdreams.enums.UserDataType;
import com.nha.pelsdreams.game.LevelCreator;
import com.nha.pelsdreams.game.objects.AbstractGameObject;
import com.nha.pelsdreams.game.objects.EnemyHorizontal;
import com.nha.pelsdreams.game.objects.EnemyJumper;
import com.nha.pelsdreams.game.objects.EnemyStatic;
import com.nha.pelsdreams.game.objects.Ground;
import com.nha.pelsdreams.game.objects.Item;
import com.nha.pelsdreams.game.objects.MainItem;
import com.nha.pelsdreams.game.objects.Platform;
import com.nha.pelsdreams.game.objects.PowerUp;

/**
 * Genera la física del juego
 * 
 * @author Ignacio Herrera Ariza
 * 
 */
public class WorldUtils {

	/**
	 * Genera la física del mundo
	 * 
	 * @return World El mundo
	 */
	public static World createWorld() {
		return new World(Constants.WORLD_GRAVITY, true);
	}

	/**
	 * Genera la física del corredor
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createRunner(World b2world, LevelCreator level) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(level.runner.position);
		Body body = b2world.createBody(bodyDef);
		level.runner.body = body;
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(Constants.RUNNER_WIDTH / 2 - 0.2f,
				Constants.RUNNER_HEIGHT / 2, level.runner.origin, 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.density = Constants.RUNNER_DENSITY;
		fixtureDef.friction = Constants.RUNNER_FRICTION;
		fixtureDef.restitution = Constants.RUNNER_RESTITUTION;
		body.createFixture(fixtureDef);
		// Asocia el objeto Runner al body
		body.setUserData(level.runner);
		// Evita que el cuerpo rote al colisionar
		body.setFixedRotation(true);
		body.setGravityScale(Constants.RUNNER_GRAVITY_SCALE);
		polygonShape.dispose();
	}

	/**
	 * Genera la física del enemigo con movimiento horizontal
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createEnemyHorizontal(World b2world, LevelCreator level) {
		Vector2 origin = new Vector2();
		Vector2 originSensor = new Vector2();
		BodyDef bodyDef = new BodyDef();
	
		for (EnemyHorizontal enemy : level.enemiesHorizontals) {
			// Sensor del cuerpo
			// Se utiliza para detectar cuando el enemigo debe salir disparado
			// (7m antes)
			originSensor.x = enemy.dimension.x / 2.0f - 7;
			originSensor.y = enemy.dimension.y / 2;
			PolygonShape sensorShape = new PolygonShape();
			sensorShape.setAsBox(0.01f, Constants.VIEWPORT_HEIGHT,
					originSensor, 0);
			FixtureDef fixtureDefSensor = new FixtureDef();
			fixtureDefSensor.shape = sensorShape;
			fixtureDefSensor.isSensor = true;
			// Cuerpo
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(enemy.position);
			Body body = b2world.createBody(bodyDef);

			origin.x = enemy.bounds.width / 2.0f;
			origin.y = enemy.bounds.height / 2.0f;

			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(enemy.dimension.x / 2 - 0.1f,
					enemy.dimension.y / 2 - 0.1f, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;

			body.createFixture(fixtureDefSensor);
			body.createFixture(fixtureDef);
			body.setGravityScale(0);
			body.setUserData(enemy);
			
			enemy.body = body;

			sensorShape.dispose();
			polygonShape.dispose();
		}
	}

	/**
	 * Genera la física del enemigo saltador
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createEnemyJumpers(World b2world, LevelCreator level) {
		Vector2 origin = new Vector2();
		Vector2 originSensor = new Vector2();
		BodyDef bodyDef = new BodyDef();
		for (EnemyJumper enemy : level.enemiesJumpers) {
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(enemy.position);
			Body body = b2world.createBody(bodyDef);
			enemy.body = body;
			// Sensor del cuerpo
			// Se utiliza para detectar cuando el enemigo debe comenzar a saltar
			// (Justo cuando aparezca el enemigo en pantalla)
			originSensor.x = enemy.dimension.x / 2.0f - 2;
			originSensor.y = enemy.dimension.y / 2;
			PolygonShape sensorShape = new PolygonShape();
			sensorShape.setAsBox(0.01f, Constants.VIEWPORT_HEIGHT,
					originSensor, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = sensorShape;
			fixtureDef2.isSensor = true;
			// Cuerpo
			PolygonShape polygonShape = new PolygonShape();
			origin.x = enemy.bounds.width / 2.0f;
			origin.y = enemy.bounds.height / 2.0f;
			polygonShape.setAsBox(enemy.dimension.x / 2 - 0.1f,
					enemy.dimension.y / 2 - 0.2f / 2, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			fixtureDef.density = Constants.ENEMY_JUMPER_DENSITY;
			fixtureDef.friction = Constants.ENEMY_JUMPER_FRICTION;

			body.setFixedRotation(true);
			body.createFixture(fixtureDef2);
			body.createFixture(fixtureDef);
			body.setUserData(enemy);
			sensorShape.dispose();
			polygonShape.dispose();
		}
	}

	/**
	 * Genera la física del enemigo estático
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createEnemyStatic(World b2world, LevelCreator level) {
		Vector2 origin1 = new Vector2();
		Vector2 origin2 = new Vector2();
		for (EnemyStatic enemy : level.enemiesStatics) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(enemy.position);
			Body body = b2world.createBody(bodyDef);
			enemy.body = body;
			PolygonShape polygonShape = new PolygonShape();
			PolygonShape polygonShape2 = new PolygonShape();
			origin1.x = enemy.bounds.width / 2.0f;
			origin1.y = enemy.bounds.height / 3.0f + 0.1f;
			origin2.x = enemy.bounds.width / 2.0f;
			origin2.y = enemy.bounds.height / 4.0f;
			polygonShape.setAsBox(enemy.dimension.x / 2 - 0.4f,
					enemy.dimension.y / 2, origin1, 0);
			polygonShape2.setAsBox(enemy.dimension.y / 2,
					enemy.dimension.x / 2 - 0.4f, origin2, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			body.createFixture(fixtureDef);
			body.createFixture(fixtureDef2);
			body.setUserData(enemy);
			polygonShape.dispose();
			polygonShape2.dispose();
		}
	}

	/**
	 * Genera la física del suelo
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createGround(World b2world, LevelCreator level) {
		Ground ground = new Ground();
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(ground.position);
		Body body = b2world.createBody(bodyDef);
		ground.body = body;
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(Constants.WORLD_GROUND_DIMENSION.x / 2.0f,
				Constants.WORLD_GROUND_DIMENSION.y, new Vector2(
						Constants.WORLD_GROUND_DIMENSION.x / 2.0f, 0), 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		body.createFixture(fixtureDef);
		body.setUserData(ground);
		polygonShape.dispose();
	}

	/**
	 * Genera la física de las plataformas
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createPlatforms(World b2world, LevelCreator level) {
		Vector2 origin = new Vector2();
		for (Platform platform : level.platforms) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(platform.position);
			Body body = b2world.createBody(bodyDef);
			platform.body = body;
			origin.x = platform.bounds.width / 2.0f;
			origin.y = platform.bounds.height / 2.0f;
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(platform.bounds.width / 2 - 0.1f,
					platform.bounds.height / 2 - 0.05f, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			body.createFixture(fixtureDef);
			body.setUserData(platform);
			polygonShape.dispose();
		}
	}

	/**
	 * Genera la física los item a recolectar
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createMainItems(World b2world, LevelCreator level) {
		Vector2 origin = new Vector2();
		for (MainItem item : level.mainItems) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(item.position);
			Body body = b2world.createBody(bodyDef);
			item.body = body;
			PolygonShape polygonShape = new PolygonShape();
			origin.x = item.bounds.width / 2.0f;
			origin.y = item.bounds.height / 2.0f;
			polygonShape.setAsBox(item.bounds.width / 2.0f,
					item.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			// Evita que el runner colisione con los items
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef);
			body.setUserData(item);
			polygonShape.dispose();
		}
	}
	
	/**
	 * Genera la física los item balls a recolectar
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createItems(World b2world, LevelCreator level) {
		Vector2 origin = new Vector2();
		for (Item item : level.items) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(item.position);
			Body body = b2world.createBody(bodyDef);
			item.body = body;
			PolygonShape polygonShape = new PolygonShape();
			origin.x = item.bounds.width / 2.0f;
			origin.y = item.bounds.height / 2.0f;
			polygonShape.setAsBox(item.bounds.width / 2.0f,
					item.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			// Evita que el runner colisione con los items
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef);
			body.setUserData(item);
			polygonShape.dispose();
		}
	}

	/**
	 * Genera la física de los powerups
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createPowerUps(World b2world, LevelCreator level) {
		Vector2 origin = new Vector2();
		for (PowerUp powerup : level.powerups) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(powerup.position);
			Body body = b2world.createBody(bodyDef);
			powerup.body = body;
			PolygonShape polygonShape = new PolygonShape();
			origin.x = powerup.bounds.width / 2.0f;
			origin.y = powerup.bounds.height / 2.0f;
			polygonShape.setAsBox(powerup.bounds.width / 2.0f,
					powerup.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = polygonShape;
			// Evita que el runner colisione con las coins
			fixtureDef.isSensor = true;
			body.createFixture(fixtureDef);
			body.setUserData(powerup);
			polygonShape.dispose();
		}
	}

	/**
	 * Genera la física de la meta
	 * 
	 * @param b2world
	 *            El mundo
	 * @param level
	 *            El nivel
	 */
	public static void createGoal(World b2world, LevelCreator level) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(level.goal.position);
		Body body = b2world.createBody(bodyDef);
		level.goal.body = body;
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(level.goal.bounds.width / 2.0f,
				level.goal.bounds.height + 4);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		// Evita que el runner colisione con las coins
		fixtureDef.isSensor = true;
		body.createFixture(fixtureDef);
		body.setUserData(level.goal);
		polygonShape.dispose();

	}

	/**
	 * Comprueba si el cuerpo es un enemigo estático
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es un enemigo, false en caso contrario
	 */
	public static boolean bodyIsEnemyStatic(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.ENEMY_STATIC;
	}
	
	/**
	 * Comprueba si el cuerpo es un enemigo horizontal
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es un enemigo, false en caso contrario
	 */
	public static boolean bodyIsEnemyHorizontal(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.ENEMY_HORIZONTAL;
	}
	
	/**
	 * Comprueba si el cuerpo es un enemigo saltador
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es un enemigo, false en caso contrario
	 */
	public static boolean bodyIsEnemyJumper(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.ENEMY_JUMPER;
	}

	/**
	 * Comprueba si el cuerpo es el corredor
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es el corredor, false en caso contrario
	 */
	public static boolean bodyIsRunner(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.RUNNER;
	}

	/**
	 * Comprueba si el cuerpo es una plataforma
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es una plataforma, false en caso contrario
	 */
	public static boolean bodyIsPlatform(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.PLATFORM;
	}

	/**
	 * Comprueba si el cuerpo es el suelo
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es el suelo, false en caso contrario
	 */
	public static boolean bodyIsGround(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.GROUND;
	}

	/**
	 * Comprueba si el cuerpo es un item
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es un item, false en caso contrario
	 */
	public static boolean bodyIsItem(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.ITEM;
	}
	
	/**
	 * Comprueba si el cuerpo es un item ball
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es un item, false en caso contrario
	 */
	public static boolean bodyIsMainItem(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.MAIN_ITEM;
	}

	/**
	 * Comprueba si el cuerpo es un powerup
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es un powerup, false en caso contrario
	 */
	public static boolean bodyIsPowerUp(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.POWER_UP;
	}

	/**
	 * Comprueba si el cuerpo es la meta
	 * 
	 * @param body
	 *            El cuerpo que colisiona
	 * @return True si es la meta, false en caso contrario
	 */
	public static boolean bodyIsGoal(Body body) {
		AbstractGameObject obj = (AbstractGameObject) body.getUserData();

		return obj != null && obj.userDataType == UserDataType.GOAL;
	}

}
