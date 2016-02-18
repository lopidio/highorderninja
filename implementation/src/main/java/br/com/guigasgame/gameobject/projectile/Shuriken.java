package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollidableFilter;

public class Shuriken extends Projectile
{
	
	private int collisionCounter;
	private int playerID;

	public Shuriken(Vec2 direction, Vec2 position, int playerID)
	{
		super(ProjectileIndex.SHURIKEN, direction, position);
		this.playerID = playerID;
		collisionCounter = 0;
	}

	@Override
	public void beginContact(Collidable collidable, Contact contact)
	{
		if (collidable != null)
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
		// rope doesn't collides with heros
		projectileCollidableFilter = new ProjectileCollidableFilter(CollidableFilter.getProjectileCollidableFilter().except(CollidableCategory.getPlayerCategory(playerID)).toFilter());
		projectileCollidableFilter.aimTo(CollidableCategory.getOtherPlayersCategory(playerID));
		
		return projectileCollidableFilter;
	}

	
}
