package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import br.com.guigasgame.collision.CollidableContactListener;


public class RopeLinkDef implements CollidableContactListener
{
	public static final float CHAIN_SIZE = 0.35f;
	public static final float SQUARED_CHAIN_SIZE = CHAIN_SIZE*CHAIN_SIZE;
	private static final float ANCHOR_POINT = 1.f*CHAIN_SIZE;

	private PolygonShape shape;
	private FixtureDef fixtureDef;
	private BodyDef bodyDef;
	private RevoluteJointDef jointDef;
	private int linksCreated;
	
	public RopeLinkDef(FixtureDef defBase)
	{
		initializeJointDef();
		initializeShape();
		initializeFixtureDef(defBase);
		initializeBodyDef();
	}

	private void initializeBodyDef()
	{
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.fixedRotation = false;
	}

	private void initializeFixtureDef(FixtureDef defBase)
	{
		fixtureDef = defBase;
		fixtureDef.shape = shape;
	}

	private void initializeShape()
	{
		shape = new PolygonShape();
		shape.setAsBox(CHAIN_SIZE, 0.05f);
	}

	private void initializeJointDef()
	{
		jointDef = new RevoluteJointDef();
		jointDef.collideConnected = false;
		jointDef.localAnchorA = new Vec2(ANCHOR_POINT, 0);
		jointDef.localAnchorB = new Vec2(-ANCHOR_POINT, 0);
		
//		jointDef.enableLimit = false;
//		jointDef.lowerAngle = (float) (-Math.PI*2);
//		jointDef.upperAngle = (float) (Math.PI*2);
	}
	
	public RevoluteJointDef getJointDef()
	{
		if (linksCreated > 5)
		{
			jointDef.enableLimit = false;
			jointDef.lowerAngle = 0;
			jointDef.upperAngle = 0;
		}
		return jointDef;
	}

	public FixtureDef getFixtureDef()
	{
		++linksCreated;
		fixtureDef.density += 1f;
		return fixtureDef;
	}

	public BodyDef adjustBodyDef(Vec2 position, float angle)
	{
		bodyDef.angle = angle;
		bodyDef.position = position;
		return bodyDef;
	}
	
//	@Override
//	public void beginContact(Collidable collidable, Object userData)
//	{
////		RevoluteJoint joint = (RevoluteJoint) userData;
////		joint.enableLimit(true);
////		joint.setLimits((float)-Math.PI*4, (float)Math.PI*4);
//	}
//	
//	@Override
//	public void endContact(Collidable collidable, Object userData)
//	{
////		RevoluteJoint joint = (RevoluteJoint) userData;
////		joint.enableLimit(true);
////		joint.setLimits(0, 0);
//	}

}
