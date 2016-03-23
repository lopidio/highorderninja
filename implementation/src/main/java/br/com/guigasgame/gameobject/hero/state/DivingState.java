package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.action.DiveAction;
import br.com.guigasgame.gameobject.hero.action.StopMovementAction;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class DivingState extends HeroState
{

	protected DivingState(PlayableGameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_DIVING);
//		setAnimationsColor(Color.MAGENTA);
	}
	
	@Override
	protected void jump()
	{
		// do nothing
	}
	
	@Override
	public void stateOnEnter()
	{
		gameHero.addAction(new DiveAction(heroStatesProperties).addPrevAction(new StopMovementAction(heroStatesProperties)));
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
