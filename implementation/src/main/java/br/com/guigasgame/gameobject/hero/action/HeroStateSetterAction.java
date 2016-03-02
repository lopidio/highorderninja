package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroState;


public class HeroStateSetterAction extends GameHeroAction
{

	HeroState newState;

	public HeroStateSetterAction(HeroState newState)
	{
		super();
		this.newState = newState;
	}
	
	@Override
	public boolean canExecute(RoundGameHero hero)
	{
		return newState.canExecute(hero);
	}

	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		gameHero.setState(newState);
	}

}
