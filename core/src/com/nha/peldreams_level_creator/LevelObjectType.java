package com.nha.peldreams_level_creator;

/**
 * Colores de los tipos de objetos en el nivel
 * 
 * @author Nacho Herrera
 * 
 */
public enum LevelObjectType {

	EMPTY(0, 0, 0), // Nada - negro
	PLATFORM(0, 255, 0), // Roca - verde
	ENEMY_HORIZONTAL(255, 255, 255), // Enemigo horizontal - blanco
	ENEMY_JUMPER(255, 0, 0), // Enemigo saltador - rojo
	ENEMY_STATIC(0, 0, 255), // Enemigo est�tico - azul
	PLAYER_SPAWNPOINT(200, 200, 200), // Punto de inicio del jugador - gris claro
	ITEM_POWER(0 , 0, 100), // PowerUps - azul oscuro
	ITEM(255, 0, 255), // Item balls - morado
	MAIN_ITEM(255, 255, 0), // Items - amarillo
	GOAL(0, 255, 255); // Llegada - cian 
	private int color;

	private LevelObjectType(int r, int g, int b) {
		color = r << 24 | g << 16 | b << 8 | 0xff;
	}

	/**
	 * Comprueba si dos colores son iguales
	 * 
	 * @param color
	 *            El color a comparar
	 * @return True si es el mismo color, false en caso contrario
	 */
	public boolean sameColor(int color) {
		return this.color == color;
	}

	/**
	 * Obtiene el color actual
	 * 
	 * @return El color
	 */
	public int getColor() {
		return color;
	}
}
