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
	private static class BestRaycast
	{
		public Vec2 direction;
		public int angleVariation;
		public BestRaycast(Vec2 direction, int angleVariation) 
		{
			this.direction = direction;
			this.angleVariation = angleVariation;
		}
		
		public boolean isWorseThan(BestRaycast other)
		{
			if (angleVariation != other.angleVariation)
				return (angleVariation > other.angleVariation);
			return direction.lengthSquared() > direction.lengthSquared();
		}

		public boolean isLongerThan(float newDistance) 
		{
			return 	( direction.length() > newDistance);
		}
		
		public boolean isValid()
		{
			return direction!= null;
		}
	}

	public final static int rayCastNumberDefault = 16;
	public final static float angleRangeInRadiansDefault = (float) (Math.PI/10.f);
	public final static float sinAngleDefault = (float) Math.sin(angleRangeInRadiansDefault/(float)rayCastNumberDefault);
	public final static float cosAngleDefault = (float) Math.cos(angleRangeInRadiansDefault/(float)rayCastNumberDefault);
	
	private int rayCastNumber;
	private float angleRangeInRadians;
	private float sinAngle;
	private float cosAngle;
	
	private BestRaycast finalBestRaycast;
	private BestRaycast currentBestRaycast;
	private final float maxDistance;
	private final Body body;
	private final Vec2 initialDirection;
	private final ProjectileCollidableFilter projectileCollidableFilter;
	
	public ProjectileAimer(Projectile projectile) 
	{
		this.rayCastNumber = rayCastNumberDefault;
		this.angleRangeInRadians = angleRangeInRadiansDefault;
		this.sinAngle = sinAngleDefault;
		this.cosAngle = cosAngleDefault;		

		this.maxDistance = projectile.getProperties().range;
		initialDirection = projectile.getDirection().clone();
		initialDirection.normalize();
		initialDirection.mulLocal(maxDistance);
		
		this.finalBestRaycast = new BestRaycast(this.initialDirection.clone(), rayCastNumber);
		this.currentBestRaycast = null;
		this.body = projectile.getCollidable().getBody();
		this.projectileCollidableFilter = projectile.getCollidableFilter();
		
		
		System.out.println("Collides with: " + Integer.toBinaryString(projectileCollidableFilter.getCollidableFilter().getCollider().value));
		System.out.println("Aiming to: " + Integer.toBinaryString(projectileCollidableFilter.getAimingMask().value));
		
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

		makeRaycast(initialDirection.clone(), 0);
		for( int i = 1; i <= rayCastNumber - 1; ++i )
		{
			Vec2 tempCC = pointsToCounterClock.clone();
			pointsToCounterClock.x = (float) (tempCC.x * cosAngle - tempCC.y * sinAngle);
			pointsToCounterClock.y = (float) (tempCC.x * sinAngle + tempCC.y * cosAngle);
			makeRaycast(tempCC, i);

			Vec2 tempCW = pointsToClockWise.clone();
			pointsToClockWise.x = (float) (tempCW.x * cosAngle + tempCW.y * sinAngle);
			pointsToClockWise.y = (float) (tempCW.y * cosAngle - tempCW.x * sinAngle);
			makeRaycast(tempCW, i);
		}
	}
	
	private void makeRaycast(Vec2 pointTo, int variationAngle)
	{
		currentBestRaycast = new BestRaycast(null, variationAngle); //2 makes this initial value invalid :D
		Vec2 initialPosition = body.getPosition();
		World bodysWorld = body.getWorld();
		bodysWorld.raycast(this, initialPosition, initialPosition.add(pointTo));

		if (currentBestRaycast.isValid() && finalBestRaycast.isWorseThan(currentBestRaycast))
		{
			finalBestRaycast = new BestRaycast(currentBestRaycast.direction.clone(), variationAngle);
			
			System.out.println("There is a best direction and it's better than the previous one. (variation: " + variationAngle + ")");
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
				checkShorterRaycast(point);
			}
			return fraction; ////get closest
		}
		return 1; //ignore
	}
	
	private void checkShorterRaycast(Vec2 point)
	{
		float newDistance = point.sub(body.getPosition()).length();
		if (newDistance <= maxDistance)
		{
			if ( !currentBestRaycast.isValid() || currentBestRaycast.isLongerThan(newDistance))
			{
				currentBestRaycast.direction = point.sub(body.getPosition());
			}
		}
	}

	public Vec2 getFinalDirection()
	{
		finalBestRaycast.direction.normalize();
		return finalBestRaycast.direction;
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
