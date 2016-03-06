package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;
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
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.move != null);
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.setForwardSide(side);
	}

}
