package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


public class GetHitAction extends GameHeroAction
{
	private float damage;
	private FixtureSensorID fixtureSensorID;
	private PlayableGameHero owner;

	public GetHitAction(float damage, FixtureSensorID fixtureSensorID, PlayableGameHero owner)
	{
		this.damage = damage;
		this.fixtureSensorID = fixtureSensorID;
		this.owner = owner;
	}

	@Override
	protected void childExecute(PlayableGameHero hit)
	{
		if (!hit.isInvencible())
		{
			owner.getFragCounter().incrementShootsOnTarget();
			hit.getHit(damage, fixtureSensorID);
			hit.getFragCounter().incrementHitAsTarget();
			if (hit.isPlayerDead())
			{
				owner.getFragCounter().incrementKills();
			}
		}
	}

}
