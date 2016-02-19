package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class RunningHeroState extends HeroState
{

	protected RunningHeroState(GameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_RUNNING);
	}

	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(gameHero));
		}
		else if (key == HeroInputKey.SLIDE)
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
		else if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}
}
