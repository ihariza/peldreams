package com.nha.pelsdreams.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

/**
 * Constantes del juego
 * 
 * @author Nacho Herrera
 * 
 */
public class Constants {

	// Nivel del log
	public static final int LOG_LEVEL = Application.LOG_NONE;
	// Muestra el FPS y Screen size
	public static final boolean DEBUG_MODE = false;
	public static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
	public static final boolean DEBUG_DRAW_UI = false;
	// Modo Dios. No le afectan ni las rocas ni los enemigos
	public static final boolean DEBUG_RUNNER_GODMODE = false;
	

	/**
	 * Pantalla
	 */
	// Ancho y alto de la pantalla
	public static final int APP_WIDTH = 800;
	public static final int APP_HEIGHT = 480;

	/**
	 * C�mara
	 */
	// Valores m�ximo y m�nimo del zoom de CameraHelper
	public static final float MAX_ZOOM_IN = 0.25f;
	public static final float MAX_ZOOM_OUT = 10.0f;
	// Ancho y alto visible del mundo (en metros)
	public static final float VIEWPORT_WIDTH = 6f;
	public static final float VIEWPORT_HEIGHT = 6f;
	// Ancho y alto del GUI (c�mara para puntuaciones, vidas, fps)
	public static final int VIEWPORT_GUI_WIDTH = 800;
	public static final int VIEWPORT_GUI_HEIGHT = 480;
	// Teclas de movimiento de la c�mara
	public static final int CAMERA_KEY_UP = Keys.W;
	public static final int CAMERA_KEY_DOWN = Keys.S;
	public static final int CAMERA_KEY_LEFT = Keys.A;
	public static final int CAMERA_KEY_RIGHT = Keys.D;
	// Tecla de aceleraci�n de la c�mara
	public static final int CAMERA_KEY_ACCELERATION = Keys.SHIFT_LEFT;
	// Tecla de aumento del zoom de la c�mara
	public static final int CAMERA_KEY_ADD_ZOOM = Keys.COMMA;
	// Tecla de reducci�n del zoom de la c�mara
	public static final int CAMERA_KEY_REDUCE_ZOOM = Keys.PERIOD;
	// Tecla de reset del zoom
	public static final int CAMERA_KEY_RESET_ZOOM = Keys.ESCAPE;
	// Tecla de reset de la posici�n de la c�mara al origen
	public static final int CAMERA_KEY_RESET = Keys.BACKSPACE;
	// Tecla de cambio de target de la c�mara
	public static final int CAMERA_KEY_TOGGLE = Keys.ENTER;

	/**
	 * Assets
	 */
	public static final String TEXTURE_ATLAS_OBJECTS = "images/objects.pack";
	public static final String TEXTURE_ATLAS_EFFECTS = "images/effects.pack";
	public static final String TEXTURE_ATLAS_RUNNER = "images/pel_atlas.pack";
	public static final String TEXTURE_ATLAS_WORLD001_BACKGROUND = "images/worlds/1/world001_background.pack";
	public static final String TEXTURE_ATLAS_WORLD001_ENEMIES = "images/worlds/1/world001_enemies.pack";
	public static final String GENERAL_NORMAL_FONT = "fonts/general_normal.fnt";
	public static final String GENERAL_BIG_FONT = "fonts/general_big.fnt";
	public static final String YELLOW_NORMAL_FONT = "fonts/yellow_normal.fnt";
	public static final String YELLOW_BIG_FONT = "fonts/yellow_big.fnt";
	public static final String PARTICLE_DUST = "particles/dust.pfx";
	public static final String PARTICLE_FALL = "particles/fall.pfx";
	public static final String PARTICLE_GOAL = "particles/goal.pfx";
	public static final String PARTICLE_ITEM = "particles/item.pfx";
	public static final String MUSIC_INTRO = "music/intro.mp3";
	public static final String MUSIC_DREAMS = "music/dreams.mp3";
	public static final String MUSIC_DESERT = "music/desert.mp3";
	public static final String MUSIC_COMPLETED = "music/completed.mp3";
	public static final String SOUND_ARROWS = "sounds/arrows.wav";
	public static final String SOUND_WALL = "sounds/wall.wav";
	public static final String SOUND_DODGING = "sounds/dodging.wav";
	public static final String SOUND_JUMP = "sounds/jump.wav";
	public static final String SOUND_SNAKE_DEAD = "sounds/snake_dead.wav";
	public static final String SOUND_TOTEM = "sounds/totem.wav";
	public static final String SOUND_DEAD = "sounds/dead.wav";
	public static final String SOUND_ITEM = "sounds/item.wav";
	public static final String SOUND_BUTTON = "sounds/button.wav";


	/**
	 * Pantallas de men�s
	 */
	public static final String TEXTURE_MAIN_MENU_ATLAS_UI = "images/main_ui.pack";
	public static final String TEXTURE_WORLD_LEVELS_ATLAS_UI = "images/world_ui.pack";
	public static final String TEXTURE_ATLAS_INGAME_UI = "images/ingame_ui.pack";
	public static final String TEXTURE_LOADING = "images/loading.pack";
	// Localizaci�n del archivo de descripci�n de la skin
	public static final String SKIN_MAIN_MENU_UI = "images/main_ui.json";
	public static final String SKIN_WORLD_LEVELS_UI = "images/world_ui.json";
	public static final String SKIN_LOADING_UI = "images/loading.json";
	public static final String SKIN_INGAME_UI = "images/ingame_ui.json";

	/**
	 * Archivo de preferencias
	 */
	public static final String PREFERENCES = "peldreams.prefs";

	/**
	 * Levels
	 */
	// Ruta de localizaci�n del los niveles
	public static final String WORLDS_PATH = "images/worlds/";
	public static final String LEVELFILE_NAME = "levels.dat";

	/**
	 * World
	 */
	// N�mero de mundo que contiene el juego
	public static final int WORLDS_NUMBER = 1;
	// Gravedad del mundo
	public static final Vector2 WORLD_GRAVITY = new Vector2(0, -20);
	// Dimensi�n del suelo
	public static final Vector2 WORLD_GROUND_DIMENSION = new Vector2(175 + 6,
			0.95f);
	// Conversi�n de los pixel del nivel al mundo
	// 1 pixel de nivel equivale a 80 pixel en pantalla
	public static final float PIXEL_TO_WORLD = 0.8f;
	// Conversi�n de metros a p�xeles y viceversa
	public static final float WORLD_TO_BOX = 0.01f;
	public static final float BOX_TO_WORLD = 100f;
	// Vidas extras al iniciar un nivel
	public static final int LIVES_START = 0;
	// Duraci�n del power-up en segundos
	public static final float ITEM_POWERUP_DURATION = 9;
	// Retardo despu�s de finalizar el nivel
	public static final float TIME_DELAY_GAME_FINISHED = 6;
	// Retardo despu�s de morir
	public static final float TIME_DELAY_GAME_OVER = 2;
	// Physics Step
	public static final float TIME_STEP = 1/300f;
	public static final int VELOCITY_ITERATIONS = 8;
	public static final int POSITION_ITERATIONS = 2;
	/**
	 * Runner
	 */
	public static final float RUNNER_WIDTH = 0.9f;
	public static final float RUNNER_HEIGHT = 1f;
	public static float RUNNER_DENSITY = 0.0f;
	public static final float RUNNER_GRAVITY_SCALE = 2.5f;
	public static final float RUNNER_RESTITUTION = 0f;
	public static final float RUNNER_FRICTION = 0f;
	public static final Vector2 RUNNER_MAX_VELOCITY = new Vector2(4.0f, 5.2f);
	public static final Vector2 RUNNER_DODGE = new Vector2(2f, 1.5f);

	public static final Vector2 RUNNER_LINEAR_IMPULSE = new Vector2(
			RUNNER_MAX_VELOCITY.x, 0f);
	public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(RUNNER_MAX_VELOCITY.x,
			3.3f);

	/**
	 * Enemy Jumper
	 */
	public static float ENEMY_JUMPER_DENSITY = 500.0f;
	public static final float ENEMY_JUMPER_FRICTION = 0;
	public static final Vector2 ENEMY_JUMPER_MAX_VELOCITY = new Vector2(0.0f, 5.2f);
	
	/**
	 * Enemy Horizontal
	 */
	public static float ENEMY_HORIZONTAL_DENSITY = 500.0f;
	public static final float ENEMY_HORIZONTAL_FRICTION = 0;
	public static final Vector2 ENEMY_HORIZONTAL_MAX_VELOCITY = new Vector2(-5.0f, 0f);
	
}
