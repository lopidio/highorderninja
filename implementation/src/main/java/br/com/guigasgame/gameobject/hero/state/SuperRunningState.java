package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

public class SuperRunningState extends HeroState 
{
	private float secondsRemaining;

	protected SuperRunningState(GameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_SUPER_RUNNING);
		setAnimationsColor(Color.MAGENTA);
		
		Float duration = heroStatesProperties.property.get("minDuration");
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;		
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		if (gameHero.isTouchingWallAhead())
		{
			gameHero.addAction(new JumpAction(heroStatesProperties));
			setState(new WallRidingState(gameHero));
		}
		else if (!gameHero.getCollidableHero().isMoving())
		{
			setState(new StandingHeroState(gameHero));
		}
		else if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
		else if (!isHeroInputPressed(HeroInputKey.ACTION) && secondsRemaining <= 0)
		{
			setState(new RunningHeroState(gameHero));
		}
	}
}
