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
	protected void childExecute(PlayableGameHero poorPlayer)
	{
		if (!poorPlayer.isInvincible())
		{
			owner.getFragCounter().incrementShootsOnTarget();
			poorPlayer.getHit(projectile.getProperties().damage, fixtureSensorID);
			poorPlayer.getFragCounter().incrementHitAsTarget();
			if (poorPlayer.isPlayerDead())
			{
				if (poorPlayer.getHeroProperties().getHeroTeam().getTeamId() != owner.getHeroProperties().getHeroTeam().getTeamId())
					owner.getFragCounter().incrementKills();
			}
		}
		else
		{
			System.out.println("Absorved projectile");
		}
	}

}
