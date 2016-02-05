package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


public class FallingHeroState extends HeroState
{

	public FallingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_FALLING);
	}
	
	@Override
	protected void stateInputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			setState(new DivingState(gameHero));
		}
	}

	@Override
	public void stateInputIsPressing(HeroInputKey key)
	{
		if (gameHero.isTouchingWallAhead())
		{
			if (key == HeroInputKey.LEFT && gameHero.getForwardSide() == Side.LEFT)
			{
				setState(new WallGrabHeroState(gameHero));
			}
			else if (key == HeroInputKey.RIGHT && gameHero.getForwardSide() == Side.RIGHT)
			{
				setState(new WallGrabHeroState(gameHero));
			}
		}
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.isTouchingGround() && !gameHero.isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}

}
