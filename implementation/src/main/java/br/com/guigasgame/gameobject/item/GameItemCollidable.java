package br.com.guigasgame.gameobject.item;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.shape.Point;

public class GameItemCollidable extends Collidable
{
	private FixtureDef fixtureDef;

	public GameItemCollidable(Vec2 position, GameItemProperties properties)
	{
		super(position);
		
		bodyDef.fixedRotation = false;
		bodyDef.type = BodyType.DYNAMIC;
		
		PolygonShape itemShape = new PolygonShape();
		
		
		final Vector2f sfmlPoint = pointToSfmlVector2(properties.size);
		Vec2 box2dPoint = WorldConstants.sfmlToPhysicsCoordinates(sfmlPoint);
		box2dPoint.mulLocal(0.5f);
		itemShape.setAsBox(box2dPoint.x, box2dPoint.y);
		
		CollidableFilter collidableFilter = CollidableCategory.GAME_ITEMS.getFilter();

		fixtureDef = new FixtureDef();
		fixtureDef.restitution = properties.restitution;
		fixtureDef.shape = itemShape;
		fixtureDef.density = properties.mass;
		fixtureDef.friction = properties.friction;
		fixtureDef.filter = new CollidableFilterBox2dAdapter(collidableFilter).toBox2dFilter();

	}
	
	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		body.createFixture(fixtureDef);
	}
	
	private static Vector2f pointToSfmlVector2(Point point)
	{
		return new Vector2f(point.getX(), point.getY());
	}

	public float getAngleRadians()
	{
		return body.getAngle();
	}
	
}
