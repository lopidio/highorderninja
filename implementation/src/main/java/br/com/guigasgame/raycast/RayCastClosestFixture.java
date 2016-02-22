package br.com.guigasgame.raycast;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.IntegerMask;

public class RayCastClosestFixture implements RayCastCallback
{
	private Fixture fixture;
	private Vec2 point;
	private Vec2 normal;
	private float fraction;
	private IntegerMask mask;
	
	public RayCastClosestFixture(World world, Vec2 from, Vec2 to, IntegerMask integerMask)
	{
		fixture = null;
		point = null;
		normal = null;
		fraction = from.sub(to).length();
		this.mask = integerMask;
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
				this.fixture = fixture;
				this.point = point;
				this.normal = normal;
			}
			this.fraction = fraction;
			return fraction;
		}
		return 1; //ignore
	}

	
	public Vec2 getNormal()
	{
		return normal;
	}

	
	public void setNormal(Vec2 normal)
	{
		this.normal = normal;
	}

	
	public Fixture getFixture()
	{
		return fixture;
	}

	
	public Vec2 getPoint()
	{
		return point;
	}

	
	public float getFraction()
	{
		return fraction;
	}
	
	
	
}
