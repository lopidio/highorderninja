package br.com.guigasgame.gameobject;

import br.com.guigasgame.animation.Drawable;
import br.com.guigasgame.animation.UpdatableFromTime;
import br.com.guigasgame.collision.Collidable;

public abstract class GameObject extends Collidable implements UpdatableFromTime, Drawable
{
	boolean alive;
	public boolean isAlive()
	{
		return alive;
	}
	
	public void markToDestroy()
	{
		alive = false;
	}
	
	@Override
	public void beginContact(Collidable collidable) {
		//Default implementation
	}

	@Override
	public void endContact(Collidable collidable) {
		//Default implementation
	}
}
