package br.com.guigasgame.collision;

import java.util.List;

public abstract class Collidable {
	private Type type;
	private List<Type> collidesWith;
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
	public boolean collidesWith(Type otherTypes) {
		if (otherTypes == null)
		{
			return true;
		}
		for (Type type : collidesWith) {
			if (type == otherTypes)
				return true;
		}
		return false;
	}

}
