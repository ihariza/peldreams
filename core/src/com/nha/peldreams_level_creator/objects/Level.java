package com.nha.peldreams_level_creator.objects;


import com.badlogic.gdx.utils.Array;
import com.nha.peldreams_level_creator.LevelProcessor;

/**
 * Representa un nivel con sus objetivos
 * @author Nacho Herrera
 *
 */
public class Level {
	public int id;
	public Array<LevelObject> levelObjects;
	public int mainItemsObjective;
	public int itemsObjective;
	
	
	/**
	 * Crea un nivel con los objetivos y los objectos del nivel.
	 * @param mainItemsObjective Objetivo para los main items.
	 * @param itemsObjective Objetivo para los items.
	 * @param filename Archivo gr�fico del nivel a procesar
	 */
	public Level(int id, int mainItemsObjective, int itemsObjective, String filename) {
		this.id = id;
		this.mainItemsObjective = mainItemsObjective;
		this.itemsObjective = itemsObjective;
		this.levelObjects = LevelProcessor.instance.processLevel(filename);
	}
}
