package br.com.guigasgame.gameobject.hero.executor;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;


public class HeroShooterExecutor
{

	private final HeroStatesProperties heroStatesProperties;
	private final GameHero gameHero;

	public HeroShooterExecutor(HeroStatesProperties heroStatesProperties, GameHero gameHero)
	{
		this.heroStatesProperties = heroStatesProperties;
		this.gameHero = gameHero;
	}

	public void shoot(Projectile projectile)
	{
		if (heroStatesProperties.canShoot)
			gameHero.shoot(projectile);
	}

}
