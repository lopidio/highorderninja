package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.StopMovementAction;


public class StopMovementState extends HeroState
{

	private float secondsRemaining;

	protected StopMovementState(RoundGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_DIVING);
		setAnimationsColor(Color.YELLOW);
		secondsRemaining = 0.5f;
	}
	
	@Override
	protected void stateOnEnter()
	{
		gameHero.addAction(new StopMovementAction(heroStatesProperties));
	}
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		if (secondsRemaining <= 0)
		{
//			if (gameHero.getCollidableHero().isFallingDown())
			{
				setState(new FallingHeroState(gameHero));
			}
//			else if (gameHero.getCollidableHero().isTouchingGround())
//			{
//				setState(new StandingHeroState(gameHero));
//			}

		}
		else
		{
			gameHero.addAction(new StopMovementAction(heroStatesProperties));
		}

	}

}
