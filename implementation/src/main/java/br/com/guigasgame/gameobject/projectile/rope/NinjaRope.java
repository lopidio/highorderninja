package br.com.guigasgame.gameobject.projectile.rope;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
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
import br.com.guigasgame.gamemachine.GameMachine;
import br.com.guigasgame.gameobject.projectile.ProjectileProperties;
import br.com.guigasgame.raycast.RayCastClosestFixture;


public class NinjaRope implements CollidableContactListener
{
	private static final float MIN_DISTANCE_BETWEEN_NODES = 0.1f;
	
	private Vector<DistanceJoint> jointVector;
	private Vector<Body> ropeBodiesList;
	private List<Body> nodeList;
	
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

	private boolean markToDestroy;
	private boolean alive;

	private float prevDistance;

	private boolean prevIncreasingDistance;
	
	public NinjaRope(World world, ProjectileProperties ropeProperties, Vec2 hookPosition, Body gameHero)
	{
		this.world = world;
		this.ropeProperties = ropeProperties;
		this.hookPosition = hookPosition;
		this.gameHero = gameHero;
		alive = true;
		initializeDefinitions();
		
		nodeList = new ArrayList<>();
		jointVector = new Vector<>();
		ropeBodiesList = new Vector<>();
		movingDirectionOfNode = new Stack<Boolean>();
		createBodyAt(hookPosition);
		createJoint(getLastBody(), gameHero);
		createRopeBodyFromBodies(getLastBody(), gameHero);		
	}
	
	private Body getLastBody()
	{
		if (nodeList.size() < 1)
			return null;
		return nodeList.get(nodeList.size() - 1);
	}
	
	
	private Body getPrevLastBody()
	{
		if (nodeList.size() < 2)
			return null;
		return nodeList.get(nodeList.size() - 2);
	}
	
	public void destroy()
	{
		System.out.println("Rope dies");
		alive = false;
		for( Joint joint : jointVector )
		{
			world.destroyJoint(joint);
		}
		for( Body body : nodeList )
		{
			world.destroyBody(body);
		}
		for( Body body : ropeBodiesList )
		{
			world.destroyBody(body);
		}
	}

	public void update(float deltaTime)
	{
		if (markToDestroy)
		{
			destroy();
			return;
		}
		float attachedSize = calculateRopeSize();
		canGrow = attachedSize <= ropeProperties.maxDistance;
		
		updateLastRopeBody();
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
//			System.out.println("-------------------------------------------");
//			int i = 0;
//			for (Body body : bodyList) {
//				System.out.println("Nodes position: " + body.getWorldCenter() + ". CW: " + movingDirectionOfNode.get(i++));
//			}
//			System.out.println("From: " + gameHero.getWorldCenter() + " | To: " + getPrevLastBody().getWorldCenter());
			
			boolean isAligned = isAligned(getPrevLastBody().getWorldCenter());
			boolean hasBlock = jointCollidesWithBlock(getPrevLastBody().getWorldCenter()) != null;//hasBlockPoint != null;
			
//			System.out.println("isAligned: " + isAligned + 
//					". hasBlock: " + hasBlock +
//					". CW: " + isMovingClockwiseDirection()
//					);
			
			float currentDistance = distanceToLastHook();
			boolean increasingDistance = currentDistance > prevDistance;
			
			if (!increasingDistance && prevIncreasingDistance) //instante de desalinhamento
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
//					System.out.println("Removing body");
					unifyLastJoints();
//					System.out.println("Body count: " + bodyList.size());
				}
				
			}
			
			this.prevBlock = hasBlock;
			this.prevAlign = isAligned;
			this.prevDistance = currentDistance;
			this.prevIncreasingDistance = increasingDistance;
			
			}
		return false;
	}

	private float distanceToLastHook()
	{
		return gameHero.getPosition().sub(nodeList.get(nodeList.size() - 1).getPosition()).lengthSquared();
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
//			System.out.println("Category: "+ Integer.toBinaryString(closestFixture.getCallBackWrapper().fixture.getFilterData().categoryBits) +
//					". Mine: " + Integer.toBinaryString(CollidableConstants.ropeNodeCategory.getMask().value) + 
//					". Distance: " + closestFixture.getCallBackWrapper().point.sub(position).length());
			if (closestFixture.getCallBackWrapper().fixture.getBody() == getLastBody())
			{
				return closestFixture.getCallBackWrapper().point.sub(position).length() > 0.1;//MIN_DISTANCE_BETWEEN_NODES;//ALIGN_TOLLERANCE;
			}
		}
		return false;
	}
	
	
	private float centerRelativeAngle()
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
		return centreSource.x * centreDestination.y - centreSource.y * centreDestination.x;
	}

	private boolean isMovingClockwiseDirection() 
	{
		return centerRelativeAngle() > 0;
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

		nodeList.remove(last.getBodyA());
		world.destroyBody(last.getBodyA());
		
//		world.destroyBody(ropeBodiesList.lastElement());
//		ropeBodiesList.remove(ropeBodiesList.lastElement());

		world.destroyBody(ropeBodiesList.lastElement());
		ropeBodiesList.remove(ropeBodiesList.lastElement());

		createJoint(prevLast.getBodyA(), gameHero);
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


	private void createRopeBodyFromBodies(Body bodyA, Body bodyB)
	{
		//REFACTOR THIS!
		Vec2 dist = bodyA.getPosition().clone().sub(bodyB.getPosition()); 
		Vec2 center = bodyB.getPosition().clone();
		center.addLocal(dist.mul(0.5f));
		
		float catAdj = dist.x;
		if (dist.y >= 0)
			catAdj *= -1;
		
		float angle = (float) Math.asin(catAdj/dist.length());
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position = center;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2f, dist.length()*0.5f, new Vec2(), angle);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.density = 0f;
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getRopeBodyCollidableFilter()).toBox2dFilter();

		
		Body body = world.createBody(bodyDef);
		body.setUserData(this);
		body.createFixture(fixtureDef);

		
		ropeBodiesList.add(body);		
	}

	private void updateLastRopeBody()
	{
//		world.destroyBody(ropeBodiesList.lastElement());
//		ropeBodiesList.remove(ropeBodiesList.lastElement());
		Body bodyA = getLastBody();
		Body bodyB = gameHero;
		
		//REFACTOR THIS!
		Vec2 dist = bodyA.getPosition().clone().sub(bodyB.getPosition()); 
		Vec2 center = bodyB.getPosition().clone();
		center.addLocal(dist.mul(0.5f));
		
		float catAdj = dist.x;
		if (dist.y >= 0)
			catAdj *= -1;
		
		float angle = (float) Math.asin(catAdj/dist.length());
		
//		BodyDef bodyDef = new BodyDef();
//		bodyDef.type = BodyType.STATIC;
//		bodyDef.position = center;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.2f, dist.length()*0.5f, new Vec2(), angle);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.density = 0f;
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getRopeBodyCollidableFilter()).toBox2dFilter();
		ropeBodiesList.lastElement().destroyFixture(ropeBodiesList.lastElement().getFixtureList());
		ropeBodiesList.lastElement().createFixture(fixtureDef);
		ropeBodiesList.lastElement().setTransform(center, 0);
		
		
//		Body body = world.createBody(bodyDef);
//		body.setUserData(this);
//		body.createFixture(fixtureDef);

		
//		ropeBodiesList.add(body);		
	}
	
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		System.out.println("Cut the rope");
		markToDestroy();
	}

	private void markToDestroy()
	{
		markToDestroy = true;
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

		nodeList.add(body);
		System.out.println("------------------------------------------\nCreateNode. Count: " + nodeList.size() + ". CW: " + movingDirectionOfNode.lastElement());
		return body;
	}


	private void divideLink(Vec2 pointOfDivision)
	{
		world.destroyBody(ropeBodiesList.lastElement());
		ropeBodiesList.remove(ropeBodiesList.lastElement());

		createBodyAt(pointOfDivision);
		world.destroyJoint(jointVector.lastElement());
		jointVector.remove(jointVector.lastElement());
		createJoint(getPrevLastBody(), getLastBody());
		createRopeBodyFromBodies(getPrevLastBody(), getLastBody());
		createJoint(getLastBody(), gameHero);
		createRopeBodyFromBodies(getLastBody(), gameHero);
	}

	public Vec2 getHookPosition()
	{
		return hookPosition;
	}

	
	public boolean isAlive()
	{
		return alive;
	}
	
	
}
