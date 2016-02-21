package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;

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
import org.jbox2d.dynamics.joints.RevoluteJointDef;

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

	private final GameHero gameHero;
	private World world;
	private RopeLinkDef ropeLink;
	private Body lastLinkBody;
	private List<Joint> jointList;
	private List<Body> bodyList;

	private boolean attachedHook;
	private boolean attachedHero;
	private boolean setState;
	private boolean markToAttachHook;
	private boolean startToReduce;
	private float size;

	private Vec2 pointToCreateJointAt;

	private DistanceJoint lastJoint;

	public NinjaRopeProjectile(Vec2 direction, GameHero gameHero)
	{
		super(ProjectileIndex.ROPE, adjustInitialDirection(direction), gameHero.getCollidable().getBody().getWorldCenter());
		this.gameHero = gameHero;
		world = null;
		jointList = new ArrayList<>();
		bodyList = new ArrayList<>();
		lastLinkBody = null;
		attachedHook = false;
		markToAttachHook = false;
		startToReduce = false;
		
		size = properties.maxDistance;
	}
	
	@Override
	protected FixtureDef createFixtureDef()
	{
		
//		collidable.getBody().setGravityScale(0.1f);

		FixtureDef fixtureDef = super.createFixtureDef();//ropeLink.getFixtureDef();//super.createFixtureDef();
//		ropeLink = new RopeLinkDef(fixtureDef);
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
		if (direction.y == 0)
		{
			direction.addLocal(0, -1f);
			direction.normalize();
		}
		else
		{
			direction.y = -Math.abs(direction.y);
		}
		return direction;
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		if (pointToCreateJointAt != null)
		{
			divideLink();
		}
		if (markToAttachHook && !attachedHook)
		{
			attachHook();
			gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, this)));
		}
		
		world.raycast(this, lastLinkBody.getWorldCenter(), gameHero.getCollidable().getBody().getWorldCenter());
		if (!attachedHook && !attachedHero)
		{
			if (ropeIsTooLong())
			{
				startToReduce = true;
				markToDestroy();
			}
			else if (needsNewLink())
			{
//				if (!attachedHook)
//					collidable.getBody().setLinearVelocity(getDirection().mul(properties.initialSpeed*deltaTime*10));
//				addNewLink();
			}
		}

		if (!setState && attachedHero && attachedHook)
		{
//			System.out.println("--");
//			for( Joint joint : jointList )
//			{
//				RevoluteJoint rj = (RevoluteJoint) joint;
//				rj.getBodyA().setType(BodyType.DYNAMIC);
//				rj.getBodyB().setType(BodyType.DYNAMIC);
//				System.out.println(rj.isLimitEnabled());
//				System.out.println(rj.getUpperLimit());
//			}
			setState = true;
			gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, this)));
		}

	}

	private void divideLink()
	{
		System.out.println("Dividing");

		BodyDef bodyDef = new BodyDef();
		bodyDef.position = pointToCreateJointAt;
		bodyDef.type = BodyType.STATIC;
		
		Body bodyMiddle = world.createBody(bodyDef);
		Body bodyBegin = lastJoint.getBodyA();
		bodyMiddle.createFixture(createFixtureDef());
		Body bodyEnd = lastJoint.getBodyB();
		world.destroyJoint(lastJoint);
		bodyList.add(bodyMiddle);
		
		
		DistanceJointDef distJoinDef = new DistanceJointDef();
		distJoinDef.bodyA = bodyBegin;
		distJoinDef.bodyB = bodyMiddle;
		distJoinDef.length = distJoinDef.bodyA.getWorldCenter().sub(distJoinDef.bodyB.getWorldCenter()).length();
		Joint joint = world.createJoint(distJoinDef);
		jointList.add(joint);

		
		DistanceJointDef otherJoinDef = new DistanceJointDef();
		otherJoinDef.bodyA = bodyMiddle;
		otherJoinDef.bodyB = bodyEnd;
		otherJoinDef.length = otherJoinDef.bodyA.getWorldCenter().sub(otherJoinDef.bodyB.getWorldCenter()).length();
		DistanceJoint otherJoint = (DistanceJoint) world.createJoint(otherJoinDef);
		jointList.add(otherJoint);
		
		lastJoint = otherJoint;
		lastLinkBody = bodyMiddle;
		
		pointToCreateJointAt = null;
		
	}

	private void attachHook()
	{
//		RevoluteJoint joint = (RevoluteJoint) collidable.getBody().getJointList().joint;
//		joint.enableLimit(true);
//		joint.setLimits((float)-Math.PI*4, (float)Math.PI*4);

		if (attachedHook)
			return;
		
		DistanceJointDef distJoinDef = new DistanceJointDef();
		distJoinDef.bodyA = collidable.getBody();
		distJoinDef.bodyB = gameHero.getCollidable().getBody();
		distJoinDef.length = distJoinDef.bodyA.getWorldCenter().sub(distJoinDef.bodyB.getWorldCenter()).length();
		lastJoint = (DistanceJoint) world.createJoint(distJoinDef);
		jointList.add(lastJoint);
		
		System.out.println("Hook attached");
		startToReduce = false;
		attachedHook = true;
		collidable.getBody().setType(BodyType.STATIC);
//		if (!attachedHero)
//		{
//			attachHero();
//		}
	}
	
	private boolean ropeIsTooLong()
	{
		return gameHero.getCollidable().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance*properties.maxDistance;
	}

	private boolean needsNewLink()
	{
		return false;
//		System.out.println("Links: " + jointList.size());
//		return lastLinkBody.getWorldCenter().sub(gameHero.getCollidable().getBody().getWorldCenter()).lengthSquared() >= RopeLinkDef.SQUARED_CHAIN_SIZE;
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
	
	
	private void addNewLink()
	{
		size -= RopeLinkDef.CHAIN_SIZE;
//		collidable.getBody().setLinearVelocity(collidable.getBody().getLinearVelocity().mul());
//		System.out.println(lastLink.getWorldCenter());
//		System.out.println(gameHero.getCollidable().getBody().getWorldCenter());
		final Vec2 angleFinder = lastLinkBody.getWorldCenter().sub(gameHero.getCollidable().getBody().getWorldCenter());
		angleFinder.normalize();
		float angle = (float) WorldConstants.getCossinBetweenVectors(angleFinder, new Vec2(-1, 0));

//		newLinkPosition.addLocal(gameHero.getCollidable().getBody().getWorldCenter());
		
//		Body body = createNewLinkBody(gameHero.getCollidable().getBody().getWorldCenter().clone(), angle);
//		body.setGravityScale(0.1f);

//		RevoluteJointDef jointDef = ropeLink.getJointDef();
//		body.applyLinearImpulse(angleFinder.mul(properties.initialSpeed/(bodyList.size()*5)), body.getWorldCenter());
//		collidable.getBody().applyForceToCenter(collidable.getBody().getLinearVelocity().mul(properties.initialSpeed));

//		System.out.println("low: " + jointDef.lowerAngle);
//		System.out.println("upper: " + jointDef.upperAngle);
		
		
//		jointDef.bodyA = lastLinkBody;
//		jointDef.bodyB = body;
// 
//		Joint joint = world.createJoint(jointDef);
//		jointList.add(joint);
//		joint.setUserData(ropeLink);
		

//		lastLinkBody = body;
//		size -= CHAIN_SIZE;
//		lastLink.setUserData(t);
	}
	
	private void attachHero()
	{
		if (attachedHero)
			return;
		
		System.out.println("Hero attached");
		
		attachedHero = true;

		float mass = 0;
		for( Body body : bodyList )
		{
			mass += body.getMass();
		}
		System.out.println(collidable.getBody().getType().toString());
		System.out.println("Hero: " + gameHero.getCollidable().getBody().getMass() + " / Rope: " + mass);

		RevoluteJointDef jointDef = ropeLink.getJointDef();
		
		jointDef.lowerAngle = (float) (-Math.PI*2);
		jointDef.upperAngle = (float) (Math.PI*2);

		jointDef.bodyA = lastLinkBody;
		jointDef.bodyB = gameHero.getCollidable().getBody();
		Joint joint = world.createJoint(jointDef);
		joint.setUserData(ropeLink);
		jointList.add(joint);
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		markToAttachHook = true;
	}
	
//	private Body createNewLinkBody(Vec2 position, float angle)
//	{
//		Body body = world.createBody(ropeLink.adjustBodyDef(position, angle));
//		body.createFixture(ropeLink.getFixtureDef());
//		bodyList.add(body);
//		return body;
//	}

	public void shorten()
	{
		lastJoint.setLength((float) (lastJoint.getLength()*0.995));
	}

	public void increase()
	{
		lastJoint.setLength((float) (lastJoint.getLength()*1.005));
	}
	
	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction)
	{
		System.out.println("raycast: " + lastLinkBody.getPosition());
		CollidableFilter fixtureCollider = new CollidableFilterBox2dAdapter(fixture.getFilterData()).toCollidableFilter();
		if (projectileCollidableFilter.getCollidableFilter().matches(fixtureCollider.getCategory()))//if collides with something interesting
		{
			System.out.println("Interesting raycast");
			if (projectileCollidableFilter.getAimingMask().matches(fixtureCollider.getCategory().getValue())) //if collides with what I am aiming to
			{
				if (!attachedHook)
				{
					markToDestroy();
					System.out.println("Wall ahead");
				}
				else
				{
					System.out.println("Collides for dividing");
					markToDivideAt(point);
				}
			}
			return fraction; ////get closest
		}
		return 1; //ignore
	}

	private void markToDivideAt(Vec2 point)
	{
		pointToCreateJointAt = point;
	}

}
