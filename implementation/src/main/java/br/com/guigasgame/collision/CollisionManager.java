package br.com.guigasgame.collision;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionManager implements ContactListener
{

	public void beginContact(Contact contact)
	{
		if (contact.isTouching())
		{
			Fixture fixtureA = contact.getFixtureA();
			Fixture fixtureB = contact.getFixtureB();

			reportToFixtureListenersBeginCollision(fixtureA, fixtureB, contact);
			reportToFixtureListenersBeginCollision(fixtureB, fixtureA, contact);
			reportCollidableBeginCollision(fixtureA.getBody(), fixtureB.getBody(), contact);
			reportCollidableBeginCollision(fixtureB.getBody(), fixtureA.getBody(), contact);
		}
	}

	public void endContact(Contact contact)
	{
		// if (contact.isTouching())
		{
			Fixture fixtureA = contact.getFixtureA();
			Fixture fixtureB = contact.getFixtureB();
			
			reportToFixtureListenersEndCollision(fixtureA, fixtureB, contact);
			reportToFixtureListenersEndCollision(fixtureB, fixtureA, contact);
			reportCollidableEndCollision(fixtureA.getBody(), fixtureB.getBody(), contact);
			reportCollidableEndCollision(fixtureB.getBody(), fixtureA.getBody(), contact);
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

	private void reportToFixtureListenersBeginCollision(Fixture fixtureA, Fixture fixtureB, Contact contact)
	{
		CollidableContactListener listener = (CollidableContactListener) fixtureA.getUserData();
		if (listener != null)
		{
			listener.beginContact(fixtureA, fixtureB, contact);
		}
	}

	private void reportToFixtureListenersEndCollision(Fixture fixtureA, Fixture fixtureB, Contact contact)
	{
		CollidableContactListener listener = (CollidableContactListener) fixtureA.getUserData();
		if (listener != null)
		{
			listener.endContact(fixtureA, fixtureB, contact);
		}
	}

	private void reportCollidableBeginCollision(Body bodyA, Body bodyB, Contact contact)
	{
		CollidableContactListener listener = (CollidableContactListener) bodyA.getUserData();
		if (listener != null)
		{
			listener.beginContact(bodyA, bodyB, contact);
		}
	}

	private void reportCollidableEndCollision(Body bodyA, Body bodyB, Contact contact)
	{
		CollidableContactListener listener = (CollidableContactListener) bodyA.getUserData();
		if (listener != null)
		{
			listener.endContact(bodyA, bodyB, contact);
		}
	}
}
