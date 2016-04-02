package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.math.FloatSignalChangeWatcher;
import br.com.guigasgame.math.TriangleAreaCalculator;
import br.com.guigasgame.raycast.RayCastCallBackWrapper;
import br.com.guigasgame.raycast.RayCastClosestFixture;


public class NinjaRopePiece extends GameObject
{
	private final NinjaRopeHookCollidable collidableHook;
	private TriangleAreaCalculator triangleArea;
	private final NinjaRopeScaler ropeScaler;
	private final Body heroBody; // Hero
	private DistanceJoint distanceJoint; // null when I'm the tail

	private boolean markedToReunite;
	private Vec2 divisionPoint;
	private FloatSignalChangeWatcher floatSignalWatcher;
	private boolean prevSameSign;
	private RayCastClosestFixture castClosestFixture;
	private boolean cutTheRope;

	private NinjaRopePieceCollidable pieceCollidable;
	
	public NinjaRopePiece(Vec2 hookPoint, Body heroBody, float maxSize)
	{
		this.heroBody = heroBody;

		collidableHook = new NinjaRopeHookCollidable(hookPoint.clone());
		castClosestFixture = new RayCastClosestFixture(heroBody.getWorld(), heroBody.getPosition().clone(), hookPoint.clone(), CollidableCategory.SCENERY.getCategoryMask());

		pieceCollidable = new NinjaRopePieceCollidable(hookPoint, heroBody.getPosition());
		pieceCollidable.addListener(this);
		collidableList.add(pieceCollidable);
		collidableList.add(collidableHook);
		ropeScaler = new NinjaRopeScaler(hookPoint.clone().sub(heroBody.getPosition().clone()).length(), maxSize);
	}

	private NinjaRopePiece(Vec2 hookPoint, Body heroBody, float maxSize, Vec2 prevCollidableHookPosition)
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
		if (null == distanceJoint || markedToReunite || divisionPoint != null) //I don't need to be updated
		{
			return;
		}
		pieceCollidable.updateShape(heroBody.getPosition().clone());
		if (ropeScaler.verifySizeChange(deltaTime))
		{
			distanceJoint.setLength(ropeScaler.getCurrentSize());
		}
		if (null != triangleArea) //Do I have at least one split?
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
			divisionPoint = calculatePreviousCollisionPoint(1 - retorno.fraction, getHeroTimeProjectionPoint(-deltaTime));
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

	public boolean isMarkedToDivide()
	{
		if (!collidableHook.isBodyIsBuilt() || divisionPoint == null)
			return false;
		return divisionPoint.sub(collidableHook.getPosition()).length() > NinjaRopeScaler.MIN_LINK_SIZE
				&& divisionPoint.sub(heroBody.getPosition()).length() > NinjaRopeScaler.MIN_LINK_SIZE;
	}

	public boolean isMarkedToReunite()
	{
		return markedToReunite;
	}

	public NinjaRopePiece divide()
	{
		System.out.println("Splitting rope");
		NinjaRopePiece retorno = new NinjaRopePiece(divisionPoint, heroBody, ropeScaler.getMaxSize() - collidableHook.getPosition().sub(divisionPoint).length(), collidableHook.getPosition());
		pieceCollidable.updateShape(divisionPoint.clone());
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
		ropeScaler.setCurrentSize(collidableHook.getPosition().sub(heroBody.getPosition()).length());
		System.out.println("Joining rope");
		createDistanceJoint(heroBody.getWorld());
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		cutTheRope = true;
		System.out.println("Rope-Shuriken collision");
	}

	public boolean isCutTheRope()
	{
		return cutTheRope;
	}

	public void shorten()
	{
		ropeScaler.shorten();
	}

	public void increase()
	{
		ropeScaler.increase();
	}

}
