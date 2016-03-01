package br.com.guigasgame.gameobject.projectile.shuriken;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.GameCollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;

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
		collidableFilter = GameCollidableCategory.SHURIKEN.getFilter().removeCollisionWith(GameCollidableCategory.getPlayerCategory(owner.getHeroProperties().getPlayerId()));
		setAnimationsColor(gameHero.getHeroProperties().getColor());
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body myBody = (Body) me;
		Body otherBody = (Body) other;
		System.out.println("Impact: " + myBody.getLinearVelocity().sub(otherBody.getLinearVelocity()).length()); //times properties.damage??
		if (otherBody.getUserData() != null)
		{
//			GameHero hit = (GameHero) otherBody.getUserData();
			List<GameCollidableCategory> categoryList = GameCollidableCategory.fromMask(otherBody.getFixtureList().getFilterData().categoryBits);
			for( GameCollidableCategory category : categoryList )
			{
				System.out.println("Hit: " + category.name());
			}
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
