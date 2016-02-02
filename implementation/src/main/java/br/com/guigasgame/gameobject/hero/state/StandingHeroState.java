package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class StandingHeroState extends HeroState
{

	public StandingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_STANDING);
	}

	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(gameHero));
		}
		else if (key == HeroInputKey.DOWN)
		{
			setState(new DuckingState(gameHero));
		}
		else if (key == HeroInputKey.SLIDE)
		{
			setState(new SlidingHeroState(gameHero));
		}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
		else if (gameHero.isMoving())
		{
			setState(new RunningHeroState(gameHero));
		}
	}

	@Override
	public void isPressed(HeroInputKey key)
	{

		if (/* gameHero.isTouchingGround() && */key == HeroInputKey.DOWN)
		{
			setState(new DuckingState(gameHero));
		}
		else if (key == HeroInputKey.LEFT)
		{
			gameHero.addAction(new MoveHeroAction(gameHero, Side.LEFT, heroStatesProperties));
		}
		else if (key == HeroInputKey.RIGHT)
		{
			gameHero.addAction(new MoveHeroAction(gameHero, Side.RIGHT, heroStatesProperties));
		}
	}

}
