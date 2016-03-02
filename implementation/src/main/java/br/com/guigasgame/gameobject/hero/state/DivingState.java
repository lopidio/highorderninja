package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.DiveAction;
import br.com.guigasgame.gameobject.hero.action.StopMovementAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


public class DivingState extends HeroState
{

	protected DivingState(RoundGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_DIVING);
		setAnimationsColor(Color.MAGENTA);
	}
	
	@Override
	protected void jump()
	{
		// do nothing
	}

	@Override
	public void stateOnEnter()
	{
		gameHero.addAction(new DiveAction(new StopMovementAction(heroStatesProperties)));
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
		if (gameHero.getCollidableHero().isTouchingGround() || !gameHero.getCollidableHero().isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
	}

}
