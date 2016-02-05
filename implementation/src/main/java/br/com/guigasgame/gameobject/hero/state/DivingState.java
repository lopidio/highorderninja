package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.DiveAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


public class DivingState extends HeroState
{

	protected DivingState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_DIVING);
		gameHero.getDrawable().getSprite().setColor(Color.MAGENTA);
	}

	@Override
	public void onEnter()
	{
		gameHero.addAction(new DiveAction(gameHero, new Vec2(0, heroStatesProperties.jump.impulse)));
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
	public void stateUpdate(float deltaTime)
	{
		if (gameHero.getCollidableHero().isTouchingGround() && !gameHero.getCollidableHero().isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}

}
