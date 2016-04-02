package br.com.guigasgame.gameobject.projectile.rope;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.math.FloatVariationWatcher;


public class NinjaRopeLinkCollidable extends Collidable
{
	private static float DISTANCE_FROM_ORIGIN = 1.5f;
	
	private final FixtureDef fixtureDef;
	private final PolygonShape shape;
	private final Vec2 hookPoint;
	private final FloatVariationWatcher numberEvolutionWatcher;
	private Vec2 sustainedBodyPosition;
	private Fixture fixture;
	private float size;
	private boolean alignmentInstant;
	private Vec2 center;
	private float angle;

	public NinjaRopeLinkCollidable(Vec2 hookPoint, Vec2 sustainedBodyPosition)
	{
		super(hookPoint.add(sustainedBodyPosition).mul(0.5f));
		
		bodyDef.fixedRotation = false;
		bodyDef.type = BodyType.DYNAMIC;
		
		this.hookPoint = hookPoint;

		recalculateShape(sustainedBodyPosition);
		numberEvolutionWatcher = new FloatVariationWatcher(angle);

		fixtureDef = new FixtureDef();
		fixtureDef.filter = new CollidableFilterBox2dAdapter(CollidableCategory.ROPE_BODY).toBox2dFilter();
		shape = new PolygonShape();
		fixtureDef.shape = shape;
	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		fixture = body.createFixture(fixtureDef);
		System.out.println("Creating link");
		updateShape();
		
	}
	
	public void updateShape()
	{
		shape.setAsBox(0.01f, size * 0.5f, new Vec2(), angle);
//		shape.getVertex(0).x = center.x;
//		shape.getVertex(0).y = center.y;
//		System.out.println("Vcount: " + shape.getVertexCount() +  "; x: " + shape.getVertex(0) );
		fixtureDef.shape = shape;
		body.destroyFixture(fixture);
		fixture = body.createFixture(fixtureDef);
		body.setTransform(center, 0);
		body.setBullet(true);
//		body.setActive(false);
//		body.setActive(true);
//		body.setAwake(true);
//		body.applyLinearImpulse(new Vec2(1.01f, 0.01f), body.getWorldCenter());
	}
	
	private Vec2 calculateDirection(Vec2 destiny)
	{
		return destiny.sub(hookPoint);
	}

	private Vec2 calculateCenter(Vec2 direction)
	{
		final Vec2 destiny = direction.clone();
		destiny.normalize();
		return hookPoint.add(destiny.mul((size+DISTANCE_FROM_ORIGIN)*0.5f));
	}

	private float calculateAngle(Vec2 direction)
	{
		Vec2 dist = direction.mul(size); 
		float catAdj = dist.x;
		if (dist.y >= 0)
			catAdj *= -1;
		
		return (float) Math.asin(catAdj/dist.length());
	}

	

//	public void setStatic()
//	{
//		body.setType(BodyType.STATIC);
//	}
//
//	public void setDynamic()
//	{
//		body.setType(BodyType.DYNAMIC);
//	}
	
	private float calculateSize()
	{
		return sustainedBodyPosition.sub(hookPoint).length();
	}

	public float getAngleRadians()
	{
		return body.getAngle();
	}

	public float getSize()
	{
		return size;
	}

	public void disableSceneryCollision()
	{
		fixture.setFilterData(new CollidableFilterBox2dAdapter(CollidableCategory.ROPE_BODY.getFilter().removeCollisionWith(CollidableCategory.SCENERY)).toBox2dFilter());
	}
//
//	public void enableSceneryCollision()
//	{
//		fixture.setFilterData(new CollidableFilterBox2dAdapter(CollidableCategory.ROPE_BODY).toBox2dFilter());
//	}
	
	public boolean isAlignedWithPrevious()
	{
		return alignmentInstant;
	}
	
	public void removeFromWorld()
	{
		body.getWorld().destroyBody(body);		
	}

	public void recalculateShape(Vec2 sustainedBodyPosition)
	{
		this.sustainedBodyPosition = sustainedBodyPosition;
		this.size = calculateSize() - DISTANCE_FROM_ORIGIN/2;
		Vec2 direction = calculateDirection(sustainedBodyPosition);
		this.center = calculateCenter(direction);
		this.angle = calculateAngle(direction);
		if (numberEvolutionWatcher != null)
		{
			numberEvolutionWatcher.updateValue(angle);
			alignmentInstant = numberEvolutionWatcher.notGreaterAnymoreInstant();// || numberEvolutionWatcher.notSmallerAnymoreInstant();
//			if (numberEvolutionWatcher.notSmallerAnymoreInstant())
//				System.out.println("Smaller!");
		}
	}

}
