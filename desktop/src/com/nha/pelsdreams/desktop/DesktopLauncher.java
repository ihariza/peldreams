package com.nha.pelsdreams.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nha.pelsdreams.PelsDreamsGame;
import com.nha.pelsdreams.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Establece la configuración
		config.title = "Pel's Dreams";
		config.useGL30 = false;
		//Ancho y alto de la pantalla
		config.width = Constants.APP_WIDTH;
	    config.height = Constants.APP_HEIGHT;

	    config.fullscreen = false;  
	    // vSync
	    config.vSyncEnabled = true;
		new LwjglApplication(new PelsDreamsGame(), config);
	}
}
