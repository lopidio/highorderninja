package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class MoveBacwardHeroAction implements GameHeroAction
{

	GameHero gameHero;
	HeroStateProperties heroStatesProperties;
	MoveHeroAction decorator;

	public MoveBacwardHeroAction(GameHero gameHero, HeroStateProperties heroStatesProperties)
	{
		this.gameHero = gameHero;
		this.heroStatesProperties = heroStatesProperties;
		decorator = new MoveHeroAction(gameHero, gameHero.getForwardSide().opposite(), heroStatesProperties);
	}
	
	@Override
	public void preExecute()
	{
		decorator.preExecute();
	}
	
	@Override
	public boolean canExecute()
	{
		return decorator.canExecute();
	}
	
	@Override
	public void postExecute()
	{
		decorator.postExecute();
	}

	@Override
	public void execute()
	{
		decorator.execute();
	}

}