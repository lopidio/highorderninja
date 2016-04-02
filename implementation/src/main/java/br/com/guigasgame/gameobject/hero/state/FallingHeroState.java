package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.side.Side;


class FallingHeroState extends HeroState
{

	public FallingHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_FALLING);
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
	protected void stateInputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION && isHeroInputMapPressed(HeroInputKey.DOWN) ||
			 (inputValue == HeroInputKey.DOWN && isHeroInputMapPressed(HeroInputKey.ACTION)))
		{
			setState(new DivingState(gameHero));
		}
	}
	
	@Override
	protected void stateDoubleTapInput(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.DOWN)
			setState(new DivingState(gameHero));
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
