package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class GotDeadHeroAction extends GameHeroAction
{

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.die();
	}

}
