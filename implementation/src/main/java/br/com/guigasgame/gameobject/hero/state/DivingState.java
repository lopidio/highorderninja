package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


public class DivingState extends HeroState
{

	protected DivingState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_DIVING);
		gameHero.getSprite().setColor(Color.MAGENTA);
	}

	@Override
	public void onEnter()
	{
		gameHero.stopMovement();
		gameHero.applyImpulse(new Vec2(0, heroStatesProperties.jumpImpulse));
	}

	@Override
	public void stateInputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ACTION)
		{
			setState(new StopMovementState(gameHero));
		}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isTouchingGround() && !gameHero.isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}

}
