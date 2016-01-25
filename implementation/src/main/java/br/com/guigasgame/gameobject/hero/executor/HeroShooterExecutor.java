package br.com.guigasgame.gameobject.hero.executor;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class HeroShooterExecutor
{

	private final HeroStatesProperties heroStatesProperties;
	private final GameHero gameHero;

	public HeroShooterExecutor(HeroStatesProperties heroStatesProperties, GameHero gameHero)
	{
		this.heroStatesProperties = heroStatesProperties;
		this.gameHero = gameHero;
	}

	public void shoot(Vec2 position, ProjectileIndex projectile, Vec2 direction)
	{
		if (heroStatesProperties.canShoot)
			gameHero.shoot(new Projectile(projectile, direction, position));
	}

}
