package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.side.Side;


public class SideOrientationHeroSetter extends GameHeroAction
{
	Side side;

	public SideOrientationHeroSetter(Side side, HeroStateProperties heroStatesProperties)
	{
		super(heroStatesProperties);
		this.side = side;
	}
	
	@Override
	public boolean childCanExecute(RoundGameHero hero)
	{
		return (heroStateProperties.move != null);
	}

	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		gameHero.setForwardSide(side);
	}

}
