package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public class RegeneratesLifeAction extends GameHeroAction
{
	
	private int lifeToRegenerate;
	
	public RegeneratesLifeAction(int lifeToRegenerate) 
	{
		this.lifeToRegenerate = lifeToRegenerate;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.regeneratesLife(lifeToRegenerate);
	}

}
