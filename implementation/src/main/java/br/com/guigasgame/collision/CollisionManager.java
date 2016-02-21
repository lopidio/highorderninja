package br.com.guigasgame.collision;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.JointEdge;

public class CollisionManager implements ContactListener
{

	public void beginContact(Contact contact)
	{
		if (contact.isTouching())
		{
			Collidable objectA = (Collidable) contact.getFixtureA().getBody().getUserData();
			Collidable objectB = (Collidable) contact.getFixtureB().getBody().getUserData();

			
			reportToJointListenersBeginCollision( contact.getFixtureA().getBody().getJointList(), objectB);
			reportToJointListenersBeginCollision(contact.getFixtureB().getBody().getJointList(), objectA);
			reportToFixtureListenersBeginCollision((CollidableContactListener) contact.getFixtureA().getUserData(), objectB);
			reportToFixtureListenersBeginCollision((CollidableContactListener) contact.getFixtureB().getUserData(), objectA);
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

			reportToJointListenersEndCollision(contact.getFixtureA().getBody().getJointList(), objectB);
			reportToJointListenersEndCollision(contact.getFixtureB().getBody().getJointList(), objectA);
			reportToFixtureListenersEndCollision((CollidableContactListener) contact.getFixtureA().getUserData(), objectB);
			reportToFixtureListenersEndCollision((CollidableContactListener) contact.getFixtureB().getUserData(), objectA);
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

	private void reportToJointListenersBeginCollision(JointEdge jointEdge, Collidable objectB)
	{
		while (jointEdge != null && jointEdge.joint != null)
		{
			CollidableContactListener listener = (CollidableContactListener) jointEdge.joint.getUserData();
			if (listener != null)
				listener.beginContact(objectB);
			jointEdge = jointEdge.next;
		}
	}
	
	private void reportToJointListenersEndCollision(JointEdge jointEdge, Collidable objectA)
	{
		while (jointEdge != null && jointEdge.joint != null)
		{
			CollidableContactListener listener = (CollidableContactListener) jointEdge.joint.getUserData();
			if (listener != null)
				listener.endContact(objectA);
			jointEdge = jointEdge.next;
		}
	}

	private void reportToFixtureListenersBeginCollision(CollidableContactListener fixtureContactListener, Collidable objectB)
	{
		if (fixtureContactListener != null)
		{
			fixtureContactListener.beginContact(objectB);
		}
	}

	private void reportToFixtureListenersEndCollision(CollidableContactListener fixtureContactListener, Collidable collider)
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
