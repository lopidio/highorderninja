package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilterManipulator;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.state.NinjaRopeSwingingState;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileCollidableFilter;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class NinjaRopeProjectile extends Projectile
{
	private static final float DISTORT_FACTOR = 10.f;

	private final GameHero gameHero;
	private World world;
	private RopeLinkDef ropeLink;
	private Body lastLink;
	private List<Joint> jointList;
	private List<Body> bodyList;

	private boolean attachedHook;
	private boolean attachedHero;
	private boolean setState;
	private boolean markToAttachHook;
	private boolean startToReduce;
	private float size;

	public NinjaRopeProjectile(Vec2 direction, GameHero gameHero)
	{
		super(ProjectileIndex.ROPE, adjustInitialDirection(direction), gameHero.getCollidable().getBody().getWorldCenter());
		this.gameHero = gameHero;
		world = null;
		jointList = new ArrayList<>();
		bodyList = new ArrayList<>();
		lastLink = null;
		attachedHook = false;
		markToAttachHook = false;
		startToReduce = false;
		
		size = properties.maxDistance;
	}
	
	@Override
	protected FixtureDef createFixtureDef()
	{
		FixtureDef fixtureDef = super.createFixtureDef();//ropeLink.getFixtureDef();//super.createFixtureDef();
		ropeLink = new RopeLinkDef(fixtureDef);
		return fixtureDef;
	}
	

	@Override
	public void beginContact(Collidable other, Contact contact)
	{
		System.out.println("Hit something");
		markToAttachHook = true;
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

		if (markToAttachHook && !attachedHook)
		{
			attachHook();
		}
		
		if (!attachedHook && !attachedHero)
		{
			if (ropeIsTooLong())
			{
				startToReduce = true;
				attachHero();
			}
			else if (needsNewLink())
			{
				addNewLink();
			}
		}

		if (!setState && attachedHero && attachedHook)
		{
			setState = true;
			gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, this)));
		}

	}

	private void attachHook()
	{
		if (attachedHook)
			return;
		System.out.println("Hook attached");
		startToReduce = false;
		attachedHook = true;
		collidable.getBody().setType(BodyType.STATIC);
		if (!attachedHero)
		{
			attachHero();
		}
	}
	
	private boolean ropeIsTooLong()
	{
		return size <= 0;// gameHero.getCollidable().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance*properties.maxDistance;
	}

	private boolean needsNewLink()
	{
//		System.out.println("Links: " + jointList.size());
		return lastLink.getWorldCenter().sub(gameHero.getCollidable().getBody().getWorldCenter()).lengthSquared() >= RopeLinkDef.SQUARED_CHAIN_SIZE;
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
		lastLink = collidable.getBody();
	}
	
	
	private void addNewLink()
	{
		size -= RopeLinkDef.CHAIN_SIZE;
//		collidable.getBody().setLinearVelocity(collidable.getBody().getLinearVelocity().mul());
//		System.out.println(lastLink.getWorldCenter());
//		System.out.println(gameHero.getCollidable().getBody().getWorldCenter());
		final Vec2 angleFinder = lastLink.getWorldCenter().sub(gameHero.getCollidable().getBody().getWorldCenter());
		angleFinder.normalize();
		float angle = (float) WorldConstants.getCossinBetweenVectors(angleFinder, new Vec2(-1, 0));

//		newLinkPosition.addLocal(gameHero.getCollidable().getBody().getWorldCenter());
		
		Body body = createNewLinkBody(gameHero.getCollidable().getBody().getWorldCenter().clone(), angle);
		RevoluteJointDef jointDef = ropeLink.getJointDef();
//		body.applyForce(angleFinder.mul(properties.initialSpeed), body.getWorldCenter());
		
		jointDef.lowerAngle = (float) (-Math.PI*2);
		jointDef.upperAngle = (float) (Math.PI*2);
		
		
		jointDef.bodyA = lastLink;
		jointDef.bodyB = body;
 
		Joint joint = world.createJoint(jointDef);
		jointList.add(joint);
		joint.setUserData(ropeLink);

		lastLink = body;

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

		
		
		
		
		JointDef jointDef = ropeLink.getJointDef();
		jointDef.bodyA = lastLink;
		jointDef.bodyB = gameHero.getCollidable().getBody();
		Joint joint = world.createJoint(jointDef);
		joint.setUserData(ropeLink);
		jointList.add(joint);
	}

	
	private Body createNewLinkBody(Vec2 position, float angle)
	{
		Body body = world.createBody(ropeLink.adjustBodyDef(position, angle));
		body.createFixture(ropeLink.getFixtureDef());
		bodyList.add(body);
		return body;
	}

	public void shorten()
	{
		// TODO Auto-generated method stub
		
	}

	public void increase()
	{
		// TODO Auto-generated method stub
		
	}

}
