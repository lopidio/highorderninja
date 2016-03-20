package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.side.Side;


class RunningHeroState extends HeroState
{

	protected RunningHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_RUNNING);
	}

	@Override
	protected void stateInputIsPressing(HeroInputKey inputValue) 
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			setState(new SuperRunningState(gameHero));
		}
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
		if (!gameHero.getCollidableHero().isMoving())
		{
			setState(new StandingHeroState(gameHero));
		}
		else if (!gameHero.isTouchingGround())
		{
			if (gameHero.getCollidableHero().isFallingDown())
				setState(new FallingHeroState(gameHero));
			if (gameHero.getCollidableHero().isAscending())
				setState(new JumpingHeroState(gameHero));
		}

		Side side = gameHero.getCollidableHero().movingToSide();
		if (side != Side.UNKNOWN && side != gameHero.getForwardSide())
		{
			if (side == Side.LEFT && !isHeroInputMapPressed(HeroInputKey.RIGHT))
				gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));
			if (side == Side.RIGHT && !isHeroInputMapPressed(HeroInputKey.LEFT))
				gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));
		}

	}
}
