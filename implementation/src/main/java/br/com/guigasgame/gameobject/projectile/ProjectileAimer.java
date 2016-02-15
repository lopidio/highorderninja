package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.CollidersFilters;


public class ProjectileAimer implements RayCastCallback
{

	private float maxDistance;
	private static int rayCastNumber = 16;
	private static float angleRangeInRadians = (float) (Math.PI/ (10 * rayCastNumber));
	
	private Vec2 finalDirection;
	private Vec2 bestDirection;

	private static float sinAngle = (float) Math.sin(angleRangeInRadians);
	private static float cosAngle = (float) Math.cos(angleRangeInRadians);
	
	private final Body body;
	private final Vec2 initialDirection;
	private final CollidersFilters collidesWith;
	private final CollidersFilters aimingAt;

	public ProjectileAimer(Body initialBody, Vec2 initialDirection, CollidersFilters collidesWith, CollidersFilters aimingAt)
	{
		super();
		this.maxDistance = 30; //default value
		this.body = initialBody;
		this.initialDirection = initialDirection.clone().mul(maxDistance);
		this.finalDirection = this.initialDirection;
		this.bestDirection = null;
		this.aimingAt = aimingAt;
		this.collidesWith = collidesWith.add(aimingAt); 
		
		generateRayCasts();
	}

	private void generateRayCasts()
	{
		/*
		 * x' = x \cos \theta - y \sin \theta\,, y' = x \sin \theta + y \cos
		 * \theta\,.
		 */

		Vec2 pointsToCounterClock = initialDirection.clone();
		Vec2 pointsToClockWise = initialDirection.clone();

		for( int i = 1; i <= rayCastNumber - 1; ++i )
		{
			Vec2 tempCC = pointsToCounterClock.clone();
			pointsToCounterClock.x = (float) (tempCC.x * cosAngle - tempCC.y * sinAngle);
			pointsToCounterClock.y = (float) (tempCC.x * sinAngle + tempCC.y * cosAngle);
			makeRaycast(tempCC);

			Vec2 tempCW = pointsToClockWise.clone();
			pointsToClockWise.x = (float) (tempCW.x * cosAngle + tempCW.y * sinAngle);
			pointsToClockWise.y = (float) (tempCW.y * cosAngle - tempCW.x * sinAngle);
			makeRaycast(tempCW);
		}
		makeRaycast(initialDirection);
	}
	
	private void makeRaycast(Vec2 pointTo)
	{
		Vec2 initialPosition = body.getPosition();
		World bodysWorld = body.getWorld();
		bodysWorld.raycast(this, initialPosition, initialPosition.add(pointTo));
		if (bestDirection != null && bestDirection.length() < finalDirection.length())
		{
			System.out.println("There is a best direction and it's better than the previous one");
			finalDirection = bestDirection;
		}
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		CollidersFilters fixtureCollider = new CollidersFilters(fixture.getFilterData().categoryBits);
		
		
//		int fixtureCategory = fixture.getFilterData().categoryBits;
		if (collidesWith.matches(fixtureCollider))
//		if ((fixtureCategory & (CollidersFilters.MASK_BULLET_AIMER | maskBits)) > 0) //if collides with scenario + other players
		{
			if (aimingAt.matches(fixtureCollider))
//			if ((fixtureCategory & maskBits) > 0) //if collides with other players
			{
				float newDistance = point.sub(body.getPosition()).length();
				if (newDistance <= maxDistance)
				{
					if (bestDirection == null || newDistance < bestDirection.length())
					{
						bestDirection = point.sub(body.getPosition());
					}
				}
			}
			else
			{
				bestDirection = null;
			}
			return fraction;
		}
		return 1;
	}
	

	public Vec2 getFinalDirection()
	{
		finalDirection.normalize();
		return finalDirection;
	}
	
	public void setMaxDistance(float sightDistance)
	{
		this.maxDistance = sightDistance;
	}
	
	public static void setRayCastNumber(int rayCastNumber)
	{
		ProjectileAimer.rayCastNumber = rayCastNumber;
	}
	
	public static void setAngleRangeInRadians(float angleRangeInRadians)
	{
		ProjectileAimer.angleRangeInRadians = angleRangeInRadians;
	}


}
