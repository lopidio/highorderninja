package br.com.guigasgame.gameobject.hero.sensors;

import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableContactListener;


public class SensorController implements CollidableContactListener
{

	private int touchingContacts;
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
