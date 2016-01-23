package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class JumpingHeroState extends HeroState
{
	protected JumpingHeroState(boolean canJumpAgain, GameHero gameHero)
	{
		super(null, new Vec2(20, 10), true, true, true, HeroAnimationsIndex.HERO_ASCENDING,
				gameHero, 2, 30);
	}

	@Override
	public void onEnter()
	{
		jump();
	}

	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (canJump && key == HeroInputKey.JUMP)
		{
			jump();
			canJump = false;
		}
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
			/*else
				if (gameHero.isTouchingGround() && key == HeroInputKey.DOWN)
				{
					setState(new DuckingState(gameHero));
				}*/
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
