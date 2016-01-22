package br.com.guigasgame.collision;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;


public abstract class Collidable
{

	private Type type;
	protected Body body;

	enum Type
	{
		GAME_HERO, PROJECTILE, WALL, ROPE, ITEM,
	}

	public abstract void endContact(Collidable collidable);

	public abstract void beginContact(Collidable collidable);

	public abstract BodyDef getBodyDef(Vec2 position);

	public void attachBody(Body body)
	{
		this.body = body;
	}

	public Type getType()
	{
		return type;
	}

}
