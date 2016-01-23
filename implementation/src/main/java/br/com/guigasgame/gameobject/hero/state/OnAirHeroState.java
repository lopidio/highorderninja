package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

class OnAirHeroState extends HeroState{

	protected OnAirHeroState(Vec2 maxSpeed, boolean canJump, Animation animation, GameHero gameHero, float jumpAcceleration) {
		super(null, maxSpeed, true, canJump, true, animation, gameHero, 2,
				jumpAcceleration);
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
