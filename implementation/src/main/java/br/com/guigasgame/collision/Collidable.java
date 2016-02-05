package br.com.guigasgame.collision;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public abstract class Collidable implements CollisionListener
{
	protected List<CollisionListener> listenerList;
	protected BodyDef bodyDef;
	protected Body body;
	
	public Collidable(Vec2 position)
	{
		bodyDef = new BodyDef();
		bodyDef.position = position;
		listenerList = new ArrayList<CollisionListener>();
	}
	
	@Override
	public void endContact(Collidable collidable, Contact contact)
	{
		for( CollisionListener listener : listenerList )
		{
			listener.endContact(collidable, contact);
		}		
	}

	@Override
	public void beginContact(Collidable collidable, Contact contact)
	{
		for( CollisionListener listener : listenerList )
		{
			listener.beginContact(collidable, contact);
		}
	}

	public final BodyDef getBodyDef()
	{
		return bodyDef;
	}
	
	public final void attachBody(World world)
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

	public final void addListener(CollisionListener listener)
	{
		this.listenerList.add(listener);
	}

	public final void removeListener(CollisionListener listener)
	{
		this.listenerList.remove(listener);
	}
}
