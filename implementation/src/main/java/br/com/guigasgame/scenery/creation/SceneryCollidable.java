package br.com.guigasgame.scenery.creation;

import java.util.ArrayList;
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
import br.com.guigasgame.gameobject.hero.action.DeadlySceneryDamageAction;
import br.com.guigasgame.gameobject.hero.playable.CollidableHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.updatable.UpdatableFromTime;


public class SceneryCollidable extends Collidable implements UpdatableFromTime
{

	private float damagePerSecond;
	private List<PlayableGameHero> hitHeros;
	private float secondCycle;

	public SceneryCollidable(float damagePerSecond)
	{
		super(new Vec2());
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.STATIC;
		this.damagePerSecond = damagePerSecond;
		hitHeros = new ArrayList<>();
		secondCycle = 0;
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
					if (category == CollidableCategory.HEROS)
					{
						CollidableHero collidableHero = (CollidableHero) otherBody.getUserData();
						PlayableGameHero hitGameHero = collidableHero.getPlayableHero();
						hitHeros.add(hitGameHero);
					}
				}
			}
		}
	}
	
	@Override
	public void endContact(Object me, Object other, Contact contact)
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
					if (category == CollidableCategory.HEROS)
					{
						CollidableHero collidableHero = (CollidableHero) otherBody.getUserData();
						PlayableGameHero hitGameHero = collidableHero.getPlayableHero();
						hitHeros.remove(hitGameHero);
					}
				}
			}
		}
	}

	@Override
	public void update(float deltaTime)
	{
		secondCycle += deltaTime;
		if (secondCycle > 1)
		{
			hitHeros();
			secondCycle -= 1;
		}
	}

	private void hitHeros()
	{
		for( PlayableGameHero hero : hitHeros )
		{
			hero.addAction(new DeadlySceneryDamageAction(damagePerSecond));
		}
	}

}
