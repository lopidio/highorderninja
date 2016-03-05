package br.com.guigasgame.scenery;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.collision.GameCollidableCategory;

public class SceneryShapeCollidable extends Collidable
{

	public SceneryShapeCollidable(Vec2 position)
	{
		super(position);
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.STATIC;
	}

	public void addFixture(Shape shape)
	{
		body.createFixture(shape, 0).
				setFilterData(new CollidableFilterBox2dAdapter(GameCollidableCategory.SCENERY).toBox2dFilter());
	}
	
}
