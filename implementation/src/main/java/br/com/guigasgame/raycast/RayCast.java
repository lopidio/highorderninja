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
		this.from = from;
		this.to = to;
		this.world = world;
	}	
	
	public abstract void shoot();
}
