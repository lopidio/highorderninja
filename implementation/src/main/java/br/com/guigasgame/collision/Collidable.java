package br.com.guigasgame.collision;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public abstract class Collidable implements CollidableContactListener
{
	protected List<CollidableContactListener> listenerList;
	protected BodyDef bodyDef;
	protected Body body;
	
	public Collidable(Vec2 position)
	{
		bodyDef = new BodyDef();
		bodyDef.position = position;
		listenerList = new ArrayList<CollidableContactListener>();
	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		for( CollidableContactListener listener : listenerList )
		{
			listener.beginContact(me, other, contact);
		}
	}

	@Override
	public void endContact(Object me, Object other, Contact contact)
	{
		for( CollidableContactListener listener : listenerList )
		{
			listener.endContact(me, other, contact);
		}
	}

	public final BodyDef getBodyDef()
	{
		return bodyDef;
	}
	
	public void attachToWorld(World world)
	{
		body = world.createBody(bodyDef);
		body.setUserData(this);
	}

	public final Body getBody()
	{
		return body;
	}
	
	public final Vec2 getPosition()
	{
		return body.getPosition();
	}

	public final void addListener(CollidableContactListener listener)
	{
		this.listenerList.add(listener);
	}

	public final void removeListener(CollidableContactListener listener)
	{
		this.listenerList.remove(listener);
	}
}
