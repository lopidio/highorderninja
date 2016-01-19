package br.com.guigasgame.gameobject;

import br.com.guigasgame.animation.Drawable;
import br.com.guigasgame.animation.UpdatableFromTime;

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
	public void handleCollision(Collidable collidable) {
		//Default implementation
	}
}
