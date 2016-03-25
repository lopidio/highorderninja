package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.projectile.Projectile;


public class HitByProjectileAction extends GameHeroAction
{
	private final Projectile projectile;
	private final FixtureSensorID fixtureSensorID;
	private final PlayableGameHero owner;

	public HitByProjectileAction(Projectile projectile, FixtureSensorID fixtureSensorID)
	{
		this.projectile = projectile;
		this.fixtureSensorID = fixtureSensorID;
		this.owner = projectile.getOwner();
	}

	@Override
	protected void childExecute(PlayableGameHero hit)
	{
		if (!hit.isInvincible())
		{
			owner.getFragCounter().incrementShootsOnTarget();
			hit.getHit(projectile.getProperties().damage, fixtureSensorID);
			hit.getFragCounter().incrementHitAsTarget();
			if (hit.isPlayerDead())
			{
				owner.getFragCounter().incrementKills();
			}
		}
		else
		{
			System.out.println("Absorved projectile");
		}
	}

}
