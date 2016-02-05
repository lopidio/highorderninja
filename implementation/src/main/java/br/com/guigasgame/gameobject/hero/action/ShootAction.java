package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;



public class ShootAction extends GameHeroAction
{
	Projectile projectile;	

	public ShootAction(HeroStateProperties heroStateProperties, Projectile projectile)
	{
		super(heroStateProperties);
		this.projectile = projectile;
	}
	
	@Override
	public boolean canExecute(GameHero hero)
	{
		return (heroStateProperties.shoot != null);
	}

	@Override
	public void execute(GameHero gameHero)
	{
		gameHero.shoot(projectile);
	}

}
