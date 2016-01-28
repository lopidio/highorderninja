package br.com.guigasgame.gameobject.hero.executor;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.gameobject.projectile.Shuriken;


public class HeroShooterExecutor
{

	private final HeroStatesProperties heroStatesProperties;
	private final GameHero gameHero;

	public HeroShooterExecutor(HeroStatesProperties heroStatesProperties, GameHero gameHero)
	{
		this.heroStatesProperties = heroStatesProperties;
		this.gameHero = gameHero;
	}

	public void shoot(Shuriken shuriken)
	{
		if (heroStatesProperties.canShoot)
			gameHero.shoot(shuriken);
	}

}
