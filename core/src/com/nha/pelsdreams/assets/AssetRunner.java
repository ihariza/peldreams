package com.nha.pelsdreams.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Representa la imagen del runner
 * 
 * @author Nacho Herrera
 * 
 */
public class AssetRunner {

	public final Animation anim_run;
	public final Animation anim_jump;
	public final Animation anim_jump_rising;
	public final Animation anim_jump_falling;
	public final Animation anim_dodging;
	public final Animation anim_dodgingReversed;
	public final Animation anim_dodged;
	public final Animation anim_dead_rock;
	public final Animation anim_dead_enemy;
	public final Animation anim_dead_enemy_end;
	public final ParticleEffect dustParticles;
	public final ParticleEffect fallParticles;

	public AssetRunner(TextureAtlas atlas, ParticleEffect dustEffect,
			ParticleEffect fallEffect) {
		// Animación correr
		Array<AtlasRegion> regRun = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regRun_1 = atlas.findRegion("pel", 1);
		AtlasRegion regRun_2 = atlas.findRegion("pel", 2);
		AtlasRegion regRun_3 = atlas.findRegion("pel", 3);
		AtlasRegion regRun_4 = atlas.findRegion("pel", 4);
		AtlasRegion regRun_5 = atlas.findRegion("pel", 5);
		regRun.add(regRun_1);
		regRun.add(regRun_2);
		regRun.add(regRun_3);
		regRun.add(regRun_4);
		regRun.add(regRun_5);

		anim_run = new Animation(1.0f / 20.0f, regRun,
				Animation.PlayMode.LOOP_PINGPONG);

		// Animación inicio de salto
		Array<AtlasRegion> regJump = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regJump_1 = atlas.findRegion("pel", 6);
		AtlasRegion regJump_2 = atlas.findRegion("pel", 7);
		regJump.add(regJump_1);
		regJump.add(regJump_2);
		regJump.add(regJump_1);

		anim_jump = new Animation(1.0f / 30.0f, regJump,
				Animation.PlayMode.NORMAL);

		// Animación salto subiendo
		Array<AtlasRegion> regJumpRising = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regJumpRising_1 = atlas.findRegion("pel", 8);
		regJumpRising.add(regJumpRising_1);

		anim_jump_rising = new Animation(1.0f / 20.0f, regJumpRising,
				Animation.PlayMode.NORMAL);

		// Animación salto cayendo
		Array<AtlasRegion> regJumpFalling = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regJumpFalling_1 = atlas.findRegion("pel", 9);
		regJumpFalling.add(regJumpFalling_1);

		anim_jump_falling = new Animation(1.0f / 20.0f, regJumpFalling,
				Animation.PlayMode.LOOP_PINGPONG);

		// Animación de corriendo a esquivar
		Array<AtlasRegion> regDodging = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regDodging_1 = atlas.findRegion("pel", 20);
		AtlasRegion regDodging_2 = atlas.findRegion("pel", 21);
		regDodging.add(regDodging_1);
		regDodging.add(regDodging_2);

		anim_dodging = new Animation(1.0f / 30.0f, regDodging,
				Animation.PlayMode.NORMAL);

		// Animación de esquivar a corriendo.
		regDodging.reverse();
		anim_dodgingReversed = new Animation(1.0f / 30.0f, regDodging,
				Animation.PlayMode.NORMAL);

		// Animación esquivar
		Array<AtlasRegion> regDodged = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regDodged_1 = atlas.findRegion("pel", 22);
		AtlasRegion regDodged_2 = atlas.findRegion("pel", 23);
		AtlasRegion regDodged_3 = atlas.findRegion("pel", 24);
		regDodged.add(regDodged_1);
		regDodged.add(regDodged_2);
		regDodged.add(regDodged_3);

		anim_dodged = new Animation(1.0f / 20.0f, regDodged,
				Animation.PlayMode.LOOP_PINGPONG);

		// Animación muerte por roca
		Array<AtlasRegion> regDeadRock = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regDeadRock_1 = atlas.findRegion("pel", 10);
		regDeadRock.add(regDeadRock_1);

		anim_dead_rock = new Animation(1.0f / 20.0f, regDeadRock,
				Animation.PlayMode.NORMAL);

		// Animación muerte por enemigo
		Array<AtlasRegion> regDeadEnemy = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regDeadEnemy_1 = atlas.findRegion("pel", 11);
		AtlasRegion regDeadEnemy_2 = atlas.findRegion("pel", 12);
		AtlasRegion regDeadEnemy_3 = atlas.findRegion("pel", 13);
		AtlasRegion regDeadEnemy_4 = atlas.findRegion("pel", 14);

		regDeadEnemy.add(regDeadEnemy_1);
		regDeadEnemy.add(regDeadEnemy_2);
		regDeadEnemy.add(regDeadEnemy_3);
		regDeadEnemy.add(regDeadEnemy_4);

		anim_dead_enemy = new Animation(1.0f / 15.0f, regDeadEnemy,
				Animation.PlayMode.NORMAL);

		// Animación final muerte por enemigo
		Array<AtlasRegion> regDeadEnemyEnd = new Array<TextureAtlas.AtlasRegion>();
		AtlasRegion regDeadEnemyEnd_1 = atlas.findRegion("pel", 14);
		AtlasRegion regDeadEnemyEnd_2 = atlas.findRegion("pel", 15);
		AtlasRegion regDeadEnemyEnd_3 = atlas.findRegion("pel", 16);
		AtlasRegion regDeadEnemyEnd_4 = atlas.findRegion("pel", 17);
		AtlasRegion regDeadEnemyEnd_5 = atlas.findRegion("pel", 18);
		AtlasRegion regDeadEnemyEnd_6 = atlas.findRegion("pel", 19);
		regDeadEnemyEnd.add(regDeadEnemyEnd_1);
		regDeadEnemyEnd.add(regDeadEnemyEnd_2);
		regDeadEnemyEnd.add(regDeadEnemyEnd_3);
		regDeadEnemyEnd.add(regDeadEnemyEnd_4);
		regDeadEnemyEnd.add(regDeadEnemyEnd_5);
		regDeadEnemyEnd.add(regDeadEnemyEnd_6);
		anim_dead_enemy_end = new Animation(1.0f / 15.0f, regDeadEnemyEnd,
				Animation.PlayMode.NORMAL);

		// Efectos de Partículas
		dustParticles = dustEffect;
		fallParticles = fallEffect;
	}
}
