package br.com.guigasgame.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;


public abstract class Collidable
{
	
	private Body body;
	private BodyDef bodyDef;
	
	public Collidable(Vec2 position)
	{
		bodyDef = new BodyDef();
		bodyDef.position = position;
	}

	protected abstract void editBodyDef(BodyDef bodyDef);

	public abstract void endContact(Collidable collidable, Contact contact);

	public abstract void beginContact(Collidable collidable, Contact contact);

	protected abstract void editBody(Body body);

	public final BodyDef getBodyDef()
	{
		return bodyDef;
	}
	
	public final void attachBody(World world)
	{
		editBodyDef(bodyDef);
		body = world.createBody(bodyDef);
		body.setUserData(this);
		editBody(body);
	}
	

	public final Body getBody()
	{
		return body;
	}

}
