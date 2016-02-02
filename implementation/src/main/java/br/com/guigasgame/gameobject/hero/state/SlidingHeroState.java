package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.MoveForwardHeroAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class SlidingHeroState extends HeroState
{

	private float secondsRemaining;

	protected SlidingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_SLIDING);
		secondsRemaining = 0.5f;
	}

	@Override
	public void updateState(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		gameHero.addAction(new MoveForwardHeroAction(gameHero, heroStatesProperties));
		if (secondsRemaining <= 0)
		{
			setState(new StandingHeroState(gameHero));
		}
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
