package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroState;


public class HeroStateSetterAction extends GameHeroAction
{

	private HeroState newState;

	public HeroStateSetterAction(HeroState newState)
	{
		super();
		this.newState = newState;
	}
	
	@Override
	public boolean canExecute(PlayableGameHero hero)
	{
		return newState.canExecute(hero);
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.setState(newState);
	}

}
