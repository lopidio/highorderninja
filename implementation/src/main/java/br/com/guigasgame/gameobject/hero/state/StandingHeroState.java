package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


public class StandingHeroState extends HeroState
{

	public StandingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_STANDING);
	}

	@Override
	protected void stateDoubleTapInput(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.LEFT || inputValue == HeroInputKey.RIGHT)
		{
			setState(new SlidingHeroState(gameHero));
		}
	}
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
		else if (gameHero.getCollidableHero().isAscending())
		{
			setState(new JumpingHeroState(gameHero));
		}
		else if (gameHero.getCollidableHero().isMoving())
		{
			setState(new RunningHeroState(gameHero));
		}
	}
	
	@Override
	protected void move(Side side)
	{
		if (!gameHero.isTouchingWallAhead())
		{
			gameHero.addAction(new MoveHeroAction(side, heroStatesProperties));
		}
		else
		{
			gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));
		}
	}
		
	@Override
	public void stateInputIsPressing(HeroInputKey key)
	{
		if (/* gameHero.isTouchingGround() && */key == HeroInputKey.DOWN)
		{
			setState(new DuckingState(gameHero));
		}
	}

}
