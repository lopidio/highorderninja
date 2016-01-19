package br.com.guigasgame.gameobject;

public abstract class Collidable {
	enum Type
	{
		GAME_HERO,
		PROJECTILE,
		WALL,
		ROPE,
		ITEM,
	}
	private Type type;
	public abstract void handleCollision(Collidable collidable);
	public Type getType() {
		return type;
	}
}
