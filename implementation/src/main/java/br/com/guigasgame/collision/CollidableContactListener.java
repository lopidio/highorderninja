package br.com.guigasgame.collision;


public interface CollidableContactListener
{
	public void endContact(Collidable collidable);

	public void beginContact(Collidable collidable);
}
