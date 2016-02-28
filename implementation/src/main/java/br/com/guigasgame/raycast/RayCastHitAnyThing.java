package br.com.guigasgame.raycast;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.IntegerMask;

public class RayCastHitAnyThing extends RayCast implements RayCastCallback
{
	
	private boolean hit;
	private IntegerMask mask;
	private RayCastCallBackWrapper callBackWrapper;
	
	public RayCastHitAnyThing(World world, Vec2 from, Vec2 to, IntegerMask integerMask)
	{
		super(world, from, to);
		
		this.mask = integerMask;
	}
	
	@Override
	public void shoot() 
	{
		hit = false;
		world.raycast(this, from, to);
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		IntegerMask fixtureMask = new IntegerMask(fixture.getFilterData().categoryBits);
		if (mask.matches(fixtureMask.value))
		{
			callBackWrapper = new RayCastCallBackWrapper(fixture, point, normal, fraction);
			hit = true;
			return 0;
		}
		return 1; //ignore
	}

	public boolean hasHit()
	{
		return hit;
	}

	public RayCastCallBackWrapper getCallBackWrapper()
	{
		return callBackWrapper;
	}

}
