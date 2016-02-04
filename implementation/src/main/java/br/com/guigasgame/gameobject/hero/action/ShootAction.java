package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;



public class ShootAction implements GameHeroAction
{
	GameHero gameHero;
	Projectile projectile;	


	
	public ShootAction(GameHero gameHero, Projectile projectile)
	{
		super();
		this.gameHero = gameHero;
		this.projectile = projectile;
	}



	@Override
	public void execute()
	{
		gameHero.shoot(projectile);
	}

}
