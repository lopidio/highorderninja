package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.GameHero;

public class Shuriken extends Projectile
{
	private int collisionCounter;
	private GameHero owner;

	public Shuriken(Vec2 direction, IntegerMask targetCategory, GameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, gameHero.getCollidableHero().getBody().getWorldCenter());
		owner = gameHero;
		collisionCounter = 0;
		targetMask = gameHero.getHeroProperties().getEnemiesMask();
		collidableFilter = CollidableConstants.Filter.SHURIKEN.getFilter().removeCollisionWith(CollidableConstants.Category.getPlayerCategory(owner.getHeroProperties().getPlayerId()));
		setAnimationsColor(gameHero.getHeroProperties().getColor());
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null)
		{
			CollidableConstants.Category category = CollidableConstants.Category.fromMask(otherBody.getFixtureList().getFilterData().categoryBits);
			System.out.println("Hit: " + category.name());
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
	
}
