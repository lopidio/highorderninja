package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class DuckingState extends HeroState
{

	protected DuckingState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_DUCKING);
//		setAnimationsColor(Color.BLUE);
	}

	@Override
	public void stateOnEnter()
	{
		//TODO has to be an action
		gameHero.getCollidableHero().disableCollision(FixtureSensorID.HEAD);
	}

	@Override
	protected void stateOnQuit()
	{
		//TODO has to be an action
		gameHero.getCollidableHero().enableCollision(FixtureSensorID.HEAD);
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
