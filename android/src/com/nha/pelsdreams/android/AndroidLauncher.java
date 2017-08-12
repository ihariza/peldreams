package com.nha.pelsdreams.android;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nha.pelsdreams.PelsDreamsGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Evita que la pantalla entre en modo dormido
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new PelsDreamsGame(), config);
	}
}
