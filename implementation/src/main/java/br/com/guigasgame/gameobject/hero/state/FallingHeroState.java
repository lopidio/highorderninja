package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.AnimationsRepositoryCentral.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;

public class FallingHeroState extends OnAirHeroState {

	public FallingHeroState(GameHero gameHero) {
		super(new Vec2(2, 10), false, Animation.createAnimation(AnimationsRepositoryCentral.getHeroAnimationRepository()
				.getAnimationsProperties(HeroAnimationsIndex.HERO_STANDING)), gameHero, 0);
	}

	@Override
	public void updateState(float deltaTime) {
		super.updateState(deltaTime);
		if (gameHero.isTouchingGround())
		{
			setState(new StandingState(gameHero));
		}
	}
	
}
