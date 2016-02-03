package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroState;


public class HeroStateSetterAction implements GameHeroAction
{

	GameHero gameHero;
	HeroState newState;

	public HeroStateSetterAction(GameHero gameHero, HeroState newState)
	{
		super();
		this.gameHero = gameHero;
		this.newState = newState;
	}


	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
