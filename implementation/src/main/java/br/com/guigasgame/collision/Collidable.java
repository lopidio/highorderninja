package br.com.guigasgame.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;


public abstract class Collidable
{

	protected Body body;
	private BodyDef bodyDef;
	
	public Collidable(Vec2 position)
	{
		bodyDef = new BodyDef();
		bodyDef.position = position;
	}

	protected abstract void editBodyDef(BodyDef bodyDef);

	public abstract void endContact(Collidable collidable);

	public abstract void beginContact(Collidable collidable);

	public final BodyDef getBodyDef()
	{
		return bodyDef;
	}
	
	public final void attachBody(World world)
	{
		editBodyDef(bodyDef);
		body = world.createBody(bodyDef);
		attachFixturesToBody(body);
	}
	
	protected void attachFixturesToBody(Body body)
	{
		
	}

	public final Body getBody()
	{
		return body;
	}

}
