package br.com.guigasgame.raycast;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.IntegerMask;

public class RayCastClosestFixture extends RayCast implements RayCastCallback
{
	private RayCastCallBackWrapper callBackWrapper;
	private IntegerMask mask;
	private float fraction;
	
	public RayCastClosestFixture(World world, Vec2 from, Vec2 to, IntegerMask integerMask)
	{
		super(world, from, to);
		fraction = from.sub(to).length();
		this.mask = integerMask;
	}
	
	public void shoot()
	{
		world.raycast(this, from, to);
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		IntegerMask fixtureMask = new IntegerMask(fixture.getFilterData().categoryBits);
		if (mask.matches(fixtureMask.value))
		{
			if (fraction < this.fraction)
			{
				callBackWrapper = new RayCastCallBackWrapper(fixture, point, normal, fraction);
			}
			this.fraction = fraction;
			return fraction;
		}
		return 1; //ignore
	}

	public float getFraction()
	{
		return fraction;
	}

	public RayCastCallBackWrapper getCallBackWrapper()
	{
		return callBackWrapper;
	}
	
}
