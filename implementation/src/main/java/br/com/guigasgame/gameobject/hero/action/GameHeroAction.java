package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;

public abstract class GameHeroAction
{
	protected final HeroStateProperties heroStateProperties;
	protected final GameHeroAction decorated;
	
	protected GameHeroAction(HeroStateProperties heroStateProperties)
	{
		this.heroStateProperties = heroStateProperties;
		this.decorated = null;
	}
	
	public GameHeroAction(GameHeroAction decorator)
	{
		this.decorated = decorator;
		this.heroStateProperties = decorator.heroStateProperties;
	}

	
	public GameHeroAction()
	{
		this.decorated = null;
		this.heroStateProperties = null;
	}

	public final void preExecute(GameHero gameHero)
	{
		if (decorated != null)
			decorated.preExecute(gameHero);
		childPreExecute(gameHero);
	}
	
	public final void postExecute(GameHero gameHero)
	{
		if (decorated != null)
			decorated.postExecute(gameHero);
		childPostExecute(gameHero);
	}

	public boolean canExecute(GameHero hero)
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
	
	public final void execute(GameHero gameHero)
	{
		if (decorated != null)
			decorated.execute(gameHero);
		childExecute(gameHero);
	}

	protected abstract void childExecute(GameHero hero);

	protected void childPreExecute(GameHero hero)
	{
		//Hook method
	}
	
	protected void childPostExecute(GameHero hero)
	{
		//Hook method
	}
	
	protected boolean childCanExecute(GameHero hero)
	{
		return true;
	}

}
