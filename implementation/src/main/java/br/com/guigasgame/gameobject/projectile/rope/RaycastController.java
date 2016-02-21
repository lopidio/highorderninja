package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

public class RaycastController implements RayCastCallback
{
	
	private Fixture fixture;
	private Vec2 point;
	private Vec2 normal;
	private float fraction;
	private Vec2 to;
	private Object from;
	private World world;
	
	private RaycastController(World world, Vec2 from, Vec2 to)
	{
		fixture = null;
		point = null;
		normal = null;
		fraction = -1;
		
		this.from = from;
		this.to = to;
		this.world = world;
		world.raycast(this, from, to);
	}
	
	public static RaycastController getClosest(World world, Vec2 from, Vec2 to)
	{
		return new RaycastController(world, from, to);
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		return 0;
	}

}
