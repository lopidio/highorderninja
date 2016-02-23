package br.com.guigasgame.raycast;

import java.util.Comparator;
import java.util.Vector;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.IntegerMask;

public class RayCastAllFixturesOnTheWay implements RayCastCallback
{
	
	
	
	private Vector<RayCastCallBackWrapper> callBackWrapperVector;
	private IntegerMask mask;

	public RayCastAllFixturesOnTheWay(World world, Vec2 from, Vec2 to, IntegerMask integerMask)
	{
		this.mask = integerMask;
		callBackWrapperVector = new Vector<>();

		System.out.println("MyMask:   " + Integer.toBinaryString(mask.value));
		world.raycast(this, from, to);
		
		callBackWrapperVector.sort(new Comparator<RayCastCallBackWrapper>()
		{

			@Override
			public int compare(RayCastCallBackWrapper a, RayCastCallBackWrapper b)
			{
				return (int) (b.fraction - a.fraction);
			}
		});
	}
	

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		IntegerMask fixtureMask = new IntegerMask(fixture.getFilterData().categoryBits);
		
		System.out.println("Category: " + Integer.toBinaryString(fixtureMask.value) + "\t\tfraction: " + fraction);
		if (mask.matches(fixtureMask.value))
		{
			System.out.println("Add");
			callBackWrapperVector.add(new RayCastCallBackWrapper(fixture, point, normal, fraction));
		}
		return 1;
	}

	public Vector<RayCastCallBackWrapper> getCallBackWrapperVector()
	{
		return callBackWrapperVector;
	}
	
	
}
