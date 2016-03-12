package br.com.guigasgame.gameobject.projectile.shuriken;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.action.GetHitAction;
import br.com.guigasgame.gameobject.hero.playable.CollidableHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;

public class Shuriken extends Projectile
{
	private int collisionCounter;
	private PlayableGameHero owner;

	public Shuriken(Vec2 direction, IntegerMask targetCategory, PlayableGameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, gameHero.getCollidableHero().getBody().getWorldCenter());
		owner = gameHero;
		collisionCounter = 0;
		targetMask = gameHero.getHeroProperties().getHitEnemiesMask();
		collidableFilter = CollidableCategory.SHURIKEN.getFilter().removeCollisionWith(owner.getHeroProperties().getHitTeamMask());
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
			List<CollidableCategory> categoryList = CollidableCategory.fromMask(otherBody.getFixtureList().getFilterData().categoryBits);
			for( CollidableCategory category : categoryList )
			{
//				System.out.println("Shuriken collided with: " + category.name());
				if (category == CollidableCategory.HEROS)
				{
					CollidableHero collidableHero = (CollidableHero) otherBody.getUserData();
					PlayableGameHero playableGameHero = collidableHero.getPlayableHero();
					playableGameHero.addAction(new GetHitAction(properties.damage));
					markToDestroy();
				}

			}
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
