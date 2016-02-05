package br.com.guigasgame.collision;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionManager implements ContactListener
{

	public void beginContact(Contact contact)
	{
		if (contact.isTouching())
		{
			Collidable objectA = (Collidable) contact.getFixtureA().getBody().getUserData();
			Collidable objectB = (Collidable) contact.getFixtureB().getBody().getUserData();

			reportToFixtureListenersBeginCollision((FixtureContactListener) contact.getFixtureA().getUserData(), objectB);
			reportToFixtureListenersBeginCollision((FixtureContactListener) contact.getFixtureB().getUserData(), objectA);
			reportCollidableBeginCollision(objectA, objectB, contact);
			reportCollidableBeginCollision(objectB, objectA, contact);
		}
	}

	public void endContact(Contact contact)
	{
		// if (contact.isTouching())
		{
			Collidable objectA = (Collidable) contact.getFixtureA().getBody().getUserData();
			Collidable objectB = (Collidable) contact.getFixtureB().getBody().getUserData();

			reportToFixtureListenersEndCollision((FixtureContactListener) contact.getFixtureA().getUserData(), objectB);
			reportToFixtureListenersEndCollision((FixtureContactListener) contact.getFixtureB().getUserData(), objectA);
			reportCollidableEndCollision(objectA, objectB, contact);
			reportCollidableEndCollision(objectB, objectA, contact);
		}
	}

	public void preSolve(Contact contact, Manifold oldManifold)
	{
		// TODO Auto-generated method stub

	}

	public void postSolve(Contact contact, ContactImpulse impulse)
	{
		// TODO Auto-generated method stub
	}

	private void reportToFixtureListenersBeginCollision(FixtureContactListener fixtureContactListener, Collidable objectB)
	{
		if (fixtureContactListener != null)
		{
			fixtureContactListener.beginContact(objectB);
		}
	}

	private void reportToFixtureListenersEndCollision(FixtureContactListener fixtureContactListener, Collidable collider)
	{
		if (fixtureContactListener != null)
		{
			fixtureContactListener.endContact(collider);
		}
	}

	private void reportCollidableBeginCollision(Collidable objectA, Collidable objectB, Contact contact)
	{
		if (objectA != null)
		{
			objectA.beginContact(objectB, contact);
		}
	}

	private void reportCollidableEndCollision(Collidable first, Collidable second, Contact contact)
	{
		if (first != null)
		{
			first.endContact(second, contact);
		}
	}
}
