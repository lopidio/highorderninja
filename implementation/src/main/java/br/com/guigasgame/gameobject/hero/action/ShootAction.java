package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;


public class ShootAction extends GameHeroAction
{
	Vec2 pointingDirection;

	public ShootAction(HeroStateProperties heroStateProperties, Vec2 pointingDirection)
	{
		super(heroStateProperties);
		this.pointingDirection = pointingDirection;
	}
	
	@Override
	public boolean childCanExecute(PlayableGameHero hero)
	{
		return (heroStateProperties.shoot != null);
	}

	@Override
	public void childExecute(PlayableGameHero gameHero)
	{
		gameHero.shoot(gameHero.getShuriken(pointingDirection));
	}

}
