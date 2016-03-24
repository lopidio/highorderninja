package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


public class CollisionDisablerAction extends GameHeroAction
{
	private FixtureSensorID sensorID;

	public CollisionDisablerAction(FixtureSensorID sensorID)
	{
		this.sensorID = sensorID;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.getCollidableHero().disableCollision(sensorID);
	}

}
