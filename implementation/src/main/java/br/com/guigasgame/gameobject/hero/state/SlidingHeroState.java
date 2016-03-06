package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class SlidingHeroState extends HeroState
{

	private float secondsRemaining;

	protected SlidingHeroState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_SLIDING);
		Float duration = heroStatesProperties.property.get("duration");
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		gameHero.addAction(new MoveHeroAction(gameHero.getForwardSide(), heroStatesProperties));
		if (secondsRemaining <= 0)
		{
			if (gameHero.getCollidableHero().isMoving())
			{
				setState(new RunningHeroState(gameHero));
			}
			else
			{
				setState(new StandingHeroState(gameHero));
			}
		}
		if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
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
