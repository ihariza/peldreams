package com.nha.pelsdreams.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.nha.pelsdreams.enums.LevelObjectType;
import com.nha.pelsdreams.game.objects.AbstractGameObject;
import com.nha.pelsdreams.game.objects.Background;
import com.nha.pelsdreams.game.objects.EnemyHorizontal;
import com.nha.pelsdreams.game.objects.EnemyJumper;
import com.nha.pelsdreams.game.objects.EnemyStatic;
import com.nha.pelsdreams.game.objects.Goal;
import com.nha.pelsdreams.game.objects.GoalParticles;
import com.nha.pelsdreams.game.objects.Ground;
import com.nha.pelsdreams.game.objects.Item;
import com.nha.pelsdreams.game.objects.MainItem;
import com.nha.pelsdreams.game.objects.Platform;
import com.nha.pelsdreams.game.objects.PowerUp;
import com.nha.pelsdreams.game.objects.Runner;
import com.nha.pelsdreams.game.objects.levels.Level;
import com.nha.pelsdreams.game.objects.levels.LevelObject;
import com.nha.pelsdreams.game.objects.levels.Levels;
import com.nha.pelsdreams.utils.Constants;

/**
 * Contiene los datos de un nivel
 * 
 * @author Nacho Herrera
 * 
 */
public class LevelCreator {

	public static final String TAG = LevelCreator.class.getName();

	// Nivel
	private Level level;

	// Objetivos
	public int mainItemsObjetive;
	public int itemsObjetive;

	// Runner
	public Runner runner;

	// Enemigos
	// Saltador
	public Array<EnemyJumper> enemiesJumpers;
	// Horizontal
	public Array<EnemyHorizontal> enemiesHorizontals;
	// Estático
	public Array<EnemyStatic> enemiesStatics;

	// Objetos
	public Array<Platform> platforms;
	public Array<MainItem> mainItems;
	public Array<Item> items;
	public Array<PowerUp> powerups;
	public Goal goal;
	// Efecto de partículas de la meta
	public GoalParticles goalParticles;

	// Decorado
	public Background background;
	public Ground ground;

	private String filename;
	private int levelNumber;

	public LevelCreator(String filename, int levelNumber) {
		this.filename = filename;
		this.levelNumber = levelNumber;
		init();
	}

	private void init() {
		// Inicializa el runner y objetos
		runner = null;
		enemiesJumpers = new Array<EnemyJumper>();
		enemiesStatics = new Array<EnemyStatic>();
		enemiesHorizontals = new Array<EnemyHorizontal>();
		platforms = new Array<Platform>();
		mainItems = new Array<MainItem>();
		items = new Array<Item>();
		powerups = new Array<PowerUp>();
		background = new Background();
		ground = new Ground();
		// Carga el archivo de datos de nivel
		level = getLevel();
		mainItemsObjetive = level.mainItemsObjective;
		itemsObjetive = level.itemsObjective;
		// Crea los objectos que contiene el nivel
		for (LevelObject object : level.levelObjects) {

			AbstractGameObject obj = null;
			// La altura base (a nivel del suelo).
			float baseHeight = Constants.WORLD_GROUND_DIMENSION.y
					+ object.position.y * Constants.PIXEL_TO_WORLD;

			// Posición de los objetos en la pantalla
			// 1 pixel de nivel equivale a 80px en pantalla de juego
			float posX = object.position.x * Constants.PIXEL_TO_WORLD;

			// Crea el objeto de juego correspondiente en el punto (x, y),
			// si hay una coincidencia entre el color del pixel en ese punto
			// y el color del tipo de bloque (LevelBlockType).

			// Espacio vacío
			if (object.type == LevelObjectType.EMPTY) {
				// No hace nada
			}

			// Punto de inicio del jugador
			else if (object.type == LevelObjectType.PLAYER_SPAWNPOINT) {
				obj = new Runner();
				obj.position.set(posX, baseHeight + ((Runner) obj).dimension.y);
				runner = (Runner) obj;
			}

			// Enemigo saltador
			else if (object.type == LevelObjectType.ENEMY_JUMPER) {
				obj = new EnemyJumper();
				obj.position.set(posX, baseHeight);
				enemiesJumpers.add((EnemyJumper) obj);
			}

			// Enemigo horizontal
			else if (object.type == LevelObjectType.ENEMY_HORIZONTAL) {
				obj = new EnemyHorizontal();
				obj.position.set(posX + 0.2f, baseHeight
						+ ((EnemyHorizontal) obj).dimension.y + 0.35f);
				enemiesHorizontals.add((EnemyHorizontal) obj);
			}

			// Enemigo estático
			else if (object.type == LevelObjectType.ENEMY_STATIC) {
				obj = new EnemyStatic();
				obj.position.set(posX, baseHeight
						+ ((EnemyStatic) obj).dimension.y);
				enemiesStatics.add((EnemyStatic) obj);
			}

			// Plataforma
			else if (object.type == LevelObjectType.PLATFORM) {
				obj = new Platform(object.unitsAmount);
				// Establece la posición de la primera unidad de la
				// línea de plataformas
				obj.position.set(posX, baseHeight + 0.2f
						+ ((Platform) obj).dimension.y);
				platforms.add((Platform) obj);
			}

			// Power-ups
			else if (object.type == LevelObjectType.ITEM_POWER) {
				obj = new PowerUp();
				obj.position
						.set(posX, baseHeight + ((PowerUp) obj).dimension.y);
				powerups.add((PowerUp) obj);
			}

			// Item
			else if (object.type == LevelObjectType.ITEM) {
				obj = new Item();
				obj.position.set(posX + 0.02f, baseHeight
						+ ((Item) obj).dimension.y + 0.4f);
				items.add((Item) obj);
			}

			// MainItem
			else if (object.type == LevelObjectType.MAIN_ITEM) {
				obj = new MainItem();
				obj.position.set(posX + 0.05f, baseHeight
						+ ((MainItem) obj).dimension.y + 0.4f);
				mainItems.add((MainItem) obj);
			}

			// Goal
			else if (object.type == LevelObjectType.GOAL) {
				obj = new Goal();
				obj.position.set(posX, baseHeight - 1.3f);
				goal = (Goal) obj;
			}

			// Color de objecto/pixel desconocido
			else {
				Gdx.app.error(TAG, "Objeto desconocido.");
			}

		}
		// Crea el efecto de partículas ajustándolo al centro de la meta en el
		// eje x
		goalParticles = new GoalParticles(goal.position.x + goal.dimension.x
				/ 2 - 0.7f, goal.position.y + 0.1f);

		Gdx.app.debug(TAG, "Nivel '" + filename + "' cargado");
	}

	public void render(SpriteBatch batch) {
		// Dibuja el background
		background.render(batch);
		// Dibuja la llegada
		goal.render(batch);
		// Dibuja el suelo
		ground.render(batch);
		// Dibuja los enemigos horizontales
		for (EnemyHorizontal enemyHorizontal : enemiesHorizontals)
			enemyHorizontal.render(batch);
		// Dibuja las plataformas
		for (Platform platform : platforms)
			platform.render(batch);
		// Dibuja los items
		for (Item item : items)
			item.render(batch);
		// Dibuja los items Balls
		for (MainItem mainItem : mainItems)
			mainItem.render(batch);
		// Dibuja los power-ups
		for (PowerUp powerup : powerups)
			powerup.render(batch);
		// Dibuja el efecto de partículas
		if (goal.isReached)
			goalParticles.render(batch);
		// Dibuja los enemigos saltadores
		for (EnemyJumper enemyJumper : enemiesJumpers)
			enemyJumper.render(batch);
		// Dibuja los enemigos estáticos
		for (EnemyStatic enemyStatic : enemiesStatics)
			enemyStatic.render(batch);
		// Dibuja el runner
		runner.render(batch);
	}

	public void update(float deltaTime) {
		runner.update(deltaTime);

		goal.update(deltaTime);

		if (goal.isReached)
			goalParticles.update(deltaTime);
		// Si la animación del goal ha terminado, finaliza la animación de
		// partículas
		if (goal.animFinished)
			goalParticles.stop(deltaTime);

		for (EnemyHorizontal enemyHorizontal : enemiesHorizontals)
			enemyHorizontal.update(deltaTime);

		for (EnemyJumper enemyJumper : enemiesJumpers)
			enemyJumper.update(deltaTime);

		for (EnemyStatic enemyStatic : enemiesStatics)
			enemyStatic.update(deltaTime);

		for (Platform platform : platforms)
			platform.update(deltaTime);

		for (Item item : items)
			item.update(deltaTime);

		for (MainItem mainItem : mainItems)
			mainItem.update(deltaTime);

		for (PowerUp powerup : powerups)
			powerup.update(deltaTime);
	}

	/**
	 * Carga el nivel del juego desde fichero
	 * 
	 * @return Level. El nivel del juego.
	 */
	public Level getLevel() {
		String save = "";
		// Lee el fichero y lo carga en save.
		FileHandle file = Gdx.files.internal(filename);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				save = com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		// Crea el level
		if (!save.isEmpty()) {
			Json json = new Json();
			Levels jsonLevels = json.fromJson(Levels.class, save);

			level = jsonLevels.levels.get(levelNumber);

			return level;
		}
		// Si no existe fichero
		else {
			Gdx.app.debug(TAG, "Error. No se puede leer el fichero");
			return null;
		}
	}
	
	public void dispose() {
		for (Item item: items) {
			item.dispose();
		}
		for (MainItem mainItem : mainItems) {
			mainItem.dispose();
		}
	}
}
