package com.nha.pelsdreams.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * Almacena el estado del juego
 * 
 * @author Ignacio Herrera
 *
 */
public class SaveGameHelper {

	private static String GAME_FILE = "game.dat";

	public static class GameState {
		// Mundo seleccionado
		public WorldState currentWorld;
		// Mundos del juego
		public Array<WorldState> worlds = new Array<WorldState>();
	}

	/**
	 * Representa el estado de un mundo
	 * 
	 * @author Ignacio Herrera
	 * 
	 */
	public static class WorldState {
		// Número del mundo correspondiente en el Array
		public int id;
		// Estado desbloqueado (true) o bloqueado (false)
		public boolean state;
		// Nivel seleccionado
		public LevelState currentLevel;
		public Array<LevelState> levels;
		
		public WorldState(){
		}

		public WorldState(int id, boolean state) {
			this.id = id;
			this.state = state;
			levels = new Array<SaveGameHelper.LevelState>();
			// Llena la colección con 12 niveles, el primero desbloqueado
			for (int i = 0; i < 12; i++) {
				if (i == 0)
					levels.add(new LevelState(i, true));
				else
					levels.add(new LevelState(i, false));
			}
		}
	}

	/**
	 * Representa el estado de un nivel
	 * 
	 * @author Ignacio Herrera
	 * 
	 */
	public static class LevelState {
		// Número del nivel
		public int id;
		// Estado desbloquedo (true) o bloqueado (false)
		public boolean state;
		// Número de items recolectados
		public int mainItems;
		
		public LevelState(){
		}

		public LevelState(int id, boolean state) {
			this.id = id;
			this.state = state;
			mainItems = 0;
		}
	}

	/**
	 * Almacena el estado del juego
	 * @param gameState. El estado del juego.
	 */
	public static void saveGameState(GameState gameState) {
		GameState jsonWorldState = new GameState();

		jsonWorldState = gameState;

		Json json = new Json();
		writeFile(GAME_FILE, json.toJson(jsonWorldState));
	}

	/**
	 * Carga el estado del juego
	 * @return GameState. El estado del juego.
	 */
	public static GameState loadGameState() {
		String save = readFile(GAME_FILE);
		GameState gameState = new GameState();
		if (!save.isEmpty()) {
			Json json = new Json();
			GameState jsonGameState = json.fromJson(GameState.class, save);

			gameState = jsonGameState;

			return gameState;
		}
		// Si no existe fichero, crea un estado nuevo
		else {
			for (int i = 0; i < Constants.WORLDS_NUMBER; i++) {
				gameState.worlds.add(new WorldState(i, true));
			}
			saveGameState(gameState);
			return gameState;
		}
	}

	/**
	 * Escribe datos en un fichero en formato codificado Base64
	 * @param fileName Nombre del fichero
	 * @param data Los datos a almacenar
	 */
	public static void writeFile(String fileName, String data) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(data),
				false);
	}

	/**
	 * Lee datos de un fichero codificado en Base64
	 * @param fileName nombre del fichero
	 * @return Los datos decodificados
	 */
	public static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}
}
