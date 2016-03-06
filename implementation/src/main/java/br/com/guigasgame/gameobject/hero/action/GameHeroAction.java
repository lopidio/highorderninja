package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;

public abstract class GameHeroAction
{
	protected final HeroStateProperties heroStateProperties;
	protected GameHeroAction decorated;
	
	protected GameHeroAction(HeroStateProperties heroStateProperties)
	{
		this.heroStateProperties = heroStateProperties;
		this.decorated = null;
	}
	
	public GameHeroAction addPrevAction(GameHeroAction decorator)
	{
		GameHeroAction decorated = decorator;
		while (decorated != null)
			decorated = decorated.decorated;
		this.decorated = decorator;
		return this;
	}
	
	public GameHeroAction()
	{
		this.decorated = null;
		this.heroStateProperties = null;
	}

	public final void preExecute(PlayableGameHero gameHero)
	{
		if (decorated != null)
			decorated.preExecute(gameHero);
		childPreExecute(gameHero);
	}
	
	public final void postExecute(PlayableGameHero gameHero)
	{
		if (decorated != null)
			decorated.postExecute(gameHero);
		childPostExecute(gameHero);
	}

	public boolean canExecute(PlayableGameHero hero)
	{
		if (decorated != null)
		{
			if (decorated.canExecute(hero))
			{
				return childCanExecute(hero);
			}
			return false;
		}
		else
		{
			return childCanExecute(hero);
		}
	}
	
	public final void execute(PlayableGameHero gameHero)
	{
		if (decorated != null)
			decorated.execute(gameHero);
		childExecute(gameHero);
	}

	protected abstract void childExecute(PlayableGameHero hero);

	protected void childPreExecute(PlayableGameHero hero)
	{
		//Hook method
	}
	
	protected void childPostExecute(PlayableGameHero hero)
	{
		//Hook method
	}
	
	protected boolean childCanExecute(PlayableGameHero hero)
	{
		return true;
	}

}
