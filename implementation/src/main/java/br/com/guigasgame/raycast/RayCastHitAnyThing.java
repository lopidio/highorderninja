package br.com.guigasgame.raycast;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.IntegerMask;

public class RayCastHitAnyThing implements RayCastCallback
{
	
	private boolean hit;
	private IntegerMask mask;

	public RayCastHitAnyThing(World world, Vec2 from, Vec2 to, IntegerMask integerMask)
	{
		this.mask = integerMask;
		hit = false;
		world.raycast(this, from, to);
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		IntegerMask fixtureMask = new IntegerMask(fixture.getFilterData().categoryBits);
//		System.out.println("Raycast - fixture: " + fixtureMask.value + " | me: " + mask.value);
		if (mask.matches(fixtureMask.value))
		{
			hit = true;
			return 0;
		}
		return 1; //ignore
	}

	public boolean hasHit()
	{
		return hit;
	}
	

}
