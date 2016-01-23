package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class DuckingState extends HeroState
{

	protected DuckingState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_STANDING);
	}

	@Override
	public void onEnter()
	{
		gameHero.disableCollision(FixtureSensorID.HEAD);
	}

	@Override
	public void onQuit()
	{
		gameHero.enableCollision(FixtureSensorID.HEAD);
	}
	
	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}

	@Override
	public void inputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.DOWN)
		{
			setState(new StandingHeroState(gameHero));
		}
	}

	@Override
	public void inputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.JUMP)
		{
			setState(new SuperJumpingHeroState(gameHero));
		}
	}
}
