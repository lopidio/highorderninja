package br.com.guigasgame.gameobject.hero.sensors;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableContactListener;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;


public class HeroFixtureController implements CollidableContactListener
{
	private FixtureSensorID sensorID;
	private List<Vec2> normals;
	public HeroFixtureController(FixtureSensorID sensorID)
	{
		this.sensorID = sensorID;
		normals = new ArrayList<>();
	}
	
	public FixtureSensorID getSensorID()
	{
		return sensorID;
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		normals.add(contact.getManifold().localNormal);
	}

	@Override
	public void endContact(Object me, Object other, Contact contact)
	{
		normals.remove(contact.getManifold().localNormal);		
	}

	public boolean isTouching()
	{
		return normals.size() > 0;
	}

	public Vec2 contactsAverageNormal()
	{
		Vec2 sum = null;
		for( Vec2 normal : normals )
		{
			if (sum == null)
				sum = normal.clone();
			else
				sum.addLocal(normal);
		}
		if (sum != null)
			sum.normalize();
		return sum;
	}
}
