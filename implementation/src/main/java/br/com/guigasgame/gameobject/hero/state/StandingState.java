package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationRepository.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

public class StandingState extends OnGroundState {

	public StandingState(GameHero gameHero) {
		super(null,
				new Vec2(10, 10), true, true, true, Animation.createAnimation(AnimationsRepositoryCentral
						.getHeroAnimationRepository().getResource(HeroAnimationsIndex.HERO_STANDING)),
				gameHero, 20, 10);

	}

	@Override
	public void inputPressed(HeroInputKey key) {
		if (key == HeroInputKey.JUMP) {
			jump();
		} else if (key == HeroInputKey.ACTION) {
			System.out.println("Action");
		}
	}

	@Override
	public void isPressed(HeroInputKey key) {

		if (key == HeroInputKey.LEFT) {
			moveLeft();
		} else if (key == HeroInputKey.RIGHT) {
			moveRight();
		}
	}

	@Override
	public void entry() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateState(float deltaTime) {
		// TODO Auto-generated method stub

	}

}
