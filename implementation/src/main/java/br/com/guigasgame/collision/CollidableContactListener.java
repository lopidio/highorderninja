package br.com.guigasgame.collision;

import org.jbox2d.dynamics.contacts.Contact;

public interface CollidableContactListener
{
	public default void endContact(Object me, Object other, Contact contact)
	{
		
	}

	public default void beginContact(Object me, Object other, Contact contact)
	{
		
	}
}
