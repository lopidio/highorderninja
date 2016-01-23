package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;

public class FallingHeroState extends OnAirHeroState {

	public FallingHeroState(GameHero gameHero) {
		super(false, HeroAnimationsIndex.HERO_FALLING, gameHero, 0);
	}

	@Override
	public void updateState(float deltaTime) {
//		super.updateState(deltaTime);
		if (gameHero.isTouchingGround() && !gameHero.isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}
	
}
