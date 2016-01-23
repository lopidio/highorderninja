package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;

public class FallingHeroState extends OnAirHeroState {

	public FallingHeroState(GameHero gameHero) {
		super(false, Animation.createAnimation(AnimationsRepositoryCentral.getHeroAnimationRepository()
				.getAnimationsProperties(HeroAnimationsIndex.HERO_FALLING)), gameHero, 0);
	}

	@Override
	public void updateState(float deltaTime) {
		if (gameHero.isTouchingGround())
		{
			setState(new StandingState(gameHero));
		}
	}
	
}
