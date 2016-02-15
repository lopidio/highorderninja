package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class DuckingState extends HeroState
{

	protected DuckingState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_DUCKING);
		gameHero.getAnimation().setColor(Color.BLUE);
	}

	@Override
	public void onEnter()
	{
		gameHero.getCollidableHero().disableCollision(FixtureSensorID.HEAD);
	}

	@Override
	public void onQuit()
	{
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
