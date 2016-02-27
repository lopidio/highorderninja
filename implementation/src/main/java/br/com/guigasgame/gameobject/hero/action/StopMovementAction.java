package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class StopMovementAction extends GameHeroAction
{

	public StopMovementAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}

	@Override
	protected void childExecute(GameHero hero)
	{
		hero.getCollidableHero().stopMovement();
	}
}
