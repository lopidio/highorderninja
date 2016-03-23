package br.com.guigasgame.gameobject.projectile.smokebomb;

import org.jbox2d.collision.WorldManifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.jsfml.graphics.Color;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.math.Randomizer;

public class SmokeBombProjectile extends Projectile
{
	private static int NUM_PARTICLES = 30;
	private Color color;

	public SmokeBombProjectile(Vec2 direction, Vec2 position, Color color)
	{
		super(ProjectileIndex.SMOKE_BOMB_PROJECTILE, direction, position);

		targetMask = CollidableCategory.SCENERY.getCategoryMask();
		collidableFilter = CollidableCategory.SMOKE_BOMB.getFilter();
		this.color = color;
		setAnimationsColor(color);
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
			direction.x = Randomizer.getRandomFloatInInterval(-0.3f, 0.3f);
			direction.y = Randomizer.getRandomFloatInInterval(-0.5f, 0.5f);
			SmokeBombParticle particle = new SmokeBombParticle(direction, position, color);
			addChild(particle);
		}
		markToDestroy();
	}
	
	@Override
	public void onEnter()
	{
		shoot();
	}


}
