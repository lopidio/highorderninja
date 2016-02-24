package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
import br.com.guigasgame.gameobject.projectile.ProjectileProperties;
import br.com.guigasgame.raycast.RayCastClosestFixture;


public class NinjaRope implements CollidableContactListener
{
	private static final float MIN_DISTANCE_BETWEEN_NODES = 0.1f;
	
	private Vector<DistanceJoint> jointVector;
	private List<Body> bodyList;
	
	private boolean canGrow;
	private World world;
	private final ProjectileProperties ropeProperties;
	private Vec2 hookPosition;

	private Body gameHero;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;

	private Stack<Boolean> movingDirectionOfNode;
	private boolean prevAlign;
	private boolean prevBlock;
	private boolean alignCondition;
	private boolean blockCondition;
	
	public NinjaRope(World world, ProjectileProperties ropeProperties, Vec2 hookPosition, Body gameHero)
	{
		this.world = world;
		this.ropeProperties = ropeProperties;
		this.hookPosition = hookPosition;
		this.gameHero = gameHero;
		initializeDefinitions();
		
		bodyList = new ArrayList<>();
		jointVector = new Vector<>();
		movingDirectionOfNode = new Stack<Boolean>();
		createBodyAt(hookPosition);
		createJoint(getLastBody(), gameHero);
	}
	
	private Body getLastBody()
	{
		if (bodyList.size() < 1)
			return null;
		return bodyList.get(bodyList.size() - 1);
	}
	
	
	private Body getPrevLastBody()
	{
		if (bodyList.size() < 2)
			return null;
		return bodyList.get(bodyList.size() - 2);
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
		
		checkJointRemotion();
		checkJointDivision();
		
	}

	private void checkJointDivision()
	{
		Vec2 blockingPoint = jointCollidesWithBlock(getLastBody().getWorldCenter());
		if (blockingPoint != null)
			divideLink(blockingPoint);
	}
	
	private boolean checkJointRemotion()
	{
		if (jointVector.size() > 1)
		{
			System.out.println("-------------------------------------------");
			for (Body body : bodyList) {
				System.out.println("Nodes position: " + body.getWorldCenter());
			}
			System.out.println("From: " + gameHero.getWorldCenter() + " | To: " + getPrevLastBody().getWorldCenter());
			
			boolean isAligned = isAligned(getPrevLastBody().getWorldCenter());
			boolean hasBlock = jointCollidesWithBlock(getPrevLastBody().getWorldCenter()) != null;//hasBlockPoint != null;
			
			System.out.println("isAligned: " + isAligned + 
					". hasBlock: " + hasBlock +
					". correctDirection: " + (isMovingClockwiseDirection() == movingDirectionOfNode.lastElement())
					);
			
			if (prevAlign && !isAligned) //instante de desalinhamento
			{
				this.alignCondition = true;
			}
			
			if (prevBlock && !hasBlock)
			{
				this.blockCondition = true;
			}
			

			if (alignCondition && blockCondition)
			{
				alignCondition = false;
				blockCondition = false;
				if (isMovingClockwiseDirection() != movingDirectionOfNode.lastElement())
				{
					System.out.println("Removing body");
					unifyLastJoints();
					System.out.println("Body count: " + bodyList.size());
				}
				
			}
			
			this.prevBlock = hasBlock;
			this.prevAlign = isAligned;
			
			}
		return false;
	}

	private Vec2 jointCollidesWithBlock(Vec2 position)
	{
		RayCastClosestFixture rayCastClosestFixture = new RayCastClosestFixture(world, 
				gameHero.getWorldCenter(),
				position, 
				CollidableConstants.getRopeBodyCollidableFilter().getCollider());
		rayCastClosestFixture.shoot();
		if (rayCastClosestFixture.getCallBackWrapper() != null)
		{
			
			Vec2 pointOfDivision = rayCastClosestFixture.getCallBackWrapper().point;
			if (pointOfDivision.sub(position).length() > MIN_DISTANCE_BETWEEN_NODES)
			{
				return rayCastClosestFixture.getCallBackWrapper().point;
			}
		}
		return null;
	}

	private boolean isAligned(Vec2 position)
	{
		RayCastClosestFixture closestFixture = new RayCastClosestFixture(world,
				gameHero.getWorldCenter(),
				position, 
				CollidableConstants.ropeNodeCategory.getMask());
		closestFixture.shoot();
		if (closestFixture.getCallBackWrapper() != null)
		{
//			if (closestFixture.getCallBackWrapper().fixture.getBody() == getLastBody())
			{
				System.out.println("Category: "+ Integer.toBinaryString(closestFixture.getCallBackWrapper().fixture.getFilterData().categoryBits) +
						". Mine: " + Integer.toBinaryString(CollidableConstants.ropeNodeCategory.getMask().value) + 
						". Distance: " + closestFixture.getCallBackWrapper().point.sub(position).length());//.point.sub(position).length());
				return closestFixture.getCallBackWrapper().point.sub(position).length() > MIN_DISTANCE_BETWEEN_NODES*3;//ALIGN_TOLLERANCE;
			}
		}
		return false;
	}

	private boolean isMovingClockwiseDirection() 
	{
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
		
		movingDirectionOfNode.pop();//remove(movingDirectionToBeAbleToremove.lastElement());
		DistanceJoint last = jointVector.get(jointVector.size() - 1);
		DistanceJoint prevLast = jointVector.get(jointVector.size() - 2);
		
		world.destroyJoint(last);
		world.destroyJoint(prevLast);
		jointVector.remove(last);
		jointVector.remove(prevLast);

		bodyList.remove(last.getBodyA());
		world.destroyBody(last.getBodyA());


		createJoint(prevLast.getBodyA(), gameHero);
	}
	
	@Override
	public void endContact(Object me, Object other, Contact contact)
	{
		System.out.println("Checkpoint removed: ");
//			markToUnifyJoint = true;
	}

	private DistanceJoint createJoint(Body fromBody, Body toBody)
	{
		DistanceJointDef distJoinDef = new DistanceJointDef();
		distJoinDef.bodyA = fromBody;
		distJoinDef.bodyB = toBody;
		distJoinDef.length = fromBody.getPosition().sub(toBody.getPosition()).length();
		DistanceJoint joint = (DistanceJoint) world.createJoint(distJoinDef);
		jointVector.add(joint);
		return joint;
	}


	public void shorten()
	{
		jointVector.lastElement().setLength((float) (jointVector.lastElement().getLength()*0.95));		
	}
	
	public void increase()
	{
		if (canGrow)
			jointVector.lastElement().setLength((float) (jointVector.lastElement().getLength()*1.05));
	}
	
	private void initializeDefinitions()
	{
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.1f);
		
		fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.density = 2.f;
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getRopeNodeCollidableFilter()).toBox2dFilter();
	}
	
	private Body createBodyAt(Vec2 position)
	{
		movingDirectionOfNode.push(isMovingClockwiseDirection());
		bodyDef.position = position;
		Body body = world.createBody(bodyDef);
		body.setUserData(this);
		body.createFixture(fixtureDef);

		bodyList.add(body);
		System.out.println("------------------------------------------\nCreateNode. Count: " + bodyList.size());
		return body;
	}


	private void divideLink(Vec2 pointOfDivision)
	{
		createBodyAt(pointOfDivision);
		world.destroyJoint(jointVector.lastElement());
		jointVector.remove(jointVector.lastElement());
		createJoint(getPrevLastBody(), getLastBody());
		createJoint(getLastBody(), gameHero);
	}

	public Vec2 getHookPosition()
	{
		return hookPosition;
	}

	
}
