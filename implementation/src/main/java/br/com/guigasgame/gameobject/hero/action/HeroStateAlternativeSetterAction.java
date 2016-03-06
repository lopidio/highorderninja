package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroState;


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
	public boolean canExecute(PlayableGameHero hero)
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
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.setState(nextState);
	}

}
