package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


public class GetHitAction extends GameHeroAction
{
	private float damage;
	private FixtureSensorID fixtureSensorID;

	public GetHitAction(float damage, FixtureSensorID fixtureSensorID)
	{
		this.damage = damage;
		this.fixtureSensorID = fixtureSensorID;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.getHit(damage, fixtureSensorID);
	}

}
