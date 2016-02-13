package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
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
	public boolean childCanExecute(GameHero hero)
	{
		return (heroStateProperties.rope != null);
	}

	@Override
	public void childExecute(GameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
