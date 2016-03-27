package br.com.guigasgame.round.hud.controller;

import org.jsfml.system.Vector2i;

import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;

public abstract class HudObject implements UpdatableFromTime, Drawable, Destroyable, ResizableByView
{

	protected boolean dead;

	@Override
	public void markToDestroy()
	{
		dead = true;
	}
	
	@Override
	public void update(float deltaTime)
	{
		//hook
	}

	@Override
	public boolean isMarkedToDestroy()
	{
		return dead;
	}
	
	@Override
	public void setViewSize(Vector2i size)
	{
		//hook
	}

}
