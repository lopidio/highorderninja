package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class FallingHeroState extends HeroState
{

	public FallingHeroState(RoundGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_FALLING);
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
		if (gameHero.getCollidableHero().isTouchingGround())
		{
			setState(new StandingHeroState(gameHero));
		}
//		else if (!gameHero.getCollidableHero().isFallingDown())
//		{
//			setState(new StandingHeroState(gameHero));
//		}
	}

}
