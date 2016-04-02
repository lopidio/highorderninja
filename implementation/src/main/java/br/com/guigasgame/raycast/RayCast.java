package br.com.guigasgame.raycast;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public abstract class RayCast implements RayCastCallback
{
	protected World world;
	protected Vec2 from;
	protected Vec2 to;
	
	public RayCast(World world, Vec2 from, Vec2 to)
	{
		this.from = from.clone();
		this.to = to.clone();
		this.world = world;
	}	
	
	public Vec2 getFrom()
	{
		return from.clone();
	}
	
	public void setFrom(Vec2 from)
	{
		this.from = from.clone();
	}

	public Vec2 getTo()
	{
		return to.clone();
	}
	
	public void setTo(Vec2 to)
	{
		this.to = to.clone();
	}



	public abstract void shoot();
}
