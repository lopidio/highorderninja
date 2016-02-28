package br.com.guigasgame.gameobject.projectile.smokebomb;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;

public class SmokeBombProjectile extends Projectile
{
	private static int NUM_PARTICLES = 50;

	public SmokeBombProjectile(Vec2 direction, Vec2 position)
	{
		super(ProjectileIndex.SMOKE_BOMB_PROJECTILE, direction, position);

		targetMask = CollidableConstants.Category.SCENERY.getMask();
		collidableFilter = CollidableConstants.Filter.SMOKE_BOMB.getFilter();

	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		collidable.getBody().setLinearVelocity(new Vec2());
		System.out.println("activate smoke bomb");
		WorldManifold manifold = new WorldManifold();
		contact.getWorldManifold(manifold);
		Vec2 position = manifold.points[0];
		for (int i = 0; i < NUM_PARTICLES; ++i)
		{
			Vec2 direction = new Vec2();
			direction.x = randomizeValueBetween(-1, 1);
			direction.y = randomizeValueBetween(-0.5f, .5f);
			SmokeBombParticle particle = new SmokeBombParticle(direction, position);
			addChild(particle);
		}
		markToDestroy();
	}
	
	float randomizeValueBetween(float min, float max)
	{
		return (float) ((Math.random()*(max - min)) + min);
	}
	

	@Override
	public void onEnter()
	{
		shoot();
	}


}
