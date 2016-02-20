package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;

public abstract class GameHeroAction
{
	protected final HeroStateProperties heroStateProperties;
	protected final GameHeroAction decorator;
	
	protected GameHeroAction(HeroStateProperties heroStateProperties)
	{
		this.heroStateProperties = heroStateProperties;
		this.decorator = null;
	}
	
	public GameHeroAction(GameHeroAction decorator)
	{
		this.decorator = decorator;
		this.heroStateProperties = decorator.heroStateProperties;
	}

	
	public GameHeroAction()
	{
		this.decorator = null;
		this.heroStateProperties = null;
	}

	public final void preExecute(GameHero gameHero)
	{
		if (decorator != null)
			decorator.preExecute(gameHero);
		childPreExecute(gameHero);
	}
	
	public final void postExecute(GameHero gameHero)
	{
		if (decorator != null)
			decorator.postExecute(gameHero);
		childPostExecute(gameHero);
	}

	public boolean canExecute(GameHero hero)
	{
		if (decorator != null)
		{
			if (decorator.canExecute(hero))
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
		if (decorator != null)
			decorator.execute(gameHero);
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
