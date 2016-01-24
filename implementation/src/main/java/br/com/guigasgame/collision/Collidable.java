package br.com.guigasgame.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;


public abstract class Collidable
{

	protected Body body;

	public abstract void endContact(Collidable collidable);

	public abstract void beginContact(Collidable collidable);

	protected abstract BodyDef getBodyDef(Vec2 position);
	
	public abstract void attachBody(World world, Vec2 bodyPosition);


	public final Body getBody()
	{
		return body;
	}

}
