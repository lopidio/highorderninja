package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroState;


public class HeroStateSetterAction extends GameHeroAction
{

	HeroState newState;

	public HeroStateSetterAction(HeroState newState)
	{
		super(null);
		this.newState = newState;
	}


	@Override
	public void execute(GameHero gameHero)
	{
		gameHero.setState(newState);
	}

}
