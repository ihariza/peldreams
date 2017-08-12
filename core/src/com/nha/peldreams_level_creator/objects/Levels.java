package com.nha.peldreams_level_creator.objects;

import com.badlogic.gdx.utils.Array;

public class Levels {
	// Ruta mundo 1
	public static String WORLD_BASE = "levels/worlds/";

	// Colecci�n de niveles
	public Array<Level> levels;

	public Levels(int world) {
		levels = new Array<Level>();
		// Crea los niveles con los objetivos
		levels.add(new Level(1, 3, 15, WORLD_BASE + world + "/" + "1.png"));
		levels.add(new Level(2, 3, 15, WORLD_BASE + world + "/" + "2.png"));
		levels.add(new Level(3, 2, 15, WORLD_BASE + world + "/" + "3.png"));
		levels.add(new Level(4, 2, 10, WORLD_BASE + world + "/" + "4.png"));
		levels.add(new Level(5, 3, 18, WORLD_BASE + world + "/" + "5.png"));
		levels.add(new Level(6, 3, 18, WORLD_BASE + world + "/" + "6.png"));
		levels.add(new Level(7, 3, 29, WORLD_BASE + world + "/" + "7.png"));
		levels.add(new Level(8, 1, 10, WORLD_BASE + world + "/" + "8.png"));
		levels.add(new Level(9, 1, 14, WORLD_BASE + world + "/" + "9.png"));
		levels.add(new Level(10, 2, 13, WORLD_BASE + world + "/" + "10.png"));
		levels.add(new Level(11, 3, 30, WORLD_BASE + world + "/" + "11.png"));
		levels.add(new Level(12, 3, 15, WORLD_BASE + world + "/" + "12.png"));
	}	

}
