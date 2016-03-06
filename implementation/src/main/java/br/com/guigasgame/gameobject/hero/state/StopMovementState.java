package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.action.StopMovementAction;


public class StopMovementState extends HeroState
{

	private float secondsRemaining;

	protected StopMovementState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_STOP);
//		setAnimationsColor(Color.YELLOW);
		secondsRemaining = 0.5f;
	}
	
	@Override
	protected void stateOnEnter()
	{
		gameHero.addAction(new StopMovementAction(heroStatesProperties));
	}
	
	@Override
	protected void shoot()
	{
		super.shoot();
		setState(new FallingHeroState(gameHero));
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
