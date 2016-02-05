package br.com.guigasgame.collision;

import org.jbox2d.dynamics.contacts.Contact;


public interface CollisionListener
{
	public default void beginContact(Collidable collidable, Contact contact)
	{
		
	}

	public default void endContact(Collidable collidable, Contact contact)
	{
		
	}

}
