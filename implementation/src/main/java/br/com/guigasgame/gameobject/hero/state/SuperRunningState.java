package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.VerticalJumpAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;

public class SuperRunningState extends HeroState 
{
	private float secondsRemaining;
	private Float minHorizontalSpeed;

	protected SuperRunningState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_SUPER_RUNNING);
		Float duration = heroStatesProperties.property.get("minDuration");
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;
		
		minHorizontalSpeed = heroStatesProperties.property.get("minHorizontalSpeed");
		if (minHorizontalSpeed == null)
			minHorizontalSpeed = 10f;

	}
	
	@Override
	public boolean canExecute(PlayableGameHero hero)
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
			gameHero.addAction(new VerticalJumpAction(heroStatesProperties));
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
		else if (!isHeroInputMapPressed(HeroInputKey.ACTION) && secondsRemaining <= 0)
		{
			setState(new RunningHeroState(gameHero));
		}
	}
}
