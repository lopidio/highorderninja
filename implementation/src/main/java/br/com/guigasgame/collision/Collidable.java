package br.com.guigasgame.collision;

import org.jbox2d.dynamics.Body;

public abstract class Collidable {
	private Type type;
	protected Body body;
	enum Type
	{
		GAME_HERO,
		PROJECTILE,
		WALL,
		ROPE,
		ITEM,
	}

	public abstract void endContact(Collidable collidable);
	public abstract void beginContact(Collidable collidable);
	public Type getType() {
		return type;
	}

}
