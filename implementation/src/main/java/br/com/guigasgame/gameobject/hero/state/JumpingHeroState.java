package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class JumpingHeroState extends OnAirHeroState
{
	protected JumpingHeroState(boolean canJumpAgain, GameHero gameHero)
	{
		super(canJumpAgain, HeroAnimationsIndex.HERO_ASCENDING, gameHero, 30);
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

	
	
}
