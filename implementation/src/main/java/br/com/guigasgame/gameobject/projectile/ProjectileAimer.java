package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;


public class ProjectileAimer implements RayCastCallback
{

	public final static int rayCastNumberDefault = 16;
	public final static float angleRangeInRadiansDefault = (float) (Math.PI/10.f);
	public final static float sinAngleDefault = (float) Math.sin(angleRangeInRadiansDefault/(float)rayCastNumberDefault);
	public final static float cosAngleDefault = (float) Math.cos(angleRangeInRadiansDefault/(float)rayCastNumberDefault);
	
	private int rayCastNumber;
	private float angleRangeInRadians;
	private float sinAngle;
	private float cosAngle;
	
	private Vec2 finalDirection;
	private Vec2 bestDirection;
	private final float maxDistance;
	private final Body body;
	private final Vec2 initialDirection;
	private final ProjectileCollidableFilter projectileCollidableFilter;
	
	public ProjectileAimer(Projectile projectile) 
	{
		this.maxDistance = projectile.getProperties().range;
		this.body = projectile.getCollidable().getBody();
		this.initialDirection = projectile.getDirection().clone().mul(maxDistance);
		this.finalDirection = initialDirection;
		this.bestDirection = null;
		this.projectileCollidableFilter = projectile.getCollidableFilter();
		
		this.rayCastNumber = rayCastNumberDefault;
		this.angleRangeInRadians = angleRangeInRadiansDefault;
		this.sinAngle = sinAngleDefault;
		this.cosAngle = cosAngleDefault;		
		
		System.out.println("Collides with:" + Integer.toBinaryString(projectileCollidableFilter.getCollidableFilter().getCollider().value));
		System.out.println("Aiming to:" + Integer.toBinaryString(projectileCollidableFilter.getAimingMask().value));
		
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
		CollidableFilter fixtureCollider = new CollidableFilterBox2dAdapter(fixture.getFilterData()).toCollidableFilter();
		
		if (projectileCollidableFilter.getCollidableFilter().matches(fixtureCollider.getCategory()))//if collides with something interesting
		{
			if (projectileCollidableFilter.getAimingMask().matches(fixtureCollider.getCategory().getValue())) //if collides with what I am aiming to
			{
				checkNewBestDirection(point);
			}
			else
			{
				bestDirection = null;
			}
			return fraction; ////get closest
		}
		return 1; //ignore
	}
	
	private void checkNewBestDirection(Vec2 point)
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

	public Vec2 getFinalDirection()
	{
		finalDirection.normalize();
		return finalDirection;
	}
	
	public void setRayCastNumber(int rayCastNumber)
	{
		this.rayCastNumber = rayCastNumber;
		updateSinAndCos();
	}
	
	public void setAngleRangeInRadians(float angleRangeInRadians)
	{
		this.angleRangeInRadians = angleRangeInRadians;
		updateSinAndCos();
	}

	private void updateSinAndCos()
	{
		sinAngle = (float) Math.sin(angleRangeInRadians/(float)rayCastNumber);
		cosAngle = (float) Math.cos(angleRangeInRadians/(float)rayCastNumber);
	}
	
}
