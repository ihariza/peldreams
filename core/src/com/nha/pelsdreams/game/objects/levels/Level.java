package com.nha.pelsdreams.game.objects.levels;

import com.badlogic.gdx.utils.Array;

/**
 * Representa un nivel con sus objetivos
 * 
 * @author Nacho Herrera
 * 
 */
public class Level {

	public int id;
	public Array<LevelObject> levelObjects;
	public int mainItemsObjective;
	public int itemsObjective;

	public Level() {

	}

}
