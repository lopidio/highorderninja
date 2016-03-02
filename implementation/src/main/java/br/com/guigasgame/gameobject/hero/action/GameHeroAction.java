package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
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

	public final void preExecute(RoundGameHero gameHero)
	{
		if (decorated != null)
			decorated.preExecute(gameHero);
		childPreExecute(gameHero);
	}
	
	public final void postExecute(RoundGameHero gameHero)
	{
		if (decorated != null)
			decorated.postExecute(gameHero);
		childPostExecute(gameHero);
	}

	public boolean canExecute(RoundGameHero hero)
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
	
	public final void execute(RoundGameHero gameHero)
	{
		if (decorated != null)
			decorated.execute(gameHero);
		childExecute(gameHero);
	}

	protected abstract void childExecute(RoundGameHero hero);

	protected void childPreExecute(RoundGameHero hero)
	{
		//Hook method
	}
	
	protected void childPostExecute(RoundGameHero hero)
	{
		//Hook method
	}
	
	protected boolean childCanExecute(RoundGameHero hero)
	{
		return true;
	}

}
