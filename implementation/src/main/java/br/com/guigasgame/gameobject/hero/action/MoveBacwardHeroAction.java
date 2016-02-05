package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class MoveBacwardHeroAction extends GameHeroAction
{
	MoveHeroAction decorator;

	public MoveBacwardHeroAction(HeroStateProperties heroStatesProperties)
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
		decorator = new MoveHeroAction(gameHero.getForwardSide().opposite(), heroStateProperties);
		return decorator.canExecute(gameHero);
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