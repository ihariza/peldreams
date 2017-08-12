package com.nha.peldreams_level_creator;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.nha.peldreams_level_creator.objects.Levels;

/**
 * Genera los niveles y los almacena en un fichero codificado
 * @author Nacho Herrera
 *
 */
public class LevelCreator extends Game {
	// Fichero de los niveles del mundo
	private static String LEVEL_FILE = "levels.dat";
	private static final int WORLD = 1;

	@Override
	public void create() {
		// Establece el tipo de log a DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Crea el fichero codificado
		createFile(new Levels(WORLD));
		Gdx.app.exit();
	}

	/**
	 * Crea el archivo con todos los niveles y objetivos de cada nivel en
	 * formato codificado.
	 * 
	 * @param levels
	 *            Niveles a almacenar
	 */
	public static void createFile(Levels levels) {
		Json json = new Json();
		FileHandle file = Gdx.files.local("/images/worlds/" + WORLD + "/" + LEVEL_FILE);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(json
				.toJson(levels)), false);
	}

}
