package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.math.FloatSignalChangeWatcher;
import br.com.guigasgame.math.TriangleAreaCalculator;
import br.com.guigasgame.raycast.RayCastCallBackWrapper;
import br.com.guigasgame.raycast.RayCastClosestFixture;


public class NinjaRopeLink extends GameObject
{

	private static final float SIZE_CHANGE_PER_SECOND = 30f;
	private static final float MIN_LINK_SIZE = 0.01f;

	private final NinjaRopeHookCollidable collidableHook;
	private TriangleAreaCalculator triangleArea;
	private final float maxSize;

	private final Body heroBody; // Hero
	private DistanceJoint distanceJoint; // null when I'm the tail

	private boolean markToShorten;
	private boolean markToIncrease;
	private boolean markedToReunite;
	private Vec2 divisionPoint;
	private FloatSignalChangeWatcher floatSignalWatcher;
	private boolean prevSameSign;
	private RayCastClosestFixture castClosestFixture;

	public NinjaRopeLink(Vec2 hookPoint, Body heroBody, float maxSize)
	{
		this.maxSize = maxSize;
		this.heroBody = heroBody;

		collidableHook = new NinjaRopeHookCollidable(hookPoint.clone());
		castClosestFixture = new RayCastClosestFixture(heroBody.getWorld(), heroBody.getPosition().clone(), hookPoint.clone(), CollidableCategory.SCENERY.getCategoryMask());

		collidableList.add(collidableHook);
	}

	private NinjaRopeLink(Vec2 hookPoint, Body heroBody, float maxSize, Vec2 prevCollidableHookPosition)
	{
		this(hookPoint, heroBody, maxSize);
		triangleArea = new TriangleAreaCalculator(prevCollidableHookPosition.clone(), hookPoint.clone(), heroBody.getPosition().clone());
		floatSignalWatcher = new FloatSignalChangeWatcher(triangleArea.getArea());
	}

	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		createDistanceJoint(world);
	}

	private void createDistanceJoint(World world)
	{
		DistanceJointDef jointDef = new DistanceJointDef();
		jointDef.bodyA = collidableHook.getBody();
		jointDef.bodyB = heroBody;
		jointDef.collideConnected = false;
		jointDef.length = jointDef.bodyA.getPosition().sub(jointDef.bodyB.getPosition()).length();
		distanceJoint = (DistanceJoint) world.createJoint(jointDef);
	}
	
	@Override
	protected void onDestroy()
	{
//		System.out.println("Destroying");
		World world = collidableHook.getBody().getWorld();
		for( Collidable collidable : collidableList )
		{
			world.destroyBody(collidable.getBody());
		}
		collidableList.clear();

		if (distanceJoint != null)
		{
			world.destroyJoint(distanceJoint);
			distanceJoint = null;
		}
	}

	@Override
	public void update(float deltaTime)
	{
		if (null == distanceJoint || markedToReunite || divisionPoint != null) //I am not prepared
		{
			return;
		}
		if (!verifySizeChange(deltaTime))
		{
			distanceJoint.setLength(collidableHook.getPosition().sub(heroBody.getPosition()).length());
		}
		if (null != triangleArea) //I have at least one split?
		{
			checkReunion();
		}

		checkSplitting(deltaTime);
	}

	private void checkReunion()
	{
		triangleArea.setC(heroBody.getPosition().clone());
		float area = triangleArea.getArea();
		boolean currentSameSign = floatSignalWatcher.hasTheSameSign(area);
				
		if (prevSameSign && !currentSameSign)
		{
			markedToReunite = true;
		}
		prevSameSign = currentSameSign;
	}

	private void checkSplitting(float deltaTime)
	{
		RayCastCallBackWrapper retorno = verifyCollision(deltaTime, getHeroTimeProjectionPoint(0));
		if (retorno != null)
		{
			divisionPoint = calculatePreviousCollisionPoint(1-retorno.fraction, getHeroTimeProjectionPoint(-deltaTime));
		}
	}
	
	private Vec2 calculatePreviousCollisionPoint(float fraction, Vec2 prevHeroPosition)
	{
		final Vec2 retorno = collidableHook.getPosition().clone();
		final Vec2 adjust = prevHeroPosition.sub(retorno).mul(fraction).clone();
		return retorno.add(adjust);
	}

	private RayCastCallBackWrapper verifyCollision(float deltaTime, Vec2 heroPosition)
	{
		if (heroPosition == null)
			return null;
		castClosestFixture.setFrom(heroPosition.clone());
		castClosestFixture.setTo(collidableHook.getPosition().clone());
		castClosestFixture.shoot();
		RayCastCallBackWrapper callBackWrapper = castClosestFixture.getCallBackWrapper();
		if (callBackWrapper != null && callBackWrapper.fraction < 1)
		{
			return callBackWrapper;
		}
		return null;
	}

	private Vec2 getHeroTimeProjectionPoint(float deltaTime)
	{
		return heroBody.getPosition().add(heroBody.getLinearVelocity().mul(deltaTime));
	}

	private boolean verifySizeChange(float deltaTime)
	{
		if (distanceJoint != null)
		{
			final float currentSize = distanceJoint.getLength();
			if (markToIncrease)
			{
				increase(deltaTime * SIZE_CHANGE_PER_SECOND);
			}
			else if (markToShorten)
			{
				shorten(deltaTime * SIZE_CHANGE_PER_SECOND);
			}
			return distanceJoint.getLength() != currentSize;
		}
		return false;
	}

	private void increase(float increaseSize)
	{
		float newSize = distanceJoint.getLength() + increaseSize;
		markToIncrease = false;
		if (newSize >= maxSize)
			newSize = maxSize;
		distanceJoint.setLength(newSize);
	}

	private void shorten(float shortenSize)
	{
		markToShorten = false;
		float newSize = distanceJoint.getLength() - shortenSize;

		if (distanceJoint.getLength() <= MIN_LINK_SIZE)
			newSize = MIN_LINK_SIZE;
		distanceJoint.setLength(newSize);
	}

	public void shorten()
	{
		markToShorten = true;
	}

	public void increase()
	{
		markToIncrease = true;
	}

	public boolean isMarkedToDivide()
	{
		if (!collidableHook.isBodyIsBuilt() || divisionPoint == null)
			return false;
		return divisionPoint.sub(collidableHook.getPosition()).length() > MIN_LINK_SIZE
				&& divisionPoint.sub(heroBody.getPosition()).length() > MIN_LINK_SIZE;
	}

	public boolean isMarkedToReunite()
	{
		return markedToReunite;
	}

	public NinjaRopeLink divide()
	{
		final float newLength = collidableHook.getPosition().sub(divisionPoint).length();
		NinjaRopeLink retorno = new NinjaRopeLink(divisionPoint, heroBody, maxSize - newLength, collidableHook.getPosition());

		heroBody.getWorld().destroyJoint(distanceJoint);
		distanceJoint = null;
		divisionPoint = null;
		addChild(retorno);
		return retorno;
	}

	public boolean isAttached()
	{
		return distanceJoint != null;
	}

	public void wakeUp()
	{
		createDistanceJoint(heroBody.getWorld());
	}


}
