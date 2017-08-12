package com.nha.pelsdreams.game.objects.levels;

import com.badlogic.gdx.math.Vector2;
import com.nha.pelsdreams.enums.LevelObjectType;

/**
 * Representa un objeto en un nivel
 * 
 * @author Nacho Herrera
 * 
 */
public class LevelObject {
	// Posición del objeto en el nivel
	public Vector2 position;
	// Tipo de objeto
	public LevelObjectType type;
	// Cantidad de unidades que lo componen
	// (En el caso de las plataformas)
	public int unitsAmount;

	public LevelObject() {
		position = new Vector2();
	}
}
