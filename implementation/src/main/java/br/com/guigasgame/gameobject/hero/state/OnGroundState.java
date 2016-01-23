package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.gameobject.hero.GameHero;


abstract class OnGroundState extends HeroState
{

	protected OnGroundState(Vec2 maxSpeed,
			boolean canShoot, boolean canJump,
			Animation animation, GameHero gameHero,
			float horizontalAcceleration, float jumpAcceleration)
	{
		super(null, maxSpeed, canShoot, canJump, false,
				animation, gameHero, jumpAcceleration, jumpAcceleration);
	}

	@Override
	public void updateState(float deltaTime) {
		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
