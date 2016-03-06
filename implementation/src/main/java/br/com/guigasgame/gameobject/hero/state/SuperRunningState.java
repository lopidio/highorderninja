package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

public class SuperRunningState extends HeroState 
{
	private float secondsRemaining;
	private Float minHorizontalSpeed;

	protected SuperRunningState(RoundGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_SUPER_RUNNING);
		Float duration = heroStatesProperties.property.get("minDuration");
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;
		
		minHorizontalSpeed = heroStatesProperties.property.get("minHorizontalSpeed");
		if (minHorizontalSpeed == null)
			minHorizontalSpeed = 10f;

	}
	
	@Override
	public boolean canExecute(RoundGameHero hero)
	{
		if (Math.abs(hero.getCollidableHero().getBodyLinearVelocity().x) >= minHorizontalSpeed)
			return true;
		return false;
	}

	
	@Override
	protected void stateDoubleTapInput(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			setState(new StopMovementState(gameHero));
		}
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
		else if (!gameHero.isTouchingGround())
		{
			if (gameHero.getCollidableHero().isFallingDown())
				setState(new FallingHeroState(gameHero));
			if (gameHero.getCollidableHero().isAscending())
				setState(new JumpingHeroState(gameHero));
		}
		else if (!isHeroInputPressed(HeroInputKey.ACTION) && secondsRemaining <= 0)
		{
			setState(new RunningHeroState(gameHero));
		}
	}
}
