package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.CollisionDisablerAction;
import br.com.guigasgame.gameobject.hero.action.CollisionEnablerAction;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
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
	
	private boolean isAbleToQuitSliding()
	{
		return secondsRemaining <= 0 && !gameHero.getCollidableHero().isHeadTouchingAnything() || gameHero.isTouchingWallAhead();
	}

	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		gameHero.addAction(new MoveHeroAction(gameHero.getForwardSide(), heroStatesProperties));
		if (isAbleToQuitSliding())
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
		else if (!gameHero.isTouchingGround())
		{
			if (gameHero.getCollidableHero().isFallingDown())
				setState(new FallingHeroState(gameHero));
			if (gameHero.getCollidableHero().isAscending())
				setState(new JumpingHeroState(gameHero));
		}
	}
	
	@Override
	public void stateOnEnter()
	{
		gameHero.addAction(new CollisionDisablerAction(FixtureSensorID.HEAD));
	}

	@Override
	protected void stateOnQuit()
	{
		gameHero.addAction(new CollisionEnablerAction(FixtureSensorID.HEAD));
	}

	
	@Override
	protected void move(Side side)
	{
		//do nothing
	}
	
}
