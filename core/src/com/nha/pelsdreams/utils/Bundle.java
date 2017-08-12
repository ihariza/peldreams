package com.nha.pelsdreams.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

/**
 * Paquete de idimas.
 * Obtiene el paquete de idiomas según la internacionalización
 * @author Nacho Herrera Ariza
 *
 */
public class Bundle {
	
	
	public static final Bundle instance = new Bundle();
	
	public I18NBundle i18n;
	
	private Bundle() {
		FileHandle baseFileHandle = Gdx.files.internal("i18n/Bundle");
		i18n = I18NBundle.createBundle(baseFileHandle);
	}
}
