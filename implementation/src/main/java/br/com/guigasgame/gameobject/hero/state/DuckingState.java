package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.CollisionDisablerAction;
import br.com.guigasgame.gameobject.hero.action.CollisionEnablerAction;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.side.Side;


class DuckingState extends HeroState
{

	protected DuckingState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_DUCKING);
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
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}
	
	@Override
	protected void move(Side side)
	{
		if (!gameHero.isTouchingWallAhead())
		{
			setState(new RunningHeroState(gameHero));
			super.move(side);
		}
		else
		{
			gameHero.addAction(new SideOrientationHeroSetter(side, heroStatesProperties));
		}
	}

	@Override
	public void stateInputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.DOWN)
		{
			setState(new StandingHeroState(gameHero));
		}
	}

	@Override
	protected void jump()
	{
		super.jump();
		setState(new JumpingHeroState(gameHero));
	}
	
}
