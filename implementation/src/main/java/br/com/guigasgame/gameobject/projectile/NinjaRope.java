//package br.com.guigasgame.gameobject.projectile;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jbox2d.collision.shapes.PolygonShape;
//import org.jbox2d.common.Vec2;
//import org.jbox2d.dynamics.Body;
//import org.jbox2d.dynamics.BodyDef;
//import org.jbox2d.dynamics.BodyType;
//import org.jbox2d.dynamics.FixtureDef;
//import org.jbox2d.dynamics.World;
//import org.jbox2d.dynamics.joints.Joint;
//import org.jbox2d.dynamics.joints.RevoluteJointDef;
//
//import br.com.guigasgame.box2d.debug.WorldConstants;
//import br.com.guigasgame.collision.CollidableConstants;
//import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
//import br.com.guigasgame.gameobject.GameObject;
//import br.com.guigasgame.gameobject.hero.GameHero;
//
//
//public class NinjaRope extends GameObject
//{
//
//
//	private boolean markToShorten;
//	private boolean markToIncrease;
//	private final GameHero gameHero;
//
//	NinjaRope(Vec2 position, GameHero gameHero, ProjectileProperties properties)
//	{
//		this.gameHero = gameHero;
//		collidable = ropeHook;
//		markToIncrease = false;
//		markToShorten = false;
//	}
//
//	@Override
//	public void update(float deltaTime)
//	{
//		if (markToIncrease)
//		{
////			ropeHook.enlarge(deltaTime * distortFactor);
//		}
//		else if (markToShorten)
//		{
////			ropeHook.enshort(deltaTime * distortFactor);
//		}
//
//		markToIncrease = false;
//		markToShorten = false;
//	}
//
//	public void increase()
//	{
//		markToShorten = false;
//		markToIncrease = true;
//	}
//
//	public void shorten()
//	{
//		markToIncrease = false;
//		markToShorten = true;
//	}
//
//	@Override
//	public void onDestroy()
//	{
//	}
//	
//	@Override
//	public void attachToWorld(World world)
//	{
//		super.attachToWorld(world);
//		this.world = world;
//		createJoints(world);
//		bodyList.add(ropeHook.getBody());
//	}
//	
//	private void createJoints(World world)
//	{
//		//create chain
//		
//		Body last_link = ropeHook.getBody();
//		RevoluteJointDef joint_def = new RevoluteJointDef();
//	 
//		final int chainNumber = calculateChainNumber();
//		final Vec2 increasingSizeRope = ropeHook.getPosition().sub(gameHero.getCollidable().getPosition()).mul(1.f/(chainNumber));
//		float angle = (float) WorldConstants.getCossinBetweenVectors(increasingSizeRope, new Vec2(-1, 0));
//
//		System.out.println("Hook: " + last_link.getPosition());
//		System.out.println("Hero: " + gameHero.getCollidable().getPosition());
//		System.out.println("number: " + chainNumber);
//		System.out.println("Angle: " + Math.toDegrees(angle));
//		System.out.println("increasingSizeRope: " + increasingSizeRope);
//
//		for(int i = 0; i <= chainNumber/2; i++)
//		{
//			Body link = createLinkBody(world, ropeHook.getPosition().sub(increasingSizeRope.mul(2*i)), angle, (chainNumber/2)-i);
//	 
//			joint_def.bodyA = last_link;
//			joint_def.bodyB = link;
//	 
//			joint_def.collideConnected = false;
//			joint_def.localAnchorA = new Vec2(chainSize*.95f, 0);
//			joint_def.localAnchorB = new Vec2(-chainSize*.95f, 0);
//	 
//			jointList.add(world.createJoint(joint_def));
//			last_link = link;
//		}
//	 
//		joint_def.bodyA = last_link;
//		joint_def.bodyB = gameHero.getCollidable().getBody();
//	 
//		joint_def.collideConnected = false;
////		joint_def.enableLimit = false;
////		joint_def.lowerAngle = (float) (-1f * Math.PI/4);
////		joint_def.upperAngle = (float) (Math.PI/4);
//	 
//		jointList.add(world.createJoint(joint_def));
//		System.out.println("BodyList: " + bodyList.size());
//	}
//	
//	private int calculateChainNumber()
//	{
//		return (int) (ropeHook.getBody().getPosition().sub(gameHero.getCollidable().getPosition()).length()/chainSize);
//	}
//
//	private Body createLinkBody(World world2, Vec2 position, float angle, float distance)
//	{
//		BodyDef bodyDef = new BodyDef();
//		bodyDef.type = BodyType.DYNAMIC;
//		bodyDef.position = position;
//		bodyDef.angle = angle;
//		
//		System.out.println(position);
//		Body body = world.createBody(bodyDef);
//		bodyList.add(body);
//
//		PolygonShape shape = new PolygonShape();
//		shape.setAsBox(chainSize, 0.05f);
//		FixtureDef fixtureDef = new FixtureDef();
//
//		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableConstants.getRopeBodyCollidableFilter()).toBox2dFilter();
//
//		fixtureDef.density = distance;//100/distance;//10f;//1/(calculateChainNumber()*distance);
//		fixtureDef.restitution = 0f;
//		fixtureDef.friction = 10f;
//		fixtureDef.shape = shape;
//		body.createFixture(fixtureDef);
//		return body;
//	}
//}
