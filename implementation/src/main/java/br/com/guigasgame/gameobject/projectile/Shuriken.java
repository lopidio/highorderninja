package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.gameobject.hero.GameHero;

public class Shuriken extends Projectile
{
	
	private int collisionCounter;
	private GameHero owner;

	public Shuriken(Vec2 direction, Vec2 position, GameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, position);
		owner = gameHero;
		collisionCounter = 0;
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null)
		{
			System.out.println("Hit player!");
			markToDestroy();
		}
		++collisionCounter;
		if (collisionCounter >= properties.numBounces)
		{
			markToDestroy();
		}
	}
	
	@Override
	public void onEnter()
	{
		shoot();
	}

	@Override
	protected ProjectileCollidableFilter createCollidableFilter()
	{
		// shuriken doesn't collides with owner hero
		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableConstants.getShurikenCollidableFilter().removeCollisionWith(CollidableConstants.getPlayerCategory(owner.getPlayerID())));
		projectileCollidableFilter.aimTo(CollidableConstants.getOtherPlayersCategory(owner.getPlayerID()));
		
		return projectileCollidableFilter;
	}

	
}
