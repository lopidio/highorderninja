package br.com.guigasgame.gameobject.hero.sensors;

import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableContactListener;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


public class HeroFixtureController implements CollidableContactListener
{
	private FixtureSensorID sensorID;
	private int touchingContacts;
	public HeroFixtureController(FixtureSensorID sensorID)
	{
		this.sensorID = sensorID;
	}
	
	public FixtureSensorID getSensorID()
	{
		return sensorID;
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		++touchingContacts;
	}

	@Override
	public void endContact(Object me, Object other, Contact contact)
	{
		--touchingContacts;
	}

	public boolean isTouching()
	{
		return touchingContacts > 0;
	}
}
