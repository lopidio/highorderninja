package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;


public class StopMovementState extends HeroState
{

	private float secondsRemaining;

	protected StopMovementState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_DIVING);
		gameHero.getAnimation().setColor(Color.YELLOW);
		secondsRemaining = 0.5f;
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		if (secondsRemaining <= 0)
		{
			if (gameHero.getCollidableHero().isFallingDown())
			{
				setState(new FallingHeroState(gameHero));
			}
			else if (gameHero.getCollidableHero().isTouchingGround())
			{
				setState(new StandingHeroState(gameHero));
			}

		}
		else
		{
			gameHero.getCollidableHero().stopMovement();
		}

	}

}