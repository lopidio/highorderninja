package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidersFilters;


public class ProjectileAimer implements RayCastCallback
{

	private final float sightDistance;
	private final Vec2 initialDirection;
	private final int rayCastNumber;
	private final float angleRangeInRadians;
	private final Body body;

	private Vec2 finalDirection;
	
	public ProjectileAimer(float sightDistance, int rayCastNumber, Vec2 initialDirection, float angleRangeInRadians, Body initialBody)
	{
		super();
		this.sightDistance = sightDistance;
		this.rayCastNumber = rayCastNumber;
		this.angleRangeInRadians = angleRangeInRadians;
		this.body = initialBody;
		this.initialDirection = initialDirection.clone().mul(sightDistance);
		this.finalDirection = this.initialDirection;
		
		generateRayCasts();
	}


	private void generateRayCasts()
	{
		/*
		 * x' = x \cos \theta - y \sin \theta\,, y' = x \sin \theta + y \cos
		 * \theta\,.
		 */
		
		final float angle = angleRangeInRadians/(float)rayCastNumber; 
		World bodysWorld = body.getWorld();
		Vec2 initialPosition = body.getPosition();
		
		for( int i = 1; i <= rayCastNumber-1; ++i )
		{
			double sinAngle = Math.sin(angle*i); 
			double cosAngle = Math.cos(angle*i);

			Vec2 pointsToCounterClock = initialDirection.clone();
			pointsToCounterClock.x = (float) (pointsToCounterClock.x * cosAngle - pointsToCounterClock. y* sinAngle);
			pointsToCounterClock.y = (float) (pointsToCounterClock.x * sinAngle + pointsToCounterClock.y * cosAngle);
			bodysWorld.raycast(this, initialPosition, initialPosition.add(pointsToCounterClock));
			
			Vec2 pointsToClockWise = initialDirection.clone();
			pointsToClockWise.x = (float) (pointsToClockWise.x * cosAngle + pointsToClockWise.y * sinAngle);
			pointsToClockWise.y = (float) (pointsToClockWise.y * cosAngle - pointsToClockWise.x * sinAngle);
			bodysWorld.raycast(this, initialPosition, initialPosition.add(pointsToCounterClock));
			
		}
		bodysWorld.raycast(this, initialPosition, initialPosition.add(initialDirection));		
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		if (fixture.getFilterData().categoryBits == CollidersFilters.CATEGORY_SINGLE_BLOCK)
		{
			System.out.println("Hit!");
			float newDistance = point.sub(body.getPosition()).length();
			if (newDistance <= sightDistance && newDistance < finalDirection.length())
			{
				finalDirection = point.sub(body.getPosition());
			}
		}
		return 1;
	}


	
	public Vec2 getFinalDirection()
	{
		finalDirection.normalize();
		return finalDirection;
	}

}
