package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class OnAirHeroState extends HeroState
{

	protected OnAirHeroState(boolean canJump,
			HeroAnimationsIndex heroAnimationsIndex, GameHero gameHero,
			float jumpAcceleration)
	{
		super(null, new Vec2(20, 10), true, canJump, true, heroAnimationsIndex,
				gameHero, 2, jumpAcceleration);
	}

	@Override
	public void isPressed(HeroInputKey key)
	{

		if (key == HeroInputKey.LEFT)
		{
			moveLeft();
		}
		else
			if (key == HeroInputKey.RIGHT)
			{
				moveRight();
			}
		if (gameHero.isTouchingWallAhead())
		{
			if (key == HeroInputKey.LEFT
					&& gameHero.getForwardSide() == Side.LEFT)
				setState(new WallGrabHeroState(gameHero));
			else if (key == HeroInputKey.RIGHT
					&& gameHero.getForwardSide() == Side.RIGHT)
				setState(new WallGrabHeroState(gameHero));
		}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
