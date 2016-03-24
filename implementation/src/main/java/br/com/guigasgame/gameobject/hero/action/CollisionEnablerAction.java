package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


public class CollisionEnablerAction extends GameHeroAction
{

	private FixtureSensorID sensorID;

	public CollisionEnablerAction(FixtureSensorID sensorID)
	{
		this.sensorID = sensorID;
	}

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.getCollidableHero().enableCollision(sensorID);
	}

}
