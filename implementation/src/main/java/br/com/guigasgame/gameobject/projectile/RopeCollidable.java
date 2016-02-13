package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.gameobject.hero.GameHero;


public class RopeCollidable extends Collidable
{

	private GameHero gameHero;
	private DistanceJoint joint;

	public RopeCollidable(Vec2 position, GameHero gameHero)
	{
		super(position);
		
		this.gameHero = gameHero;
		
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.STATIC;
		bodyDef.bullet = true;
		
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		createJoint(world);
	}
	
	private void removeJoint()
	{
		// System.out.println("Remove");
		// if (joint != null)
		// world.destroyJoint(joint);
		// joint = null;
	}

	private void createJoint(World world)
	{
		DistanceJointDef distDef = new DistanceJointDef();

		distDef.bodyA = gameHero.getCollidable().getBody();
		distDef.bodyB = body;
		distDef.collideConnected = false;
		distDef.length = body.getPosition().sub(gameHero.getCollidable().getBody().getPosition()).length();

		System.out.println("Creates");
		joint = (DistanceJoint) world.createJoint(distDef);
	}

	public void enshort()
	{
		if (joint != null)
		{
			joint.setLength(joint.getLength() * 0.995f);
		}
	}


}
