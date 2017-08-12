package com.nha.pelsdreams.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nha.pelsdreams.utils.Constants;
/**
 * Representa los assets para la UI
 * @author Nacho Herrera
 *
 */
public class AssetInGameUI {
	// Boton de menu
	public ButtonStyle menuButtonStyle;
	// Boton de control saltar
	public ButtonStyle upControlButtonStyle;
	// Boton de control esquivar
	public ButtonStyle downControlButtonStyle;
	public Skin skin;

	public AssetInGameUI(TextureAtlas atlas) {
		skin = new Skin(Gdx.files.internal(Constants.SKIN_INGAME_UI), atlas);

		// Filtro suavizado para las texturas
		for (Texture t : skin.getAtlas().getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.getFont("default-font").getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.getFont("big-font").getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.getFont("yellow-font").getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.getFont("red-font").getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		menuButtonStyle = new ButtonStyle();
		menuButtonStyle.up = skin.getDrawable("menu_up");
		menuButtonStyle.down = skin.getDrawable("menu_down");

		upControlButtonStyle = new ButtonStyle();
		upControlButtonStyle.up = skin.getDrawable("upbtn_up");
		upControlButtonStyle.down = skin.getDrawable("upbtn_down");

		downControlButtonStyle = new ButtonStyle();
		downControlButtonStyle.up = skin.getDrawable("downbtn_up");
		downControlButtonStyle.down = skin.getDrawable("downbtn_down");
	}
}
