package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.CollidersFilters;


public class ProjectileAimer implements RayCastCallback
{

	private final float sightDistance;
	private final Vec2 initialDirection;
	private static final int rayCastNumber = 16;
	private static final float angleRangeInRadians = (float) (Math.PI
			/ (6 * rayCastNumber));
	private static final float sinAngle = (float) Math.sin(angleRangeInRadians);
	private static final float cosAngle = (float) Math.cos(angleRangeInRadians);
	private final Body body;

	private Vec2 finalDirection;

	public ProjectileAimer(float sightDistance, Vec2 initialDirection, Body initialBody)
	{
		super();
		this.sightDistance = sightDistance;
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

		World bodysWorld = body.getWorld();
		Vec2 initialPosition = body.getPosition();

		Vec2 pointsToCounterClock = initialDirection.clone();
		Vec2 pointsToClockWise = initialDirection.clone();

		for( int i = 1; i <= rayCastNumber - 1; ++i )
		{
			Vec2 tempCC = pointsToCounterClock.clone();
			pointsToCounterClock.x = (float) (tempCC.x * cosAngle
					- tempCC.y * sinAngle);
			pointsToCounterClock.y = (float) (tempCC.x * sinAngle
					+ tempCC.y * cosAngle);
			bodysWorld.raycast(this, initialPosition, initialPosition.add(tempCC));

			Vec2 tempCW = pointsToClockWise.clone();
			pointsToClockWise.x = (float) (tempCW.x * cosAngle
					+ tempCW.y * sinAngle);
			pointsToClockWise.y = (float) (tempCW.y * cosAngle
					- tempCW.x * sinAngle);
			bodysWorld.raycast(this, initialPosition, initialPosition.add(tempCW));
		}
		bodysWorld.raycast(this, initialPosition, initialPosition.add(initialDirection));
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		if (fixture.getFilterData().categoryBits == CollidersFilters.CATEGORY_SINGLE_BLOCK)
		{
			float newDistance = point.sub(body.getPosition()).length();
			if (newDistance <= sightDistance
					&& newDistance < finalDirection.length())
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
