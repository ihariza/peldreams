package com.nha.peldreams_level_creator;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;
import com.nha.peldreams_level_creator.objects.LevelObject;

/**
 * Procesa un nivel de tipo gr�fico Carga el nivel en formato gr�fico y almacena
 * una colecci�n el tipo de objetos que lo componen y su posici�n
 * 
 * @author Nacho Herrera
 * 
 */
public class LevelProcessor {

	public static final LevelProcessor instance = new LevelProcessor();

	private LevelProcessor() {

	}

	/**
	 * Procesa un nivel
	 * 
	 * @param filename
	 *            Archivo de nivel a procesar
	 * @return Una colecci�n con los objectos del nivel
	 */
	public Array<LevelObject> processLevel(String filename) {
		// Inicializa el array de objetos
		Array<LevelObject> objects = new Array<LevelObject>();
		// Carga el archivo que representa los datos de nivel
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		// Escanea los p�xeles desde la parte inferior izquierda a la parte
		// superior derecha
		// Cantidad de unidades que contiene cada l�nea de plataforma
		int unitsAmount = 0;
		for (int pixelY = pixmap.getHeight() - 1; pixelY > 0; pixelY--) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				LevelObject obj = null;
				// Obtiene el valor del color del pixel actual como valor RGBA
				// de 32-bit
				// La altura de abajo hacia arriba (abajo = 0; arriba = 6);
				int posY = pixmap.getHeight() - pixelY - 1;
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				int nextPixel = pixmap.getPixel(pixelX + 1, pixelY);

				// Crea el objeto de juego correspondiente en el punto (x, y),
				// si hay una coincidencia entre el color del pixel en ese punto
				// y el color del tipo de bloque (LevelBlockType).

				// Espacio vac�o
				if (LevelObjectType.EMPTY.sameColor(currentPixel)) {
					// No hace nada
				}

				// Punto de inicio del jugador
				else if (LevelObjectType.PLAYER_SPAWNPOINT
						.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.PLAYER_SPAWNPOINT;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Enemigo saltador
				else if (LevelObjectType.ENEMY_JUMPER.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.ENEMY_JUMPER;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Enemigo horizontal
				else if (LevelObjectType.ENEMY_HORIZONTAL
						.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.ENEMY_HORIZONTAL;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Enemigo est�tico
				else if (LevelObjectType.ENEMY_STATIC.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.ENEMY_STATIC;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Plataforma
				else if (LevelObjectType.PLATFORM.sameColor(currentPixel)) {
					// Crea la plataforma si es la �ltima unidad
					if (!LevelObjectType.PLATFORM.sameColor(nextPixel)) {
						obj = new LevelObject();
						obj.unitsAmount = unitsAmount + 1;
						obj.type = LevelObjectType.PLATFORM;
						// Establece la posici�n de la primera unidad de la
						// l�nea de plataformas
						obj.position.set(pixelX - unitsAmount, posY);
						objects.add(obj);
						unitsAmount = 0;
					} else {
						unitsAmount += 1;
					}
				}

				// Power-ups
				else if (LevelObjectType.ITEM_POWER.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.ITEM_POWER;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Item
				else if (LevelObjectType.ITEM.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.ITEM;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// MainItem
				else if (LevelObjectType.MAIN_ITEM.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.MAIN_ITEM;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Goal
				else if (LevelObjectType.GOAL.sameColor(currentPixel)) {
					obj = new LevelObject();
					obj.type = LevelObjectType.GOAL;
					obj.position.set(pixelX, posY);
					objects.add(obj);
				}

				// Color de objecto/pixel desconocido
				else {
					// canal de color rojo
					int r = 0xff & (currentPixel >>> 24);
					// Canal de color verde
					int g = 0xff & (currentPixel >>> 16);
					// Canal de color azul
					int b = 0xff & (currentPixel >>> 8);
					// Canal alpha
					int a = 0xff & currentPixel;
					Gdx.app.error("Error. " + filename, "Objeto desconocido en x<"
							+ pixelX + "> y<" + pixelY + ">: r<" + r + "> g<"
							+ g + "> b<" + b + "> a<" + a + ">");
				}
			}
		}
		pixmap.dispose();
		Gdx.app.debug("Debug. ", "Nivel '" + filename + "' cargado");
		return objects;
	}

}
