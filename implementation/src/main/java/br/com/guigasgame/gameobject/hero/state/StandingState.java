package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.AnimationsRepositoryCentral.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

public class StandingState extends OnGroundState {
	public StandingState(GameHero gameHero) {
		super(new Vec2(10, 10), true, true, Animation.createAnimation(AnimationsRepositoryCentral
						.getHeroAnimationRepository().getAnimationsProperties(HeroAnimationsIndex.HERO_STANDING)),
				gameHero, 20, 10);
	}

	@Override
	public void inputPressed(HeroInputKey key) {
		if (key == HeroInputKey.JUMP && !gameHero.isFallingDown()) {
			jump();
		} else if (key == HeroInputKey.ACTION) {
			System.out.println("Action");
		} else if (gameHero.isTouchingGround() && key == HeroInputKey.DOWN) {
			setState(new DuckingState(gameHero));
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

}
