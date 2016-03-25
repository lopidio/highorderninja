package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class StopHorizontalMovementAction extends GameHeroAction
{

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.getCollidableHero().getBodyLinearVelocity().x = 0;
	}

}
