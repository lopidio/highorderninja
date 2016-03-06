package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class StopMovementAction extends GameHeroAction
{

	public StopMovementAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.getCollidableHero().stopMovement();
	}
}
