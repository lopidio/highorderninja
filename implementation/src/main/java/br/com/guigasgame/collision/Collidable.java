package br.com.guigasgame.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;


public abstract class Collidable
{

	protected Body body;

	public abstract void endContact(Collidable collidable);

	public abstract void beginContact(Collidable collidable);

	public abstract BodyDef getBodyDef(Vec2 position);

	public final Body getBody()
	{
		return body;
	}

}
