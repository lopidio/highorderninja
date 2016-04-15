package br.com.guigasgame.gameobject.projectile.shuriken;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilterBox2dAdapter;
import br.com.guigasgame.gameobject.hero.action.HitByProjectileAction;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroFixtureController;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class Shuriken extends Projectile
{

	// private int collisionCounter;
	private float autoDestructionCounter = 0.3f;
	private boolean beginAutoDestruction;

	public Shuriken(Vec2 direction, PlayableGameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, gameHero);
		// collisionCounter = 0;

		targetPriorityQueue.add(owner.getHeroProperties().getHitEnemiesMask());
		targetPriorityQueue.add(CollidableCategory.GAME_ITEMS.getCategoryMask());
		collidableFilter = CollidableCategory.SHURIKEN.getFilter().removeCollisionWith(owner.getHeroProperties().getHitTeamMask());
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		WorldManifold worldManifold = new WorldManifold();
		contact.getWorldManifold(worldManifold);
//		double angle = WorldConstants.getSmallestAngleBetweenVectorsInRadians(collidable.getBody().getLinearVelocity(), worldManifold.normal) - Math.PI/2;
		double angle = WorldConstants.radiansToDegrees(WorldConstants.calculateAngleInRadians(worldManifold.normal));
		System.out.println(angle);
		super.beginContact(me, other, contact);
		if (Math.abs(angle) > 20)
			initializeAutoDestruction();
		else
			System.out.println("Ricochet");
		// ++collisionCounter;
		// if (collisionCounter >= properties.numBounces)
		// {
		// }
	}

	@Override
	protected void hitHero(PlayableGameHero hitGameHero)
	{
		for( Fixture fixtureIterator = hitGameHero.getCollidableHero().getBody().getFixtureList(); fixtureIterator != null; fixtureIterator = fixtureIterator.getNext() )
		{
			HeroFixtureController heroFixtureController = (HeroFixtureController) fixtureIterator.getUserData();
			if (!fixtureIterator.isSensor()
					&& heroFixtureController.isTouching())
			{
				if (hitGameHero.isTouchingGround())
				{
					if (heroFixtureController.getSensorID() == FixtureSensorID.FEET)
						continue;
				}
				hitGameHero.addAction(new HitByProjectileAction(this, heroFixtureController.getSensorID()));
				markToDestroy();
				return;
			}
		}
		hitGameHero.addAction(new HitByProjectileAction(this, FixtureSensorID.FEET));
		markToDestroy();
	}

	public void initializeAutoDestruction()
	{
		collidable.getBody().getFixtureList().setFilterData(new CollidableFilterBox2dAdapter(collidableFilter.removeCollisionWith(CollidableCategory.HEROES)).toBox2dFilter());
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
