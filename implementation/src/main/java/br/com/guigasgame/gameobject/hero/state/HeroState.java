package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.GameHero;

public abstract class HeroState {
	private HeroState previousState;
	private float maxSpeed;
	private boolean canShoot;
	private boolean canUseRope;
//	private float durationSeconds;
	Animation animation;
	public abstract void entry(GameHero gameHero);
	public void setPreviousState(HeroState state) {
		previousState = state;
	}
	
	public abstract void updateState(GameHero gameHero, float deltaTime);

}
