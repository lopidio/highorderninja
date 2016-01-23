package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.AnimationsRepositoryCentral.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

public class DuckingState extends OnGroundState {

	protected DuckingState(GameHero gameHero) {
		super(null, new Vec2(0,0), false, true, false, Animation.createAnimation(AnimationsRepositoryCentral
				.getHeroAnimationRepository().getAnimationsProperties(HeroAnimationsIndex.HERO_STANDING)),
				gameHero, 0,
				30);
	}

	@Override
	public void entry() {

	}

	@Override
	public void updateState(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputReleased(HeroInputKey inputValue) {
		if (inputValue == HeroInputKey.DOWN)
		{
			setState(new StandingState(gameHero));
		}
	}
	
}
