package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.CollidableFilterManipulator;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.state.NinjaRopeSwingingState;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileCollidableFilter;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class NinjaRopeProjectile extends Projectile implements RayCastCallback
{
	private static final float DISTORT_FACTOR = 10.f;
	private static final float MIN_DISTANCE = 0.5f;

	private final GameHero gameHero;
	private World world;

	private Body lastLinkBody;
	private DistanceJoint lastJoint;
	private Vector<DistanceJoint> jointList;
	private List<Body> bodyList;

	private boolean startToRetract;
	private boolean hookIsAttached;
	private boolean markToAttachHook;

	private Vec2 pointToCreateJointAt;

	private boolean canGrow;

	private FixtureDef fixtureDef;
	private float lastDistance;
	private float maxDistance;
	protected boolean collidesWithSomething;
	private boolean markToRemoveJoint;

	public NinjaRopeProjectile(Vec2 direction, GameHero gameHero)
	{
		super(ProjectileIndex.ROPE, adjustInitialDirection(direction), gameHero.getCollidable().getBody().getWorldCenter());
		this.gameHero = gameHero;
		world = null;
		jointList = new Vector<>();
		bodyList = new ArrayList<>();
		lastLinkBody = null;
		hookIsAttached = false;
		markToAttachHook = false;
		startToRetract = false;
	}
	
	@Override
	protected FixtureDef createFixtureDef()
	{
		fixtureDef = super.createFixtureDef();//ropeLink.getFixtureDef();//super.createFixtureDef();
		return fixtureDef;
	}
	
	@Override
	public void onEnter()
	{
		shoot();
	}

	@Override
	public void onDestroy()
	{
		for( Joint joint : jointList )
		{
			world.destroyJoint(joint);
		}
		for( Body body : bodyList )
		{
			world.destroyBody(body);
		}
	}

	protected static Vec2 adjustInitialDirection(Vec2 direction)
	{
//		if (direction.y == 0)
//		{
//			direction.addLocal(0, -1f);
//		}
//		else
//		{
//			direction.y = -Math.abs(direction.y);
//		}
		direction.normalize();
		return direction;
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);

		float attachedSize = calculateRopeSize();
		canGrow = attachedSize <= properties.maxDistance;
		
		
		if (pointToCreateJointAt != null)
		{
			divideLink();
		}
		if (markToAttachHook)
		{
			attachHook();
		}
		if(markToRemoveJoint)
			removeLastJoints();
		
		checkJointRemoval();

		//		if (lastJoint != null && lastJoint.getBodyA().getWorldCenter().sub(lastJoint.getBodyB().getWorldCenter()).length() < MIN_DISTANCE)
//		{
//			removeJoint(lastJoint, lastJoint);
//		}
		
		//-------------
		world.raycast(this, gameHero.getCollidable().getBody().getWorldCenter(), lastLinkBody.getWorldCenter());
		//-------------
		
		if (!hookIsAttached)
		{
			if (ropeIsTooLong())
			{
				startToRetract = true;
				markToDestroy();
			}
		}
	}

	private boolean checkJointRemoval()
	{
		if (jointList.size() > 1)
		{
			DistanceJoint last = jointList.get(jointList.size() - 1);
			DistanceJoint prevLast = jointList.get(jointList.size() - 2);
			
			collidesWithSomething = false;
			RayCastCallback rayCastCallback = new RayCastCallback()
			{
				
				@Override
				public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
				{
					CollidableFilter fixtureCollider = new CollidableFilterBox2dAdapter(fixture.getFilterData()).toCollidableFilter();
					if (projectileCollidableFilter.getCollidableFilter().matches(fixtureCollider.getCategory()))//if collides with something interesting
					{
						if (projectileCollidableFilter.getAimingMask().matches(fixtureCollider.getCategory().getValue())) //if collides with what I am aiming to
						{
							if (fraction < .98f)
							{
								collidesWithSomething = true;
								return 0;
							}
						}
					}
					return 1; //ignore
				}
			};
			
			world.raycast(rayCastCallback, gameHero.getCollidable().getBody().getWorldCenter(), prevLast.getBodyA().getWorldCenter());
			if (collidesWithSomething)
			{
//				System.out.println("hitsPrev");
				return false;
			}
			
//			Vec2 prevLastVec = prevLast.getBodyA().getPosition().sub(prevLast.getBodyB().getPosition());
//			Vec2 lastVec = last.getBodyA().getPosition().sub(last.getBodyB().getPosition());
//			float angle = (float) WorldConstants.getAngleBetweenVectors(prevLastVec, lastVec);
//			System.out.println("Angle: " + angle);
//			world.raycast(rayCastCallback, gameHero.getCollidable().getBody().getWorldCenter(), last.getBodyA().getWorldCenter());
//			if (collidesWithSomething)
//			{
//				System.out.println("hitsLast");
//				return false;
//			}
//			world.raycast(rayCastCallback, gameHero.getCollidable().getPosition(), last.getBodyA().getPosition());
			
			if (!collidesWithSomething)
			{
				System.out.println("Remove joint");
//				removeJoint(prevLast, last);
				prevLast.getBodyB().setType(BodyType.DYNAMIC);
				
				System.out.println("------------------------------------------\njointsCount: " + jointList.size());
			}
			
//			Vec2 lastVec = prevLast.getBodyA().getPosition().sub(last.getBodyB().getPosition());
////			double angle = WorldConstants.getAngleBetweenVectors(lastVec, prevLastVec);
////			double currentAngle = Math.toDegrees(angle);
//			
//
//			float currentDistance = lastVec.length();
//			float diffTollerance = maxDistance*0.003f;
//			float diffToMaxDistance = maxDistance - currentDistance;
//			
//			boolean isIncreasing = (currentDistance - lastDistance) > diffTollerance*0.1;
//			
//			System.out.println("(" + jointList.size() + ") increasing: "+isIncreasing + " | currentDistance: " + currentDistance + ". lasDist: " + lastDistance + "| max: " + maxDistance + 
//					" --- diffToMax: " + diffToMaxDistance + "--- tol: " + diffTollerance);
//			lastDistance = currentDistance;
//			if (isIncreasing && diffToMaxDistance < diffTollerance)
//			{
//				System.out.println("Remove joint");
//				removeJoint(prevLast, last);
//				maxDistance = updateDistanceFromTwoLastJoints();
//				
//				return true;
//			}
		}
		return false;
	}


	private float calculateRopeSize()
	{
		float attachedSize = 0 ;
		for( Joint joint : jointList )
		{
			float jointSize = joint.getBodyA().getWorldCenter().sub(joint.getBodyB().getWorldCenter()).length();
			attachedSize += jointSize;
		}
		return attachedSize;
	}

	private void removeLastJoints()
	{
		if (jointList.size() < 2)
			return;
		DistanceJoint last = jointList.get(jointList.size() - 1);
		DistanceJoint prevLast = jointList.get(jointList.size() - 2);
		
		world.destroyJoint(last);
		world.destroyJoint(prevLast);
		world.destroyBody(last.getBodyA());

		bodyList.remove(last.getBodyA());
		jointList.remove(last);
		jointList.remove(prevLast);

		lastJoint = createJoint(prevLast.getBodyA(), last.getBodyB());
		lastLinkBody = prevLast.getBodyA();
		markToRemoveJoint = false;
	}
	
	@Override
	public void endContact(Object me, Object other, Contact contact)
	{
		Body myBody = (Body) me;
		System.out.println("Checkpoint removed: " + !myBody.isBullet());
		if (!myBody.isBullet())
		{
			markToRemoveJoint = true;
		}

	}

	private void divideLink()
	{
		jointList.remove(lastJoint);
		System.out.println("Dividing");

		BodyDef bodyDef = new BodyDef();
		bodyDef.position = pointToCreateJointAt;
		bodyDef.type = BodyType.STATIC;

		Body middleBody = world.createBody(bodyDef);
		middleBody.setUserData(this);

		bodyList.add(middleBody);
		
		Body beginBody = lastJoint.getBodyA();
		middleBody.createFixture(createFixtureDef());
		Body endBody = lastJoint.getBodyB();
		world.destroyJoint(lastJoint);
		
		createJoint(beginBody, middleBody);
		lastJoint = createJoint(middleBody, endBody);

		updateDistanceFromTwoLastJoints();
		lastLinkBody = middleBody;
		pointToCreateJointAt = null;
		System.out.println("------------------------------------------\njointsCount: " + jointList.size());

	}

	private float updateDistanceFromTwoLastJoints()
	{
		if (jointList.size() >= 2)
		{
			System.out.println("MaxDistance updated");
			DistanceJoint last = jointList.get(jointList.size() - 1);
			DistanceJoint prevLast = jointList.get(jointList.size() - 2);
			
			Vec2 lastVec = last.getBodyA().getPosition().sub(last.getBodyB().getPosition());
			Vec2 prevLastVec = prevLast.getBodyB().getPosition().sub(prevLast.getBodyA().getPosition());

			maxDistance = lastVec.length() + prevLastVec.length();
			lastDistance = maxDistance;
			return maxDistance;

		}
		maxDistance = -1;
		lastDistance = -1;
		return -1;
	}

	private DistanceJoint createJoint(Body fromBody, Body toBody)
	{
		DistanceJointDef distJoinDef = new DistanceJointDef();
		distJoinDef.bodyA = fromBody;
		distJoinDef.bodyB = toBody;
		distJoinDef.length = fromBody.getPosition().sub(toBody.getPosition()).length();
		System.out.println("JointsDistance: " + distJoinDef.length);
		DistanceJoint joint = (DistanceJoint) world.createJoint(distJoinDef);
		jointList.add(joint);
		System.out.println("------------------------------------------\njointsCount: " + jointList.size());

		return joint;
	}

	private void attachHook()
	{
		if (hookIsAttached)
			return;

		gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, this)));
		
		collidable.getBody().setType(BodyType.STATIC);
		lastJoint = createJoint(collidable.getBody(), gameHero.getCollidable().getBody());
		
		System.out.println("Hook attached");
		startToRetract = false;
		hookIsAttached = true;
	}
	
	private boolean ropeIsTooLong()
	{
		return gameHero.getCollidable().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance*properties.maxDistance;
	}

	@Override
	protected ProjectileCollidableFilter createCollidableFilter()
	{
		// rope doesn't collides with heros
		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableFilterManipulator.createFromCollidableFilter(CollidableConstants.getRopeBodyCollidableFilter()));
		projectileCollidableFilter.aimTo(CollidableConstants.sceneryCategory);
		
		return projectileCollidableFilter;
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		this.world = world;
		lastLinkBody = collidable.getBody();
	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		markToAttachHook = true;
	}
	
	public void shorten()
	{
		lastJoint.setLength((float) (lastJoint.getLength()*0.95));
	}

	public void increase()
	{
		if (canGrow)
			lastJoint.setLength((float) (lastJoint.getLength()*1.05));
	}
	
	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		CollidableFilter fixtureCollider = new CollidableFilterBox2dAdapter(fixture.getFilterData()).toCollidableFilter();
		if (projectileCollidableFilter.getCollidableFilter().matches(fixtureCollider.getCategory()))//if collides with something interesting
		{
			if (projectileCollidableFilter.getAimingMask().matches(fixtureCollider.getCategory().getValue())) //if collides with what I am aiming to
			{
				if (!hookIsAttached)
				{
					System.out.println("Wall on the way");
					markToDestroy();
					return 0;
				}
				else if (point.sub(lastLinkBody.getPosition()).length() > MIN_DISTANCE)
				{
					System.out.println("Collides for dividing");
					markToDivideAt(point.clone());
					return fraction;
				}
			}
		}
		return 1; //ignore
	}

	private void markToDivideAt(Vec2 point)
	{
		pointToCreateJointAt = point.clone();
	}

}
