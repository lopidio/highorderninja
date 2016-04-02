package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;

public class NinjaRopeHookCollidable extends Collidable
{
	private boolean bodyIsBuilt;

	public NinjaRopeHookCollidable(Vec2 position)
	{
		super(position);
		bodyDef.type = BodyType.STATIC;
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		System.out.println("Creating hook");
		
		CircleShape shape = new CircleShape();
		shape.setRadius(0.1f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.0f;
		fixtureDef.shape = shape;
		fixtureDef.density = 0f;
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableCategory.ROPE_NODE).toBox2dFilter();
		body.createFixture(fixtureDef);
		bodyIsBuilt = true;
	}
	
	public void removeFromWorld()
	{
		body.getWorld().destroyBody(body);
	}

	public boolean isBodyIsBuilt()
	{
		return bodyIsBuilt;
	}
	
}
