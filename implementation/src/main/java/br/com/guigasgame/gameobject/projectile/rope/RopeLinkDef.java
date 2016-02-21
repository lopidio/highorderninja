package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableContactListener;


public class RopeLinkDef implements CollidableContactListener
{
	public static final float CHAIN_SIZE = 0.35f;
	public static final float SQUARED_CHAIN_SIZE = CHAIN_SIZE*CHAIN_SIZE;

	private PolygonShape shape;
	private FixtureDef fixtureDef;
	private BodyDef bodyDef;
	private RevoluteJointDef jointDef;
	
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
		jointDef.localAnchorA = new Vec2(CHAIN_SIZE*.95f, 0);
		jointDef.localAnchorB = new Vec2(-CHAIN_SIZE*.95f, 0);
		
		jointDef.enableLimit = true;
		jointDef.lowerAngle = -(float) (Math.PI);
		jointDef.upperAngle = (float) (Math.PI);
	}
	
	public RevoluteJointDef getJointDef()
	{
		jointDef.lowerAngle /= 1.1f;
		jointDef.upperAngle /= 1.1f;
		return jointDef;
	}

	public FixtureDef getFixtureDef()
	{
		fixtureDef.density += .5f;
		return fixtureDef;
	}

	public BodyDef adjustBodyDef(Vec2 position, float angle)
	{
		bodyDef.angle = angle;
		bodyDef.position = position;
		return bodyDef;
	}
	
	@Override
	public void beginContact(Collidable collidable)
	{
	}
	
	@Override
	public void endContact(Collidable collidable)
	{
		
	}

}
