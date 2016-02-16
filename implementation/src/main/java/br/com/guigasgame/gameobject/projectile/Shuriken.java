package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableConstants;

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
	protected CollidableFilter createCollidableFilter(CollidableFilter collisionProperty) 
	{
	}

	@Override
	public void onEnter()
	{
		shoot();
		ProjectileAimer aimer = new ProjectileAimer(body, direction, 
					CollisionProperty.projectilesCollideWith.except(CollisionProperty.getPlayerCategory(playerID)), 
					CollisionProperty.playersCategory.except(CollisionProperty.getPlayerCategory(playerID)));
	}

	@Override
	protected ProjectileCollidableFilter createCollidableFilter(ProjectileCollidableFilter collisionProperty) {
		// TODO Auto-generated method stub
		return CollidableConstants.projectileCollisionProperty.except(CollidableConstants.getPlayerCategory(playerID));
		return null;
	}

	
}
