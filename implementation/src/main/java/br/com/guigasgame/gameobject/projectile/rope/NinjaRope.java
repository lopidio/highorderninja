package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableContactListener;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.CollidableFilterManipulator;
import br.com.guigasgame.gameobject.projectile.ProjectileProperties;
import br.com.guigasgame.raycast.RayCastClosestFixture;


public class NinjaRope implements CollidableContactListener
{
	private static final float MIN_DISTANCE = 0.1f;
	private static final float ALIGN_TOLLERANCE = .01f;
	
	private Vector<DistanceJoint> jointVector;
	private List<Body> bodyList;
	
	private boolean canGrow;
	private World world;
	private Body lastLinkNode;
	private final ProjectileProperties ropeProperties;
	private boolean markToUnifyJoint;
	private DistanceJoint lastJoint;
	private DistanceJoint prevLastJoint;
	private Vec2 hookPosition;

	private Body gameHero;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;

	boolean movingDirectionToBeAbleToremove;

	
	public NinjaRope(World world, ProjectileProperties ropeProperties, Vec2 hookPosition, Body gameHero)
	{
		this.world = world;
		this.ropeProperties = ropeProperties;
		this.hookPosition = hookPosition;
		this.gameHero = gameHero;
		initializeDefinitions();
		
		bodyList = new ArrayList<>();
		jointVector = new Vector<>();
		lastLinkNode = createBodyAt(hookPosition);
		createJoint(lastLinkNode, gameHero);
		
	}
	
	public void destroy()
	{
		for( Joint joint : jointVector )
		{
			world.destroyJoint(joint);
		}
		for( Body body : bodyList )
		{
			world.destroyBody(body);
		}
	}

	public void update(float deltaTime)
	{
		float attachedSize = calculateRopeSize();
		canGrow = attachedSize <= ropeProperties.maxDistance;
		
//		if(markToUnifyJoint)
//			unifyLastJoints();
//		
		
		checkJointDivision();

		checkJointRemotion();
		
	}

	private Vec2 thereIsLineToLastBody()
	{
		RayCastClosestFixture rayCastClosestFixture = new RayCastClosestFixture(world, 
				gameHero.getWorldCenter(),
				lastLinkNode.getWorldCenter(), 
				CollidableConstants.getRopeBodyCollidableFilter().getCollider());
		
		Vec2 pointOfDivision = rayCastClosestFixture.getPoint();

		if (pointOfDivision != null)
		{
			if (pointOfDivision.sub(lastLinkNode.getPosition()).length() > MIN_DISTANCE)
			{
				return pointOfDivision;
			}
		}	
		return pointOfDivision;
	}
	
	private void checkJointDivision()
	{
		RayCastClosestFixture rayCastClosestFixture = new RayCastClosestFixture(world, 
				gameHero.getWorldCenter(),
				lastLinkNode.getWorldCenter(), 
				CollidableConstants.getRopeBodyCollidableFilter().getCollider());
		
		Vec2 pointOfDivision = rayCastClosestFixture.getPoint();

		if (pointOfDivision != null)
		{
			if (pointOfDivision.sub(lastLinkNode.getPosition()).length() > MIN_DISTANCE)
			{
				divideLink(pointOfDivision);
			}
		}
	}

	private boolean checkJointRemotion()
	{
		if (jointVector.size() > 1)
		{
//			for (Body body : bodyList) {
//				System.out.println("Nodes position: " + body.getPosition());
//			}
			DistanceJoint prevLast = jointVector.get(jointVector.size() - 2);
			
			RayCastClosestFixture closestFixture = new RayCastClosestFixture(world,
					gameHero.getPosition(),
					prevLast.getBodyA().getPosition(), 
					CollidableFilterManipulator.createFromCollidableFilter(CollidableConstants.getRopeNodeCollidableFilter()).removeCollisionWith(CollidableConstants.sceneryCategory).getCollider());

			boolean isAligned = closestFixture.getPoint().sub(prevLast.getBodyB().getPosition()).length() <= ALIGN_TOLLERANCE;
			
			
			
//			System.out.println();// + " | From: " + gameHero.getPosition() + " | To: " + prevLast.getBodyA().getWorldCenter());
			System.out.println("isAligned: " + isAligned + ". CW: " + isMovingClockwiseDirection());
//			System.out.println("Remotion fraction: " + closestFixture.getPoint());
			
			if (isAligned)
			{
				if (isMovingClockwiseDirection() == movingDirectionToBeAbleToremove)
				{
					
//					RayCastClosestFixture rayCastClosestFixture = new RayCastClosestFixture(world, 
//							gameHero.getWorldCenter(),
//							lastLinkNode.getWorldCenter(), 
//							CollidableConstants.getRopeBodyCollidableFilter().getCollider());
//					
//					Vec2 pointOfDivision = rayCastClosestFixture.getPoint();
//
//					if (pointOfDivision != null)
//					{
//						if (pointOfDivision.sub(lastLinkNode.getPosition()).length() > MIN_DISTANCE)
//						{
//							divideLink(pointOfDivision);
//						}
//					}
					
					
					
					System.out.println("Removing body");
					unifyLastJoints();
				}
			}
			else
			{
//				System.out.println("Not aligned");
//					prevLast.getBodyB().setType(BodyType.DYNAMIC);
//					
//				if (secondAlign)
//				{
//					secondAlign = false;
//				}
//				System.out.println("HasHit");
			}
		}
		return false;
	}

	private boolean isMovingClockwiseDirection() {
		/*
		 * 
		Given the points Source, Destination and Centre, where a move has happened from Source to Destination first compute the vectors:		
		CentreSource = Source - Centre
		CentreDestination = Destination - Centre			 
		*/
		
		Vec2 centre = hookPosition;
		Vec2 source = gameHero.getPosition();
		Vec2 destination = gameHero.getPosition().add(gameHero.getLinearVelocity());
		Vec2 centreSource = source.sub(centre);
		Vec2 centreDestination = destination.sub(centre);
		
		//RorL = CentreSourceX * CentreDestinationY - CentreSourceY * CentreDestinationX			
		float angle = centreSource.x * centreDestination.y - centreSource.y * centreDestination.x;
		return angle > 0;
	}


	private float calculateRopeSize()
	{
		float attachedSize = 0 ;
		for( Joint joint : jointVector )
		{
			float jointSize = joint.getBodyA().getWorldCenter().sub(joint.getBodyB().getWorldCenter()).length();
			attachedSize += jointSize;
		}
		return attachedSize;
	}

	private void unifyLastJoints()
	{
		if (jointVector.size() < 2)
			return;
		DistanceJoint last = lastJoint;//jointVector.get(jointVector.size() - 1);
		DistanceJoint prevLast = prevLastJoint;//jointVector.get(jointVector.size() - 2);
		
		world.destroyJoint(last);
		world.destroyJoint(prevLast);
		jointVector.remove(last);
		jointVector.remove(prevLast);

		bodyList.remove(last.getBodyA());
		world.destroyBody(last.getBodyA());


		lastJoint = createJoint(prevLast.getBodyA(), gameHero);
		lastLinkNode = prevLast.getBodyA();
		markToUnifyJoint = false;
	}
	
	@Override
	public void endContact(Object me, Object other, Contact contact)
	{
		System.out.println("Checkpoint removed: ");
			markToUnifyJoint = true;
	}

	private DistanceJoint createJoint(Body fromBody, Body toBody)
	{
		movingDirectionToBeAbleToremove = !isMovingClockwiseDirection();
		DistanceJointDef distJoinDef = new DistanceJointDef();
		distJoinDef.bodyA = fromBody;
		distJoinDef.bodyB = toBody;
		distJoinDef.length = fromBody.getPosition().sub(toBody.getPosition()).length();
		DistanceJoint joint = (DistanceJoint) world.createJoint(distJoinDef);
		jointVector.add(joint);
		
		System.out.println("------------------------------------------\nCreateJoint. Count: " + jointVector.size());
		prevLastJoint = lastJoint;
		lastJoint = joint;
		return joint;
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
	
	private void initializeDefinitions()
	{
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.001f);
		
		fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.density = 2.f;
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getRopeNodeCollidableFilter()).toBox2dFilter();
	}
	
	private Body createBodyAt(Vec2 position)
	{
		bodyDef.position = position;
		Body body = world.createBody(bodyDef);
		body.setUserData(this);
		body.createFixture(fixtureDef);

		bodyList.add(body);
		lastLinkNode = body;
		return body;
	}


	private void divideLink(Vec2 pointOfDivision)
	{
		jointVector.remove(lastJoint);

		Body middleBody = createBodyAt(pointOfDivision);
		lastLinkNode = middleBody;
		
		Body beginBody = lastJoint.getBodyA();
		Body endBody = lastJoint.getBodyB();
		
		world.destroyJoint(lastJoint);
		
		createJoint(beginBody, middleBody);
		createJoint(middleBody, endBody);
	}

	public Vec2 getHookPosition()
	{
		return hookPosition;
	}

	
}
