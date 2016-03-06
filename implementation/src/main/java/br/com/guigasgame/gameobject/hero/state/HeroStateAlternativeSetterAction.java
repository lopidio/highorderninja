package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.action.GameHeroAction;


public class HeroStateAlternativeSetterAction extends GameHeroAction
{
	private HeroState nextState;
	private HeroState alternativeState;

	public HeroStateAlternativeSetterAction(HeroState nextState, HeroState alternativeState)
	{
		this.nextState = nextState;
		this.alternativeState = alternativeState;
	}

	
	@Override
	public boolean canExecute(RoundGameHero hero)
	{
		if (nextState.canExecute(hero))
			return true;
		if (alternativeState.canExecute(hero))
		{
			nextState = alternativeState;
			return true;
		}
		return false;
	}

	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		gameHero.setState(nextState);
	}

}
