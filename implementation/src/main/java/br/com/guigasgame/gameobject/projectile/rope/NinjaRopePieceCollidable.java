package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;


public class NinjaRopePieceCollidable extends Collidable
{
	private final FixtureDef fixtureDef;
	private final PolygonShape shape;
	private final Vec2 hookPoint;
	private Vec2 sustainedBodyPosition;
	private Fixture fixture;
	private float size;
	private Vec2 center;
	private float angle;

	public NinjaRopePieceCollidable(Vec2 hookPoint, Vec2 sustainedBodyPosition)
	{
		super(hookPoint.add(sustainedBodyPosition).mul(0.5f).clone());
		
		bodyDef.fixedRotation = false;
		bodyDef.type = BodyType.STATIC;
		
		this.hookPoint = hookPoint.clone();

		recalculateShape(sustainedBodyPosition.clone());

		fixtureDef = new FixtureDef();
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableCategory.ROPE_BODY.getFilter().removeCollisionWith(CollidableCategory.SCENERY)).toBox2dFilter();
		shape = new PolygonShape();
		fixtureDef.shape = shape;
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		fixture = body.createFixture(fixtureDef);
		updateFixture();
	}
	
	private void updateFixture()
	{
		shape.setAsBox(0.01f, size * 0.5f, new Vec2(), angle);
		fixtureDef.shape = shape;
		body.destroyFixture(fixture);
		fixture = body.createFixture(fixtureDef);
		body.setTransform(center, 0);
	}
	
	private Vec2 calculateDirection(Vec2 destiny)
	{
		return destiny.sub(hookPoint);
	}

	private Vec2 calculateCenter(Vec2 direction)
	{
		final Vec2 destiny = direction.clone();
		destiny.normalize();
		return hookPoint.add(destiny.mul((size)*0.5f));
	}

	private float calculateSize()
	{
		return sustainedBodyPosition.sub(hookPoint).length();
	}

	public float getSize()
	{
		return size;
	}

	private void recalculateShape(Vec2 sustainedBodyPosition)
	{
		this.sustainedBodyPosition = sustainedBodyPosition;
		this.size = calculateSize();
		Vec2 direction = calculateDirection(sustainedBodyPosition);
		this.center = calculateCenter(direction);
		this.angle = WorldConstants.calculateAngleInRadians(direction);
	}
	
	public void updateShape(Vec2 heroPosition)
	{
		recalculateShape(heroPosition.clone());
		updateFixture();
	}

}
