package br.com.guigasgame.gameobject.projectile.aimer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.GameCollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.raycast.RayCastCallBackWrapper;
import br.com.guigasgame.raycast.RayCastClosestFixture;


public class ProjectileAimer
{
	
	private static class RayCastAimer
	{
			public Vec2 direction;
			public int angleVariation;
			public RayCastAimer(Vec2 direction, int angleVariation) 
			{
				this.direction = direction;
				this.angleVariation = angleVariation;
			}
			
			public boolean isWorseThan(RayCastAimer other)
			{
				if (angleVariation != other.angleVariation)
					return (angleVariation > other.angleVariation);
				return direction.lengthSquared() > direction.lengthSquared();
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
	
	private RayCastAimer finalRaycastAimer;
	private final float maxDistance;
	private final Body body;
	private final Vec2 initialDirection;
	private final CollidableFilter collidableFilter;
	private final IntegerMask targetMask;
	
	public ProjectileAimer(Projectile projectile, Body body) 
	{
		this.rayCastNumber = rayCastNumberDefault;
		this.angleRangeInRadians = angleRangeInRadiansDefault;
		this.sinAngle = sinAngleDefault;
		this.cosAngle = cosAngleDefault;
		setAngleRangeInRadians(projectile.getProperties().rangeAngle);

		this.maxDistance = projectile.getProperties().maxDistance;
		initialDirection = projectile.getDirection().clone();
		initialDirection.normalize();
		initialDirection.mulLocal(maxDistance);
		
		this.finalRaycastAimer = new RayCastAimer(this.initialDirection.clone().mul(maxDistance), rayCastNumber);
		this.body = body;
		this.collidableFilter = projectile.getCollidableFilter();
		this.targetMask = projectile.getTargetMask();
		
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

		shootRaycast(initialDirection.clone(), 0);
		for( int i = 1; i <= rayCastNumber - 1; ++i )
		{
			Vec2 tempCC = pointsToCounterClock.clone();
			pointsToCounterClock.x = (float) (tempCC.x * cosAngle - tempCC.y * sinAngle);
			pointsToCounterClock.y = (float) (tempCC.x * sinAngle + tempCC.y * cosAngle);
			shootRaycast(tempCC, i);

			Vec2 tempCW = pointsToClockWise.clone();
			pointsToClockWise.x = (float) (tempCW.x * cosAngle + tempCW.y * sinAngle);
			pointsToClockWise.y = (float) (tempCW.y * cosAngle - tempCW.x * sinAngle);
			shootRaycast(tempCW, i);
		}
	}
	
	private void shootRaycast(Vec2 pointTo, int variationAngle)
	{
		Vec2 initialPosition = body.getPosition();
		World bodysWorld = body.getWorld();
		
		
		RayCastClosestFixture closestFixture = new RayCastClosestFixture(bodysWorld, initialPosition, initialPosition.add(pointTo), 
			 collidableFilter.getCollider().set(GameCollidableCategory.SMOKE_BOMB.getCategoryMask().value)); //Smoke bomb ahead
		
		closestFixture.shoot();
		RayCastCallBackWrapper response = closestFixture.getCallBackWrapper();
		
		if (response != null)
		{
			if (targetMask.matches(response.fixture.getFilterData().categoryBits)) //if its what I am aiming at
			{
				checkShorterRaycast(response.point, variationAngle);
			}
		}
		
	}

	private void checkShorterRaycast(Vec2 point, int variationAngle)
	{
		
		RayCastAimer newOne = new RayCastAimer(point.sub(body.getPosition()), variationAngle);
		
		float newDistance = point.sub(body.getPosition()).length();
		if (newDistance <= finalRaycastAimer.direction.length())
		{
			if (finalRaycastAimer.isWorseThan(newOne))
			{
				finalRaycastAimer = newOne;
				System.out.println("There is a best direction and it's better than the previous one. (distance: " + finalRaycastAimer.direction.length() + ". variation: " + variationAngle + ")");
			}
		}
	}

	public Vec2 getFinalDirection()
	{
		finalRaycastAimer.direction.normalize();
		return finalRaycastAimer.direction;
	}
	
	public void setRayCastNumber(int rayCastNumber)
	{
		this.rayCastNumber = rayCastNumber;
		updateSinAndCos();
	}
	
	public void setAngleRangeInRadians(float angleRangeInRadians)
	{
		if (Math.abs(this.angleRangeInRadians - angleRangeInRadians) > 0.0001f)
		{
			this.angleRangeInRadians = angleRangeInRadians;
			updateSinAndCos();
		}
	}

	private void updateSinAndCos()
	{
		sinAngle = (float) Math.sin(angleRangeInRadians/(float)rayCastNumber);
		cosAngle = (float) Math.cos(angleRangeInRadians/(float)rayCastNumber);
	}
	
}
