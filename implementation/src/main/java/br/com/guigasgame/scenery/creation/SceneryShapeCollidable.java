package br.com.guigasgame.scenery.creation;

import java.util.List;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;


public class SceneryShapeCollidable extends Collidable
{

	private float damagePerSecond;

	public SceneryShapeCollidable(Vec2 position, br.com.guigasgame.shape.Shape shape)
	{
		super(position);
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.STATIC;
		this.damagePerSecond = shape.getDamagePerSecond();
	}

	public Fixture addFixture(Shape shape)
	{
		Fixture retorno = body.createFixture(shape, 0);
		retorno.setFilterData(new CollidableFilterBox2dAdapter(CollidableCategory.SCENERY).toBox2dFilter());
		return retorno;
	}

	public float getDamagePerSecond()
	{
		return damagePerSecond;
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		if (damagePerSecond > 0)
		{
			Body otherBody = (Body) other;
			if (otherBody.getUserData() != null)
			{
				Fixture fixture = otherBody.getFixtureList();
				List<CollidableCategory> categoryList = CollidableCategory.fromMask(fixture.getFilterData().categoryBits);
				for( CollidableCategory category : categoryList )
				{
					System.out.println("Scenery collided with: "
							+ category.name());
					// if (category == CollidableCategory.HEROS)
					// {
					// System.out.println(heroFixtureController.getSensorID());
					// }

				}
			}
		}
	}

}
