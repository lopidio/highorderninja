package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class MoveForwardHeroAction extends GameHeroAction
{

	MoveHeroAction decorator;

	public MoveForwardHeroAction(HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
	}
	
	@Override
	public void preExecute(GameHero gameHero)
	{
		decorator.preExecute(gameHero);
	}
	
	@Override
	public boolean canExecute(GameHero gameHero)
	{
		if (heroStateProperties.move != null)
		{
			decorator = new MoveHeroAction(gameHero.getForwardSide(), heroStateProperties);
			return decorator.canExecute(gameHero);
		}
		return false;
	}
	
	@Override
	public void postExecute(GameHero gameHero)
	{
		decorator.postExecute(gameHero);
	}

	@Override
	public void execute(GameHero gameHero)
	{
		decorator.execute(gameHero);
	}

}
