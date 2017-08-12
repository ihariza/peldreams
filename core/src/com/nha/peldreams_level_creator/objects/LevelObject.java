package com.nha.peldreams_level_creator.objects;


import com.badlogic.gdx.math.Vector2;
import com.nha.peldreams_level_creator.LevelObjectType;

/**
 * Representa un objeto en un nivel
 * @author Nacho Herrera
 *
 */
public class LevelObject {
	// Posici�n del objeto en el nivel
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
