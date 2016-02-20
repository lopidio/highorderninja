package br.com.guigasgame.gameobject.projectile;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.CollidableFilterManipulator;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.state.NinjaRopeSwingingState;


public class NinjaRopeProjectile extends Projectile
{
	private static final float TOTAL_MASS = 5f;
	private static final float CHAIN_SIZE = 0.35f;
	private static final float SQUARED_CHAIN_SIZE = CHAIN_SIZE*CHAIN_SIZE;
	private static final float DISTORT_FACTOR = 10.f;

	private final GameHero gameHero;
	private World world;
	private List<Joint> jointList;
	private List<Body> bodyList;
	private Body lastLink;
	private final PolygonShape linkShape;
	private final RevoluteJointDef jointDef;
	private final FixtureDef linkFixtureDef;
	private final BodyDef linkBodyDef;
	private boolean attachedHook;
	private boolean attachedHero;
	private boolean setState;
	private boolean markToAttachHook;
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
		
		size = properties.maxDistance;
		
		jointDef = new RevoluteJointDef();
		jointDef.collideConnected = false;
		jointDef.localAnchorA = new Vec2(CHAIN_SIZE*.95f, 0);
		jointDef.localAnchorB = new Vec2(-CHAIN_SIZE*.95f, 0);
		
		
		linkShape = new PolygonShape();
		linkShape.setAsBox(CHAIN_SIZE, 0.05f);
		linkFixtureDef = new FixtureDef();

		linkFixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getRopeBodyCollidableFilter()).toBox2dFilter();
		linkFixtureDef.restitution = 0f;
		linkFixtureDef.friction = 0f;
		linkFixtureDef.shape = linkShape;
		
		linkBodyDef = new BodyDef();
		linkBodyDef.type = BodyType.DYNAMIC;

		linkFixtureDef.density = 10.f;//distance;//10f;//1/(calculateChainNumber()*distance);

		collidable.setFixtureDef(linkFixtureDef);
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
			direction.addLocal(0, -0.5f);
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
		if (!attachedHook)
		{
			if (ropeIsTooFar())
			{
				
				System.out.println("Rope is too far");
				// Rope is too far
//				markToDestroy();
				attachHero();
			}
			else
			{
				
				float localSize = 0;
				for( Body body : bodyList )
				{
					localSize += CHAIN_SIZE;
				}
				
//				System.out.println("local: " + localSize);
//				System.out.println("Sub: " + gameHero.getCollidable().getPosition().sub(collidable.getPosition()).length());
				if (needsNewLink())
				{
					addNewLink();
				}
			}
		}
		if (markToAttachHook)
		{		
			collidable.getBody().setType(BodyType.STATIC);
			collidable.getBody().setActive(true);// = BodyType.STATIC;?
			if (!attachedHook)
			{
				attachedHook = true;
				attachHero();
			}
		}
		if (!setState && attachedHero && attachedHook)
		{
			setState = true;
			gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, this)));
		}

	}
	
	private boolean ropeIsTooFar()
	{
		return gameHero.getCollidable().getPosition().sub(collidable.getPosition()).lengthSquared() >= properties.maxDistance*properties.maxDistance;
	}

	@Override
	protected ProjectileCollidableFilter createCollidableFilter()
	{
		// rope doesn't collides with heros
		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableFilterManipulator.createFromCollidableFilter(CollidableConstants.getRopeBodyCollidableFilter()).removeCollisionWith(CollidableConstants.herosCategory));
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
	
	private boolean needsNewLink()
	{
//		System.out.println(lastLink.getPosition());
//		System.out.println(gameHero.getCollidable().getPosition());
//		System.out.println(lastLink.getPosition().sub(gameHero.getCollidable().getPosition()));
		return size >= 0 && lastLink.getPosition().sub(gameHero.getCollidable().getPosition()).lengthSquared() >= SQUARED_CHAIN_SIZE*2;
	}
	
	private void addNewLink()
	{
//		System.out.println("Size: " + size);
		Vec2 increasingSizeRope = lastLink.getPosition().sub(gameHero.getCollidable().getPosition());
		increasingSizeRope.normalize();

//		System.out.println("Hook pos: " + collidable.getBody().getWorldCenter());
		
		
		float angle = (float) WorldConstants.getCossinBetweenVectors(increasingSizeRope, new Vec2(-1, 0));
		Vec2 newPosition = gameHero.getCollidable().getBody().getWorldCenter().clone();
		newPosition.addLocal(lastLink.getWorldCenter());
		newPosition.mulLocal(0.5f);

		jointDef.bodyA = lastLink;
		jointDef.bodyB = createNewLinkBody(newPosition, angle);
 
		jointList.add(world.createJoint(jointDef));
		lastLink = jointDef.bodyB;
		size -= CHAIN_SIZE;
//		lastLink.setUserData(t);
	}
	
	private void attachHero()
	{
		if (attachedHero)
			return;
		attachedHero = true;

		float mass = 0;
		for( Body body : bodyList )
		{
			mass += body.getMass();
		}
		System.out.println(collidable.getBody().getType().toString());
		System.out.println("Hero: " + gameHero.getCollidable().getBody().getMass() + " / Rope: " + mass);

//		DistanceJointDef jointDef = new DistanceJointDef();
//		jointDef.localAnchorA = new Vec2(CHAIN_SIZE*.95f, 0);
//		jointDef.localAnchorB = new Vec2(-CHAIN_SIZE*.95f, 0);

//		jointList.add(world.createJoint(jointDef));
//		DistanceJoint joint = (DistanceJoint) world.createJoint(jointDef);
//		joint.setLength(joint.getLength());
//		jointList.add(joint );

		jointDef.bodyA = lastLink;
		jointDef.bodyB = gameHero.getCollidable().getBody();
 
		jointList.add(world.createJoint(jointDef));
	}

	
	private Body createNewLinkBody(Vec2 position, float angle)
	{
		linkBodyDef.angle = angle;
		linkBodyDef.position = position;

		Body body = world.createBody(linkBodyDef);
//		linkFixtureDef.density = 1;//Math.max(10*TOTAL_MASS/size, 1);//5f;//100/distance;//10f;//1/(calculateChainNumber()*distance);
		body.createFixture(linkFixtureDef);
		bodyList.add(body);
//		MassData massData = new MassData();
//		collidable.getBody().getMassData(massData);
//		massData.mass/=bodyList.size();
//		for( Body b : bodyList )
//		{
//			b.setMassData(massData);
//			b.resetMassData();
//		}
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
