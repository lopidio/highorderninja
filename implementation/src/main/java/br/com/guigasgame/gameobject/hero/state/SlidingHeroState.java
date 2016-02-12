package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.MoveForwardHeroAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class SlidingHeroState extends HeroState
{

	private float secondsRemaining;

	protected SlidingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_SLIDING);
		secondsRemaining = 0.5f;
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		gameHero.addAction(new MoveForwardHeroAction(heroStatesProperties));
		if (secondsRemaining <= 0)
		{
			if (gameHero.getCollidableHero().isMoving())
			{
				setState(new RunningHeroState(gameHero));
			}
			else if (gameHero.getCollidableHero().isFallingDown())
			{
				setState(new FallingHeroState(gameHero));
			}
			else
			{
				setState(new StandingHeroState(gameHero));
			}
		}
	}
	
	@Override
	protected void move(Side side)
	{
		//do nothing
	}
	
	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(gameHero));
		}
	}
	
}
