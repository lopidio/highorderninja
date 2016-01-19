package br.com.guigasgame.collision;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.gameobject.GameObject;

public class CollisionManager implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		if (contact.isTouching()) {
			GameObject objectA = (GameObject) contact.getFixtureA().getBody().getUserData();
			GameObject objectB = (GameObject) contact.getFixtureB().getBody().getUserData();

			reportToFixtureListenersBeginCollision((FixtureContactListener) contact.getFixtureA().getUserData(), objectB);
			reportToFixtureListenersBeginCollision((FixtureContactListener) contact.getFixtureB().getUserData(), objectA);
			reportCollidableBeginCollision(objectA, objectB);
			reportCollidableBeginCollision(objectB, objectA);
		}
	}

	@Override
	public void endContact(Contact contact) {
		if (contact.isTouching()) {
			GameObject objectA = (GameObject) contact.getFixtureA().getUserData();
			GameObject objectB = (GameObject) contact.getFixtureB().getUserData();

			reportToFixtureListenersEndCollision((FixtureContactListener) contact.getFixtureA().getUserData(), objectB);
			reportToFixtureListenersEndCollision((FixtureContactListener) contact.getFixtureB().getUserData(), objectA);
			reportCollidableEndCollision(objectA, objectB);
			reportCollidableEndCollision(objectB, objectA);
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	private void reportToFixtureListenersBeginCollision(FixtureContactListener fixtureContactListener,
			GameObject collider) {
		if (fixtureContactListener != null) {
			fixtureContactListener.beginContact(collider);
		}
	}

	private void reportToFixtureListenersEndCollision(FixtureContactListener fixtureContactListener, GameObject collider) {
		if (fixtureContactListener != null) {
			fixtureContactListener.endContact(collider);
		}
	}

	private void reportCollidableBeginCollision(GameObject first, GameObject second) {
		if (first != null) {
			if (second == null || first.collidesWith(second.getType())) {
				first.beginContact(second);
			}
		}
	}

	private void reportCollidableEndCollision(GameObject first, GameObject second) {
		if (first != null) {
			if (second == null || first.collidesWith(second.getType())) {
				first.endContact(second);
			}
		}
	}
}
