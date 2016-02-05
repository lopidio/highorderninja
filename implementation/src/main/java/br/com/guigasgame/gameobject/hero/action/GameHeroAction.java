package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;



public abstract class GameHeroAction
{
	protected final HeroStateProperties heroStateProperties;
	
	protected GameHeroAction(HeroStateProperties heroStateProperties)
	{
		this.heroStateProperties = heroStateProperties;
	}
	
	public abstract void execute(GameHero hero);

	public void preExecute(GameHero hero)
	{
		
	}
	
	public void postExecute(GameHero hero)
	{
		
	}
	
	public boolean canExecute(GameHero hero)
	{
		return true;
	}
}
