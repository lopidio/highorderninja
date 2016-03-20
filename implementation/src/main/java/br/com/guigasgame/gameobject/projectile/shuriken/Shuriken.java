package br.com.guigasgame.gameobject.projectile.shuriken;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.action.HitByShurikenAction;
import br.com.guigasgame.gameobject.hero.playable.CollidableHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroFixtureController;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;

public class Shuriken extends Projectile
{
	private int collisionCounter;
	private PlayableGameHero owner;
	private float autoDestructionCounter = 0.3f;
	private boolean beginAutoDestruction;

	public Shuriken(Vec2 direction, IntegerMask targetCategory, PlayableGameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, gameHero.getCollidableHero().getBody().getWorldCenter());
		owner = gameHero;
		collisionCounter = 0;
		targetMask = gameHero.getHeroProperties().getHitEnemiesMask();
		collidableFilter = CollidableCategory.SHURIKEN.getFilter().removeCollisionWith(owner.getHeroProperties().getHitTeamMask());
		setAnimationsColor(gameHero.getHeroProperties().getColor());
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null && !beginAutoDestruction)
		{
//			CollidableHero hit = (CollidableHero) otherBody.getUserData();
//			hit.getRoundGameHero()
			owner.hitOnTarget();
			Fixture fixture = otherBody.getFixtureList();
			List<CollidableCategory> categoryList = CollidableCategory.fromMask(fixture.getFilterData().categoryBits);
			for( CollidableCategory category : categoryList )
			{
//				System.out.println("Shuriken collided with: " + category.name());
				if (category == CollidableCategory.HEROS)
				{
					for (Fixture fixtureIterator = otherBody.getFixtureList(); fixtureIterator != null; fixtureIterator = fixtureIterator.getNext()) 
					{
						HeroFixtureController heroFixtureController = (HeroFixtureController) fixtureIterator.getUserData();
						if (!fixtureIterator.isSensor() && heroFixtureController.isTouching())
						{
							CollidableHero collidableHero = (CollidableHero) otherBody.getUserData();
							PlayableGameHero hitGameHero = collidableHero.getPlayableHero();
							if (hitGameHero.isTouchingGround())
							{
								if (heroFixtureController.getSensorID() == FixtureSensorID.FEET)
									continue;
							}
							System.out.println(heroFixtureController.getSensorID());
							hitGameHero.addAction(new HitByShurikenAction(this, heroFixtureController.getSensorID(), owner));
							return;
						}
					}
				}

			}
		}
		++collisionCounter;
		if (collisionCounter >= properties.numBounces)
		{
			initializeAutoDestruction();
		}
	}
	
	public void initializeAutoDestruction()
	{
		beginAutoDestruction = true;
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (beginAutoDestruction)
		{
			autoDestructionCounter -= deltaTime;
			if (autoDestructionCounter <= 0)
				markToDestroy();
		}
	}
	
	@Override
	public void onEnter()
	{
		shoot();
	}
	
}
