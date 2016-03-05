package br.com.guigasgame.gameobject.projectile.shuriken;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.GameCollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.RoundGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;

public class Shuriken extends Projectile
{
	private int collisionCounter;
	private RoundGameHero owner;

	public Shuriken(Vec2 direction, IntegerMask targetCategory, RoundGameHero gameHero)
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
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null)
		{
//			CollidableHero hit = (CollidableHero) otherBody.getUserData();
//			hit.getRoundGameHero()
			owner.hitOnTarget();
			List<GameCollidableCategory> categoryList = GameCollidableCategory.fromMask(otherBody.getFixtureList().getFilterData().categoryBits);
			for( GameCollidableCategory category : categoryList )
			{
				System.out.println("Hit: " + category.name());
			}
//			markToDestroy();
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
