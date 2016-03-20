package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.projectile.shuriken.Shuriken;


public class HitByShurikenAction extends GameHeroAction
{
	private Shuriken shuriken;
	private FixtureSensorID fixtureSensorID;
	private PlayableGameHero owner;

	public HitByShurikenAction(Shuriken shuriken, FixtureSensorID fixtureSensorID, PlayableGameHero owner)
	{
		this.shuriken = shuriken;
		this.fixtureSensorID = fixtureSensorID;
		this.owner = owner;
	}

	@Override
	protected void childExecute(PlayableGameHero hit)
	{
		if (!hit.isInvincible())
		{
			owner.getFragCounter().incrementShootsOnTarget();
			hit.getHit(shuriken.getProperties().damage, fixtureSensorID);
			hit.getFragCounter().incrementHitAsTarget();
			if (hit.isPlayerDead())
			{
				owner.getFragCounter().incrementKills();
			}
			shuriken.markToDestroy();
		}
		else
		{
			shuriken.initializeAutoDestruction();
			System.out.println("Absorved shuriken");
		}
	}

}
