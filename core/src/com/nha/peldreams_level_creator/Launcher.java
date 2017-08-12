package com.nha.peldreams_level_creator;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pel's Dreams Level Creator";
		config.width = 300;
	    config.height = 300;
		new LwjglApplication(new LevelCreator(), config);
	}

}
