package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilterManipulator;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.state.NinjaRopeSwingingState;


public class NinjaRopeProjectile extends Projectile
{
	private final GameHero gameHero;

	public NinjaRopeProjectile(Vec2 direction, GameHero gameHero)
	{
		super(ProjectileIndex.ROPE, adjustInitialDirection(direction), gameHero.getCollidable().getBody().getWorldCenter());
		this.gameHero = gameHero;
	}

	@Override
	public void beginContact(Collidable other, Contact contact)
	{
		NinjaRope ninjaRope = new NinjaRope(collidable.getPosition(), gameHero, properties);
		gameHero.addAction(new HeroStateSetterAction(new NinjaRopeSwingingState(gameHero, ninjaRope)));

		addChild(ninjaRope);

		markToDestroy();
	}

	@Override
	public void onEnter()
	{
		shoot();
	}

	protected static Vec2 adjustInitialDirection(Vec2 direction)
	{
		if (direction.y == 0)
		{
			direction.addLocal(0, -0.5f);
			direction.normalize();
		}
		else
		{
			direction.y = -Math.abs(direction.y);
		}
		return direction;
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (gameHero.getCollidable().getPosition().sub(collidable.getPosition()).length() >= properties.maxDistance)
		{
			System.out.println("Rope is too far");
			// Rope is too far
			markToDestroy();
		}
	}
	
	@Override
	protected ProjectileCollidableFilter createCollidableFilter()
	{
		// rope doesn't collides with heros
		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableFilterManipulator.createFromCollidableFilter(CollidableConstants.getProjectileCollidableFilter()).removeCollisionWith(CollidableConstants.herosCategory));
		projectileCollidableFilter.aimTo(CollidableConstants.sceneryCategory);
		
		return projectileCollidableFilter;
	}

}
