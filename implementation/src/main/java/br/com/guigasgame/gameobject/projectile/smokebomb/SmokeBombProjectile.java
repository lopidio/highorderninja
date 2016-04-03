package br.com.guigasgame.gameobject.projectile.smokebomb;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.gameobject.hero.action.HitByProjectileAction;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.projectile.Projectile;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;
import br.com.guigasgame.math.Randomizer;

public class SmokeBombProjectile extends Projectile
{
	private static int NUM_PARTICLES = 30;
	private ColorBlender color;

	public SmokeBombProjectile(Vec2 direction, PlayableGameHero hero)
	{
		super(ProjectileIndex.SMOKE_BOMB_PROJECTILE, direction, hero);

		targetPriorityQueue.add(owner.getHeroProperties().getHitEnemiesMask());
		targetPriorityQueue.add(CollidableCategory.SCENERY.getCategoryMask());
		collidableFilter = CollidableCategory.SMOKE_BOMB_PROJECTILE.getFilter().removeCollisionWith(owner.getHeroProperties().getHitTeamMask());
		this.color = hero.getHeroProperties().getColor();
	}
	
	@Override
	protected void hitHero(PlayableGameHero hitGameHero)
	{
		hitGameHero.addAction(new HitByProjectileAction(this, FixtureSensorID.BODY));
		activate();
	}
	
	@Override
	protected void hitCollidable(CollidableCategory category, Body other)
	{
		activate();
	}
	
	private void activate()
	{
		collidable.getBody().setLinearVelocity(new Vec2());
		for (int i = 0; i < NUM_PARTICLES; ++i)
		{
			Vec2 direction = new Vec2();
			direction.x = Randomizer.getRandomFloatInInterval(-0.3f, 0.3f);
			direction.y = Randomizer.getRandomFloatInInterval(-0.5f, 0.5f);
			SmokeBombParticle particle = new SmokeBombParticle(direction, collidable.getPosition(), color, owner);
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
