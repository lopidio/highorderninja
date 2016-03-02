package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;



public class ShootRopeAction extends GameHeroAction
{
	Projectile projectile;	

	public ShootRopeAction(HeroStateProperties heroStateProperties, Projectile projectile)
	{
		super(heroStateProperties);
		this.projectile = projectile;
	}
	
	@Override
	public boolean childCanExecute(RoundGameHero hero)
	{
		return (heroStateProperties.rope != null);
	}

	@Override
	public void childExecute(RoundGameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
