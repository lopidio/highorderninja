package br.com.guigasgame.gameobject.hero.executor;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStatesProperties;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileDirection;
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

	public void shoot(Vec2 position, ProjectileIndex projectile, ProjectileDirection direction)
	{
		System.out.println(direction);
		if (heroStatesProperties.canShoot)
			gameHero.shoot(new Projectile(projectile, direction, position));
	}

	public static ProjectileDirection createDirection(boolean up, boolean right, boolean down, boolean left)
	{
		if (up)
		{
			if (right)
				return ProjectileDirection.UP_RIGHT;
			else if (left)
				return ProjectileDirection.UP_LEFT;
			else
				return ProjectileDirection.UP;
		}
		else if (down)
		{
			if (right)
				return ProjectileDirection.DOWN_RIGHT;
			else if (left)
				return ProjectileDirection.DOWN_LEFT;
			else
				return ProjectileDirection.DOWN;
		}
		else
		{
			if (right)
				return ProjectileDirection.RIGHT;
			else if (left)
				return ProjectileDirection.LEFT;
			else
				return ProjectileDirection.NONE;
		}
	}

}
